package oop.ex6.analysis.scope;

import oop.ex6.analysis.types.VarTypes;

/**
 * class representing a symbol (variable) in memory
 */
public class Symbol {
	/** the symbol's name */
	private final String name;
	/** the symbol's type */
	private final VarTypes type;
	/** whether the symbol's initialized */
	private boolean isInitialized;
	/** whether the symbol's final */
	private boolean isFinal;

	/**
	 * create a new symbol
	 * @param name the symbol's name
	 * @param type the symbol's type
	 * @param isInitialized whether the symbol's initialized
	 * @param isFinal whether the symbol's final
	 */
	public Symbol(String name, VarTypes type, boolean isInitialized, boolean isFinal){
		this.name = name;
		this.type = type;
		this.isInitialized = isInitialized;
		this.isFinal = isFinal;

	}

	/**
	 * copy constructor for a symbol
	 * @param symbol the symbol to copy
	 */
	public Symbol(Symbol symbol) {
		this(symbol.name, symbol.type, symbol.isInitialized, symbol.isFinal);
	}

	/**
	 * @return the symbol's name
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * @return the symbol's type
	 */
	public VarTypes getType(){
		return this.type;
	}

	/**
	 * @return whether the symbol's initialized
	 */
	public boolean isInitialized(){
		return this.isInitialized;
	}

	/**
	 * @return whether the symbol's final
	 */
	public boolean isFinal(){
		return this.isFinal;
	}

	/**
	 * set is initialized value
	 * @param initialized the value to set
	 */
	public void setInitialized(boolean initialized) {
		this.isInitialized = initialized;
	}

	/**
	 * set is final value
	 * @param isFinal the value to set
	 */
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
}
