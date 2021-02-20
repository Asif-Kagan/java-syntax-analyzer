package oop.ex6.analysis.scope;

import oop.ex6.analysis.types.VarTypes;
import oop.ex6.exceptions.VerifierException;

import java.util.StringJoiner;

/**
 * thrown when a method is looked for but does not exists
 */
public class InvalidMethodException extends VerifierException {
    /** default error message */
    private static final String DEFAULT_MSG = "Method %s doesn't exists";

    /**
     * new exception with the default message
     * @param method the method's name
     */
    public InvalidMethodException(String method) {
        super(String.format(DEFAULT_MSG, method));
    }
}


