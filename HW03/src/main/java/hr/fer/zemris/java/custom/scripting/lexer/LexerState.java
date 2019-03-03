package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents the lexer mode.
 */
public enum LexerState {
    /**
     * Basic lexer mode, the lexer is not within the tag.
     */
    BASIC,
    /**
     * Tag lexer mode, the lexer is within the tag.
     */
    TAG
}
