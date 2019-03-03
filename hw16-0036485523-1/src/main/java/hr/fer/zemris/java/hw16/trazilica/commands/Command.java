package hr.fer.zemris.java.hw16.trazilica.commands;

/**
 * An interface that represents a command. Each command can be executed and  it has its name.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @param arguments command arguments.
     * @return shell status.
     */
    CommandStatus executeCommand(String arguments);

    /**
     * Returns the name of the command.
     *
     * @return the name of the command.
     */
    String getCommandName();

}
