package hr.fer.zemris.java.hw07.shell;

/**
 * Thrown to indicate that an error occurred while reading/writing to/from shell.
 */
public class ShellIOException extends RuntimeException {
    /**
     * Default serial UID version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an {@link ShellIOException} with no detail message.
     */
    public ShellIOException() {
    }

    /**
     * Constructs an {@link ShellIOException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ShellIOException(String message) {
        super(message);
    }
}
