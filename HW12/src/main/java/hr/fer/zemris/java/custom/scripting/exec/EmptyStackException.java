package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Thrown to indicate that stack is empty..
 *
 * @author Tomislav Bozuric
 */
public class EmptyStackException extends RuntimeException {
    /**
     * Default serial UID version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an {@link ObjectMultistack} with no detail message.
     */
    public EmptyStackException() {
    }

    /**
     * Constructs an {@link ObjectMultistack} with the specified detail message.
     *
     * @param message the detail message
     */
    public EmptyStackException(String message) {
        super(message);
    }
}
