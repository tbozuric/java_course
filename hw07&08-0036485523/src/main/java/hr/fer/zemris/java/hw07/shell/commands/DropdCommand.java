package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Represents the drop directory command that removes the directory from the top of the stack and sets the
 * removed directory as current(if such exists).
 */
public class DropdCommand extends AbstractCommand {
    /**
     * Creates an instance of drop directory command.
     */
    public DropdCommand() {
        super("dropd", new ArrayList<>(Arrays.asList("dropd", "" +
                "Removes the directory from the top of the stack and sets the removed directory " +
                "as current(if such exists)")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("dropd command takes no arguments.");
            return ShellStatus.CONTINUE;
        }
        Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
        if (stack == null || stack.empty()) {
            env.writeln("Stack is empty.");
            return ShellStatus.CONTINUE;
        }
        stack.pop();
        return ShellStatus.CONTINUE;
    }
}
