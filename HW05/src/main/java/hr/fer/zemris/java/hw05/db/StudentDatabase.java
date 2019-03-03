package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a database of students. A student is represented by his/her name, surname, jmbag and final grade on the course.
 */
public class StudentDatabase {

    /**
     * List of all student records in database.
     */
    private List<StudentRecord> studentRecords;
    /**
     * Index to obtain a student record using the index object in the complexity of O(1).
     */
    private SimpleHashtable<String, StudentRecord> index;

    /**
     * Creates an instance of student database from the given records.
     *
     * @param records student records.
     */
    public StudentDatabase(List<String> records) {
        studentRecords = new ArrayList<>();
        index = new SimpleHashtable<>(records.size());
        processRecords(records);
    }

    /**
     * Processes student records and stores them in simple hash table and list of all student records.
     *
     * @param records student records.
     */
    private void processRecords(List<String> records) {
        for (String record : records) {
            record = record.trim().replaceAll("\\s+", " ");
            String[] recordAsArray = record.split(" ");
            StudentRecord studentRecord;
            // "normal" format
            if (recordAsArray.length == 4) {
                studentRecord = new StudentRecord(recordAsArray[0], recordAsArray[2], recordAsArray[1], Integer.parseInt(recordAsArray[3]));
            } else {
                //if a person has a two surnames
                studentRecord = new StudentRecord(recordAsArray[0], recordAsArray[3], recordAsArray[1] + " " + recordAsArray[2], Integer.parseInt(recordAsArray[4]));
            }
            studentRecords.add(studentRecord);
            index.put(recordAsArray[0], studentRecord);
        }
    }

    /**
     * Uses index to obtain requested record in O(1).If record does not exists, the method returns null .
     *
     * @param jmbag jmbag of student.
     * @return a record of a student who has a given jmbag.
     */
    public StudentRecord forJMBAG(String jmbag) {
        return index.get(jmbag);
    }

    /**
     * Accepts a reference to an object which is an instance of IFilter interface and adds student records to the temporary list if they passing through  the filter.
     *
     * @param filter some filter, instance of IFilter interface.
     * @return a list of students passing through the filter.
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> filteredStudents = new ArrayList<>();

        for (StudentRecord record : studentRecords) {
            if (filter.accepts(record)) {
                filteredStudents.add(record);
            }
        }
        return filteredStudents;
    }


}
