package hr.fer.zemris.java.hw07.crypto;

/**
 * Thrown to indicate that an encryption / decryption error occurred .
 */
public class AesCrypterException extends RuntimeException {
    /**
     * Default serial UID version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an {@link AesCrypterException} with no detail message.
     */
    public AesCrypterException() {
        super();
    }

    /**
     * Constructs an {@link AesCrypterException} with the specified detail message.
     *
     * @param message the detail message
     */
    public AesCrypterException(String message) {
        super(message);
    }
}
