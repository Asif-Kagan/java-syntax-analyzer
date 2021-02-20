package oop.ex6.lexer.token;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * enum holding all possible tokens oop.ex6.analysis.types for sjava program
 */
public enum TokenTypes {
    // keywords
    INT("int"),
    DOUBLE("double"),
    BOOLEAN("boolean"),
    CHAR("char"),
    STRING("String"),
    NULL("null"),
    VOID("void"),
    FINAL("final"),
    RETURN("return"),
    IF("if"),
    WHILE("while"),

    // keyword / literal
    CONSTANT_BOOLEAN("true|false"),

    // special characters
    COMMENT("^//.*", true),
    WS("\\s++", true),

    // identifiers
    IDENTIFIER_VAR_ONLY("_\\w++"),
    IDENTIFIER("[a-zA-Z]\\w*+"),

    // constant literals
    CONSTANT_DOUBLE("[-+]?(?:\\d++\\.\\d*+|\\.\\d++)"),
    CONSTANT_INT("[-+]?\\d++"),
    CONSTANT_STRING("\\\".*?\\\""),
    CONSTANT_CHAR("\\'.\\'"),

    // symbols
    ASSIGN("="),
    COMA(","),
    END(";"),
    AND("&&"),
    OR("\\|\\|"),
    OPENING_BLOCK("\\{"),
    CLOSING_BLOCK("\\}"),
    OPENING_PARENTHESIS("\\("),
    CLOSING_PARENTHESIS("\\)");

    /** flag stating this oop.ex6.lexer.token should be skipped */
    private static final boolean SKIP = true;
    /** regex or sign for regex concatenation */
    private static final String REGEX_OR = "|";

    double a = 5.;

    /** set of all possible tokens for quick contains lookup */
    private static final Set<String> TOKENS = new HashSet<>();
    // add all tokens into the tokens set
    static {
        for (TokenTypes tokenType : TokenTypes.values()) {
            TOKENS.add(tokenType.name());
        }
    }

    /** this token's regex pattern */
    private final Pattern pattern;
    /** raw regex expression without named group */
    private final String rawRegex;
    /** should this token be skipped */
    private final boolean skip;
    private final String regexGroupName;

    /**
     * construct a oop.ex6.lexer.token without skip
     * @param test the regex pattern's string
     */
    TokenTypes(String test) {
        this(test, !SKIP);
    }

    /**
     * construct a oop.ex6.lexer.token with given skip flag
     * @param test the regex pattern's string
     * @param skip should this oop.ex6.lexer.token be skipped
     */
    TokenTypes(String test, boolean skip) {
        this.rawRegex = test;
        this.regexGroupName = Utilities.snakeToCamelCase(this.name());
        this.pattern = Pattern.compile(String.format("(?<%s>%s)", this.regexGroupName, test));
        this.skip = skip;
    }

    /**
     * @return the regex group name for this token type
     */
    public String getRegexGroupName() {
        return this.regexGroupName;
    }

    /**
     * @return true if this oop.ex6.lexer.token should be skipped, false otherwise
     */
    public boolean isSkip() {
        return this.skip;
    }

    /**
     * test against this oop.ex6.lexer.token's regex
     * @param value the value to test
     * @return true if matches, false otherwise
     */
    public boolean test(String value) {
        return this.pattern.matcher(value).matches();
    }

    /**
     * @return get regex string of this oop.ex6.lexer.token
     */
    public String getRegex() {
        return this.pattern.toString();
    }

    /**
     * @return the raw regex string expression
     */
    public String getRawRegex() {
        return this.rawRegex;
    }

    /**
     * @param type type string to look for in the enum
     * @return true if is key of the enum, false otherwise
     */
    public static boolean contains(String type) {
        return TOKENS.contains(type);
    }

    /**
     * @return a regex string to test for all of the tokens at once
     */
    public static String all() {
        StringJoiner joiner = new StringJoiner(REGEX_OR);
        for (TokenTypes tokenType : TokenTypes.values()) {
            joiner.add(tokenType.getRegex());
        }
        return joiner.toString();
    }
}
