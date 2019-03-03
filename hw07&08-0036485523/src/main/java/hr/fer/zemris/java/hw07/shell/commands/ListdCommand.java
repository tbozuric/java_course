package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Represents the list directories command that prints to the terminal all the paths that are on the stack starting
 * from the last one added.
 */
public class ListdCommand extends AbstractCommand {
    /**
     * Creates an instance of list directories command.
     */
    public ListdCommand() {
        super("listd", new ArrayList<>(Arrays.asList("listd", "Prints to the terminal all the paths " +
                "that are on the stack starting from the last one added.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("listd command takes no arguments.");
            return ShellStatus.CONTINUE;
        }
        Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
        if (stack == null || stack.empty()) {
            env.writeln("No stored directories.");
            return ShellStatus.CONTINUE;
        }
        stack.forEach(p -> env.writeln(p.toString()));
        return ShellStatus.CONTINUE;
    }
}
