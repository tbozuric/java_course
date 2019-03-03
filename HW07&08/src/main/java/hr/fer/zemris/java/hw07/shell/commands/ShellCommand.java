package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.List;

/**
 *An interface that represents a shell command. Each command can be executed, it has its name and description.
 */
public interface ShellCommand {
    /**
     * Executes the command.
     * @param env reference to the environment.
     * @param arguments command arguments.
     * @return shell status.
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     *Returns the name of the command.
     * @return the name of the command.
     */
    String getCommandName();

    /**
     *Returns a description of the command.
     * @return a description of the command.
     */
    List<String> getCommandDescription();
}
