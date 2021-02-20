package oop.ex6.lexer.token;

import oop.ex6.lexer.InvalidTokenException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * static factory class to get the tokens representation of a string
 */
public class TokenFactory {
    /** default ArrayList size */
    private static final int DEFAULT_SIZE = 20;

    /** the invalid tokens regex capture group's name */
    private static final String INVALID_TOKEN_GROUP = "invalidToken";
    /** regex for invalid tokens capture group */
    private static final String INVALID_TOKEN = String.format("(?<%s>.)", INVALID_TOKEN_GROUP);
    /** regex or character */
    private static final String OR = "|";

    /** regex pattern for all possible sjava tokens */
    private static final Pattern TOKENS = Pattern.compile(TokenTypes.all()+OR+INVALID_TOKEN);

    /**
     * break a text line into it's sjava's tokens
     * @param line an sjava text line
     * @param lineNumber the line's oop.ex6.lexer.line number (for a more informative exception throwing)
     * @return array of tokens in the oop.ex6.lexer.line
     * @throws InvalidTokenException upon any invalid sjava token
     */
    public static Token[] getTokens(String line, int lineNumber) throws InvalidTokenException {
        Matcher matcher = TOKENS.matcher(line);
        List<Token> tokens = new ArrayList<>(DEFAULT_SIZE);

        while (matcher.find()) {
            if (matcher.group(INVALID_TOKEN_GROUP) != null) {
                throw new InvalidTokenException(matcher.group(INVALID_TOKEN_GROUP), lineNumber);
            }

            for (TokenTypes tokenType : TokenTypes.values()) {
                String match = matcher.group(tokenType.getRegexGroupName());
                if (match != null) {
                    if (!tokenType.isSkip()) {
                        tokens.add(new Token(match, tokenType, lineNumber));
                    }
                    break;
                }
            }
        }

        return tokens.toArray(new Token[0]);
    }
}
