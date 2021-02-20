package oop.ex6.parser;

import oop.ex6.analysis.ast.Node;
import oop.ex6.analysis.ast.Return;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.lexer.token.TokenTypes;

/**
 * Parsing strategy for a return statement
 */
public class ReturnStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final ReturnStrategy instance = new ReturnStrategy();

    /** private default constructor */
    private ReturnStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static ReturnStrategy instance() {
        return instance;
    }

    /**
     * parse using the current oop.ex6.parser for variable assignment
     * @param parent the parent Node in the AST
     * @throws VerifierException upon any illegal state
     */
    @Override
    public void parse(Node parent) throws VerifierException {
        this.parser.consume(TokenTypes.RETURN);
        this.parser.consume(TokenTypes.END);
        parent.addReturn(new Return());
    }

    /**
     * @return true if this strategy should be used as the next parsing strategy, false otherwise
     */
    @Override
    public boolean isNext() {
        return this.parser.peek(TokenTypes.RETURN);
    }
}
