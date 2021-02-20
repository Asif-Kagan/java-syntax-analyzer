package oop.ex6.analysis.ast;

import oop.ex6.analysis.types.VarTypes;

/**
 * constant expression node for the AST.
 * describes a constant expression
 */
public class Constant implements Expression {
    /** the constant's data type */
    private final VarTypes type;
    /** the constant's string value */
    private final String value;

    /**
     * create a new constant
     * @param value the value of the constant
     * @param type the data type of the constant
     */
    public Constant(String value, VarTypes type) {
        this.type = type;
        this.value = value;
    }

    /**
     * get the constant's data type
     * @param analyzer analyzer object to use
     * @return the expression's data type
     */
    @Override
    public VarTypes getType(Analyzer analyzer) {
        return this.type;
    }
}
