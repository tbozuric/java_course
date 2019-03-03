package hr.fer.zemris.java.custom.scripting.lexer;


import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * Represent lexer for our "virtual" language.
 */
public class SmartScriptLexer {
    /**
     * The first sign after the tag is opened.
     */
    private static final char DOLLAR_SIGN = '$';
    /**
     * Denotes a print tag.
     */
    private static final char TAG_ECHO = '=';
    /**
     * Escape character.
     */
    private static final char ESCAPE_CHARACTER = '\\';
    /**
     * Indicates the opening of a empty or non-empty tag.
     */
    private static final char TAG_OPEN = '{';

    /**
     * Input text.
     */
    private char[] data;

    /**
     * Current token.
     */
    private Token token;

    /**
     * Current index in input text.
     */
    private int currentIndex;

    /**
     * Lexer mode(whether or not it is within the tag)
     * {@link LexerState}
     */
    private LexerState state;

    /**
     * Creates an instance with data set to given text, and lexer state set to basic
     *
     * @param text input text(program code)
     */
    public SmartScriptLexer(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input text can not be null!");
        }
        data = text.toCharArray();
        state = LexerState.BASIC;
    }

    /**
     * Returns next token in input text.
     *
     * @return next token in input text
     */
    public Token nextToken() {
        extractNextToken();
        return getToken();
    }

    /**
     * Returns current token.
     *
     * @return current token.
     */
    public Token getToken() {
        return token;
    }

    /**
     * Sets lexer mode to the given state.
     *
     * @param state mode
     */
    public void setState(LexerState state) {
        if (state == null) {
            throw new IllegalArgumentException("State can only be BASIC or EXTENDED. Was null.");
        }
        this.state = state;
    }

    /**
     * Extracts the next token in the input text(program code), and sets current token to the extracted value.
     *
     * @throws LexerException if the input text(program code) is in the wrong format.
     */
    private void extractNextToken() {
        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException("No tokens available.");
        }


        if (currentIndex >= data.length) {
            token = new Token(TokenType.EOF, null);
            return;
        }

        try {

            if (state == LexerState.TAG) {
                extractInTagMode();
                return;
            }


            if (data[currentIndex] == ESCAPE_CHARACTER || data[currentIndex] != TAG_OPEN) {
                int startIndex = currentIndex;
                if (data[currentIndex] == ESCAPE_CHARACTER) {
                    parseEscapeCharacter();
                } else {
                    currentIndex++;
                }

                while ((currentIndex < data.length) && (data[currentIndex] != ESCAPE_CHARACTER || (data[currentIndex] != TAG_OPEN))) {
                    if (data[currentIndex] == ESCAPE_CHARACTER) {
                        parseEscapeCharacter();
                        continue;
                    } else if (data[currentIndex] == TAG_OPEN) {
                        int nextIndex = currentIndex + 1;
                        if (nextIndex < data.length && data[nextIndex] == DOLLAR_SIGN) {
                            break;
                        }
                    }
                    currentIndex++;
                }

                int endIndex = currentIndex;
                String value = new String(data, startIndex, endIndex - startIndex);
                value = value.replaceAll("(\\\\)(\\{)", "$2");
                value = value.replaceAll("(\\\\)(\\\\)", "$2");
                token = new Token(TokenType.TEXT, value);
                return;
            }

            if (data[currentIndex] == TAG_OPEN) {
                int startIndex = currentIndex;
                int nextIndex = currentIndex + 1;
                if (nextIndex < data.length) {
                    if (data[nextIndex] != DOLLAR_SIGN) {
                        throw new LexerException("Invalid input");
                    }
                }
                currentIndex += 2;

                String tag = new String(data, startIndex, 2);
                token = new Token(TokenType.TAG_OPEN, tag);
            }
        } catch (StringIndexOutOfBoundsException exc) {
            throw new LexerException("Invalid input");
        }
    }


    /**
     * Extracts the tag name and then calls the method @link extractInsideTag.
     *
     * @throws LexerException if an invalid character / word appears in the tag.
     */
    private void extractInTagMode() {
        skipBlanks();
        if (token.getValue().equals("{$")) {
            findTagName();
        } else {
            extractInsideTag();
        }
    }

    /**
     * Extracts elements when the current position within the tag.
     *
     * @throws LexerException if the input text(program code) is invalid.
     */
    private void extractInsideTag() {
        int startIndex = currentIndex;
        while (currentIndex < data.length && notBlankOrDollarOrQuotes(data[currentIndex])) {
            currentIndex++;
        }

        if (data[currentIndex] == DOLLAR_SIGN && startIndex == currentIndex) {
            currentIndex += 2;
        } else if (data[currentIndex] == '"') {
            String value = extractString();
            if (isValidString(value)) {
                value = value.replaceAll("(\\\\)([\"]+)", "$2");
                value = value.replaceAll("(\\\\)(\\\\)", "$2");
                //tretiraj /n /r i /t kao njihove "stvarne vrijednosti"
                value = value.replaceAll("\\\\n", "\n");
                value = value.replaceAll("\\\\t", "\t");
                value = value.replaceAll("\\\\r", "\r");

                token = new Token(TokenType.STRING, value);
                return;
            }
        }
        int endIndex = currentIndex;


        String value = new String(data, startIndex, endIndex - startIndex);
        if (isValidVariableName(value)) {
            token = new Token(TokenType.VARIABLE, value);
        } else if (isValidFunctionName(value)) {
            token = new Token(TokenType.FUNCTION, value);
        } else if (isValidInteger(value)) {
            token = new Token(TokenType.INT, Integer.valueOf(value));
        } else if (isValidDouble(value)) {
            token = new Token(TokenType.DOUBLE, Double.valueOf(value));
        } else if (isValidOperator(value)) {
            token = new Token(TokenType.OPERATOR, value);
        } else if (value.equals("$}")) {
            token = new Token(TokenType.TAG_CLOSE, value);
        } else {
            throw new LexerException("Illegal input");
        }
    }

    /**
     * Extracts the currently found string and replaces escape characters(\\ -> \ , \" -> ").
     *
     * @return new string without escape characters.
     * @throws LexerException if the string is in the wrong format.
     */
    private String extractString() {
        int startIndex = currentIndex;
        currentIndex++;
        while (currentIndex < data.length && data[currentIndex] != '"') {
            if (data[currentIndex] == ESCAPE_CHARACTER) {
                parseEscapeInString();
            }
            currentIndex++;
        }
        currentIndex++;
        return new String(data, startIndex, currentIndex - startIndex);
    }

    /**
     * Parses the escape characters in the current string(\\ or \").
     *
     * @throws LexerException if the input text is in the wrong format.
     */
    private void parseEscapeInString() {
        currentIndex++;
        if (currentIndex >= data.length) {
            throw new LexerException("Invalid input.");
        } else if (!String.valueOf(data[currentIndex]).matches("[\"\\\\nrt]")) {
            throw new LexerException("Invalid input");
        }
        currentIndex++;

    }


    /**
     * Finds the tag name within the tag(if it exists).
     * Valid variable name starts by letter and after follows zero or more letters, digits or underscores.
     *
     * @throws LexerException if the tag is invalid or does not exist.
     */
    private void findTagName() {
        int startIndex = currentIndex;
        if (data[currentIndex] == TAG_ECHO) {
            token = new Token(TokenType.TAG_NAME, "=");
            currentIndex++;
            return;
        }

        //parser se brine za sintaksu, trazi prvi razmak
        while (currentIndex < data.length && notBlankOrDollarOrQuotes(data[currentIndex])) {
            currentIndex++;
        }

        int endIndex = currentIndex;
        String tagName = new String(data, startIndex, endIndex - startIndex);
        if (!isValidTagName(tagName)) {
            throw new LexerException("Invalid input.");
        }
        token = new Token(TokenType.TAG_NAME, tagName);

    }

    /**
     * Checks if the given string is valid.
     *
     * @param value a string to check
     * @return whether the string is valid.
     * @throws LexerException if the string is in invalid format.
     */
    private boolean isValidString(String value) {
        if (value.matches("\".*\"")) {
            if (value.contains("\\")) {
                for (int i = -1; (i = value.indexOf("\\", i + 1)) != -1; i++) {
                    int nextIndex = i + 1;
                    if (nextIndex < value.length() && !String.valueOf(value.charAt(nextIndex)).matches("[\"\\\\nrt]")) {
                        throw new LexerException("Illegal string");
                    }
                }
            }
        }
        return true;
    }


    /**
     * Checks if the name of variable is valid. Valid variable name starts by letter and after follows zero or more letters, digits or underscores.
     *
     * @param value to check.
     * @return whether the given string is valid.
     */
    private boolean isValidVariableName(String value) {
        return value.matches("([A-Za-z])([A-Za-z0-9_]*)");
    }

    /**
     * Checks if the function name is valid. Checks whether it starts with @ and then follows (one or more) letters or _.
     *
     * @param funcionName nam of the function
     * @return whether the function name is valid.
     */
    private boolean isValidFunctionName(String funcionName) {
        return funcionName.matches("(@[A-Za-z])([A-Za-z0-9_]*)");
    }

    /**
     * Checks if the operator is valid. Valid operators are + (plus), - (minus), * (multiplication), / (division), ^ (power).
     *
     * @param operator to check.
     * @return whether the operator is valid.
     */
    private boolean isValidOperator(String operator) {
        return operator.matches("[+\\-*/^]");
    }

    /**
     * Checks if the name of tag is valid. Checks whether it starts with = or letter and then follows (one or more) letters or _.
     *
     * @param tagName to check.
     * @return whether the name of tag is valid.
     */
    private boolean isValidTagName(String tagName) {
        return tagName.matches("=|([A-Za-z][A-Za-z0-9_]*)");
    }

    /**
     * Checks if the number is valid double.
     *
     * @param number to check.
     * @return whether the number is valid double.
     */
    private boolean isValidDouble(String number) {
        return number.matches("([-+]?[0-9]+[.[0-9]+]*)");
    }

    /**
     * Checks if the number is valid integer.
     *
     * @param number to check.
     * @return whether the number is valid integer.
     */
    private boolean isValidInteger(String number) {
        return number.matches("[-+]?[0-9]+");
    }

    /**
     * Parses escape character in input text.
     *
     * @throws LexerException if the input text is invalid.
     */
    private void parseEscapeCharacter() {
        currentIndex++;
        if (currentIndex >= data.length) {
            throw new LexerException("Invalid input.");
        }
        if (data[currentIndex] != ESCAPE_CHARACTER && data[currentIndex] != TAG_OPEN) {
            throw new LexerException("Invalid input");
        }
        currentIndex++;
    }

    /**
     * Skips all the blanks in the input text.
     */
    private void skipBlanks() {
        while (currentIndex < data.length) {
            char c = data[currentIndex];
            if (isBlank(c)) {
                currentIndex++;
                continue;
            }
            break;
        }
    }

    /**
     * Checks whether the character is not blank , $  and quotes.
     *
     * @param c a character to check
     * @return whether a character is not a $, quotes and blank.
     */
    private boolean notBlankOrDollarOrQuotes(char c) {
        return !isBlank(c) && c != DOLLAR_SIGN && c != '"';
    }

    /**
     * Checks whether the character is blank.
     *
     * @param c a character to check.
     * @return whether a character is blank.
     */
    private boolean isBlank(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

}
