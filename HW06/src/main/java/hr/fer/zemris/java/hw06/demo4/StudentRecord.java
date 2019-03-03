package hr.fer.zemris.java.hw06.demo4;

import java.util.Objects;

/**
 * Represents one record of the student. Each record consists of JMBAG , first name , last name, points on midterm,
 * points on final exam, points on laboratory exercises  and final grade of the student.
 */
public class StudentRecord {
    /**
     * Student's JMBAG.
     */
    private String JMBAG;

    /**
     * Student's last name.
     */
    private String lastName;

    /**
     * Student's first name.
     */
    private String firstName;
    /**
     * Student's points on midterm.
     */
    private double pointsOnMidterm;
    /**
     * Student's points on final exam.
     */
    private double pointsOnFinalExam;
    /**
     * Student's points on laboratory exercises.
     */
    private double pointsOnLaboratoryExercises;

    /**
     * Student's final grade on the course.
     */
    private int finalGrade;

    /**
     * Creates an instance of student record. Each instance contains jmbag, first name , last name and final grade.
     *
     * @param JMBAG                       student's jmbag.
     * @param firstName                   student's first name.
     * @param lastName                    student's last name.
     * @param pointsOnMidterm             student's points on midterm.
     * @param pointsOnFinalExam           student's points on final exam.
     * @param pointsOnLaboratoryExercises student's points on laboratory exercises.
     * @param finalGrade                  student's final grade.
     */
    public StudentRecord(String JMBAG, String lastName, String firstName, double pointsOnMidterm, double pointsOnFinalExam, double pointsOnLaboratoryExercises, int finalGrade) {
        this.JMBAG = JMBAG;
        this.lastName = lastName;
        this.firstName = firstName;
        this.pointsOnMidterm = pointsOnMidterm;
        this.pointsOnFinalExam = pointsOnFinalExam;
        this.pointsOnLaboratoryExercises = pointsOnLaboratoryExercises;
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
     * Returns student's last name.
     *
     * @return student's last name.
     */
    public String getLastName() {
        return lastName;
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
     * Returns student's points on midterm.
     *
     * @return student's points on midterm.
     */
    public double getPointsOnMidterm() {
        return pointsOnMidterm;
    }

    /**
     * Returns student's points on final exam.
     *
     * @return student's points on final exam.
     */
    public double getPointsOnFinalExam() {
        return pointsOnFinalExam;
    }

    /**
     * Returns student's points on laboratory exercises.
     *
     * @return student's points on laboratory exercises.
     */
    public double getPointsOnLaboratoryExercises() {
        return pointsOnLaboratoryExercises;
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

    /**
     * Returns a string representation of student record.
     * @return a string representation of student record.
     */
    @Override
    public String toString() {
        return "JMBAG='" + JMBAG + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", pointsOnMidterm=" + pointsOnMidterm +
                ", pointsOnFinalExam=" + pointsOnFinalExam +
                ", pointsOnLaboratoryExercises=" + pointsOnLaboratoryExercises +
                ", finalGrade=" + finalGrade;
    }
}
