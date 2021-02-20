package oop.ex6.analysis.ast;


import oop.ex6.exceptions.VerifierException;

/**
 * If node for the AST.
 * describes an if statement
 */
public class If implements Statement, Node {
    /** the boolean expression of the if's condition */
    private BooleanExpression expression;
    /** the if's code block */
    private Block block;

    /**
     * add a boolean expression as the if's condition
     * @param expression boolean expression to add
     */
    @Override
    public void addBooleanExpression(BooleanExpression expression) {
        this.expression = expression;
    }

    /**
     * add a code block to this if
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
        this.expression.analyze(analyzer);
        analyzer.openScope();
        for (Statement statement : this.block.getStatements()) {
            statement.analyze(analyzer);
        }
        analyzer.closeScope();
    }
}
