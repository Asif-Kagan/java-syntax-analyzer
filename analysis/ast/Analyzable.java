package oop.ex6.analysis.ast;

import oop.ex6.exceptions.VerifierException;

/**
 * an interface for all analyzable by analyzer AST nodes.
 */
public interface Analyzable {
    /**
     * run the passed analyzer on this Node
     * @param analyzer analyzer object to use
     * @throws VerifierException if the program isn't valid
     */
    void analyze(Analyzer analyzer) throws VerifierException;
}
