package oop.ex6.analysis.ast;


import oop.ex6.exceptions.VerifierException;
import oop.ex6.analysis.types.VarTypes;

public class VarDeclaration implements Statement, Node {
    /** the variable data type */
    private final VarTypes type;
    /** the variable name */
    private final String name;
    /** whether the variable is final */
    private final boolean isFinal;
    /** inline-assignment for this variable */
    private Assignment assignment;

    /**
     * create a new variable declaration without inline assignment
     * @param type the variable data type
     * @param name the variable name
     * @param isFinal whether the variable is final
     */
    public VarDeclaration(VarTypes type, String name, boolean isFinal) {
        this.name = name;
        this.type = type;
        this.isFinal = isFinal;
        this.assignment = null;
    }

    /**
     * add an inline assignment to this variable declaration
     * @param assignment assignment to add
     */
    @Override
    public void addAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    /**
     * @return the data type of this declaration
     */
    VarTypes getType() {
        return type;
    }

    /**
     * @return the variable name
     */
    String getName() {
        return name;
    }

    /**
     * run the passed analyzer on this Node
     * @param analyzer analyzer object to use
     * @throws VerifierException if the program isn't valid
     */
    @Override
    public void analyze(Analyzer analyzer) throws VerifierException {
        analyzer.declare(this.type, this.name, false);
        if (this.assignment != null) {
            this.assignment.analyze(analyzer);
        }
        analyzer.getSymbol(this.name).setFinal(this.isFinal);
    }
}
