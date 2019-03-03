package hr.fer.zemris.java.hw07.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;

/**
 * Allows the user to encrypt/ decrypt files using the AES128 crypto-algorithm in CBC mode.
 */
public class AesCbcCrypter implements Crypter {
    /**
     * Buffer size.
     */
    private static final int BUFFER_SIZE = 1024;
    /**
     * Cipher.
     */
    private Cipher cipher;
    /**
     * Secret key specification.
     */
    private SecretKeySpec keySpec;
    /**
     * Algorithm parameter specification.
     */
    private AlgorithmParameterSpec paramSpec;

    /**
     * Creates an instance of {@link AesCbcCrypter}.
     *
     * @param keyText key as hex-encoded string.
     * @param ivText  initialization vector.
     * @throws AesCrypterException if it is not possible to create a new instance of cipher.
     */
    public AesCbcCrypter(String keyText, String ivText) {
        keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
        paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new AesCrypterException("An error occurred while creating a cipher");
        }
    }

    /**
     * Encrypts/Decrypts the given file using the AES128 crypto-algorithm in CBC mode with padding.
     *
     * @param source      source file.
     * @param destination destination file.
     * @param encrypt     true if we want to encrypt the file.
     * @throws IOException if it is not possible to open the file or write to the destination file.
     */
    @Override
    public void crypt(Path source, Path destination, boolean encrypt) throws IOException {

        try {
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new AesCrypterException("Cipher initialization error!");
        }

        try {
            InputStream is = Files.newInputStream(source, StandardOpenOption.READ);
            OutputStream os = Files.newOutputStream(destination, StandardOpenOption.CREATE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = is.read(buffer)) != -1) {
                os.write(cipher.update(buffer, 0, read));
            }
            os.write(cipher.doFinal());
            is.close();
            os.flush();
            os.close();
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new AesCrypterException("An error occurred during the encryption/decryption.");
        }
    }
}
