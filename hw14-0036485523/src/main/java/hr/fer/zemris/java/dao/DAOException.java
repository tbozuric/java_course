package hr.fer.zemris.java.dao;

/**
 * Thrown to indicate that an error occurred while working with the database.
 */

public class DAOException extends RuntimeException {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an instance od {@link DAOException} with no detail message.
     */
    public DAOException() {
    }

    /**
     * Creates an instance of {@link DAOException} with the specified detail
     * message, cause, suppression enabled or disabled, and writable
     * stack trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause.  (A {@code null} value is permitted,
     *                           and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     */
    public DAOException(String message, Throwable cause,
                        boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Creates an instance of {@link DAOException} with the specified detail message and
     * cause.
     *
     * @param message the detail message .
     * @param cause   the cause . (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     **/
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code EmptyStackException} with the specified detail message.
     *
     * @param message the detail message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Creates an instance of {@link DAOException}with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     *
     * @param cause the cause  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}