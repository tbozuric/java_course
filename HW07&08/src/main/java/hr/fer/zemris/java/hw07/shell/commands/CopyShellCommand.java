package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The copy command expects two arguments: source file name and destination file name (i.e. paths and
 * names). Is destination file exists, the user will be asked if he/she wants to overwrite the file. Copy command
 * works only with files (no directories). If the second argument is directory, we assume that user wants
 * to copy the original file into that directory using the original file name.
 */
public class CopyShellCommand extends AbstractCommand {

    /**
     * Buffer size.
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * Creates an instance of copy shell command.
     */
    public CopyShellCommand() {
        super("copy", new ArrayList<>(Arrays.asList("copy source_file destination_file",
                "Expects two arguments: source file name and destination file name (i.e. paths and" +
                        "names).", "Command works only with files (no directories).",
                " If the second argument is directory, we assume that user wants " +
                        "to copy the original file into that directory using the original file name.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        List<String> args = getArgumentsAsList(arguments);

        if (args.size() != 2) {
            env.writeln("Invalid format of copy command. Excepts two arguments: source file name and destination file name.");
            return ShellStatus.CONTINUE;
        }

        Path source = env.getCurrentDirectory().resolve(args.get(0).trim());

        if (Files.notExists(source) || !source.toFile().isFile()) {
            env.writeln("Source is not a file or file does not exist.");
            return ShellStatus.CONTINUE;
        }

        Path newDestination = env.getCurrentDirectory().resolve(Paths.get(args.get(1).trim(), source.getFileName().toString().trim()));
        if (Files.exists(newDestination)) {
            env.writeln("The file already exists. Do you want to overwrite it?");
            while (true) {
                String answer = env.readLine();
                switch (answer.toLowerCase()) {
                    case "yes":
                        copyFileUsingStream(source, newDestination, env);
                        env.writeln("Copied file : " + source + " to " + newDestination);
                        return ShellStatus.CONTINUE;
                    case "no":
                        return ShellStatus.CONTINUE;
                    default:
                        env.writeln("Please write \"yes\" or \"no\".");
                        break;
                }
            }
        }
        copyFileUsingStream(source, newDestination, env);
        env.writeln("Copied file : " + source + " to " + newDestination);
        return ShellStatus.CONTINUE;
    }

    /**
     * Copies the file from the source location to the destination location.
     *
     * @param source source file.
     * @param dest   destination.
     * @param env    reference to environment. {@link Environment}
     */
    private void copyFileUsingStream(Path source, Path dest, Environment env) {
        try (InputStream is = Files.newInputStream(source, StandardOpenOption.READ);
             OutputStream os = Files.newOutputStream(dest, StandardOpenOption.CREATE,
                     StandardOpenOption.TRUNCATE_EXISTING)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException e) {
            env.writeln("An error occurred while copying the file.");
        }
    }
}
