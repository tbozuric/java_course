package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexerException;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a query parser that runs over over the student database.
 * Expression consists from multiple comparison expressions. If more than one expression is given, all of them must be composed by logical AND operator.
 * Allowed operators are:
 *                          <p>">"</p>
 *                          <p>"<"</p>
 *                          <p>">="</p>
 *                          <p>"<="</p>
 *                          <p>"!="</p>
 *                          <p>"LIKE"</p>
 * Query is composed from one or more conditional expressions.
 * Command, attribute name, operator, string literal and logical operator AND can be separated by more than one tabs or spaces.
 */
public class QueryParser {
    /**
     * Parse error message.
     */
    private static final String PARSE_ERROR = "Unable to parse query.";
    /**
     * Less operator for string comparison.
     */
    private static final String OPERATOR_LESS = "<";
    /**
     * Greater operator for string comparison.
     */
    private static final String OPERATOR_GREATER = ">";
    /**
     * Greater or equals  operator for string comparison.
     */
    private static final String OPERATOR_GREATER_OR_EQUALS = ">=";
    /**
     * Less or equals operator for string comparison.
     */
    private static final String OPERATOR_LESS_OR_EQUALS = "<=";
    /**
     * Equals operator for string comparison.
     */
    private static final String OPERATOR_EQUALS = "=";
    /**
     * Not equals operator for string comparison.
     */
    private static final String OPERATOR_NOT_EQUALS = "!=";
    /**
     * Like operator for string comparison.The behaviour of the operator is as in normal SQL queries.
     */
    private static final String OPERATOR_LIKE = "LIKE";
    /**
     * Attribute tag for student first name.
     */
    private static final String FIRST_NAME_FIELD = "firstName";
    /**
     * Attribute tag for student last name.
     */
    private static final String LAST_NAME_FIELD = "lastName";
    /**
     * Attribute tag for student jmbag.
     */
    private static final String JMBAG_FIELD = "jmbag";
    /**
     * Represents wildcard "*" which may appear in LIKE operator.
     */
    private static final char WILDCARD = '*';

    /**
     * Reference to query lexer.
     */
    private QueryLexer lexer;
    /**
     * List of all conditional expressions in the query.
     * Query is composed from one or more conditional expressions. Each conditional
     * expression has field name, operator symbol, string literal. Only AND is allowed for expression combining.
     */
    private List<ConditionalExpression> conditionalExpressions;
    /**
     * Represents number of AND operators in query.
     */
    private int numberOfANDOperators;

    /**
     * Creates an instance of query parser.
     *
     * @param query database query.
     */
    public QueryParser(String query) {
        conditionalExpressions = new ArrayList<>();
        lexer = new QueryLexer(query);
        parse();
    }

    /**
     * Parses the entire input query, or stops working if it finds a mistake.
     *
     * @throws QueryParserException if it is not possible to parse the query.
     */
    private void parse() {
        try {
            Token token = lexer.nextToken();
            while (token.getType() != TokenType.END) {
                if (token.getType() == TokenType.FIELD_NAME) {

                    IFieldValueGetter fieldValueGetter;
                    fieldValueGetter = parseFieldValueGetter(token);

                    token = lexer.nextToken();
                    Token operator = token;
                    IComparisonOperator comparisonOperator = parseComparisonOperator(token);

                    token = lexer.nextToken();
                    if (token.getType() != TokenType.STRING_LITERAL) {
                        throw new QueryParserException(PARSE_ERROR);
                    }

                    String stringLiteral = token.getValue().toString();
                    checkOperator(operator, stringLiteral);

                    conditionalExpressions.add(new ConditionalExpression(fieldValueGetter, stringLiteral, comparisonOperator));
                    token = lexer.nextToken();

                    if (numberOfANDOperators > 0) {
                        numberOfANDOperators--;
                    }
                } else if (token.getType() == TokenType.LOGICAL_OPERATOR) {
                    if (numberOfANDOperators > 0) {
                        throw new QueryParserException(PARSE_ERROR);
                    }
                    token = lexer.nextToken();
                    numberOfANDOperators++;
                }
            }
        } catch (QueryLexerException ex) {
            throw new QueryParserException(PARSE_ERROR);
        }
        if (conditionalExpressions.size() == 0) {
            throw new QueryParserException(PARSE_ERROR);
        }
    }

