package oop.ex6.parser;


import oop.ex6.analysis.ast.Node;
import oop.ex6.analysis.ast.ParamDeclarationList;
import oop.ex6.analysis.ast.VarDeclaration;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.lexer.line.GrammarGroups;
import oop.ex6.analysis.types.VarTypes;
import oop.ex6.lexer.token.Token;
import oop.ex6.lexer.token.TokenTypes;

/**
 * Parsing strategy for parameters declarations lists
 */
public class ParamDeclarationListStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final ParamDeclarationListStrategy instance = new ParamDeclarationListStrategy();

    /** private default constructor */
    private ParamDeclarationListStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static ParamDeclarationListStrategy instance() {
        return instance;
    }

    /**
     * parse using the current oop.ex6.parser for variable assignment
     * @param parent the parent Node in the AST
     * @throws VerifierException upon any illegal state
     */
    @Override
    public void parse(Node parent) throws VerifierException {
        ParamDeclarationList paramList = new ParamDeclarationList();
        parent.addParamDeclarationList(paramList);

        while (this.parser.peek(TokenTypes.FINAL) || this.parser.peek(GrammarGroups.TYPE)) {
            boolean isFinal = this.parser.consumeIf(TokenTypes.FINAL);
            Token typeToken = this.parser.nextToken(GrammarGroups.TYPE);
            VarTypes type = VarTypes.getType(typeToken.getType());
            String name = this.parser.consume(GrammarGroups.IDENTIFIER);
            paramList.addVarDeclaration(new VarDeclaration(type, name, isFinal));

            if (!this.parser.consumeIf(TokenTypes.COMA)) {
                break;
            }
        }
    }
}
