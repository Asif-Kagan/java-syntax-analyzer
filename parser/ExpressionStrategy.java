package oop.ex6.parser;

import oop.ex6.analysis.ast.*;
import oop.ex6.exceptions.VerifierIOException;
import oop.ex6.lexer.InvalidTokenException;
import oop.ex6.exceptions.VerifierException;
import oop.ex6.lexer.line.GrammarGroups;
import oop.ex6.analysis.types.VarTypes;
import oop.ex6.lexer.token.Token;

/**
 * Parsing strategy for an expression
 */
public class ExpressionStrategy extends ParseStrategy {
    /** singleton instance for this strategy */
    private static final ExpressionStrategy instance = new ExpressionStrategy();

    /** private default constructor */
    private ExpressionStrategy() {}

    /**
     * @return singleton instance for this strategy
     */
    public static ExpressionStrategy instance() {
        return instance;
    }

    /**
     * set the currently used oop.ex6.parser
     * @param parser initialized and open oop.ex6.parser
     */
    @Override
    void setParser(Parser parser) {
        super.setParser(parser);
        MethodCallStrategy.instance().setParser(this.parser);
    }

    /**
     * parse using the current oop.ex6.parser for variable assignment
     * @param parent the parent Node in the AST
     * @throws VerifierException upon any illegal state
     * @throws VerifierIOException upon any IO exception thrown while parsing
     */
    @Override
    public void parse(Node parent) throws VerifierException, VerifierIOException {
        if (MethodCallStrategy.instance().isNext()) {
            this.parser.use(MethodCallStrategy.instance()).parse(parent);
        }
        else if(this.parser.peek(GrammarGroups.IDENTIFIER)) {
            Symbol symbol = new Symbol(this.parser.consume(GrammarGroups.IDENTIFIER));
            parent.addExpression(symbol);
        }
        else if (this.parser.peek(GrammarGroups.CONSTANTS)) {
            Token token = this.parser.nextToken();
            VarTypes type = VarTypes.getType(token.getType());
            Constant constant = new Constant(token.getValue(), type);
            parent.addExpression(constant);
        }
        else {
            throw new InvalidTokenException(this.parser.nextToken());
        }
    }
}
