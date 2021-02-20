package oop.ex6.parser;

import oop.ex6.analysis.ast.Block;
import oop.ex6.analysis.ast.If;
import oop.ex6.analysis.ast.Node;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.line.LineTypes;
import oop.ex6.lexer.token.TokenTypes;

/**
 * Parsing strategy for an if statement
 */
public class IfStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final IfStrategy instance = new IfStrategy();

    /** private default constructor */
    private IfStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static IfStrategy instance() {
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
        this.parser.consume(TokenTypes.IF);
        If ifStatement = new If();
        parent.addStatement(ifStatement);

        this.parser.consume(TokenTypes.OPENING_PARENTHESIS);

        this.parser.use(BooleanExpressionStrategy.instance()).parse(ifStatement);
        this.parser.consume(TokenTypes.CLOSING_PARENTHESIS);

        this.parser.consume(TokenTypes.OPENING_BLOCK);

        Block block = new Block();
        ifStatement.addBlock(block);
        while (!this.parser.peekNextLine(LineTypes.CLOSE_BLOCK)) {
            ParseStrategy strategy = this.parser.nextLine().getParseStrategy();
            this.parser.use(strategy).parse(block);
        }
        this.parser.nextLine();

        this.parser.consume(TokenTypes.CLOSING_BLOCK);
    }

    /**
     * @return true if this strategy should be used as the next parsing strategy, false otherwise
     */
    @Override
    public boolean isNext() {
        return this.parser.peek(TokenTypes.IF);
    }

}
