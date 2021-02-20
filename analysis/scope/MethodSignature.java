package oop.ex6.analysis.scope;

import oop.ex6.analysis.types.VarTypes;

/**
 * class representing a method's signature
 */
public class MethodSignature {
    /** the method's return type */
    private final VarTypes returnType;
    /** the method's name */
    private final String name;
    /** the method's parameter list types */
    private final VarTypes[] paramList;

    /**
     * create a new method signature
     * @param returnType the method's return type
     * @param name the method's name
     * @param paramList the method's parameter list types
     */
    public MethodSignature(VarTypes returnType, String name, VarTypes[] paramList) {
        this.returnType = returnType;
        this.name = name;
        this.paramList = paramList;
    }

    /**
     * @return the method's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the method's parameter list types
     */
    public VarTypes[] getParamList() {
        return paramList;
    }

    /**
     * @return the method's return type
     */
    public VarTypes getReturnType() {
        return returnType;
    }
}
