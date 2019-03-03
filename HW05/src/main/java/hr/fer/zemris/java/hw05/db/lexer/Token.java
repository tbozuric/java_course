package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Represents a token in the input query.
 */
public class Token {
    /**
     * Token type
     */
    private TokenType type;

    /**
     * The value represented by token.
     */
    private Object value;

    /**
     * Creates an instance of token with value and the type of the token set to the given type and value.
     *
     * @param type  of token
     * @param value of token
     */
    public Token(TokenType type, Object value) {
        if (type == null) {
            throw new IllegalArgumentException("Token type can not be null.");
        }
        this.type = type;
        this.value = value;

    }

    /**
     * Returns value represented by token.
     *
     * @return value represented by token.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns type of token.
     *
     * @return type of token
     */
    public TokenType getType() {
        return type;
    }


}
