package hr.fer.zemris.java.hw16.trazilica.commands;

import hr.fer.zemris.java.hw16.trazilica.Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Represents the command that receives a ordinal number and prints the document on the screen. The command makes sense
 * to run only after the {@link QueryCommand} has been executed.
 * The command prints the full file name and text stored in the file.
 */
public class TypeCommand implements Command {


    @Override
    public CommandStatus executeCommand(String arguments) {
        Engine engine = Engine.getInstance();
        List<Engine.SimilarityTuple> topSimilarities = engine.getTopSimilarities();
        if (topSimilarities == null) {
            System.out.println("Please run query command first.");
            return CommandStatus.CONTINUE;
        }

        int index;
        try {
            index = Integer.parseInt(arguments.trim());
        } catch (NumberFormatException e) {
            System.out.println("The given index is not in valid format. Please try again.");
            return CommandStatus.CONTINUE;
        }
        if (topSimilarities.size() <= index) {
            System.out.println("Invalid index. Please try again.");
            return CommandStatus.CONTINUE;
        }
        Path documentPath = topSimilarities.get(index).getDocument().getPath();
        System.out.println("Dokument: " + documentPath);
        System.out.println("-------------------------------------------------------------------------");
        printContentOfFile(documentPath);
        return CommandStatus.CONTINUE;

    }

    @Override
    public String getCommandName() {
        return "type";
    }


    /**
     * Prints the content of the file.
     *
     * @param documentPath the path to the document.
     */
    private static void printContentOfFile(Path documentPath) {
        try (BufferedReader br = Files.newBufferedReader(documentPath)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
    }
}
