package hr.fer.zemris.java.hw16.trazilica.commands;

import hr.fer.zemris.java.hw16.trazilica.Engine;
import hr.fer.zemris.java.hw16.trazilica.vectorization.TFIDFCalculator;
import hr.fer.zemris.java.hw16.trazilica.vectorization.VectorizedDocument;

import java.util.List;
import java.util.Set;

/**
 * Represents the command that performs a search. Prints the top 10 results (according to TF-IDF criteria).
 * The output is ranked by similarity. Each row contains a row number, similarity (four decades) and path to the document.
 */
public class QueryCommand implements Command {
    /**
     * Represents the TF-IDF calculator.
     */
    private TFIDFCalculator tfIdf;

    /**
     * Represents a list of vectorized documents.
     */
    private List<VectorizedDocument> vectorizedDocuments;

    /**
     * Represents a vocabulary (the words used in a language)
     */
    private Set<String> vocabulary;

    /**
     * Creates an instance of {@link QueryCommand}.
     *
     * @param tfIdf               the TF-IDF calculator.
     * @param vectorizedDocuments a list of vectorized documents.
     * @param vocabulary          the words used in a language.
     */
    public QueryCommand(TFIDFCalculator tfIdf, List<VectorizedDocument> vectorizedDocuments, Set<String> vocabulary) {
        this.tfIdf = tfIdf;
        this.vectorizedDocuments = vectorizedDocuments;
        this.vocabulary = vocabulary;
    }

    @Override
    public CommandStatus executeCommand(String arguments) {
        Engine engine = Engine.getInstance();
        String[] items = arguments.trim().replaceAll("\\s+", " ").split(" ");
        List<String> filteredItems = engine.process(items, vocabulary);

        if (filteredItems.size() == 0) {
            System.out.println("No results.");
            return CommandStatus.CONTINUE;
        }

        double[] vector = tfIdf.query(vocabulary, filteredItems);
        engine.setTopSimilarities(engine.findMostSimilarDocuments(vector, vectorizedDocuments));
        engine.executeQuery(filteredItems, engine.getTopSimilarities());
        System.out.println(engine.getLastResultByQueryCommand());

        return CommandStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "query";
    }
}
