package oop.ex6.analysis.ast;

import oop.ex6.analysis.scope.UnInitializedSymbol;
import oop.ex6.analysis.types.InvalidTypeException;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.analysis.types.VarTypes;

import java.util.LinkedList;
import java.util.List;

/**
 * boolean expression node for the AST.
 * describes a boolean expression consisting of several sub-expressions
 */
public class BooleanExpression implements Expression, Node, Analyzable {
    /** list of sub-expression which consist this expression */
    List<Expression> subExpressions;

    /**
     * create a new "empty" boolean expression
     */
    public BooleanExpression() {
        this.subExpressions = new LinkedList<>();
    }

    /**
     * run the passed analyzer on this Node
     * @param analyzer analyzer object to use
     * @throws VerifierException if the program isn't valid
     */
    @Override
    public void analyze(Analyzer analyzer) throws VerifierException {
        for (Expression exp : this.subExpressions) {
            if (!exp.isInit(analyzer)) {
                throw new UnInitializedSymbol(exp.toString());
            }
            if (!VarTypes.BOOLEAN.accepts(exp.getType(analyzer))) {
                throw new InvalidTypeException(exp.getType(analyzer).getName());
            }
        }
    }

    /**
     * get the expression data type
     * @param analyzer analyzer object to use
     * @return the expression's data type
     */
    @Override
    public VarTypes getType(Analyzer analyzer) {
        return VarTypes.BOOLEAN;
    }

    /**
     * add a sub expression to the expressions list
     * @param expression the expression to add
     */
    @Override
    public void addExpression(Expression expression) {
        this.subExpressions.add(expression);
    }

    /**
     * add a method call as a sub-expression to the expressions list
     * @param methodCall the method call to add
     */
    @Override
    public void addMethodCall(MethodCall methodCall) throws UnsupportedASTOperation {
        this.subExpressions.add(methodCall);
    }
}
