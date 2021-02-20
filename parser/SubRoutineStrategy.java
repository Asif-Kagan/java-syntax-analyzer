package oop.ex6.parser;

import oop.ex6.analysis.ast.Block;
import oop.ex6.analysis.ast.Node;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.line.InvalidLineGrammarException;
import oop.ex6.lexer.line.Line;
import oop.ex6.lexer.line.LineTypes;
import oop.ex6.lexer.token.TokenTypes;

import java.util.StringJoiner;

/**
 * Parsing strategy for a sub-routine implementation
 */
public class SubRoutineStrategy extends ParseStrategy {
    /** missing return statement exception message */
    private static final String MISSING_RETURN_MSG = "missing return statement at line %d";
    /** missing return statement exception message */
    private static final String MISSING_RETURN_MSG_LAST_LINE = "missing return statement at EOF";

    /** singleton instance for this strategy */
    private static final SubRoutineStrategy instance = new SubRoutineStrategy();

    /** private default constructor */
    private SubRoutineStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static SubRoutineStrategy instance() {
        return instance;
    }

    /**
     * parse using the current oop.ex6.parser for variable assignment
     * @param parent the parent Node in the AST
     * @throws VerifierException upon any illegal state
     * @throws VerifierIOException upon any IO exception thrown while parsing
     */
    @Override
    public void parse(Node parent) throws VerifierException, VerifierIOException {
        Block block = new Block();
        parent.addBlock(block);

        Line line;
        while ((line = this.parser.nextLine()) != null) {
            if (this.parser.consumeIf(TokenTypes.RETURN) &&
                    this.parser.peekNextLine(LineTypes.CLOSE_BLOCK)) {
                this.parser.nextLine();
                return;
            }
            if (this.parser.peek(TokenTypes.CLOSING_BLOCK)) {
                throw new InvalidLineGrammarException(String.format(MISSING_RETURN_MSG, line.getLineNumber()));
            }

            this.parser.use(line.getParseStrategy()).parse(block);
        }

        throw new InvalidLineGrammarException(MISSING_RETURN_MSG_LAST_LINE);
    }
}