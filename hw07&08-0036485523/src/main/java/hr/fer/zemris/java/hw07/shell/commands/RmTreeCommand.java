package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the remove tree command that deletes the directory and its complete content.
 */
public class RmTreeCommand extends AbstractCommand {
    /**
     * Creates an instance of remove tree command.
     */
    public RmTreeCommand() {
        super("rmtree", new ArrayList<>(Arrays.asList("rmtree pathToDir",
                "Deletes the directory \"pathToDir\" and its complete content,")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);

        if (args.size() != 1) {
            env.writeln("rmtree command takes one argument.");
            return ShellStatus.CONTINUE;
        }
        try {
            Path path = env.getCurrentDirectory().resolve(args.get(0).trim());
            if (path.toFile().exists() && path.toFile().isDirectory()) {
                try {
                    Files.walkFileTree(path, buildRemoveFileVisitor());
                } catch (IOException e) {
                    env.writeln("An error occurred while visiting files.");
                }
            } else {
                env.writeln("Directory does not exist.");
                return ShellStatus.CONTINUE;
            }
        } catch (InvalidPathException e) {
            env.writeln("Path is not valid.");
            return ShellStatus.CONTINUE;
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Builds a new file visitor that deletes all items within the directory.
     *
     * @return a new "remove file visitor".
     */
    private FileVisitor<Path> buildRemoveFileVisitor() {
        return new FileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                file.toFile().delete();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                dir.toFile().delete();
                return FileVisitResult.CONTINUE;
            }
        };

    }
}
