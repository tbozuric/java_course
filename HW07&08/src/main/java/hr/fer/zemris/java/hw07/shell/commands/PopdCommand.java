package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Represents the pop directory command that removes the path from the top of the stack(it it exists) and sets
 * it as a current directory.
 */
public class PopdCommand extends AbstractCommand {
    /**
     * Creates an instance of pop directory command.
     */
    public PopdCommand() {
        super("popd", new ArrayList<>(Arrays.asList("popd",
                "Removes the path from the top of the stack and sets it as a current directory " +
                        "(if it exists - for example, it may be deleted in the meantime, " +
                        "in which case the path is still removing, but the current directory does not change).")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("popd command takes no arguments.");
            return ShellStatus.CONTINUE;
        }
        Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");

        if (stack == null || stack.empty()) {
            env.writeln("Stack is empty. First push the directory to stack with pushd command.");
            return ShellStatus.CONTINUE;
        }
        Path path = stack.pop();
        if (path.toFile().isDirectory() && path.toFile().exists()) {
            env.setCurrentDirectory(path);
            env.writeln("Current directory is : " + path);
        }
        return ShellStatus.CONTINUE;
    }
}
