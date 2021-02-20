package oop.ex6.analysis.scope;

import oop.ex6.exceptions.VerifierException;

/**
 * thrown when a method is looked for but does not exists
 */
public class UnInitializedSymbol extends VerifierException {
    /** default error message */
    private static final String DEFAULT_MSG = "Symbol %s is uninitialized";

    /**
     * new exception with the default message
     * @param symbolName the symbols's name
     */
    public UnInitializedSymbol(String symbolName) {
        super(String.format(DEFAULT_MSG, symbolName));
    }
}



