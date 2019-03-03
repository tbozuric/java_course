package hr.fer.zemris.java.hw05.db;


/**
 * Represents concrete strategies for each comparison operator {@link IComparisonOperator}.
 */
public class ComparisonOperators {
    /**
     * A concrete strategy that examines whether one string is less than the other.
     */
    public static final IComparisonOperator LESS;
    /**
     * A concrete strategy that examines whether one string is less or equals to  the other.
     */
    public static final IComparisonOperator LESS_OR_EQUALS;
    /**
     * A concrete strategy that examines whether one string is greater than the other.
     */
    public static final IComparisonOperator GREATER;
    /**
     * A concrete strategy that examines whether one string is greater or equals to the other.
     */
    public static final IComparisonOperator GREATER_OR_EQUALS;
    /**
     * A concrete strategy that examines whether one string is equals to the other.
     */
    public static final IComparisonOperator EQUALS;
    /**
     * A concrete strategy that examines whether one string is not equals to the other.
     */
    public static final IComparisonOperator NOT_EQUALS;
    /**
     * A concrete strategy that examines whether one string is "like" other.
     */
    public static final IComparisonOperator LIKE;

    static {
        LESS = (s1, s2) -> s1.compareTo(s2) < 0;
        LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;
        GREATER = (s1, s2) -> s1.compareTo(s2) > 0;
        GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;
        EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;
        NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;
        LIKE = (s1, s2) -> s1.matches(s2.replace("*", "[0-9A-Za-zŽžČčĆćŠšĐđ -]*"));

    }
}
