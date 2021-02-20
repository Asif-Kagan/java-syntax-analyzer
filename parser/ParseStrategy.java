package oop.ex6.parser;

import oop.ex6.analysis.ast.Node;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.LexerIOException;

public abstract class ParseStrategy {
    /** oop.ex6.parser to use for parsing */
    protected Parser parser;

    /**
     * parse using the current oop.ex6.parser for variable assignment
     * @param parent the parent Node in the AST
     * @throws VerifierException upon any illegal state
     * @throws LexerIOException upon any IO exception thrown while parsing
     */
    abstract void parse(Node parent) throws VerifierException, VerifierIOException;

    /**
     * set the currently used oop.ex6.parser
     * @param parser initialized and open oop.ex6.parser
     */
    void setParser(Parser parser) {
        this.parser = parser;
    }

    /**
     * @return true if this strategy should be used as the next parsing strategy, false otherwise
     */
    boolean isNext() {
        return false;
    }
}
