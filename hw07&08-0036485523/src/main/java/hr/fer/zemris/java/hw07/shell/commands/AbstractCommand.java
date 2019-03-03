package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an abstract command that is represented by name and description.
 */
public abstract class AbstractCommand implements ShellCommand {
    /**
     * The name of the command.
     */
    private String commandName;
    /**
     * Description of the command.
     */
    private List<String> commandDescription;
    /**
     * Command arguments delimiter.
     */
    private static final String DELIMITER = "¤";

    /**
     * Initializes the name and description of the command.
     *
     * @param commandName        name of the command.
     * @param commandDescription description of the command.
     */
    public AbstractCommand(String commandName, List<String> commandDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommandName() {
        return commandName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCommandDescription() {
        return commandDescription;
    }

    /**
     * Returns arguments of the command  as a list of strings.
     *
     * @param arguments command arguments.
     * @return list of arguments.
     */
    protected List<String> getArgumentsAsList(String arguments) {
        return new ArrayList<>(Arrays.asList(arguments.split(DELIMITER))).stream().filter(s -> s.length() != 0).collect(Collectors.toList());
    }
}
