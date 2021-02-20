package oop.ex6.parser;

import oop.ex6.analysis.ast.Method;
import oop.ex6.analysis.ast.Node;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.line.GrammarGroups;
import oop.ex6.lexer.token.TokenTypes;
import oop.ex6.analysis.types.VarTypes;

/**
 * Parsing strategy for a method declaration and implementation
 */
public class MethodStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final MethodStrategy instance = new MethodStrategy();

    /** private default constructor */
    private MethodStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static MethodStrategy instance() {
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
        TokenTypes returnType = this.parser.nextToken(GrammarGroups.RETURN_TYPES).getType();
        String funcName = this.parser.consume(GrammarGroups.IDENTIFIER);

        Method method = new Method(VarTypes.getType(returnType) , funcName);
        parent.addMethod(method);

        this.parser.consume(TokenTypes.OPENING_PARENTHESIS);
        this.parser.use(ParamDeclarationListStrategy.instance()).parse(method);
        this.parser.consume(TokenTypes.CLOSING_PARENTHESIS);

        this.parser.consume(TokenTypes.OPENING_BLOCK);
        this.parser.use(SubRoutineStrategy.instance()).parse(method);
        this.parser.consume(TokenTypes.CLOSING_BLOCK);
    }

    /**
     * @return true if this strategy should be used as the next parsing strategy, false otherwise
     */
    @Override
    public boolean isNext() {
        return this.parser.peekNext(TokenTypes.OPENING_PARENTHESIS);
    }
}
