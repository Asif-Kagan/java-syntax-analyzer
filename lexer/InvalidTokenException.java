package oop.ex6.lexer;

import oop.ex6.exceptions.VerifierException;
import oop.ex6.lexer.token.Token;

/**
 * an exception thrown when an invalid token type is encountered
 */
public class InvalidTokenException extends VerifierException {
    /** default exception message */
    private static final String DEFAULT_MSG = "Invalid token `%s` in line %d.";

    /**
     * default constructor for the exception
     * @param token the token that triggered the exception
     */
    public InvalidTokenException(Token token) {
        super(String.format(DEFAULT_MSG, token.getValue(), token.getLineNumber()));
    }

    /**
     * constructor for the exception
     * @param token the invalid token value
     * @param lineNumber the line number of the invalid token
     */
    public InvalidTokenException(String token, int lineNumber) {
        super(String.format(DEFAULT_MSG, token, lineNumber));
    }
}
