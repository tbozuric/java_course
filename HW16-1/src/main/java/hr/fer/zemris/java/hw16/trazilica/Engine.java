package hr.fer.zemris.java.hw16.trazilica;

import hr.fer.zemris.java.hw16.trazilica.vectorization.VectorizedDocument;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents the engine to calculate the similarity of queries with vectorized documents.
 */
public class Engine {

    /**
     * The engine.
     */
    private static Engine engine = new Engine();

    /**
     * The maximum number of results that returns the query.
     */
    private static final int MAX_NUMBER_OF_RESULTS = 10;

    /**
     * The threshold for comparing number with zero.
     */
    private static double THRESHOLD = 1e-6;


    /**
     * Represents the last remembered result obtained with the
     * {@link hr.fer.zemris.java.hw16.trazilica.commands.QueryCommand}
     */

    private String lastResultByQueryCommand;

    /**
     * Represents a list of the most similar documents under the TF-IDF criterion.
     */
    private List<SimilarityTuple> topSimilarities;

    /**
     * Creates an {@link Engine}
     */
    private Engine() {
    }

    /**
     * Returns the singleton instance to the engine.
     *
     * @return the singleton instance to the engine.
     */
    public static Engine getInstance() {
        return engine;
    }


    /**
     * Returns the string with the top 10 results(or less if there are not so many results)
     *
     * @param query           the query
     * @param topSimilarities the ranked list of results based on document similarities.
     * @return the string with the top 10 results.
     */
    public String executeQuery(List<String> query, List<SimilarityTuple> topSimilarities) {
        int i = 0;
        StringBuilder result = new StringBuilder();
        result.append("Query is: ");
        result.append(query);
        result.append("\nNajboljih 10 rezultata:\n");

        for (SimilarityTuple tuple : topSimilarities) {
            result.append(String.format("[%d] (%.4f) %s\n", i++, tuple.similarity, tuple.document.getPath()));
        }

        lastResultByQueryCommand = result.toString();

        return lastResultByQueryCommand;
    }

    /**
     * Returns a sorted list of the most similar documents.
     *
     * @param vector              the tf-idf vector
     * @param vectorizedDocuments the list of vectorized documents.
     * @return the list of maximum 10 best results.
     */
    public List<SimilarityTuple> findMostSimilarDocuments(double[] vector, List<VectorizedDocument> vectorizedDocuments) {
        List<SimilarityTuple> temp = new ArrayList<>();
        for (VectorizedDocument document : vectorizedDocuments) {
            temp.add(new SimilarityTuple(document, document.cosineSimilarity(vector)));
        }
        temp = temp.stream().sorted().collect(Collectors.toList());

        List<SimilarityTuple> result = new ArrayList<>();
        int numberOfElements = 0;

        for (SimilarityTuple tuple : temp) {
            if (numberOfElements >= MAX_NUMBER_OF_RESULTS || isZero(tuple.similarity)) {
                break;
            }
            result.add(tuple);
            numberOfElements++;
        }
        return result;
    }

    /**
     * Returns the query arguments that appear in the vocabulary.
     *
     * @param items      the parts of the query.
     * @param vocabulary the vocabulary.
     * @return the query arguments that appear in the vocabulary.
     */
    public List<String> process(String[] items, Set<String> vocabulary) {
        List<String> queryItems = new ArrayList<>();
        for (String item : items) {
            if (vocabulary.contains(item)) {
                queryItems.add(item);
            }
        }
        return queryItems;
    }

    /**
     * Returns the created vocabulary based on found documents.
     *
     * @param path      the path to all documents from which we build a vocabulary.
     * @param stopWords the stop-words.
     * @return the created vocabulary based on found documents.
     * @throws IOException if an error occurs while reading the file.
     */
    public Set<String> createVocabulary(Path path, Set<String> stopWords) throws IOException {
        Set<String> vocabulary = new HashSet<>();
        Files.walkFileTree(path, new AbstractVisitor() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                vocabulary.addAll(UtilDocument.parseDocument(file));
                return FileVisitResult.CONTINUE;
            }
        });
        vocabulary.removeAll(stopWords);
        return vocabulary;
    }

    /**
     * Checks if the given number is equal to zero.
     *
     * @param value the value checking.
     * @return true if the given number is equal to zero.
     */
    private static boolean isZero(double value) {
        return value >= -THRESHOLD && value <= THRESHOLD;
    }

    /**
     * Returns the last remembered result obtained by {@link hr.fer.zemris.java.hw16.trazilica.commands.QueryCommand}.
     *
     * @return the last remembered result.
     */
    public String getLastResultByQueryCommand() {
        return lastResultByQueryCommand;
    }

    /**
     * Sets the last remembered result obtained by {@link hr.fer.zemris.java.hw16.trazilica.commands.QueryCommand}.
     *
     * @param lastQueryResult the result from the last query command.
     */
    public void setLastResultByQueryCommand(String lastQueryResult) {
        this.lastResultByQueryCommand = lastQueryResult;
    }

    /**
     * Returns a list of the most similar documents under the TF-IDF criterion.
     *
     * @return a list of the most similar documents under the TF-IDF criterion.
     */
    public List<SimilarityTuple> getTopSimilarities() {
        return topSimilarities;
    }

    /**
     * Sets a list od the most similar documents under the TF-IDF criterion.
     *
     * @param topSimilarities a list of the most similar documents.
     */
    public void setTopSimilarities(List<SimilarityTuple> topSimilarities) {
        this.topSimilarities = topSimilarities;
    }


    /**
     * Represents a tuple of documents and their similarities with the document built from the query.
     */
    public static class SimilarityTuple implements Comparable<SimilarityTuple> {
        /**
         * The vectorized document {@link VectorizedDocument}.
         */
        private VectorizedDocument document;

        /**
         * The similarity to the document built from the query..
         */
        private double similarity;

        /**
         * Creates an instance of {@link SimilarityTuple}.
         *
         * @param document   the vectorized document.
         * @param similarity the similarity to query items.
         */
        public SimilarityTuple(VectorizedDocument document, double similarity) {
            this.document = document;
            this.similarity = similarity;
        }

        @Override
        public int compareTo(SimilarityTuple o) {
            return Double.compare(o.similarity, similarity);
        }

        /**
         * Returns the document.
         *
         * @return the document.
         */
        public VectorizedDocument getDocument() {
            return document;
        }

        /**
         * Returns the similarity.
         *
         * @return the similarity.
         */
        public double getSimilarity() {
            return similarity;
        }

    }
}
