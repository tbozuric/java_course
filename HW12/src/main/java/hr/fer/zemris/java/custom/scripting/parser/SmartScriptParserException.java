package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown to indicate that an error occurred while parsing.
 *
 * @author Tomislav Bozuric
 */
public class SmartScriptParserException extends RuntimeException {
    /**
     * Constructs an {@code  SmartScriptParserException} with no detail message.
     */
    public SmartScriptParserException() {
    }

    /**
     * Constructs an {@code  SmartScriptParserException} with the specified detail message.
     *
     * @param message the detail message
     */
    public SmartScriptParserException(String message) {
        super(message);
    }
}
