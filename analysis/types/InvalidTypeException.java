package oop.ex6.analysis.types;

import oop.ex6.exceptions.VerifierException;

/**
 * exception being thrown as result of type errors in the parsed code
 */
public class InvalidTypeException extends VerifierException {
    /** default exception message */
    private static final String DEFAULT_MSG = "Invalid type %s.";

    /**
     * create the exception for the given type error
     * @param type the invalid type
     */
    public InvalidTypeException(String type) {
        super(String.format(DEFAULT_MSG, type));
    }
}
