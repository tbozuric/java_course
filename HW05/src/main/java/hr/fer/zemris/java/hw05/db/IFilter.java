package hr.fer.zemris.java.hw05.db;

/**
 * This interface filters student records. It accepts only those records that fill the filter.
 */
public interface IFilter {
    /**
     * Checks whether the filter accepts this student record.
     *
     * @param record student record.
     * @return whether the filter accepts this student record.
     */
    boolean accepts(StudentRecord record);
}
