package oop.ex6.analysis.scope;

import oop.ex6.analysis.types.VarTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * class representing a single scope, keeping track of its variables, and their states as
 * they are being declared and used.
 */
public class SymbolTable {
	/** variable not found exception message */
	private static final String VAR_NOT_FOUND = "ERROR: Variable %s not found";
	/** variable is final exception message */
	private static final String CANNOT_UPDATE_VAR = "ERROR: Variable %s is final, cannot be updated";

	/** the actual symbols table */
	private final Map<String, Symbol> symbolMap;

	/**
	 * create a new empty symbol table
	 */
	public SymbolTable() {
		this.symbolMap = new HashMap<>();
	}

	/**
	 * copy constructor for a symbol table
	 * @param table the table to copy
	 */
    public SymbolTable(SymbolTable table) {
        this.symbolMap = new HashMap<>();
        for (String symbolName : table.symbolMap.keySet()) {
            this.symbolMap.put(symbolName, new Symbol(table.symbolMap.get(symbolName)));
        }
    }

	/**
	 * get a symbol by name from the table
	 * @param varName the symbol to get name
	 * @return the symbol with the provided name
	 * @throws InvalidVarException if the symbol could not be found
	 */
	Symbol getSymbol(String varName) throws InvalidVarException{
		if(symbolMap.containsKey(varName)){
			return symbolMap.get(varName);
		}
		throw new InvalidVarException(String.format(VAR_NOT_FOUND, varName));
	}

	/**
	 * check if symbol exists in the table
	 * @param varName the symbol name to look for
	 * @return true if is contained in table, false otherwise
	 */
	boolean hasSymbol(String varName){
		return symbolMap.containsKey(varName);
	}

	/**
	 * declare a new symbol and add it to this table
	 * @param symbol the symbol to add to the table
	 * @throws DoubleDeclarationException if that symbol is already declared
	 */
	void declareSymbol(Symbol symbol) throws DoubleDeclarationException {
		if (this.hasSymbol(symbol.getName())) {
			throw new DoubleDeclarationException(symbol.getName());
		}
		this.symbolMap.put(symbol.getName(), symbol);
	}

	/**
	 * update a symbol with a given type
	 * @param symbol the symbol to update
	 * @param type the type to be used for updating
	 * @throws InvalidVarException if symbol cannot be updated
	 */
	void updateSymbol(Symbol symbol, VarTypes type) throws InvalidVarException{
		if (symbol.isFinal()) {
		    throw new InvalidVarException(String.format(CANNOT_UPDATE_VAR, symbol.getName()));
        }

		if (!symbol.getType().accepts(type)) {
		    throw new InvalidVarException(symbol.getName(), type.getName());
        }

        symbol.setInitialized(true);
	}

	/**
	 * update a symbol using another symbol
	 * @param symbol the symbol to update
	 * @param secondSymbol the symbol to be used for updating
	 * @throws InvalidVarException if symbol cannot be updated
	 */
    void updateSymbol(Symbol symbol, Symbol secondSymbol) throws InvalidVarException, UnInitializedSymbol {
        if (symbol.isFinal()) {
            throw new InvalidVarException(String.format(CANNOT_UPDATE_VAR, symbol.getName()));
        }
        if (!secondSymbol.isInitialized()) {
        	throw new UnInitializedSymbol(secondSymbol.getName());
		}

        if (!symbol.getType().accepts(secondSymbol.getType())) {
            throw new InvalidVarException(symbol.getName(), secondSymbol.getType().getName());
        }

        symbol.setInitialized(true);
    }
}
