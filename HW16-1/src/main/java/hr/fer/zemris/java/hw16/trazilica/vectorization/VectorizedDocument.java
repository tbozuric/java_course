package hr.fer.zemris.java.hw16.trazilica.vectorization;

import java.nio.file.Path;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * This class represents a vectorized document.
 */
public class VectorizedDocument {

    /**
     * The threshold for comparing number with zero.
     */
    private static double THRESHOLD = 1e-2;

    /**
     * The path to the document.
     */
    private Path path;

    /**
     * The vector representation of the document.
     */
    private double[] vector;

    /**
     * Creates an instance of {@link VectorizedDocument}.
     *
     * @param path   the path to the file.
     * @param vector the vector representation of the document.
     */
    public VectorizedDocument(Path path, double[] vector) {
        this.path = path;
        this.vector = vector;
    }

    /**
     * Returns the path to the document.
     *
     * @return the path to the document.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Sets the path to the document.
     *
     * @param path the path to the document.
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Returns the vector representation of the document.
     *
     * @return the vector representation of the document.
     */
    public double[] getVector() {
        return vector;
    }

    /**
     * Sets the vector representation of the document.
     *
     * @param vector the vector representation of the document.
     */
    public void setVector(double[] vector) {
        this.vector = vector;
    }

    /**
     * Returns the cosine similarity between two vectors.
     *
     * @param other the vector representing some another document.
     * @return the cosine similarity between two vectors.
     * @throws IllegalArgumentException if the given vector is a null reference or wrong dimensions.
     * @see <a href="https://en.wikipedia.org/wiki/Cosine_similarity">Cosine similarity</a>
     */
    public double cosineSimilarity(double[] other) {
        if (other == null || vector.length != other.length) {
            throw new IllegalArgumentException("Argument is illegal.");
        }
        double numerator = 0.0;
        double denominator;
        for (int i = 0; i < vector.length; i++) {
            if (!isZero(vector[i]) && !isZero(other[i])) {
                numerator += vector[i] * other[i];
            }
        }
        denominator = getNorm(vector) * getNorm(other);
        return numerator / denominator;
    }

    /**
     * The norm (L2) of the vector.
     *
     * @param vector the input vector.
     * @return the (L2) norm of the vector.
     */
    private double getNorm(double[] vector) {
        double result = 0.0;

        for (double item : vector) {
            if (!isZero(item)) {
                result += pow(item, 2.0);
            }
        }
        result = sqrt(result);
        return result;
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
}
