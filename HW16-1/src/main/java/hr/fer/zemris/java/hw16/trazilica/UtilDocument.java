package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents auxiliary class to parse words from the document.
 */
public class UtilDocument {

    /**
     * Returns the list of words that appear in the document
     *
     * @param file the path to the file.
     * @return the list of words that appear in the document.
     * @throws IOException if an error occurs while reading the file.
     */
    public static List<String> parseDocument(Path file) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                char[] characters = line.trim().toCharArray();
                int startPosition = -1;
                for (int i = 0; i < characters.length; i++) {
                    char c = characters[i];
                    if (!Character.isAlphabetic(c)) {
                        if (startPosition == -1) {
                            continue;
                        }
                        words.add(new String(characters, startPosition, i - startPosition).toLowerCase());
                        startPosition = -1;
                    } else if (Character.isAlphabetic(c) && startPosition == -1) {
                        startPosition = i;
                    }
                }
                if (startPosition != -1) {
                    words.add(new String(characters, startPosition, characters.length - startPosition).toLowerCase());
                }
            }
        }
        return words;
    }
}
