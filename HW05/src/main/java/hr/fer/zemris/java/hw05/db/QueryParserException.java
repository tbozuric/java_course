package hr.fer.zemris.java.hw05.db;

/**
 * Thrown to indicate that an error occurred while parsing.
 *
 * @author Tomislav Bozuric
 */
public class QueryParserException extends RuntimeException {
    /**
     * Constructs an {@link QueryParserException} with no detail message.
     */
    public QueryParserException() {
    }

    /**
     * Constructs an {@link QueryParserException} with the specified detail message.
     *
     * @param message the detail message
     */
    public QueryParserException(String message) {
        super(message);
    }
}