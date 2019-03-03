package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Demonstration program that illustrates database operation using streams.
 */
public class StudentDemo {
    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        List<StudentRecord> records = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/studenti.txt"), StandardCharsets.UTF_8);
            records = convert(lines);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Input file is not in valid format!");
            System.exit(1);
        }

        long broj1 = vratiBodovaViseOd25(records);
        System.out.println("Broj studenata sa više od 25 bodova: " + broj1);

        long broj5 = vratiBrojOdlikasa(records);
        System.out.println("Broj odlikaša: " + broj5);


        List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
        List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);

        List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
        Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
        Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
        Map<Boolean, List<StudentRecord>> prolazNeProlaz = razvrstajProlazPad(records);


        System.out.println("Odlikaši: ");
        odlikasi.forEach(System.out::println);


        System.out.println("Odlikaši sortirano: ");
        odlikasiSortirano.forEach(System.out::println);


        System.out.println("Nepoloženi jmbagovi: ");
        nepolozeniJMBAGovi.forEach(System.out::println);
        System.out.println(nepolozeniJMBAGovi.size());

        System.out.println("Mapa po ocjenama: ");
        for (Map.Entry<Integer, List<StudentRecord>> entry : mapaPoOcjenama.entrySet()) {
            System.out.println(entry.getKey() + ":");
            entry.getValue().forEach(System.out::println);
        }

        System.out.println("Broj studenata po ocjenama: ");
        for (Map.Entry<Integer, Integer> entry : mapaPoOcjenama2.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("Prolaz/pad: ");
        for (Map.Entry<Boolean, List<StudentRecord>> entry : prolazNeProlaz.entrySet()) {
            if (entry.getKey()) {
                System.out.println("Prolaz: ");
            } else {
                System.out.println("Pad: ");
            }
            entry.getValue().forEach(System.out::println);
        }


    }

    /**
     * Returns a list of students sorted by pass / fail course.
     *
     * @param records list of student records.
     * @return a list of students sorted by pass / fail course.
     */
    private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
        return records.stream()
                .collect(Collectors.partitioningBy(s -> s.getFinalGrade() > 1));
    }

    /**
     * Returns the number of students by grades.
     *
     * @param records list of student records.
     * @return the number of students by grades.
     */
    private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
        return records.stream()
                .collect(Collectors.toMap(StudentRecord::getFinalGrade, s -> 1, (s1, s2) -> s1 + s2));
    }

    /**
     * Returns a list of students by grades.
     *
     * @param records list of student records.
     * @return a list of students by grades.
     */
    private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
    }

    /**
     * A list of jmbags from students who have not passed the course.
     *
     * @param records list of student records.
     * @return list of jmbags from students who have not passed the course.
     */
    private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
        return records.stream()
                .filter(s -> s.getFinalGrade() == 1)
                .map(StudentRecord::getJMBAG).sorted()
                .collect(Collectors.toList());
    }

    /**
     * Returns the sorted list of students with excellent grades sorted by the number of points.
     *
     * @param records list of student records.
     * @return the sorted list of students with excellent grades sorted by the number of points.
     */
    private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
        return records.stream()
                .filter(s -> s.getFinalGrade() == 5)
                .sorted((s1, s2) -> Double.compare(sumOfPoints(s2),
                        sumOfPoints(s1)))
                .collect(Collectors.toList());
    }

    /**
     * Returns students who have passed the course and received a grade 5.
     *
     * @param records list of student records.
     * @return a list of students who received grade 5.
     */
    private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
        return records.stream()
                .filter(s -> s.getFinalGrade() == 5)
                .collect(Collectors.toList());
    }

    /**
     * Returns the number of students who have passed the course and received a grade 5.
     *
     * @param records list of student records.
     * @return number of students who have passed the course and received a grade 5.
     */
    private static long vratiBrojOdlikasa(List<StudentRecord> records) {
        return records.stream()
                .filter(s -> s.getFinalGrade() == 5)
                .count();
    }

    /**
     * The number of students who in the sum of points have more than 25 points.
     *
     * @param records list of student record.
     * @return number of students who in the sum of points have more than 25 points.
     */
    private static long vratiBodovaViseOd25(List<StudentRecord> records) {
        return records.stream()
                .filter(s -> sumOfPoints(s) > 25)
                .count();
    }

    /**
     * Returns the sum of points for some student record.
     *
     * @param record student record.
     * @return number of points for the given student.
     */
    private static double sumOfPoints(StudentRecord record) {
        return record.getPointsOnMidterm() + record.getPointsOnFinalExam() + record.getPointsOnLaboratoryExercises();
    }

    /**
     * Converts the list of lines from the file to the list of student records.
     *
     * @param lines file rows.
     * @return list of student records.
     */
    private static List<StudentRecord> convert(List<String> lines) {
        List<StudentRecord> studentRecords = new ArrayList<>();
        for (String record : lines) {
            record = record.trim().replaceAll("\\s+", " ");
            if (record.length() == 0) {
                continue;
            }
            String[] recordAsArray = record.split(" ");
            StudentRecord studentRecord;
            studentRecord = new StudentRecord(recordAsArray[0], recordAsArray[1], recordAsArray[2], Double.parseDouble(recordAsArray[3]),
                    Double.parseDouble(recordAsArray[4]), Double.parseDouble(recordAsArray[5]), Integer.parseInt(recordAsArray[6]));
            studentRecords.add(studentRecord);
        }
        return studentRecords;
    }
}
