package hr.fer.zemris.java.hw16.trazilica.vectorization;

import hr.fer.zemris.java.hw16.trazilica.AbstractVisitor;
import hr.fer.zemris.java.hw16.trazilica.UtilDocument;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents the TF-IDF representation of the document.
 * For more information:
 *
 * @see <a href="https://en.wikipedia.org/wiki/Tf%E2%80%93idf">TF-IDF</a>
 */
public class TFIDFCalculator implements Vectorization {

    /**
     * The vector representation of the document.
     */
    private double[] idf;

    /**
     * The number of documents.
     */
    private int counter = 0;

    @Override
    public List<VectorizedDocument> documentsAsVector(Set<String> vocabulary, Path documentsRoot) {
        if (!documentsRoot.toFile().exists() || !documentsRoot.toFile().isDirectory() || vocabulary == null) {
            return null;
        }

        List<String> vocabularyList = new ArrayList<>(vocabulary);

        List<VectorizedDocument> vectorizedDocuments = new ArrayList<>();
        int size = vocabulary.size();
        idf = new double[size];
        counter = 0;

        try {
            Files.walkFileTree(documentsRoot, new AbstractVisitor() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            List<String> words = UtilDocument.parseDocument(file);
                            double[] tf = new double[size];
                            for (int i = 0; i < size; i++) {
                                String word = vocabularyList.get(i);
                                tf[i] = Collections.frequency(words, word);
                                idf[i] = tf[i] > 0 ? idf[i] + 1 : idf[i];
                            }
                            vectorizedDocuments.add(new VectorizedDocument(file, tf));
                            counter++;
                            return FileVisitResult.CONTINUE;
                        }
                    }
            );
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            return null;
        }

        for (int i = 0; i < size; i++) {
            idf[i] = Math.log((double) counter / idf[i]);
        }

        for (int i = 0; i < counter; i++) {
            double[] tf = vectorizedDocuments.get(i).getVector();
            for (int j = 0; j < size; j++) {
                tf[j] = tf[j] * idf[j];
            }
            vectorizedDocuments.get(i).setVector(tf);
        }
        return vectorizedDocuments;
    }

    @Override
    public double[] query(Set<String> vocabulary, List<String> tempDocument) {
        List<String> vocabularyList = new ArrayList<>(vocabulary);

        int size = vocabulary.size();
        double[] tfIdf = new double[size];

        for (int i = 0; i < size; i++) {
            tfIdf[i] = Collections.frequency(tempDocument, vocabularyList.get(i));
        }

        for (int i = 0; i < size; i++) {
            tfIdf[i] = tfIdf[i] * idf[i];
        }
        return tfIdf;
    }
}
