package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the make directory command.
 * The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.
 */
public class MkdirShellCommand extends AbstractCommand {
    /**
     * Creates an instance of make directory command.
     */
    public MkdirShellCommand() {
        super("mkdir", new ArrayList<>(Arrays.asList("mkdir directory_structure",
                "Takes a single argument: directory name.",
                "Then creates the appropriate directory structure.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (getArgumentsAsList(arguments).size() != 1) {
            env.writeln("mkdir command takes one argument.");
            return ShellStatus.CONTINUE;
        }
        Path p = env.getCurrentDirectory().resolve(arguments.trim());
        try {
            Files.createDirectories(p);
            env.writeln("Created directory : " + p.toString());
        } catch (IOException e) {
            env.writeln("An error occurred while  creating a directory");
        }
        return ShellStatus.CONTINUE;
    }
}
