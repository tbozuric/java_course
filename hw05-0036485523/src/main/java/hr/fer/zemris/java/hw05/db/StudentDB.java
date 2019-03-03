package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a demonstration program for working with the database.
 * Symbol for prompt which program writes out is “ > ”.
 * This is one example of a table output after a query has been executed:
 *           +============+========+========+===+
 *           | 0000000003 | Bosnić | Andrea | 4 |
 *           +============+========+========+===+
 * Queries are in format > query jmbag = "0000000003" , or query jmbag = "0000000003" AND lastName LIKE "B*" ...
 */
public class StudentDB {
    /**
     * Length of jmbag with two spaces. Length of jmbag is 10.
     */
    private static final int LENGTH_OF_JMBAG_WITH_SPACES = 12;
    /**
     * The number of spaces before and after attribute printing.
     */
    private static final int NUMBER_OF_SPACES = 2;
    /**
     * Message when leaving the program. We exit program with "exit" command.
     */
    private static final String GOODBYE_MESSAGE = "Goodbye!";
    /**
     * Represents exit command.
     */
    private static final String EXIT_COMMAND = "exit";
    /**
     * Symbol for prompt.
     */
    private static final String BEGINNING_LINE_SYMBOL = "> ";
    /**
     * Represents the keyword that must start eqch query.
     */
    private static final String KEYWORD_QUERY = "query";
    /**
     * The number that we print if there is no record for the entered query.
     */
    private static final int NO_RECORDS = 0;
    /**
     * The number that we print if there is only one record for the entered query.
     */
    private static final int ONE_RECORD = 1;
    /**
     * Separator for the attributes in the table.
     */
    private static final String SEPARATOR = " | ";
    /**
     * Part of the header/footer of the table.
     */
    private static final char LINE_PART = '=';
    /**
     * Separator for different columns.
     */
    private static final String COLUMN_SEPARATOR = "+";
    /**
     * Grade column header/footer.
     */
    private static final String GRADE_COLUMN = "+===+";

    /**
     * Method invoked when running the program.
     * Reads data from database and prints appropriate message for each query.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        StudentDatabase database = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
            database = new StudentDatabase(lines);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


        Scanner scanner = new Scanner(System.in);
        String line;


        while (true) {
            System.out.print(BEGINNING_LINE_SYMBOL);
            line = scanner.nextLine();
            if (line.equals(EXIT_COMMAND)) {
                System.out.println(GOODBYE_MESSAGE);
                break;
            }
            if (!line.trim().startsWith(KEYWORD_QUERY)) {
                System.out.println("Every query must start with keyword \"query\"");
                continue;
            }
            line = line.trim().replace(KEYWORD_QUERY, "");
            try {
                QueryParser parser = new QueryParser(line);
                int maximumLengthOfFirstName;
                int maximumLengthOfLastName;

                if (parser.isDirectQuery()) {
                    StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
                    if (r != null) {
                        System.out.println("Using index for records retrieval.");

                        maximumLengthOfLastName = r.getLastName().length();
                        maximumLengthOfFirstName = r.getFirstName().length();

                        printHeaderOrFooter(maximumLengthOfLastName, maximumLengthOfFirstName);
                        printStudentInfo(r, maximumLengthOfLastName, maximumLengthOfFirstName);
                        printHeaderOrFooter(maximumLengthOfLastName, maximumLengthOfFirstName);
                        printNumberOfRecords(ONE_RECORD);

                    } else {
                        printNumberOfRecords(NO_RECORDS);
                    }
                } else {
                    List<StudentRecord> studentRecords = database.filter(new QueryFilter(parser.getQuery()));
                    if (studentRecords.size() == NO_RECORDS) {
                        printNumberOfRecords(NO_RECORDS);
                        continue;
                    }
                    maximumLengthOfFirstName = studentRecords.stream().max(Comparator.comparing(s -> s.getFirstName().length())).get().getFirstName().length();
                    maximumLengthOfLastName = studentRecords.stream().max(Comparator.comparing(s -> s.getLastName().length())).get().getLastName().length();

                    printHeaderOrFooter(maximumLengthOfLastName, maximumLengthOfFirstName);
                    for (StudentRecord r : studentRecords) {
                        printStudentInfo(r, maximumLengthOfLastName, maximumLengthOfFirstName);
                    }
                    printHeaderOrFooter(maximumLengthOfLastName, maximumLengthOfFirstName);
                    printNumberOfRecords(studentRecords.size());
                }
            } catch (QueryParserException ex) {
                System.out.println(" Entered query is in invalid format! Please try again!");
            }

        }
        scanner.close();
    }

    /**
     * Prints a message about the  total number of selected records.
     * @param numberOfRecords total number of selected records.
     */
    private static void printNumberOfRecords(int numberOfRecords) {
        System.out.println("Records selected : " + numberOfRecords);
    }

    /**
     * Prints  the jmbag,  first name, last name and the final grade of student.
     *
     * @param r                        student record.
     * @param maximumLengthOfLastName  longest last name.
     * @param maximumLengthOfFirstName longest first name.
     */
    private static void printStudentInfo(StudentRecord r, int maximumLengthOfLastName, int maximumLengthOfFirstName) {
        System.out.println("|" + " "
                               + r.getJMBAG() + SEPARATOR
                               + r.getLastName()
                               + repeatChar(' ', maximumLengthOfLastName - r.getLastName().length()) + SEPARATOR
                               + r.getFirstName()
                               + repeatChar(' ', maximumLengthOfFirstName - r.getFirstName().length()) + SEPARATOR
                               + r.getFinalGrade() + SEPARATOR);
    }

    /**
     * Prints the header or footer of the record table.
     *
     * @param maximumLengthOfLastName  longest last name.
     * @param maximumLengthOfFirstName longest first name.
     */
    private static void printHeaderOrFooter(int maximumLengthOfLastName, int maximumLengthOfFirstName) {
        System.out.println(COLUMN_SEPARATOR + repeatChar(LINE_PART, LENGTH_OF_JMBAG_WITH_SPACES)
                               + COLUMN_SEPARATOR
                               + repeatChar(LINE_PART, maximumLengthOfLastName + NUMBER_OF_SPACES)
                               + COLUMN_SEPARATOR
                               + repeatChar(LINE_PART, maximumLengthOfFirstName + NUMBER_OF_SPACES)
                               + GRADE_COLUMN);
    }

    /**
     * Returns a string that consists of n same characters.
     *
     * @param c      the character we want to repeat length times.
     * @param length number of repetitions.
     * @return a string that consists of n same characters.
     */
    private static String repeatChar(char c, int length) {
        char[] data = new char[length];
        Arrays.fill(data, c);
        return new String(data);
    }
}
