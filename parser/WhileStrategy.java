package oop.ex6.parser;

import oop.ex6.analysis.ast.Block;
import oop.ex6.analysis.ast.Node;
import oop.ex6.analysis.ast.While;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.line.LineTypes;
import oop.ex6.lexer.token.TokenTypes;

/**
 * Parsing strategy for a while statement
 */
public class WhileStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final WhileStrategy instance = new WhileStrategy();

    /** private default constructor */
    private WhileStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static WhileStrategy instance() {
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
        this.parser.consume(TokenTypes.WHILE);
        While whileStatement = new While();
        parent.addStatement(whileStatement);

        this.parser.consume(TokenTypes.OPENING_PARENTHESIS);
        this.parser.use(BooleanExpressionStrategy.instance()).parse(whileStatement);
        this.parser.consume(TokenTypes.CLOSING_PARENTHESIS);

        this.parser.consume(TokenTypes.OPENING_BLOCK);
        Block block = new Block();
        whileStatement.addBlock(block);
        while (!this.parser.peekNextLine(LineTypes.CLOSE_BLOCK)) {
            this.parser.use(this.parser.nextLine().getParseStrategy()).parse(block);
        }
        this.parser.nextLine();
        this.parser.consume(TokenTypes.CLOSING_BLOCK);
    }

    /**
     * @return true if this strategy should be used as the next parsing strategy, false otherwise
     */
    @Override
    public boolean isNext() {
        return this.parser.peek(TokenTypes.WHILE);
    }
}

