package oop.ex6.lexer.token;

import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * general utility function for the entire program
 */
public class Utilities {

    /** pattern to match the snake_case underscore separator */
    private static final Pattern UNDER_SCORE = Pattern.compile("(.)_(.)");
    /** pattern to match the camel case end of last word and beginning of next one */
    private static final Pattern CAMEL = Pattern.compile("([a-z])([A-Z])");
    /** last word ending char group */
    private static final int LAST_WORD_END_GROUP = 1;
    /** next word starting char group */
    private static final int NEXT_WORD_START_GROUP = 2;
    /** camelCase concat format */
    private static final String CAMELCASE_FORMAT = "%s%s";
    /** snake_case concat format */
    private static final String SNAKE_CASE_FORMAT = "%s_%s";

    /**
     * case a SNAKE_CASE string into a camelCase representations
     * @param str the snake case string
     * @return a camel case version of it
     */
    public static String snakeToCamelCase(String str) {
        str = str.toLowerCase();
        Matcher matcher = UNDER_SCORE.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer,
                    String.format(CAMELCASE_FORMAT,matcher.group(LAST_WORD_END_GROUP),
                            matcher.group(NEXT_WORD_START_GROUP).toUpperCase()));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static String camelToSnakeCase(String str) {
        Matcher matcher = CAMEL.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer,
                    String.format(SNAKE_CASE_FORMAT, matcher.group(LAST_WORD_END_GROUP),
                            matcher.group(NEXT_WORD_START_GROUP).toLowerCase()));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
