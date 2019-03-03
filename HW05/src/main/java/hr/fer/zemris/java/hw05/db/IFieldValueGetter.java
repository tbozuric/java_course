package hr.fer.zemris.java.hw05.db;

/**
 * This interface is responsible for obtaining a requested field value from given {@link StudentRecord}.
 */
public interface IFieldValueGetter {
    /**
     * Returns the requested field within the student record.
     * Requested fields can be : "jmbag" , "firstName" , "lastName".
     *
     * @param record student record.
     * @return the requested field in string format.
     */
    String get(StudentRecord record);
}
