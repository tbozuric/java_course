package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Represents the types of tokens that may appear in the query.
 */
public enum TokenType {
    /**
     * End of query.
     */
    END,
    /**
     * Comparison operator.It represents one of following seven comparison operators: > , < , >= , <= , = , !=, LIKE .
     */
    OPERATOR,
    /**
     * Represents string literal which must be written in quotes.
     */
    STRING_LITERAL,
    /**
     * Represents query attribute. Possible attributes are : "jmbag", "lastName", "firstName".
     */
    FIELD_NAME,
    /**
     * Represent logical operator AND.
     * If more than one expression is given, all of them must be composed by logical AND operator.
     */
    LOGICAL_OPERATOR
}
