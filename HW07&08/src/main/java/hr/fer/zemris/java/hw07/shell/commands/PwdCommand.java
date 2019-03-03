package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the pwd command that prints to the our shell the current directory.
 */
public class PwdCommand extends AbstractCommand {
    /**
     * Creates an instance of pwd command.
     */
    public PwdCommand() {
        super("pwd", new ArrayList<>(Arrays.asList("pwd",
                "Prints the current directory.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("pwd command takes no arguments.");
            return ShellStatus.CONTINUE;
        }
        env.writeln(env.getCurrentDirectory().toString());
        return ShellStatus.CONTINUE;
    }
}
