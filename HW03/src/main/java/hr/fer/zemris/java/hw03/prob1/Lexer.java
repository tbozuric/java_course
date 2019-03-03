package hr.fer.zemris.java.hw03.prob1;

/**
 * Represent lexer for our "virtual" language.
 */
public class Lexer {
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
    public Lexer(String text) {
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
     * @param state mode(BASIC OR EXTENDED)
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
     * @throws LexerException if the input text(program code) is invalid.
     */
    private void extractNextToken() {

        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException("No tokens available.");
        }

        skipBlanks();

        if (currentIndex >= data.length) {
            token = new Token(TokenType.EOF, null);
            return;
        }

        if (state == LexerState.EXTENDED) {
            parseInExtendedMode();
            return;
        }

        if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
            String value = extractWord();
            token = new Token(TokenType.WORD, value);
            return;
        }

        if (Character.isDigit(data[currentIndex])) {
            long number = extractNumber();
            token = new Token(TokenType.NUMBER, number);
            return;
        }

        token = new Token(TokenType.SYMBOL, data[currentIndex]);
        currentIndex++;
    }

    /**
     * Parsing elements when the lexer is in extended mode, within # tags.
     *
     * @throws LexerException if an invalid character / word appears.
     */
    private void parseInExtendedMode() {
        int startIndex = currentIndex;
        skipBlanks();
        while (currentIndex < data.length && data[currentIndex] != '#') {
            if (isBlank(data[currentIndex])) {
                String value = new String(data, startIndex, currentIndex - startIndex);
                token = new Token(TokenType.WORD, value);
                return;
            }
            currentIndex++;
        }

        if (startIndex != currentIndex) {
            String value = new String(data, startIndex, currentIndex - startIndex);
            token = new Token(TokenType.WORD, value);
            return;
        }

        if (data[currentIndex] == '#') {
            token = new Token(TokenType.SYMBOL, data[currentIndex]);
            currentIndex++;
            return;
        }

        throw new LexerException("Invalid input");
    }

    /**
     * Extracts a new word.
     *
     * @return extracted word.
     * @throws LexerException if the word is invalid.
     */
    private String extractWord() {
        int startIndex = currentIndex;

        if (data[currentIndex] == '\\') {
            parseEscapeCharacter();
        } else {
            currentIndex++;
        }
        while ((currentIndex < data.length) && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
            if (data[currentIndex] == '\\') {
                parseEscapeCharacter();
                continue;
            }
            currentIndex++;
        }

        int endIndex = currentIndex;
        String value = new String(data, startIndex, endIndex - startIndex);
        value = value.replaceAll("(\\\\)([0-9]+)", "$2");
        value = value.replaceAll("(\\\\)(\\\\)", "$2");
        return value;
    }

    /**
     * Extracts the number in the long type data.
     *
     * @return extracted number.
     * @throws LexerException if the number does not fit in the long data type.
     */
    private long extractNumber() {
        if (currentIndex >= data.length) {
            throw new LexerException("Invalid number constant.");
        }

        int startIndex = currentIndex;
        while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
            currentIndex++;
        }

        int endIndex = currentIndex;
        if (startIndex == endIndex) {
            throw new LexerException("Invalid number constant.");
        }

        String value = new String(data, startIndex, endIndex - startIndex);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            throw new LexerException("The number can not be placed in a long data type.");
        }
    }


    /**
     * Parses the escape characters.(\\ or \[0-9]+").
     *
     * @throws LexerException if the input text is invalid.
     */
    private void parseEscapeCharacter() {
        currentIndex++;
        if (currentIndex >= data.length || (data[currentIndex] != '\\' && !Character.isDigit(data[currentIndex]))) {
            throw new LexerException("Invalid input.");
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
     * Checks whether the character is blank.
     *
     * @param c a character to check.
     * @return whether a character is blank.
     */
    private boolean isBlank(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }


}
