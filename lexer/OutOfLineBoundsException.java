package oop.ex6.lexer;

import oop.ex6.exceptions.VerifierException;

/**
 * an exception thrown when a line unexpectedly ends
 */
public class OutOfLineBoundsException extends VerifierException {
    /** default exception message */
    private static final String DEFAULT_MSG = "Unexpected oop.ex6.lexer line end.";

    /**
     * default constructor for the exception
     */
    public OutOfLineBoundsException() {
        super(DEFAULT_MSG);
    }

    /**
     * constructor for the exception
     * @param message error message for the exception
     */
    public OutOfLineBoundsException(String message) {
        super(message);
    }
}
