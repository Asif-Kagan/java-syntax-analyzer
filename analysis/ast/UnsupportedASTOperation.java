package oop.ex6.analysis.ast;

import oop.ex6.exceptions.VerifierException;

/**
 * exception thrown when an AST node is being called with an operation which is not supported by it
 */
public class UnsupportedASTOperation extends VerifierException {
    /** default error message */
    private static final String DEFAULT_MSG = "Unsupported operation \"%s\" in %s node";

    /**
     * constructor with default error message
     */
    public UnsupportedASTOperation() {
        this("");
    }

    /**
     * constructor with costume error message
     * @param operation the failed operation name
     */
    public UnsupportedASTOperation(String operation) {
        super(String.format(DEFAULT_MSG, operation, ""));
    }

    /**
     * constructor with costume error message
     * @param operation the failed operation name
     * @param node the throwing node
     */
    public UnsupportedASTOperation(String operation, Node node) {
        super(String.format(DEFAULT_MSG, operation, node.getClass().getSimpleName()));
    }
}
