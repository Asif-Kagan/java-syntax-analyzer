package oop.ex6.analysis.ast;

import oop.ex6.exceptions.VerifierException;

import java.util.LinkedList;
import java.util.List;

/**
 * Parameters declarations list node for the AST.
 * describes a parameter list such as the one in a method's declaration
 */
public class ParamDeclarationList implements Node, Analyzable {
    /** list of all declarations in the param list */
    private final List<VarDeclaration> declarations;

    /**
     * create a new empty declarations list
     */
    public ParamDeclarationList() {
        this.declarations = new LinkedList<>();
    }

    /**
     * @return get list of all declarations
     */
    List<VarDeclaration> getDeclarations() {
        return this.declarations;
    }

    /**
     * add variable declaration to the parameters declarations
     * @param varDeclaration variable declaration to add
     */
    @Override
    public void addVarDeclaration(VarDeclaration varDeclaration) {
        this.declarations.add(varDeclaration);
    }

    /**
     * run the passed analyzer on this Node
     * @param analyzer analyzer object to use
     * @throws VerifierException if the program isn't valid
     */
    @Override
    public void analyze(Analyzer analyzer) throws VerifierException {
        for (VarDeclaration declaration : this.declarations) {
            declaration.analyze(analyzer);
            analyzer.getSymbol(declaration.getName()).setInitialized(true);
        }
    }
}
