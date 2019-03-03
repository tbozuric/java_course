package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Represents the push directory command that pushes on the stack current directory an then as a current directory sets the
 * given single argument, path.
 */
public class PushdCommand extends AbstractCommand {
    /**
     * Creates an instance of push directory command.
     */
    public PushdCommand() {
        super("pushd", new ArrayList<>(Arrays.asList("pushd path", "" +
                "The current directory pushes on the stack and then as a current directory sets the " +
                "given single argument \"path\"")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);

        if (args.size() != 1) {
            env.writeln("pushd command takes one arguments");
            return ShellStatus.CONTINUE;
        }
        try {
            Path path = env.getCurrentDirectory().resolve(args.get(0).trim());
            if (!path.toFile().isDirectory() || !path.toFile().exists()) {
                env.writeln("Directory does not exist.");
                return ShellStatus.CONTINUE;
            }
            if (env.getSharedData("cdstack") == null) {
                Stack<Path> stack = new Stack<>();
                stack.push(env.getCurrentDirectory());
                env.setSharedData("cdstack", stack);
            } else {
                ((Stack<Path>) env.getSharedData("cdstack")).push(env.getCurrentDirectory());
            }
            env.setCurrentDirectory(path);
        } catch (InvalidPathException e) {
            env.writeln("Path is not valid. Please try again.");
            return ShellStatus.CONTINUE;
        }
        return ShellStatus.CONTINUE;
    }
}
