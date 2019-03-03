package hr.fer.zemris.java.hw07.crypto;

/**
 * Thrown to indicate that an error occurred while calculating SHA-Digest .
 */
public class ShaDigestException extends RuntimeException {
    /**
     * Default serial UID version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an {@link ShaDigestException} with no detail message.
     */
    public ShaDigestException() {
        super();
    }

    /**
     * Constructs an {@link ShaDigestException} with the specified detail message.
     *
     * @param message the detail message
     */
    public ShaDigestException(String message) {
        super(message);
    }
}
