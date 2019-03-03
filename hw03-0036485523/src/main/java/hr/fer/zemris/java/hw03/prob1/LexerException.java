package hr.fer.zemris.java.hw03.prob1;

/**
 * Thrown to indicate that an error occurred while lexer working.
 *
 * @author Tomislav Bozuric
 */
public class LexerException extends RuntimeException {

    /**
     * Constructs an {@code  LexerException} with no detail message.
     */
    public LexerException() {
        super();
    }

    /**
     * Constructs an {@code LexerException} with the specified detail message.
     *
     * @param message the detail message
     */
    public LexerException(String message) {
        super(message);
    }
}
