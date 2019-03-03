package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.*;

/**
 * Represents a help command. Help command if started with no arguments, it lists names of all supported commands.
 * If started with single arguments, it prints name and the description of selected command.
 */
public class HelpShellCommand extends AbstractCommand {
    /**
     * Creates an instance of help shell command.
     */
    public HelpShellCommand() {
        super("help", new ArrayList<>(Arrays.asList("help [command_name]",
                "This command if started with no arguments, " +
                        "lists names of all supported commands.",
                "If started with single argument, prints name and the" +
                        " description of selected command (or print" +
                        "appropriate error message if no such command exists).")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);
        int size = args.size();
        if (size > 1) {
            env.writeln("Help command takes zero or one argument.");
            return ShellStatus.CONTINUE;
        }

        if (size == 0) {
            for (Map.Entry<String, ShellCommand> command : env.commands().entrySet()) {
                env.writeln(String.join("", Collections.nCopies(100, "-")));
                env.writeln("Command : " + command.getKey());
                printCommandDescription(env, command.getValue());
            }
            return ShellStatus.CONTINUE;
        }

        ShellCommand command = env.commands().getOrDefault(args.get(0), null);

        if (command == null) {
            env.writeln("Command does not exists.");
            return ShellStatus.CONTINUE;
        }
        printCommandDescription(env, command);
        return ShellStatus.CONTINUE;
    }

    /**
     * Prints a description of the use of the shell command.
     *
     * @param env     reference to environment {@link Environment}
     * @param command shell command.
     */
    private void printCommandDescription(Environment env, ShellCommand command) {
        env.write("Usage : ");
        for (String str : command.getCommandDescription()) {
            env.writeln(str);
        }
        env.writeln(String.join("", Collections.nCopies(100, "-")));
    }
}
