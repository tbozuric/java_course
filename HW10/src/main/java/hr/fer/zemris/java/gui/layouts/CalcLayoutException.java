package hr.fer.zemris.java.gui.layouts;

/**
 * Thrown to indicate that an error occurred in {@link CalcLayout}.
 *
 * @author Tomislav Bozuric
 */
public class CalcLayoutException extends RuntimeException {

    /**
     * Default serial UID version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an {@link CalcLayoutException} with no detail message.
     */
    public CalcLayoutException() {
        super();
    }

    /**
     * Constructs an {@link CalcLayoutException} with the specified detail message.
     *
     * @param message the detail message
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
