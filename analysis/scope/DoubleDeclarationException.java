package oop.ex6.analysis.scope;

import oop.ex6.exceptions.VerifierException;

/**
 * exception thrown when a symbol is being re-declared
 */
public class DoubleDeclarationException extends VerifierException {
    /** default error message */
    private static final String DEFAULT_MSG = "Symbol %s is already declared";

    /**
     * create a new exception with relevant error message
     * @param symbolName the problematic symbol's name
     */
    DoubleDeclarationException(String symbolName) {
        super(String.format(DEFAULT_MSG, symbolName));
    }
}
