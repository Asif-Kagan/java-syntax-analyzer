package oop.ex6.analysis.types;

import oop.ex6.lexer.token.TokenTypes;

import java.util.Arrays;
import java.util.HashSet;

/**
 * enum of all possible variable types (and return types),
 * listing for each type it's literal, and which variable types could be accepted by it.
 */
public enum VarTypes {
    VOID(TokenTypes.VOID, TokenTypes.VOID),
    NULL(TokenTypes.NULL, TokenTypes.NULL),
    INT(TokenTypes.INT, TokenTypes.CONSTANT_INT),
    DOUBLE(TokenTypes.DOUBLE, TokenTypes.CONSTANT_DOUBLE, VarTypes.INT),
    BOOLEAN(TokenTypes.BOOLEAN, TokenTypes.CONSTANT_BOOLEAN, VarTypes.DOUBLE, VarTypes.INT),
    STRING(TokenTypes.STRING, TokenTypes.CONSTANT_STRING, VarTypes.NULL),
    CHAR(TokenTypes.CHAR, TokenTypes.CONSTANT_CHAR);

    /**
     * get the variable type of a tokenType
     * @param tokenType the tokenType to get it's variable type
     * @return variable type for this tokenType
     * @throws InvalidTypeException if tokenType isn't of any variable type
     */
    public static VarTypes getType(TokenTypes tokenType) throws InvalidTypeException {
        for (VarTypes varType : VarTypes.values()) {
            if (varType.name == tokenType || varType.constant == tokenType) {
                return varType;
            }
        }
        throw new InvalidTypeException(tokenType.getRawRegex());
    }

    /** the token for the name of the variable */
    private final TokenTypes name;
    /** the constant value of this type */
    private final TokenTypes constant;
    /** all varTypes that are excepted by this type */
    private final HashSet<VarTypes> accepts;

    /**
     * create a new variable type
     * @param name the token for the name of the variable
     * @param constant the constant value of this type
     * @param accepts all varTypes that are excepted by this type
     */
    VarTypes(TokenTypes name, TokenTypes constant, VarTypes... accepts) {
        this.name = name;
        this.constant = constant;
        this.accepts = new HashSet<>(Arrays.asList(accepts));
    }

    /**
     * @return the type's name
     */
    public String getName() {
        return this.name.getRawRegex();
    }

    /**
     * check if the current type can accept the passed type
     * @param varType the type to check
     * @return true if this type can accept varType, false otherwise
     */
    public boolean accepts(VarTypes varType) {
        return this == varType || this.accepts.contains(varType);
    }
}
