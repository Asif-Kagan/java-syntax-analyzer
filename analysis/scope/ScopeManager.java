package oop.ex6.analysis.scope;

import oop.ex6.analysis.types.VarTypes;

import java.util.LinkedList;

/**
 * class handling all scopes and keeping track of variables
 */
public class ScopeManager {
	private static final String VAR_NOT_DECLARE = "ERROR: variable hasn't declared yet.";

	/** stack of all scopes */
	private final LinkedList<SymbolTable> scopes;
	/** a saved copy of the global scope */
	private SymbolTable globals;

	/**
	 * create a new scope manager with default global scope opened
	 */
	public ScopeManager() {
		this.scopes = new LinkedList<>();
		this.openScope();	// global oop.ex6.analysis.scope
	}

	/**
	 * save the current global scope state
	 */
	public void saveGlobalsState() {
	    this.globals = new SymbolTable(this.scopes.getLast());
    }

	/**
	 * reset the global scope from the last saved state
	 */
	public void resetGlobals() {
        this.scopes.removeLast();
        this.scopes.addLast(new SymbolTable(this.globals));
    }

	/**
	 * open a new scope
	 */
	public void openScope() {
		scopes.push(new SymbolTable());
	}

	/**
	 * close the latest scope
	 */
	public void closeScope(){
		scopes.pop();
	}

	/**
	 * @return the currently used scope
	 */
	public SymbolTable getCurrentScope() {
		return this.scopes.getFirst();
	}

	/**
	 * get a symbol by its name, by the order of precedence of the scopes
	 * @param varName the symbol to get name
	 * @return the requested symbol
	 * @throws InvalidVarException if the symbol is not found
	 */
	public Symbol getSymbol(String varName) throws InvalidVarException {
		for(SymbolTable scope : this.scopes){
			if(scope.hasSymbol(varName)){
				return scope.getSymbol(varName);
			}
		}
		throw new InvalidVarException(VAR_NOT_DECLARE);
	}

	/**
	 * check if a symbol exists in any of the scopes
	 * @param varName the symbol to search for
	 * @return true of found, false otherwise
	 */
	public boolean hasSymbol(String varName){
		for(SymbolTable symbolTable : this.scopes){
			if(symbolTable.hasSymbol(varName)){
				return true;
			}
		}
		return false;
	}

	/**
	 * declare a new symbol with the provided parameters in the latest scope
	 * @param type the symbol's type
	 * @param varName the symbol's name
	 * @param isFinal whether the symbol is final
	 * @throws DoubleDeclarationException if the symbol is already declared
	 */
	public void declare(VarTypes type, String varName, boolean isFinal) throws DoubleDeclarationException {
		this.getCurrentScope().declareSymbol(new Symbol(varName, type, false, isFinal));
	}

	/**
	 * update the provide symbol using a provided type
	 * @param varName the symbol to update
	 * @param varType the type to use for updating
	 * @throws InvalidVarException if symbol wasn't found
	 */
    public void update(String varName, VarTypes varType) throws InvalidVarException{
        if(this.hasSymbol(varName)){
        	Symbol symbol = this.getSymbol(varName);
            this.getCurrentScope().updateSymbol(symbol, varType);
            return;
        }
		throw new InvalidVarException(VAR_NOT_DECLARE);
    }
}
