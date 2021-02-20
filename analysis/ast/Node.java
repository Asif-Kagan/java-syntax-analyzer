package oop.ex6.analysis.ast;

/**
 * interface for an AST node,
 * creating a joined type and stating all possible operations, which can be implemented by the
 * different AST nodes.
 */
public interface Node {

    /**
     * add a method
     * @param method method to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addMethod(Method method) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("method", this);
    }

    /**
     * add a parameters list
     * @param paramList parameter list to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addParamList(ParamList paramList) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("paramList", this);
    }

    /**
     * add a parameters declaration list
     * @param paramList the parameters declaration to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addParamDeclarationList(ParamDeclarationList paramList) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("ParamDeclarationList", this);
    }

    /**
     * add a code block
     * @param block code block to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addBlock(Block block) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("block", this);
    }

    /**
     * add an expression
     * @param expression expression to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addExpression(Expression expression) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("expression", this);
    }

    /**
     * add a boolean expression
     * @param expression boolean expression to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addBooleanExpression(BooleanExpression expression) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("booleanExpression", this);
    }

    /**
     * add assignment
     * @param assignment assignment to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addAssignment(Assignment assignment) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("assignment", this);
    }

    /**
     * add a statement
     * @param statement statement to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addStatement(Statement statement) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("statement", this);
    }

    /**
     * add variable declaration
     * @param varDeclaration variable declaration to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addVarDeclaration(VarDeclaration varDeclaration) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("varDeclaration", this);
    }

    /**
     * add a method
     * @param methodCall method call to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addMethodCall(MethodCall methodCall) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("methodCall", this);
    }

    /**
     * add a return
     * @param ret return to add
     * @throws UnsupportedASTOperation in case this operation is not supported by the calling node
     */
    default void addReturn(Return ret) throws UnsupportedASTOperation {
        throw new UnsupportedASTOperation("return", this);
    }
}
