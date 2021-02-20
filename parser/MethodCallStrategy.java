package oop.ex6.parser;

import oop.ex6.analysis.ast.MethodCall;
import oop.ex6.analysis.ast.Node;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.token.TokenTypes;

/**
 * Parsing strategy for a method call
 */
public class MethodCallStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final MethodCallStrategy instance = new MethodCallStrategy();

    /** private default constructor */
    private MethodCallStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static MethodCallStrategy instance() {
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
        String callee = this.parser.consume(TokenTypes.IDENTIFIER);
        MethodCall methodCall = new MethodCall(callee);
        parent.addMethodCall(methodCall);
        this.parser.consume(TokenTypes.OPENING_PARENTHESIS);
        this.parser.use(ParamListStrategy.instance()).parse(methodCall);
        this.parser.consume(TokenTypes.CLOSING_PARENTHESIS);
    }

    /**
     * @return true if this strategy should be used as the next parsing strategy, false otherwise
     */
    @Override
    public boolean isNext() {
        return (this.parser.peekNext(TokenTypes.OPENING_PARENTHESIS));
    }
}
