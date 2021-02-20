package oop.ex6.analysis.ast;

import oop.ex6.exceptions.VerifierException;
import oop.ex6.analysis.scope.MethodSignature;
import oop.ex6.analysis.types.VarTypes;

import java.util.LinkedList;

/**
 * Method node for the AST.
 * describes a method and stores its implementation
 */
public class Method implements Node, Analyzable {
    /** the method's return type */
    private final VarTypes returnType;
    /** the method's name */
    private final String name;
    /** the method's parameters declarations list */
    private ParamDeclarationList paramList;
    /** the method's code block */
    private Block block;

    /**
     * create a new method
     * @param returnType the method's return type
     * @param name the method's name
     */
    public Method(VarTypes returnType, String name) {
        this.returnType = returnType;
        this.name = name;
    }

    /**
     * @return the method signature object of this method
     */
    public MethodSignature getSignature() {
        LinkedList<VarTypes> params = new LinkedList<>();
        for (VarDeclaration declaration : this.paramList.getDeclarations()) {
            params.addLast(declaration.getType());
        }

        return new MethodSignature(this.returnType, this.name, params.toArray(new VarTypes[0]));
    }

    /**
     * add a parameters declaration list to this method
     * @param paramList the parameters declaration to add
     */
    @Override
    public void addParamDeclarationList(ParamDeclarationList paramList) {
        this.paramList = paramList;
    }

    /**
     * add a code block (implementation) to this method
     * @param block code block to add
     */
    @Override
    public void addBlock(Block block) {
        this.block = block;
    }

    /**
     * run the passed analyzer on this Node
     * @param analyzer analyzer object to use
     * @throws VerifierException if the program isn't valid
     */
    @Override
    public void analyze(Analyzer analyzer) throws VerifierException {
        analyzer.openScope();

        this.paramList.analyze(analyzer);

        for (Statement statement : this.block.getStatements()) {
            statement.analyze(analyzer);
        }

        analyzer.closeScope();
    }
}
