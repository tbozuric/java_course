package hr.fer.zemris.java.hw16.trazilica;

import hr.fer.zemris.java.hw16.trazilica.commands.*;
import hr.fer.zemris.java.hw16.trazilica.vectorization.TFIDFCalculator;
import hr.fer.zemris.java.hw16.trazilica.vectorization.VectorizedDocument;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * A demonstration program that allows you to find similar documents based on a database of documents.
 * The documents in the database are vectorized,  and using the td-idf method we can find similar documents for each query.
 * <p>
 * Supported commands:
 * <p>
 * <ol>
 * <li> <code>query</code> - performs  a search</li>
 * <li><code>type</code>-receives the number of result and prints the document to the screen</li>
 * <li><code>results</code>-prints to the screen a list of results found by the last-executed "query" command.</li>
 * <li><code>exit</code></li>
 * </ol>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Tf%E2%80%93idf">TF-IDF</a>
 */
public class Konzola {

    /**
     * The error message.
     */
    private static final String ERROR_MESSAGE = "Please enter the path to the article directory as an argument";

    /**
     * The path to the file with croatian stop words.
     */
    private static final String STOP_WORDS_PATH = "src/main/resources/hrvatski_stoprijeci.txt";


    /**
     * The method invoked when running the program.
     *
     * @param args path to document files.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println(ERROR_MESSAGE);
            System.exit(-1);
        }

        String dir = args[0];
        Path path = Paths.get(dir);
        if (!path.toFile().exists() || !path.toFile().isDirectory()) {
            System.out.println(ERROR_MESSAGE);
            System.exit(-1);
        }

        Set<String> stopWords;
        Set<String> vocabulary = null;

        Engine engine = Engine.getInstance();

        try {
            stopWords = new HashSet<>(UtilDocument
                    .parseDocument(Paths.get(STOP_WORDS_PATH)));
            vocabulary = engine.createVocabulary(path, stopWords);
        } catch (IOException e) {
            System.out.println("An error occurred while trying to read the file. please try again later");
            System.exit(-1);
        }

        TFIDFCalculator tfIdf = new TFIDFCalculator();
        List<VectorizedDocument> vectorizedDocuments = tfIdf.documentsAsVector(vocabulary, path);

        String input = "Enter command > ";
        System.out.println("Veličina rječnika je: " + vocabulary.size());


        Scanner sc = new Scanner(System.in);
        CommandStatus status = CommandStatus.CONTINUE;


        SortedMap<String, Command> commands = new TreeMap<>();
        commands.put("exit", new ExitCommand());
        commands.put("query", new QueryCommand(tfIdf, vectorizedDocuments, vocabulary));
        commands.put("results", new ResultsCommand());
        commands.put("type", new TypeCommand());

        while (status != CommandStatus.TERMINATE) {
            System.out.print(input);
            String[] parts = sc.nextLine().trim().split("\\s+");
            String command = parts[0];

            StringBuilder arguments = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                arguments.append(parts[i]).append(" ");
            }

            Command cmd = commands.get(command);
            if (cmd == null) {
                System.out.println("Nepoznata naredba.");
                System.out.println();
            } else {
                if (parts.length > 1) {
                    status = cmd.executeCommand(arguments.toString());
                } else {
                    status = cmd.executeCommand(null);
                }
            }
        }
        sc.close();
    }
}


