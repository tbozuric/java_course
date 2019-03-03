package hr.fer.zemris.java.hw05.db;

/**
 * Represents a conditional expression obtained from query.
 * Conditional expression consists of : {@link IFieldValueGetter} , string literal and {@link IComparisonOperator}.
 * Conditional expressions are separated by a logical operator AND.
 */
public class ConditionalExpression {
    /**
     * Represents a reference to {@link IFieldValueGetter} strategy.
     */
    private IFieldValueGetter fieldValueGetter;
    /**
     * Represents a string literal.
     */
    private String stringLiteral;
    /**
     * Represents a reference to {@link IComparisonOperator} strategy.
     */
    private IComparisonOperator comparisonOperator;

    /**
     * Creates an instance of conditional expression.
     *
     * @param fieldValueGetter   getter of the some student record field. Fields can be : "jmbag" , "firstName" , "lastName".
     * @param stringLiteral      a string literal.
     * @param comparisonOperator a comparison operator.
     */
    public ConditionalExpression(IFieldValueGetter fieldValueGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
        this.fieldValueGetter = fieldValueGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Returns field value getter.
     *
     * @return field value getter.
     */
    public IFieldValueGetter getFieldValueGetter() {
        return fieldValueGetter;
    }

    /**
     * Returns a string literal.
     *
     * @return a string literal.
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     * Returns a comparison operator.
     *
     * @return a comparison operator.
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    /**
     * Sets a field value getter to the given strategy.
     *
     * @param fieldValueGetter a reference to {@link IFieldValueGetter} concrete strategy.
     */
    public void setFieldValueGetter(IFieldValueGetter fieldValueGetter) {
        this.fieldValueGetter = fieldValueGetter;
    }

    /**
     * Sets a string literal to the given value.
     *
     * @param stringLiteral a reference to string literal.
     */
    public void setStringLiteral(String stringLiteral) {
        this.stringLiteral = stringLiteral;
    }

    /**
     * Sets a comparision operator strategy to the given strategy.
     *
     * @param comparisonOperator a reference to {@link IComparisonOperator} concrete strategy.
     */
    public void setComparisonOperator(IComparisonOperator comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }
}
