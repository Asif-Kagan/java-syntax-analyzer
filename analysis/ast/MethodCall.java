package oop.ex6.analysis.ast;

import oop.ex6.analysis.types.InvalidTypeException;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.analysis.scope.InvalidMethodException;
import oop.ex6.analysis.types.VarTypes;

import java.util.StringJoiner;

/**
 * Method call node for the AST.
 * describes a method call
 */
public class MethodCall implements Node, Expression, Statement {
    /** the called function name */
    String callee;
    /** param list object containing all parameters */
    ParamList paramList;

    /**
     * create a new function call
     * @param callee the function name
     */
    public MethodCall(String callee) {
        this.callee = callee;
    }

    /**
     * get the method call return type
     * @param analyzer analyzer object to use
     * @return the method call return type
     * @throws InvalidMethodException if the method was not declared
     * @throws InvalidTypeException if the method's return type is void
     */
    @Override
    public VarTypes getType(Analyzer analyzer) throws InvalidMethodException, InvalidTypeException {
        VarTypes returnType = analyzer.getSignature(callee).getReturnType();
        if (returnType == VarTypes.VOID) {
            throw new InvalidTypeException(VarTypes.VOID.getName());
        }
        return returnType;
    }

    /**
     * add a parameters list to this method call
     * @param paramList parameter list to add
     */
    @Override
    public void addParamList(ParamList paramList) {
        this.paramList = paramList;
    }

    /**
     * run the passed analyzer on this Node
     * @param analyzer analyzer object to use
     * @throws VerifierException if the program isn't valid
     */
    @Override
    public void analyze(Analyzer analyzer) throws VerifierException {
        VarTypes[] paramTypes = this.paramList.getParamsTypes(analyzer);
        if (!analyzer.has(this.callee, paramTypes)) {
            StringJoiner joiner = new StringJoiner(", ");
            for (VarTypes type : paramTypes) {
                joiner.add(type.getName());
            }
            String signature = String.format("%s(%s)", this.callee, joiner.toString());
            throw new InvalidMethodException(signature);
        }
    }
}
