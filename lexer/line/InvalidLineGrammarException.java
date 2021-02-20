package oop.ex6.lexer.line;

import oop.ex6.exceptions.VerifierException;

/**
 * an exception thrown when an invalid grammar is encountered for an entire line
 */
public class InvalidLineGrammarException extends VerifierException {
    /** default exception message */
    private static final String DEFAULT_MSG = "Invalid line grammar at line %d.";

    /**
     * constructor for an error message for a specific line
     * @param lineNumber the number of the line that triggered the issue
     */
    public InvalidLineGrammarException(int lineNumber) {
        super(String.format(DEFAULT_MSG, lineNumber));
    }

    /**
     * constructor for the exception
     * @param message error message for the exception
     */
    public InvalidLineGrammarException(String message) {
        super(message);
    }
}
