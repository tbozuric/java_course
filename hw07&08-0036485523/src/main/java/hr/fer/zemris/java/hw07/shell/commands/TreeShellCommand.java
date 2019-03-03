package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the tree shell command. The tree command expects a single argument: directory name and prints a tree
 * (each directory level shifts
 * output two characters to the right).
 */
public class TreeShellCommand extends AbstractCommand {
    /**
     * Creates an instance of tree shell command.
     */
    public TreeShellCommand() {
        super("tree", new ArrayList<>(Arrays.asList("tree path_to_directory",
                "Expects a single argument: directory " +
                        "name and prints a tree (each directory level shifts " +
                        "output two characters to the right)")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);

        if (args.size() != 1) {
            env.writeln("tree command takes only one argument.");
            return ShellStatus.CONTINUE;
        }

        Path p = env.getCurrentDirectory().resolve(args.get(0).trim());

        if (!Files.isDirectory(p)) {
            env.writeln("The directory does not exist.");
            return ShellStatus.CONTINUE;
        }

        try {
            Files.walkFileTree(p, buildFileVisitor());
        } catch (IOException e) {
            env.writeln("An error occurred while visiting files.");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Creates a new file visitor who browses directories and prints them in the form of a tree.
     *
     * @return a new file visitor.
     */
    private FileVisitor<Path> buildFileVisitor() {
        return new FileVisitor<>() {

            int indent = 0;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (indent == 0) {
                    System.out.println(dir.toAbsolutePath().normalize());
                } else {
                    System.out.format("%" + (2 * indent) + "s%s%n", "", dir.getFileName());
                }
                indent++;
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.format("%" + (2 * indent) + "s%s%n", "", file.getFileName());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                indent -= 1;
                return FileVisitResult.CONTINUE;
            }
        };
    }
}
