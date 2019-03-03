package hr.fer.zemris.java.blog.crypto;

import java.io.IOException;
import java.nio.file.Path;

/**
 * An interface that provides a method for computing a particular file digest.
 */
public interface Digester {
    /**
     * Calculates the file digest.
     *
     * @param file whose digest we want to calculate.
     * @return digest of the given file as a byte array.
     * @throws IOException if it is not possible to open the file.
     */
    byte[] digest(Path file) throws IOException;

    /**
     * Calculates the digest from the given text.
     *
     * @param text whose digest we want to calculate.
     * @return digest of the given file as a byte array.
     */
    byte[] digest(String text);
}
