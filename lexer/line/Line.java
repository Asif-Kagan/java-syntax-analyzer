package oop.ex6.lexer.line;

import oop.ex6.parser.ParseStrategy;
import oop.ex6.lexer.token.Token;

import java.util.StringJoiner;

/**
 * class representing a single line in the file
 */
public class Line {
    /** the tokens consisting this line */
    private final Token[] tokens;
    /** the line type */
    private final LineTypes lineType;
    /** the line parsing strategy */
    private final ParseStrategy parseStrategy;
    /** the line number */
    private final int lineNumber;

    /**
     * create a new line object
     * @param tokens the tokens consisting this line
     * @param lineType the line type
     * @param lineNumber the line number
     */
    Line(Token[] tokens, LineTypes lineType, int lineNumber) {
        this.tokens = tokens;
        this.lineType = lineType;
        this.parseStrategy = lineType.getStrategy();
        this.lineNumber = lineNumber;
    }

    /**
     * @return the line number for this line
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @return the classified line type
     */
    public LineTypes getLineType() {
        return lineType;
    }

    /**
     * @return all tokens consisting this line
     */
    public Token[] getTokens() {
        return tokens;
    }

    /**
     * @return the parse strategy to use for this line
     */
    public ParseStrategy getParseStrategy() {
        return parseStrategy;
    }

    /**
     * @return string representation of this line
     */
    @Override
    public String toString() {
        StringJoiner stringBuilder =  new StringJoiner(", ");
        for (Token token : this.tokens) {
            stringBuilder.add(token.toString());
        }

        if (parseStrategy != null) {
            stringBuilder.add("\t|| " + parseStrategy.toString());
        }
        return stringBuilder.toString();
    }
}

