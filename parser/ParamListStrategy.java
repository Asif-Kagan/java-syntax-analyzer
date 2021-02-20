package oop.ex6.parser;

import oop.ex6.analysis.ast.Node;
import oop.ex6.analysis.ast.ParamList;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.line.GrammarGroups;
import oop.ex6.lexer.token.TokenTypes;

/**
 * Parsing strategy for parameters lists
 */
public class ParamListStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final ParamListStrategy instance = new ParamListStrategy();

    /** private default constructor */
    private ParamListStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static ParamListStrategy instance() {
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
        ParamList paramList = new ParamList();
        parent.addParamList(paramList);

        while (parser.peek(GrammarGroups.EXPRESSION)) {
            this.parser.use(ExpressionStrategy.instance()).parse(paramList);

            if (!parser.consumeIf(TokenTypes.COMA)) {
                break;
            }
        }
    }
}
