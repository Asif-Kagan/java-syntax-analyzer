package oop.ex6.analysis.ast;

import oop.ex6.exceptions.VerifierException;
import oop.ex6.analysis.types.VarTypes;

/**
 * Symbol node for the AST.
 * describes a symbol within the AST tree (variable name)
 */
public class Symbol implements Expression {
    /** the symbol's name */
    private final String name;

    /**
     * create a new symbol with the given name
     * @param name the symbol's name
     */
    public Symbol(String name) {
        this.name = name;
    }

    /**
     * get the initialization state of the expression
     * @param analyzer analyzer object to use
     * @return true if initialized, false otherwise
     * @throws VerifierException if failed to get value (symbol doesn't exists)
     */
    @Override
    public boolean isInit(Analyzer analyzer) throws VerifierException {
        return analyzer.getSymbol(this.name).isInitialized();
    }

    /**
     * get the expression data type
     * @param analyzer analyzer object to use
     * @return the expression's data type
     * @throws VerifierException if the type is invalid (void)
     */
    @Override
    public VarTypes getType(Analyzer analyzer) throws VerifierException {
        return analyzer.getSymbol(this.name).getType();
    }

    /**
     * @return the string representation of this symbol
     */
    @Override
    public String toString() {
        return this.name;
    }
}
