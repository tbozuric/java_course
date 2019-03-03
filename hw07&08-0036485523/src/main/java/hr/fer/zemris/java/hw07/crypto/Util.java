package hr.fer.zemris.java.hw07.crypto;

import java.util.regex.Pattern;

/**
 * Auxiliary class that allows the transformation of hex-encoded strings into bytes and vice versa.
 */
public class Util {
    /**
     * Hexadecimal base.
     */
    private static final int HEX_RADIX = 16;
    /**
     * A shift used to transform bytes into a hex-encoded string.
     */
    private static final int SHIFT = 4;
    /**
     * All hexadecimals values used to transform bytes into a hex-encoded string.
     */
    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    /**
     * Hexadecimal value check pattern.
     */
    private static final Pattern hexEncodedString = Pattern.compile("[0-9A-Fa-f]*");

    /**
     * Takes hex-encoded String and return appropriate byte[]. For zero-length
     * string, method returns zero-length byte array. Method support both uppercase letters and lowercase letters.
     *
     * @param keyText hex-encoded key.
     * @return transformed hex-encoded key to byte array.
     * @throws IllegalArgumentException if the key is not in the correct format.
     */
    public static byte[] hexToByte(String keyText) {
        int length = keyText.length();
        if (length == 0) {
            return new byte[0];
        }
        if (!isValidHexEncodedString(keyText)) {
            throw new IllegalArgumentException("Key text is not valid hex-encoded string!");
        }
        byte[] data = new byte[length / 2];
        char[] keyTextAsArray = keyText.toLowerCase().toCharArray();
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(keyTextAsArray[i], HEX_RADIX) << SHIFT) + Character.digit(keyTextAsArray[i + 1], HEX_RADIX));
        }

        return data;
    }

    /**
     * Checks if the text is a valid hex-encoded string.
     *
     * @param keyText text that we want to check.
     * @return true if the given text is valid hex-encoded string.
     */
    public static boolean isValidHexEncodedString(String keyText) {
        return hexEncodedString.matcher(keyText).matches() && keyText.length() % 2 == 0;
    }

    /**
     * Takes a byte array and creates its hex-encoding: for each byte of given
     * array, two characters are returned in string, in big-endian notation.
     *
     * @param bytes which we want to transform into hex-encoded string.
     * @return hex-encoded representation of bytes.
     */
    public static String bytesToHex(byte[] bytes) {
        if (bytes.length == 0) {
            return "";
        }
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            //example :  1010 0001 -> 0000 1010 , unsigned shift
            hexChars[i * 2] = hexArray[value >>> 4];
            //example : 10100001 -> 0000 0001
            hexChars[i * 2 + 1] = hexArray[value & 0x0F];

        }
        return String.valueOf(hexChars);
    }

}
