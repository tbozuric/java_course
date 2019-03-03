package hr.fer.zemris.java.hw16.trazilica.vectorization;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Represents an interface that provides methods for vectorization of a document.
 * For example, we can use binary representation of the document, TF-IDF representation, term-frequency representation...
 */
public interface Vectorization {

    /**
     * Returns the list of vectorized documents.
     *
     * @param vocabulary    the vocabulary.
     * @param documentsRoot the path to the root document directory.
     * @return the list of vectorized documents.
     */
    List<VectorizedDocument> documentsAsVector(Set<String> vocabulary, Path documentsRoot);

    /**
     * Returns the vectorized   representation of the given temporary document.
     *
     * @param vocabulary   the vocabulary.
     * @param tempDocument the temporary document.
     * @return the vectorized representation of the given temporary document.
     */
    double[] query(Set<String> vocabulary, List<String> tempDocument);
}
