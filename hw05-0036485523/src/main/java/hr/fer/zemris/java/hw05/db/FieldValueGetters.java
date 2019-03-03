package hr.fer.zemris.java.hw05.db;

/**
 * Represents concrete strategies for each field value {@link IFieldValueGetter}.
 */
public class FieldValueGetters {
    /**
     * Represents a concrete strategy for getting a student name.
     */
    public static final IFieldValueGetter FIRST_NAME;
    /**
     * Represents a concrete strategy for getting a student last name.
     */
    public static final IFieldValueGetter LAST_NAME;
    /**
     * Represents a concrete strategy for getting a student JMBAG.
     */
    public static final IFieldValueGetter JMBAG;


    static {
        FIRST_NAME = StudentRecord::getFirstName;
        LAST_NAME = StudentRecord::getLastName;
        JMBAG = StudentRecord::getJMBAG;
    }
}
