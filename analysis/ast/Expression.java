package oop.ex6.analysis.ast;

import oop.ex6.exceptions.VerifierException;
import oop.ex6.analysis.types.VarTypes;

/**
 * interface for an Expression node of the AST
 */
public interface Expression {
    /**
     * get the expression data type
     * @param analyzer analyzer object to use
     * @return the expression's data type
     * @throws VerifierException if the type is invalid (void)
     */
    VarTypes getType(Analyzer analyzer) throws VerifierException;

    /**
     * get the initialization state of the expression
     * @param analyzer analyzer object to use
     * @return true if initialized, false otherwise
     * @throws VerifierException if failed to get value (symbol doesn't exists)
     */
    default boolean isInit(Analyzer analyzer) throws VerifierException {
        return true;
    }
}