    /**
     * Checks for the correct string literal that comes in pair with the comparison operator.
     * When LIKE operator is used, string literal can contain a wildcard "*" .
     * This character, if present, can occur at most once, but it can be at the beginning ,at the end or somewhere in the middle).
     *
     * @param operator      comparison operator.
     * @param stringLiteral string literal that we check.
     * @throws QueryParserException if string literal is invalid.
     */
    private void checkOperator(Token operator, String stringLiteral) {
        if (!operator.getValue().equals(OPERATOR_LIKE)) {
            if (stringLiteral.contains(String.valueOf(WILDCARD))) {
                throw new QueryParserException(PARSE_ERROR);
            }
        } else {
            if (stringLiteral.chars().filter(ch -> ch == WILDCARD).count() > 1) {
                throw new QueryParserException(PARSE_ERROR);
            }
        }
    }

    /**
     * Parses comparison operator. Allowed operators are : ">", "<" ,">=" ,"<=" , ">" ,"!=", "LIKE".
     *
     * @param token current token.
     * @return comparison operator that represents current token.
     * @throws QueryParserException if comparision operator is invalid.
     */
    private IComparisonOperator parseComparisonOperator(Token token) {
        if (token.getType() != TokenType.OPERATOR) {
            throw new QueryParserException(PARSE_ERROR);
        }
        IComparisonOperator comparisonOperator;

        switch (token.getValue().toString()) {
            case OPERATOR_LESS:
                comparisonOperator = ComparisonOperators.LESS;
                break;
            case OPERATOR_GREATER:
                comparisonOperator = ComparisonOperators.GREATER;
                break;
            case OPERATOR_GREATER_OR_EQUALS:
                comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
                break;
            case OPERATOR_LESS_OR_EQUALS:
                comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
                break;
            case OPERATOR_EQUALS:
                comparisonOperator = ComparisonOperators.EQUALS;
                break;
            case OPERATOR_NOT_EQUALS:
                comparisonOperator = ComparisonOperators.NOT_EQUALS;
                break;
            case OPERATOR_LIKE:
                comparisonOperator = ComparisonOperators.LIKE;
                break;
            default:
                throw new QueryParserException(PARSE_ERROR);
        }
        return comparisonOperator;
    }

    /**
     * Parses field value getter. Allowed field values are: "firstName" , "lastName", "LIKE".
     *
     * @param token current token.
     * @return the filed value that represents current token.
     * @throws QueryParserException if field value getter is invalid.
     */
    private IFieldValueGetter parseFieldValueGetter(Token token) {
        IFieldValueGetter fieldValueGetter;
        switch (token.getValue().toString()) {
            case FIRST_NAME_FIELD:
                fieldValueGetter = FieldValueGetters.FIRST_NAME;
                break;
            case LAST_NAME_FIELD:
                fieldValueGetter = FieldValueGetters.LAST_NAME;
                break;
            case JMBAG_FIELD:
                fieldValueGetter = FieldValueGetters.JMBAG;
                break;
            default:
                throw new QueryParserException(PARSE_ERROR);
        }
        return fieldValueGetter;
    }

    /**
     * Returns true if the query was of the form jmbag="xxx" (i.e. it must have only one comparison, on attribute jmbag , and operator must be equals).
     *
     * @return true if the query was direct.
     */
    public boolean isDirectQuery() {
        ConditionalExpression expression = conditionalExpressions.get(0);
        return conditionalExpressions.size() == 1 && expression.getFieldValueGetter() == FieldValueGetters.JMBAG &&
                conditionalExpressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS;
    }

    /**
     * Returns a queried jmbag if the query was direct.
     *
     * @return queried jmbag.
     * @throws IllegalStateException if the query was not direct.
     */
    public String getQueriedJMBAG() {
        if (isDirectQuery())
            return conditionalExpressions.get(0).getStringLiteral();
        throw new IllegalStateException("Query is not direct!");
    }

    /**
     * Returns the list of conditional expressions in the query.
     *
     * @return list od conditional expressions in the query.
     */
    public List<ConditionalExpression> getQuery() {
        return conditionalExpressions;
    }


}
