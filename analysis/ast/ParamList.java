package oop.ex6.analysis.ast;

import oop.ex6.exceptions.VerifierException;
import oop.ex6.analysis.types.VarTypes;

import java.util.LinkedList;
import java.util.List;

/**
 * Parameters list node for the AST.
 * describes a parameter list such as the one in a method's call
 */
public class ParamList implements Node{
    /** list of all the parameters values */
    private final List<Expression> params;

    /**
     * create a new empty parameters list
     */
    public ParamList() {
        this.params = new LinkedList<>();
    }

    /**
     * get the parameters types as an array
     * @param analyzer analyzer object to use
     * @return the parameters types as an array
     * @throws VerifierException if an expression (symbol) is not defined
     */
    VarTypes[] getParamsTypes(Analyzer analyzer) throws VerifierException {
        LinkedList<VarTypes> types = new LinkedList<>();
        for (Expression exp : this.params) {
            types.add(exp.getType(analyzer));
        }
        return types.toArray(new VarTypes[0]);
    }

    /**
     * add an expression to the parameters list
     * @param expression expression to add
     */
    @Override
    public void addExpression(Expression expression) {
        this.params.add(expression);
    }

// avoid recursion failure
//    @Override
//    public void addMethodCall(MethodCall methodCall) throws UnsupportedASTOperation {
//        this.params.add(methodCall);
//    }
}
