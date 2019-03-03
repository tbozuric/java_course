package hr.fer.zemris.java.hw03.prob1;

/**
 * Represents the lexer mode.
 */
public enum LexerState {
    /**
     * Basic lexer mode, the lexer is not within the (#) tag.
     */
    BASIC,
    /**
     * Tag lexer mode, the lexer is within the (#) tag.
     */
    EXTENDED
}
