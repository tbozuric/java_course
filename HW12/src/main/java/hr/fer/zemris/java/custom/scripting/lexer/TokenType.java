package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents the token type
 */
public enum TokenType {
    /**
     * End of file.
     */
    EOF,
    /**
     * Some text.
     */
    TEXT,
    /**
     * Integer number.
     */
    INT,
    /**
     * Double number.
     */
    DOUBLE,
    /**
     * Function with associated name.
     */
    FUNCTION,
    /**
     * Variable with associated name.
     */
    VARIABLE,
    /**
     * Some string inside the tags.
     */
    STRING,
    /**
     * Some operator(*+-/)
     */
    OPERATOR,
    /**
     * Open tag("{$")
     */
    TAG_OPEN,
    /**
     * Close tag($})
     */
    TAG_CLOSE,
    /**
     * Tag name.
     */
    TAG_NAME
}
