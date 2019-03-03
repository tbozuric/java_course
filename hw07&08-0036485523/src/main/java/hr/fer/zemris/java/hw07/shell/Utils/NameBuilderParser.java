package hr.fer.zemris.java.hw07.shell.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.commands.MassRenameCommand;

/**
 * Represents a parser that pareses the expressions for the {@link MassRenameCommand} (sub-commands "show" and "execute".
 * The command starts with ${  , ends with }. Inside can be arbitrary spaces(but not between digits).
 * Eg. ${1,03}, ${1,       03} , ${   1  , 03}..
 * There are no escapes. The expression may contain substitution commands that are ${numberOfGroup}
 * or ${numberOfGroup, additionalClarification}. If the substitution command is a form ${numberOfGroup} , it replaces
 * "itself" with a string mapped to the given group.
 * If the substitution command is a form ${numberOfGroup, additionalClarification} , then the additional clarification
 * must be a number , or a zero and the number(eg. 05).
 * The number itself determines how many minimum characters will be "broadcast" when writing the required group.
 */
public class NameBuilderParser {
    /**
     * List of name builders.
     */
    private List<NameBuilder> builders;
    /**
     * Input expression as char array.
     */
    private char[] input;
    /**
     * A pattern for checking the correctness of the expression.
     */
    Pattern pattern = Pattern.compile("[0-9]+|[0-9]+\\s?,\\s?[0-9]+");

    /**
     * Creates an instance of {@link NameBuilderParser}.
     *
     * @param expression input expression.
     */
    public NameBuilderParser(String expression) {
        this.input = expression.trim().toCharArray();
        builders = new ArrayList<>();
        parse();
    }

    /**
     * Parses the input expression.
     *
     * @throws ParserException if the input expression is invalid.
     */
    private void parse() {

        int startIndex = 0;
        int index = 0;
        if (input.length == 0) {
            throw new ParserException("Expression is not valid.");
        }
        while (index < input.length) {
            if (input[index] == '$') {
                index++;
                if (index < input.length && input[index] == '{') {
                    String text = new String(input, startIndex, index - 1 - startIndex);
                    builders.add(constantStringNameBuilder(text));
                    index++;
                    index = extractSubstitutionExpression(index);
                    startIndex = index;
                    continue;
                }
            }
            index++;
        }
        if (startIndex != 0 && startIndex < input.length) {
            builders.add(constantStringNameBuilder(new String(input, startIndex, index - startIndex)));
        }
    }

    /**
     * Returns the current position after processing the "substitution command"
     * (command in format ${numberOfGroup} or ${numberOfGroup, additionalClarification}).
     *
     * @param index position before processing.
     * @return current position.
     * @throws ParserException if the substitution command is not valid.
     */
    private int extractSubstitutionExpression(int index) {
        if (index > input.length) {
            throw new ParserException("Expression is not valid.");
        }
        int start = index;
        while (index < input.length) {
            if (input[index] == '}') {
                String text = new String(input, start, index - start);
                text = text.replaceAll("\\s+", " ").trim();
                if (!pattern.matcher(text).matches()) {
                    throw new ParserException("Expression is not valid.");
                }
                builders.add(substitutionExpressionBuilder(text));
                break;
            }
            index++;
        }
        if (index >= input.length) {
            throw new ParserException("Expression is not valid. There is no closing bracket.");
        }
        return index + 1;
    }

    /**
     * Returns the "main" name builder that invokes the execute method of all other name builders.
     *
     * @return the "main" name builder.
     */
    public NameBuilder getNameBuilder() {
        return info -> {
            for (NameBuilder builder : builders) {
                builder.execute(info);
            }
        };
    }

    /**
     * Returns a new string builder that always appends a constant string.
     *
     * @param text to append.
     * @return a new string builder that always appends a constant string.
     */
    private NameBuilder constantStringNameBuilder(String text) {
        return info -> info.getStringBuilder().append(text);
    }

    /**
     * Returns a new string builder that always appends the given group.
     *
     * @param text to append.
     * @return a new name builder.
     */
    private NameBuilder substitutionExpressionBuilder(String text) {
        return info -> {
            StringBuilder stringBuilder = info.getStringBuilder();
            String group;

            if (text.contains(",")) {
                String[] elems = text.split(",");
                group = info.getGroup(Integer.parseInt(elems[0]));
                if (elems[1].startsWith("0")) {
                    stringBuilder.append(String.format("%0" + (Integer.parseInt(elems[1]) - group.length()) + "d%s", 0, group));
                } else {
                    stringBuilder.append(String.format("%" + elems[1] + "s", group));
                }
            } else {
                stringBuilder.append(info.getGroup(Integer.parseInt(text)));
            }
        };
    }
}
