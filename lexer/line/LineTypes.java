package oop.ex6.lexer.line;

import oop.ex6.parser.*;
import oop.ex6.lexer.token.Token;

import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * enum of all possible line types, their grammar structure and their parsing strategy
 *
 * the enum construction is of string templates which contain both TokenType names (uppercase), and
 * the references to previous GrammarGroups enums (placed inside ${} in lowercase), therefore allowing the
 * grammar validation of the structured lines to be more readable.
 */
public enum LineTypes {

    RETURN("RETURN END", ReturnStrategy.instance()),
    OPEN_BLOCK("OPENING_BLOCK"),
    CLOSE_BLOCK("CLOSING_BLOCK"),

    ASSIGN("${assign} END", AssignmentStrategy.instance()),
    VAR_DEC("${var_dec} END", VarDecStrategy.instance()),

    FUNC_CALL("${func_call} END",
            MethodCallStrategy.instance()),
    FUNC_DEC("VOID IDENTIFIER OPENING_PARENTHESIS(?: ${param_list_dec})? CLOSING_PARENTHESIS OPENING_BLOCK",
            MethodStrategy.instance()),
    IF("IF OPENING_PARENTHESIS ${boolean_exp} CLOSING_PARENTHESIS OPENING_BLOCK",
            IfStrategy.instance()),
    WHILE("WHILE OPENING_PARENTHESIS ${boolean_exp} CLOSING_PARENTHESIS OPENING_BLOCK",
            WhileStrategy.instance());

    /** space character */
    private static final String SPACE = " ";
    /** non capture group format */
    private static final String NON_CAPT_GROUP = "(?:%s)";
    /** labeled word format */
    private static final String WORD_REG = "\\$\\{(%s)\\}";
    static {
        for (LineTypes line : LineTypes.values()) {
            String pattern = line.rawGrammar;
            for (GrammarGroups grammar : GrammarGroups.values()) {
                pattern = pattern.replaceAll(String.format(WORD_REG,grammar.name().toLowerCase()),
                        String.format(NON_CAPT_GROUP, grammar.getRegex()));
            }
            line.grammar = Pattern.compile(pattern);
        }
    }

    /** the line's raw pre-processed grammar */
    private String rawGrammar;
    /** the line's grammar */
    private Pattern grammar;
    /** the strategy to parse this line */
    private final ParseStrategy strategy;

    /**
     * create a new line type with given grammar and strategy
     * @param grammar the line's grammar
     * @param strategy the strategy to parse this line
     */
    LineTypes(String grammar, ParseStrategy strategy) {
        this.rawGrammar = grammar;
        this.strategy = strategy;
    }

    /**
     * create a new lineType with default null parsing strategy
     * @param grammar the line's grammar
     */
    LineTypes(String grammar) {
        this(grammar, null);
    }

    /**
     * @return the strategy to use for parsing this line
     */
    ParseStrategy getStrategy() {
        return this.strategy;
    }

    /**
     * test an array of tokens against this line
     * @param line array of tokens representing a line
     * @return true if matches, false otherwise
     */
    public boolean test(Token[] line) {

        StringJoiner joiner = new StringJoiner(SPACE);
        for (Token token : line) {
            joiner.add(token.getType().name());
        }
        return this.grammar.matcher(joiner.toString()).matches();
    }
}
