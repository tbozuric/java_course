package hr.fer.zemris.java.hw07.crypto;

import java.io.IOException;
import java.nio.file.Path;

/**
 * An interface that allows encrypting / decrypting of given files.
 */
public interface Crypter {
    /**
     * This method encrypts / decrypts the file depending on the encrypt flag.
     *
     * @param source      source file.
     * @param destination destination file.
     * @param encrypt     true if we want to encrypt the file.
     * @throws IOException if it is not possible to open the file or write to the destination file.
     */
    void crypt(Path source, Path destination, boolean encrypt) throws IOException;
}
