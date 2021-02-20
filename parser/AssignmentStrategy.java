package oop.ex6.parser;

import oop.ex6.analysis.ast.Assignment;
import oop.ex6.analysis.ast.Node;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.line.GrammarGroups;
import oop.ex6.lexer.token.TokenTypes;

/**
 * Parsing strategy for variable assignments
 */
public class AssignmentStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final AssignmentStrategy instance = new AssignmentStrategy();

    /** private default constructor */
    private AssignmentStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static AssignmentStrategy instance() {
        return instance;
    }

    /**
     * parse using the current oop.ex6.parser for variable assignment
     * @param parent the parent Node in the AST
     * @throws VerifierException upon any illegal state
     * @throws VerifierIOException upon any IO exception thrown while parsing
     */
    @Override
    public void parse(Node parent) throws VerifierException, VerifierIOException {
        String name = this.parser.consume(GrammarGroups.IDENTIFIER);
        this.parser.consume(TokenTypes.ASSIGN);

        Assignment assignment = new Assignment(name);
        parent.addAssignment(assignment);
        this.parser.use(ExpressionStrategy.instance()).parse(assignment);
    }

    /**
     * @return true if next parsing strategy should be this, false otherwise
     */
    @Override
    public boolean isNext() {
        return this.parser.peek(TokenTypes.ASSIGN);
    }
}
