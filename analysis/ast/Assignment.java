package oop.ex6.analysis.ast;

import oop.ex6.analysis.scope.UnInitializedSymbol;
import oop.ex6.exceptions.VerifierException;

/**
 * assignment node for the AST.
 * describes an assignment operation between a symbol and some expression
 */
public class Assignment implements Statement, Node {
    /** the expression symbol name */
    private static final String EXP_SYMBOL_NAME = "expression";

    /** the left hand symbol to be assigned to */
    private final String leftHand;
    /** expression to assign to the left hand */
    private Expression expression;

    /**
     * create a new assignment
     * @param name the left hand symbol name
     */
    public Assignment(String name) {
        this.leftHand = name;
    }

    /**
     * run the passed analyzer on this Node
     * @param analyzer analyzer object to use
     * @throws VerifierException if the program isn't valid
     */
    @Override
    public void analyze(Analyzer analyzer) throws VerifierException {
        if (!this.expression.isInit(analyzer)) {
            throw new UnInitializedSymbol(EXP_SYMBOL_NAME);
        }
        analyzer.update(this.leftHand, this.expression.getType(analyzer));
    }

    /**
     * add an expression
     * @param expression expression to add
     */
    @Override
    public void addExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * add a method call as a possible expression
     * @param methodCall method call to add
     */
    @Override
    public void addMethodCall(MethodCall methodCall) {
        this.expression = methodCall;
    }
}
