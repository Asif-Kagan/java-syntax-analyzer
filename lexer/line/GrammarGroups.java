package oop.ex6.lexer.line;

import oop.ex6.lexer.token.TokenTypes;
import java.util.*;

/**
 * enum grouping together important tokens under groups, or holding more complex
 * regex to validate for the structure of several tokens.
 *
 * the enum construction is of string templates which contain both TokenType names (uppercase), and
 * the references to previous GrammarGroups enums (placed inside ${} in lowercase), therefore allowing the
 * grammar validation of the structured lines to be more readable.
 */
public enum GrammarGroups {
    BOOLEAN_OPERATOR("AND", "OR"),
    TYPE("BOOLEAN", "STRING", "INT", "DOUBLE", "CHAR"),
    CONSTANTS("CONSTANT_BOOLEAN", "CONSTANT_STRING", "CONSTANT_CHAR",
            "CONSTANT_DOUBLE", "CONSTANT_INT", "NULL"),
    IDENTIFIER("IDENTIFIER", "IDENTIFIER_VAR_ONLY"),

    PARAM_LIST_DEC("(?:FINAL )?${type} ${identifier}(?: COMA (?:FINAL )?${type} ${identifier})*?"),
    FUNC_CALL("IDENTIFIER OPENING_PARENTHESIS(?: .*)*? CLOSING_PARENTHESIS"),
    EXPRESSION("${identifier}", "${constants}", "${func_call}"),
    RETURN_TYPES("VOID", "${type}"),
    ASSIGN("${identifier} ASSIGN ${expression}"),
    VAR_DEC("(?:FINAL )?${type} (?:${assign}|${identifier})(?: COMA (?:${assign}|${identifier}))*?"),
    BOOLEAN_EXP("${expression}(?: ${boolean_operator} ${expression})*?");

    // create set for contains checks
    private static final Set<String> GRAMMAR = new HashSet<>();
    static {
        for (GrammarGroups group : GrammarGroups.values()) {
            GRAMMAR.add(group.name());
        }
    }

    // parse token options
    /*
    go through all enum elements and replace any lowercase string within the options list which has an
    appropriate enum value in this enum with their appropriate value.
     */
    private static final String LABEL = "\\$\\{(.*?)\\}";
    private static final String FIRST_CAPT_GROUP = "$1";
    static {
        for (GrammarGroups group : GrammarGroups.values()) {
            group.options = new LinkedList<>();
            for (String tokenType : group.rawOptions) {
                if (TokenTypes.contains(tokenType)) {
                    group.options.add(TokenTypes.valueOf(tokenType));
                }
                else {
                    tokenType = tokenType.replaceAll(LABEL, FIRST_CAPT_GROUP);
                    if (GrammarGroups.contains(tokenType.toUpperCase())) {
                        group.options.addAll(GrammarGroups.valueOf(tokenType.toUpperCase()).options);
                    }
                }
            }
        }
    }

    // parse as regex
    /*
    go through all enum elements and replace any lowercase string within the options list which has an
    appropriate enum value in this enum with their appropriate value.
     */
    private static final String OR = "|";
    private static final String NON_CAPT_GROUP = "(?:%s)";
    private static final String WORD_REG = "\\$\\{(%s)\\}";
    static {
        for (GrammarGroups group : GrammarGroups.values()) {
            StringJoiner joiner = new StringJoiner(OR);
            for (String option : group.rawOptions) {
                joiner.add(option);
            }

            String pattern = joiner.toString();
            for (GrammarGroups grammar : GrammarGroups.values()) {
                pattern = pattern.replaceAll(String.format(WORD_REG,grammar.name().toLowerCase()),
                        String.format(NON_CAPT_GROUP, grammar.getRegex()));
            }
            group.regex = pattern;
        }
    }

    /** list of all token type that count as this group */
    private List<TokenTypes> options;
    /** regex expression to capture this grammar group */
    private String regex;
    /** the raw un-parsed string options array */
    private final String[] rawOptions;

    /**
     * create a new grammar group
     * @param s all options for this group
     */
    GrammarGroups(String... s) {
        this.rawOptions = s;
    }

    /**
     * @return regex string to capture this group
     */
    public String getRegex() {
        return this.regex;
    }

    /**
     * @return a list of token types which consist this group
     */
    public List<TokenTypes> getOptions() {
        return options;
    }

    /**
     * check if a given group name belongs to the enum
     * @param type the group type name
     * @return true if conatined, false otherwise
     */
    public static boolean contains(String type) {
        return GRAMMAR.contains(type);
    }

}
