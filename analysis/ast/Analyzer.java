package oop.ex6.analysis.ast;

import oop.ex6.analysis.scope.Symbol;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.analysis.scope.*;
import oop.ex6.analysis.types.VarTypes;

/**
 * class to be used for analyzing a parsed program
 */
public class Analyzer {

    /** scope manager */
    private final ScopeManager scopeManager;
    /** methods manager */
    private final MethodTable methodsManager;

    /**
     * create a new analyzer
     */
    public Analyzer() {
        this.scopeManager = new ScopeManager();
        this.methodsManager = new MethodTable();
    }

    /**
     * analyze the program and throw an error if it's invalid
     * @param program the parsed program to analyze
     * @throws VerifierException if there's any error's while analyzing
     */
    public void analyze(Program program) throws VerifierException {
        for (Method method : program.getMethods()) {
            this.methodsManager.declare(method.getSignature());
        }

        for (Statement statement : program.getStatements()) {
            statement.analyze(this);
        }

        this.scopeManager.saveGlobalsState();
        for (Method method : program.getMethods()) {
            this.scopeManager.resetGlobals();
            method.analyze(this);
        }
    }

    // ===========================================================
    // -------------------------- Facade -------------------------
    // ===========================================================

    /**
     * open a new scope
     */
    void openScope() {
        this.scopeManager.openScope();
    }

    /**
     * close the latest scope
     */
    void closeScope() {
        this.scopeManager.closeScope();
    }

    /**
     * check if a method is defined
     * @param name the method's name
     * @param paramTypes the method's parameters types
     * @return true if method is defined, false otherwise
     */
    boolean has(String name, VarTypes[] paramTypes) {
        return this.methodsManager.has(name, paramTypes);
    }

    /**
     * get a symbol by its name, by the order of precedence of the scopes
     * @param name the symbol to get name
     * @return the requested symbol
     * @throws InvalidVarException if the symbol is not found
     */
    Symbol getSymbol(String name) throws InvalidVarException {
        return this.scopeManager.getSymbol(name);
    }

    /**
     * get the signature of a method by it's name
     * @param name the required method name
     * @return the signature of the required method
     * @throws InvalidMethodException if no method with this name exists
     */
    MethodSignature getSignature(String name) throws InvalidMethodException {
        return this.methodsManager.getSignature(name);
    }

    /**
     * update the provided symbol using a provided type
     * @param varName the symbol to update
     * @param type the type to use for updating
     * @throws InvalidVarException if symbol wasn't found
     */
    void update(String varName, VarTypes type) throws InvalidVarException {
        this.scopeManager.update(varName, type);
    }

    /**
     * declare a new symbol with the provided parameters in the latest scope
     * @param type the symbol's type
     * @param varName the symbol's name
     * @param isFinal whether the symbol is final
     * @throws DoubleDeclarationException if the symbol is already declared
     */
    void declare(VarTypes type, String varName, boolean isFinal) throws DoubleDeclarationException {
        this.scopeManager.declare(type, varName, isFinal);
    }
}
