package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Thrown to indicate that an error occurred while lexer working.
 *
 */
public class QueryLexerException extends RuntimeException {

    /**
     * Constructs an  {@link QueryLexerException} with no detail message.
     */
    public QueryLexerException() {
        super();
    }

    /**
     * Constructs an {@link QueryLexerException} with the specified detail message.
     *
     * @param message the detail message
     */
    public QueryLexerException(String message) {
        super(message);
    }
}
