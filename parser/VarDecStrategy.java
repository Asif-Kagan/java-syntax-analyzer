package oop.ex6.parser;

import oop.ex6.analysis.ast.Node;
import oop.ex6.analysis.ast.VarDeclaration;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.line.GrammarGroups;
import oop.ex6.analysis.types.VarTypes;
import oop.ex6.lexer.token.Token;
import oop.ex6.lexer.token.TokenTypes;

/**
 * Parsing strategy for a variable declaration
 */
public class VarDecStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final VarDecStrategy instance = new VarDecStrategy();

    /** private default constructor */
    private VarDecStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static VarDecStrategy instance() {
        return instance;
    }

    /**
     * set the currently used oop.ex6.parser
     * @param parser initialized and open oop.ex6.parser
     */
    @Override
    void setParser(Parser parser) {
        super.setParser(parser);
        AssignmentStrategy.instance().setParser(parser);
    }

    /**
     * parse using the current oop.ex6.parser for variable assignment
     * @param parent the parent Node in the AST
     * @throws VerifierException upon any illegal state
     * @throws VerifierIOException upon any IO exception thrown while parsing
     */
    @Override
    public void parse(Node parent) throws VerifierException, VerifierIOException {
        boolean isFinal = this.parser.consumeIf(TokenTypes.FINAL);
        Token typeToken = this.parser.nextToken(GrammarGroups.TYPE);
        VarTypes type = VarTypes.getType(typeToken.getType());

        this.optionalAssignment(type, isFinal, parent);
        while (this.parser.consumeIf(TokenTypes.COMA)) {
            this.optionalAssignment(type, isFinal, parent);
        }

        this.parser.consume(TokenTypes.END);
    }

    /**
     * @return true if this strategy should be used as the next parsing strategy, false otherwise
     */
    @Override
    public boolean isNext() {
        return this.parser.peek(TokenTypes.FINAL) || this.parser.peek(GrammarGroups.TYPE);
    }

    /**
     * add an optional assignment and create a var declaration for the provided type final and parent
     * @param type the type of the variable
     * @param isFinal is the variable final
     * @param parent the parent element to attach the variable to
     * @throws VerifierException upon any illegal state
     */
    private void optionalAssignment(VarTypes type, boolean isFinal, Node parent) throws VerifierException, VerifierIOException {
        String name = parser.consume(GrammarGroups.IDENTIFIER);
        VarDeclaration varDeclaration = new VarDeclaration(type, name, isFinal);
        parent.addVarDeclaration(varDeclaration);

        if (isFinal || AssignmentStrategy.instance().isNext()) {
            this.parser.back();
            this.parser.use(AssignmentStrategy.instance()).parse(varDeclaration);
        }
    }
}
