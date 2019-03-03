package hr.fer.zemris.java.hw07.shell.Utils;


/**
 * Thrown to indicate that an error occurred while parsing command.
 */
public class ParserException extends RuntimeException {
    /**
     * Default serial UID version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an {@link ParserException} with no detail message.
     */
    public ParserException() {
        super();
    }

    /**
     * Constructs an {@link ParserException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ParserException(String message) {
        super(message);
    }
}
