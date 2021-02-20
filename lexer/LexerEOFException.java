package oop.ex6.lexer;

/**
 * an exception thrown when the oop.ex6.lexer can't get the next oop.ex6.lexer.line due to EOF.
 */
public class LexerEOFException extends LexerIOException {
    /** default exception message */
    private static final String DEFAULT_MSG = "unexpected EOF";

    /**
     * default constructor for the exception
     */
    public LexerEOFException() {
        super(DEFAULT_MSG);
    }

    /**
     * constructor for the exception
     * @param message error message for the exception
     */
    public LexerEOFException(String message) {
        super(message);
    }
}
