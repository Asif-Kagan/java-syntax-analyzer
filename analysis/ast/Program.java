package oop.ex6.analysis.ast;

import java.util.LinkedList;
import java.util.List;

/**
 * Program node for the AST.
 * describes the root of the AST, and holds global statements and the methods.
 */
public class Program implements Node {
    /** the program's methods list */
    private final List<Method> methods;
    /** the program's global statements list */
    private final List<Statement> statements;

    /**
     * create a new empty program
     */
    public Program() {
        this.methods = new LinkedList<>();
        this.statements = new LinkedList<>();
    }

    /**
     * @return the program's methods list
     */
    public List<Method> getMethods() {
        return this.methods;
    }

    /**
     * @return the program's global statements list
     */
    public List<Statement> getStatements() {
        return this.statements;
    }

    /**
     * add variable declaration as a global statement
     * @param varDeclaration variable declaration to add
     */
    @Override
    public void addVarDeclaration(VarDeclaration varDeclaration) {
        this.statements.add(varDeclaration);
    }

    /**
     * add an assignment as a global statement
     * @param assignment assignment to add
     */
    @Override
    public void addAssignment(Assignment assignment) {
        this.statements.add(assignment);
    }

    /**
     * add a method to the program
     * @param method method to add
     */
    public void addMethod(Method method) {
        this.methods.add(method);
    }

    // removed for recursion limit
    //    @Override
//    public void addMethodCall(MethodCall methodCall) throws UnsupportedASTOperation {
//        statements.add(methodCall);
//    }
}
