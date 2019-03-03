package hr.fer.zemris.java.hw05.db;

/**
 * Represents an interface that allows you to compare two literal strings.
 */
public interface IComparisonOperator {
    /**
     * Checks if string literals satisfies comparison operator.
     * Allowed comparison operators are : "<" , ">" ,">=" , "<=" , "!=" , "LIKE"
     *
     * @param value1 that will be compared to the value2.
     * @param value2 with which to compare
     * @return true if string literals satisfy operator comparison.
     */
    boolean satisfied(String value1, String value2);
}
