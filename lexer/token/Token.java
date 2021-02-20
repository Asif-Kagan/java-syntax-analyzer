package oop.ex6.lexer.token;

/**
 * class representing a single sjava oop.ex6.lexer.token
 */
public class Token {
    /** the token's type */
    private final TokenTypes type;
    /** the token's value */
    private final String value;
    /** the token's oop.ex6.lexer.line number */
    private final int lineNumber;

    /**
     * constructor a new token
     * @param value the token's value
     * @param type the token's type
     * @param lineNumber the token's oop.ex6.lexer.line number
     */
    Token(String value, TokenTypes type, int lineNumber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNumber;
    }

    /**
     * @return the oop.ex6.lexer.token's type
     */
    public TokenTypes getType() {
        return type;
    }

    /**
     * @return the oop.ex6.lexer.token's value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the oop.ex6.lexer.token's oop.ex6.lexer.line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * // todo: remove
     * @return string representation of a oop.ex6.lexer.token (for debug)
     */
    @Override
    public String toString() {
        return this.type.name() + ": " + this.value;
    }
}
