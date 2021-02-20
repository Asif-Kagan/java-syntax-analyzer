package oop.ex6.parser;

import oop.ex6.analysis.ast.BooleanExpression;
import oop.ex6.analysis.ast.Node;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.line.GrammarGroups;

/**
 * Parsing strategy for a boolean expression
 */
public class BooleanExpressionStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final BooleanExpressionStrategy instance = new BooleanExpressionStrategy();

    /**
     * @return singleton instance for this strategy
     */
    public static BooleanExpressionStrategy instance() {
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
        BooleanExpression booleanExpression = new BooleanExpression();
        parent.addBooleanExpression(booleanExpression);

        while (this.parser.peek(GrammarGroups.IDENTIFIER) || this.parser.peek(GrammarGroups.CONSTANTS)) {
            this.parser.use(ExpressionStrategy.instance()).parse(booleanExpression);
            if (!this.parser.consumeIf(GrammarGroups.BOOLEAN_OPERATOR)) {
                break;
            }
        }
    }
}
