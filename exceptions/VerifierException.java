package oop.ex6.exceptions;

/**
 * general error type thrown by the sjava verifier
 */
public class VerifierException extends Exception {
    /** default exception message */
    private static final String DEFAULT_MSG = "Verifier exception";

    /**
     * default constructor for the exception
     */
    public VerifierException() {
        super(DEFAULT_MSG);
    }

    /**
     * constructor for the exception
     * @param message error message for the exception
     */
    public VerifierException(String message) {
        super(message);
    }
}
