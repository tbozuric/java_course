package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents the ls command.
 * Command ls takes a single argument – directory – and writes a directory listing (not recursive).
 * Example :
 * <p>
 * drwx       4096 2018-04-26 02:20:08 hw07-0036485523
 * </p>
 * <p>
 * -rw-       1032 2018-04-21 21:50:49 hw07-0036485523.iml
 * </p>
 */
public class LsShellCommand extends AbstractCommand {
    /**
     * Creates an instance of ls shell command.
     */
    public LsShellCommand() {
        super("ls", new ArrayList<>(Arrays.asList("ls path_to_directory",
                "Prints the directory and file information for the given directory.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {

        List<String> args = getArgumentsAsList(arguments);

        if (args.size() != 1) {
            env.writeln("Ls command takes one argument.");
            return ShellStatus.CONTINUE;
        }

        Path path = env.getCurrentDirectory().resolve(args.get(0).trim());

        BiConsumer<Boolean, String> consumer = (x, y) -> {
            if (x) {
                env.write(y);
            } else {
                env.write("-");
            }
        };

        if (!path.toFile().isDirectory()) {
            env.writeln("Command takes a single argument-directory.");
            return ShellStatus.CONTINUE;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Files.walk(path, 1).forEach(f -> printInfoAboutFile(f, sdf, consumer, env));
        } catch (IOException e) {
            env.write("An error occurred while reading the file attributes.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Prints information about the given file or directory.
     *
     * @param path     to the file or directory.
     * @param sdf      simple date format.
     * @param consumer a simple consumer to print attributes.
     * @param env      a reference to environment {@link Environment}
     */
    private void printInfoAboutFile(Path path, SimpleDateFormat sdf, BiConsumer<Boolean, String> consumer, Environment env) {
        BasicFileAttributeView faView = Files.getFileAttributeView(
                path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
        );
        BasicFileAttributes attributes;
        try {
            attributes = faView.readAttributes();
            FileTime fileTime = attributes.creationTime();
            String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

            File file = path.toFile();
            consumer.accept(file.isDirectory(), "d");
            consumer.accept(file.canRead(), "r");
            consumer.accept(file.canWrite(), "w");
            consumer.accept(file.canExecute(), "x");
            env.write(" ");
            env.write(String.format("%10d", file.length()));
            env.write(" ");
            env.write(formattedDateTime);
            env.write(" ");
            env.write(file.getName());
            env.writeln("");
        } catch (IOException e) {
            env.write("An error occurred while reading the file attributes.");
        }
    }

}
