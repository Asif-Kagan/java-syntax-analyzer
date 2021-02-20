package oop.ex6.analysis.scope;

import oop.ex6.analysis.types.VarTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * a class to handle the global functions declarations
 */
public class MethodTable {
    /** the actual methods signatures table */
    private final Map<String, MethodSignature> methodsMap;

    /**
     * create a new empty methods table
     */
    public MethodTable() {
        this.methodsMap = new HashMap<>();
    }

    /**
     * check if a certain method is declared
     * @param name the method's name
     * @param varTypes the method's parameter types list
     * @return true if the method is declared, false otherwise
     */
    public boolean has(String name, VarTypes[] varTypes) {
        MethodSignature method = this.methodsMap.get(name);
        if (method == null || varTypes.length != method.getParamList().length) {
            return false;
        }

        int i = 0;
        for (VarTypes paramType : method.getParamList()) {
            if (!paramType.accepts(varTypes[i++])) {
                return false;
            }
        }
        return true;
    }

    /**
     * get the signature of a method by it's name
     * @param name the required method name
     * @return the signature of the required method
     * @throws InvalidMethodException if no method with this name exists
     */
    public MethodSignature getSignature(String name) throws InvalidMethodException {
        if(this.methodsMap.containsKey(name)){
            return this.methodsMap.get(name);
        }
        throw new InvalidMethodException(name);
    }

    /**
     * declare a new method
     * @param method the method signature to declare
     * @throws DoubleDeclarationException if a method of the same name is already declared
     */
    public void declare(MethodSignature method) throws DoubleDeclarationException {
        if(!this.methodsMap.containsKey(method.getName())){
            this.methodsMap.put(method.getName(), method);
            return;
        }
        throw new DoubleDeclarationException(method.getName());
    }
}

