package oop.ex6.analysis.scope;

import oop.ex6.exceptions.VerifierException;

/**
 * exception thrown of invalid variable assignments
 */
public class InvalidVarException extends VerifierException {
	/** default error message */
	private static final String DEFAULT_MSG = "Variable %s cannot accept %s";

	/**
	 * new exception with the default message
	 * @param varA name of accepting variable
	 * @param varB name of updating variable
	 */
	public InvalidVarException(String varA, String varB){
		super(String.format(DEFAULT_MSG, varA, varB));
	}

	/**
	 * new exception with a costume error method
	 * @param message the error message
	 */
	public InvalidVarException(String message){
		super(message);
	}


}
