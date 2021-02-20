package oop.ex6.lexer.line;

import oop.ex6.lexer.token.Token;

/**
 * factory to produce and hide the implementation of the line object
 */
public class LineFactory {
    /**
     * construct a line from an array of tokens
     * @param tokens tokens which are part of this line
     * @param lineNumber the line's line number in the source file
     * @return the line object representation of those tokens
     * @throws InvalidLineGrammarException in case the line couldn't have be classified
     */
    public static Line getLine(Token[] tokens, int lineNumber) throws InvalidLineGrammarException {
        LineTypes type = null;
        for (LineTypes lineType : LineTypes.values()) {
            if (lineType.test(tokens)) {
                type = lineType;
                break;
            }
        }

        if (type != null) {
            return new Line(tokens, type, lineNumber);
        }
        throw new InvalidLineGrammarException(lineNumber);
    }
}
