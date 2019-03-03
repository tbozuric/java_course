package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Represents one record of the student. Each record consists of JMBAG , first name . last name and final grade of the student.
 */
public class StudentRecord {
    /**
     * Student's JMBAG.
     */
    private String JMBAG;
    /**
     * Student's first name.
     */
    private String firstName;
    /**
     * Student's last name.
     */
    private String lastName;
    /**
     * Student's final grade on the course.
     */
    private int finalGrade;

    /**
     * Creates an instance of student record. Each instance contains jmbag, first name , last name and final grade.
     *
     * @param JMBAG      student's jmbag.
     * @param firstName  student's first name.
     * @param lastName   student's last name.
     * @param finalGrade student's final grade.
     */
    public StudentRecord(String JMBAG, String firstName, String lastName, int finalGrade) {
        this.JMBAG = JMBAG;
        this.firstName = firstName;
        this.lastName = lastName;
        this.finalGrade = finalGrade;
    }

    /**
     * Returns student's jmbag.
     *
     * @return student's jmbag.
     */
    public String getJMBAG() {
        return JMBAG;
    }

    /**
     * Returns student's first name.
     *
     * @return student's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns student's last name.
     *
     * @return student's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns student's final grade on the course.
     *
     * @return student's final grade on the course.
     */
    public int getFinalGrade() {
        return finalGrade;
    }

    /**
     * Student records are equals if they are equal to their jmbag.
     *
     * @param o other student record.
     * @return whether student records are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return Objects.equals(JMBAG, that.JMBAG);
    }

    /**
     * Returns hash code of student record.
     *
     * @return hash code of student record.
     */
    @Override
    public int hashCode() {
        return Objects.hash(JMBAG);
    }
}
