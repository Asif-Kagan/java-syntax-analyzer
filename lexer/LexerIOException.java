package oop.ex6.lexer;

import oop.ex6.exceptions.VerifierIOException;

/**
 * an exception thrown when the oop.ex6.lexer throws any IOException
 */
public class LexerIOException extends VerifierIOException {
    /** default exception message */
    private static final String DEFAULT_MSG = "Tokenizer IO error";

    /**
     * default constructor for the exception
     */
    public LexerIOException() {
        super(DEFAULT_MSG);
    }

    /**
     * constructor for the exception
     * @param message error message for the exception
     */
    public LexerIOException(String message) {
        super(message);
    }
}
