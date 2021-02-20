package oop.ex6.exceptions;

import java.io.IOException;

/**
 * general error type thrown by the sjava verifier when an IOException is encountered
 */
public class VerifierIOException extends IOException {
    /** default exception message */
    private static final String DEFAULT_MSG = "Verifier IO exception";

    /**
     * default constructor for the exception
     */
    public VerifierIOException() {
        super(DEFAULT_MSG);
    }

    /**
     * constructor for the exception
     * @param message error message for the exception
     */
    public VerifierIOException(String message) {
        super(message);
    }
}
