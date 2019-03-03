package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Represent lexer for database queries.
 * Expressions are built using only jmbag , lastName and firstName attributes. No other attributes are allowed in query.
 * Expression consists from multiple comparison expressions. If more than one expression is given, all of them must be composed by logical AND operator.
 * Allowed operators are:
 *                  <p>>">"</p>
 *                  <p>"<"</p>
 *                  <p>">="</p>
 *                  <p>"<="</p>
 *                  <p>"!=</p>
 *                  <p>"LIKE"</p>
 * Query is composed from one or more conditional expressions.
 * Command, attribute name, operator, string literal and logical operator AND can be separated by more than one tabs or spaces.
 */
public class QueryLexer {

    /**
     * Comparison operator greater.
     */
    private static final char GREATER_OPERATOR = '>';
    /**
     * Comparison operator equals.
     */
    private static final char EQUALITY_OPERATOR = '=';
    /**
     * Comparison operator less.
     */
    private static final char LESS_OPERATOR = '<';
    /**
     * Part of comparison operator "!=".
     */
    private static final char NOT_EQUALS_OPERATOR = '!';
    /**
     * Comparison operator LIKE.
     */
    private static final String LIKE_OPERATOR = "LIKE";
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
     * Creates an instance with data set to given query.
     *
     * @param query input query.
     */
    public QueryLexer(String query) {

        if (query == null) {
            throw new IllegalArgumentException("Input text can not be null!");
        }
        query = query.trim().replaceAll("\\s+", " ");
        data = query.toCharArray();
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
     * Returns next token in input text.
     *
     * @return next token in input text
     */
    public Token nextToken() {
        extractNextToken();
        return getToken();
    }

    /**
     * Extracts the next token from the query, and sets current token to the extracted value.
     * The valid operations are in format : attribute-comparison operator-string literal.
     *
     * @throws QueryLexerException if the query is invalid.
     */
    private void extractNextToken() {
        if (token != null && token.getType() == TokenType.END) {
            throw new QueryLexerException("No tokens available.");
        }

        skipBlanks();

        if (currentIndex >= data.length) {
            token = new Token(TokenType.END, null);
            return;
        }

        if (String.valueOf(data[currentIndex]).matches("[><=!]")) {
            extractOperator();
            return;
        }

        if (data[currentIndex] == 'L') {
            if (tryExtractLikeOperator()) {
                return;
            }
        }

        if (data[currentIndex] == 'A' || data[currentIndex] == 'a') {
            if (tryExtractAndOperator()) {
                return;
            }
        }

        if(currentIndex>=data.length){
            throw new QueryLexerException("Illegal input");
        }

        if (data[currentIndex] == '"') {
            extractStringLiteral();
            return;
        }

        int startIndex = currentIndex;
        while (currentIndex < data.length && Character.isLetter(data[currentIndex]) && !String.valueOf(data[currentIndex]).matches("[><=!L]")) {
            currentIndex++;
        }
        int endIndex = currentIndex;
        if (endIndex > data.length) {
            throw new QueryLexerException("Illegal input");
        }
        String fieldName = new String(data, startIndex, endIndex - startIndex);
        token = new Token(TokenType.FIELD_NAME, fieldName);
    }

    /**
     * Tries to extract comparison operator LIKE. The operator must be exactly in the form of : "LIKE".
     *
     * @return true if it was successfully extracted.
     */
    private boolean tryExtractLikeOperator() {
        int startIndex = currentIndex;
        currentIndex += 4;
        if (currentIndex < data.length) {
            String likeOperator = new String(data, startIndex, currentIndex - startIndex);
            if (likeOperator.equals(LIKE_OPERATOR)) {
                token = new Token(TokenType.OPERATOR, likeOperator);
                return true;
            }
        }
        return false;
    }

    /**
     * Extracts comparision operator. This method extracts one of these operators : ">" , "<" , ">=" , "=<" , "=" , "!=".
     * On the left side of a comparison operator a field name is required and on the right side string literal.
     */
    private void extractOperator() {
        int nextIndex = currentIndex + 1;
        switch (data[currentIndex]) {
            case GREATER_OPERATOR:
                if (nextIndex < data.length && data[nextIndex] == EQUALITY_OPERATOR) {
                    token = new Token(TokenType.OPERATOR, ">=");
                    currentIndex += 2;
                } else {
                    currentIndex++;
                    token = new Token(TokenType.OPERATOR, String.valueOf(GREATER_OPERATOR));
                }
                break;
            case LESS_OPERATOR:
                if (nextIndex < data.length && data[nextIndex] == EQUALITY_OPERATOR) {
                    token = new Token(TokenType.OPERATOR, "<=");
                    currentIndex += 2;
                } else {
                    currentIndex++;
                    token = new Token(TokenType.OPERATOR, String.valueOf(LESS_OPERATOR));
                }
                break;
            case EQUALITY_OPERATOR:
                token = new Token(TokenType.OPERATOR, String.valueOf(EQUALITY_OPERATOR));
                currentIndex++;
                break;
            case NOT_EQUALS_OPERATOR:
                if (nextIndex < data.length && data[nextIndex] == EQUALITY_OPERATOR) {
                    token = new Token(TokenType.OPERATOR, "!=");
                    currentIndex += 2;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Extracts string literal. String literals must be written in quotes, and quote can not be written in string.
     */
    private void extractStringLiteral() {
        currentIndex++;
        int startIndex = currentIndex;

        if (startIndex > data.length) {
            throw new QueryLexerException("Illegal input");
        }

        while (currentIndex < data.length && data[currentIndex] != '"') {
            currentIndex++;
        }
        int endIndex = currentIndex;

        if (startIndex == endIndex || endIndex > data.length) {
            throw new QueryLexerException("Illegal input");
        }

        String stringLiteral = new String(data, startIndex, endIndex - startIndex);
        token = new Token(TokenType.STRING_LITERAL, stringLiteral);
        currentIndex++;
    }

    private boolean tryExtractAndOperator() {
        int startIndex = currentIndex;
        currentIndex += 3;
        if (currentIndex < data.length) {
            String logicalOperator = new String(data, startIndex, currentIndex - startIndex);
            if (logicalOperator.matches("[Aa][Nn][Dd]")) {
                token = new Token(TokenType.LOGICAL_OPERATOR, logicalOperator.toUpperCase());
                return true;
            }
        }
        return false;
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
