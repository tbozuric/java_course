package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Represents query filter. Passes through the filter only those n-torches from the database that satisfy the comparison operators in the conditional expressions.
 */
public class QueryFilter implements IFilter {
    /**
     * List of all conditional expressions in the query.
     * Query is composed from one or more conditional expressions. Each conditional
     * expression has field name, operator symbol, string literal. Only AND is allowed for expression combining.
     */
    private List<ConditionalExpression> conditionalExpressions;

    /**
     * Creates a query filter instance.
     *
     * @param conditionalExpressions list of all conditional expressions in the query.
     */
    public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
        this.conditionalExpressions = conditionalExpressions;
    }

    /**
     * Returns only those n-torches that satisfy the comparison operator in the conditional expression.
     *
     * @param record student record.
     * @return n-torches that satisfy the comparison operator in the conditional expression.
     */
    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression conditionalExpression : conditionalExpressions) {
            if (!conditionalExpression.getComparisonOperator().satisfied(conditionalExpression.getFieldValueGetter().get(record), conditionalExpression.getStringLiteral())) {
                return false;
            }
        }
        return true;
    }
}
