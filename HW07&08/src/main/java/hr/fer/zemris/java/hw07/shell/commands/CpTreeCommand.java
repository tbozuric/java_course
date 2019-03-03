package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a command that copies the tree from the source location to the destination location.
 */
public class CpTreeCommand extends AbstractCommand {
    /**
     * Command arguments delimiter.
     */
    private static final String DELIMITER = "¤";

    /**
     * Creates an instance of copy tree command.
     */
    public CpTreeCommand() {
        super("cptree", new ArrayList<>(Arrays.asList("cptree source destination",
                "Copies the tree from the source location to the destination location.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);
        if (args.size() != 2) {
            env.writeln("cptree command takes two arguments.");
            return ShellStatus.CONTINUE;
        }
        try {
            Path source = env.getCurrentDirectory().resolve(args.get(0).trim());
            Path destination = env.getCurrentDirectory().resolve(args.get(1).trim());

            if (source.toFile().exists() && source.toFile().isDirectory()) {
                Path dest;
                boolean rename = false;

                if (destination.toFile().exists() && destination.toFile().isDirectory()) {
                    dest = destination;
                } else if (destination.getParent() != null && destination.getParent().toFile().exists()) {
                    dest = destination.getParent();
                    rename = true;
                } else {
                    env.writeln("Invalid format of cptree command. Please check the paths to the directories.");
                    return ShellStatus.CONTINUE;
                }

                try {
                    Files.walkFileTree(source, buildCopyTreeFileVisitor(env, dest, source));
                    if (rename) {
                        Path dir = dest.resolve(source.getFileName());
                        File newName = new File(Paths.get(dir.getParent().toString(),
                                destination.getFileName().toString()).toString());
                        dir.toFile().renameTo(newName);
                        env.writeln(dir.toString() + " renamed to " + newName);
                    }
                } catch (IOException e) {
                    env.writeln("An error occurred while visiting files.");
                    return ShellStatus.CONTINUE;
                }
            }
        } catch (InvalidPathException e) {
            env.writeln("Please check the given paths.");
            return ShellStatus.CONTINUE;
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Builds a tree {@link FileVisitor} that copies files to the given location(targetPath).
     *
     * @param env        reference to our {@link Environment}
     * @param targetPath path to the target directory.
     * @param sourcePath path to the source directory.
     * @return a new tree file visitor.
     */
    private FileVisitor<Path> buildCopyTreeFileVisitor(Environment env, Path targetPath, Path sourcePath) {
        return new FileVisitor<>() {

            MkdirShellCommand mkdir = new MkdirShellCommand();
            CopyShellCommand copy = new CopyShellCommand();
            Path currentTargetPath;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                currentTargetPath = targetPath.resolve(sourcePath.getParent().relativize(dir));
                mkdir.executeCommand(env, String.valueOf(currentTargetPath));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                copy.executeCommand(env, file.toAbsolutePath().normalize() + DELIMITER +
                        currentTargetPath);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        };
    }
}
