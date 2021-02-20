package oop.ex6.analysis.ast;


import java.util.LinkedList;
import java.util.List;

/**
 * code block node for the AST.
 * describes a code block containing multiple statements
 */
public class Block implements Node {
    /** list of all statements in this code block */
    private final List<Statement> statements;

    /**
     * create a new empty code block
     */
    public Block() {
        this.statements = new LinkedList<>();
    }

    /**
     * @return a list of all statements of this code block
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /**
     * add variable declaration as a possible statement
     * @param varDeclaration variable declaration to add
     */
    @Override
    public void addVarDeclaration(VarDeclaration varDeclaration) {
        this.statements.add(varDeclaration);
    }

    /**
     * add assignment as a possible statement
     * @param assignment assignment to add
     */
    @Override
    public void addAssignment(Assignment assignment) {
        this.statements.add(assignment);
    }

    /**
     * add a statement (used by if/while)
     * @param statement statement to add
     */
    @Override
    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

    /**
     * add a method call as a possible statement
     * @param methodCall method call to add
     */
    @Override
    public void addMethodCall(MethodCall methodCall) {
        this.statements.add(methodCall);
    }

    /**
     * add a return as a possible statement
     * @param ret return to add
     */
    @Override
    public void addReturn(Return ret) {
        this.statements.add(ret);
    }
}
