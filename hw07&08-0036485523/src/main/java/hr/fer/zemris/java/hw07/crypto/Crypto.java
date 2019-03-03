package hr.fer.zemris.java.hw07.crypto;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Allows the user to encrypt/decrypt given file using the AES crypto- algorithm and the 128-bit encryption key
 * or calculate and check the SHA-256 file digest.
 */
public class Crypto {
    /**
     * A keyword for calculating a file digest.
     */
    private static final String CHECK_SHA = "checksha";
    /**
     * A keyword for encrypting a file.
     */
    private static final String ENCRYPT = "encrypt";
    /**
     * A keyword for decrypting a file.
     */
    private static final String DECRYPT = "decrypt";
    /**
     * The expected length of the hex-encoded string.
     */
    private static final int HEX_ENCODED_TEXT_LENGTH = 32;

    /**
     * Method invoked when running the program.
     *
     * @param args arguments that must  be in format :
     *             "checksha path_to_file" ;
     *             "encrypt source_file destination_file";
     *             "decrypt crypted_file destination_file".
     */
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Invalid number of arguments!");
            return;
        }
        Scanner sc = new Scanner(System.in);

        switch (args[0]) {
            case CHECK_SHA:
                if (args.length != 2) {
                    System.out.println("Invalid input!");
                    return;
                }
                checkShaDigest(Paths.get(args[1].trim()).toAbsolutePath().normalize(), sc);
                break;
            case ENCRYPT:
                if (!checkArguments(args)) {
                    return;
                }
                encryptOrDecrypt(Paths.get(args[1].trim()).toAbsolutePath().normalize(),
                        Paths.get(args[2].trim()).toAbsolutePath().normalize(), sc, true);
                break;
            case DECRYPT:
                if (!checkArguments(args)) {
                    return;
                }
                encryptOrDecrypt(Paths.get(args[1].trim()).toAbsolutePath().normalize(),
                        Paths.get(args[2].trim()).toAbsolutePath().normalize(), sc, false);
                break;
            default:
                System.out.println("Invalid arguments! Allowed arguments are : checksha, encrypt , decrypt.");
        }
        sc.close();
    }

    /**
     * Checks if the user has entered the correct number of arguments.
     *
     * @param args arguments.
     * @return true if the user has entered the correct number of arguments.
     */
    private static boolean checkArguments(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid input!");
            return false;
        }
        return true;
    }

    /**
     * Encrypts/decrypts the given file using the AES crypto-algorithm and the 128-bit encryption key.
     *
     * @param source      path to source file.
     * @param destination path to destination file.
     * @param sc          reference to scanner for reading the input.
     * @param encrypt     flag indicating whether we want to encrypt or decrypt.
     */
    private static void encryptOrDecrypt(Path source, Path destination, Scanner sc, boolean encrypt) {
        String keyText;
        String ivText;

        System.out.println("Please provide password as hex-encoded text(16 bytes , i.e. 32 hex-digits) : ");
        System.out.print("> ");
        keyText = loadParameter(sc, "hex-encoded password");

        System.out.println("Please provide initialization vector as hex-encoded text(16 bytes , i.e. 32 hex-digits) : ");
        System.out.print("> ");
        ivText = loadParameter(sc, "initialization vector");

        try {
            AesCbcCrypter aes = new AesCbcCrypter(keyText, ivText);
            aes.crypt(source, destination, encrypt);
            String operation = encrypt ? "Encryption" : "Decryption";
            System.out.println(operation + " completed. Generated file " + destination + "  based on file " + source + ".");
        } catch (IOException e) {
            System.out.println("Please check the paths to the files!");
        } catch (AesCrypterException e) {
            System.out.println("An error occurred during encrypting/decrypting. Please try again later!");
        }

    }

    /**
     * Auxiliary method for loading parameters.
     *
     * @param sc        reference to scanner for reading the input.
     * @param parameter string representation of parameter that we want to load.
     * @return loaded parameter.
     */
    private static String loadParameter(Scanner sc, String parameter) {
        String text;
        while (true) {
            text = sc.nextLine();
            if (!Util.isValidHexEncodedString(text) || text.length() != HEX_ENCODED_TEXT_LENGTH) {
                System.out.println("Entered " + parameter + " is in a illegal format. Please try again!");
                System.out.print("> ");
                continue;
            }
            break;
        }
        return text;
    }

    /**
     * Calculates and checks the SHA-256 file digest.
     *
     * @param source path to source file.
     * @param sc     reference to scanner for reading the input.
     */
    private static void checkShaDigest(Path source, Scanner sc) {

        System.out.println("Please provide expected sha-256 digest for " + source + ":");
        System.out.print("> ");
        String hex;
        while (true) {
            hex = sc.nextLine();
            if (!Util.isValidHexEncodedString(hex)) {
                System.out.println("Entered sha-256 digest is in a illegal format. Please try again!");
                System.out.print("> ");
                continue;
            }
            break;
        }
        try {
            Digester shaDigester = new SHADigester();
            String hash = Util.bytesToHex(shaDigester.digest(source));
            if (hash.equals(hex)) {
                System.out.println("Digesting completed. Digest of " + source + " matches expected digest.");
                return;
            }
            System.out.println("Digesting completed. Digest of " + source + " does not match the expected digest. Digest\n" +
                    "was: " + hash);
        } catch (ShaDigestException e) {
            System.out.println("An error occurred while creating a hash algorithm!");
        } catch (IOException e) {
            System.out.println("Please check the path to the file!");
        }
    }
}