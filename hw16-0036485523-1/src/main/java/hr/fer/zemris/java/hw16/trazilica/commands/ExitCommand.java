package hr.fer.zemris.java.hw16.trazilica.commands;

/**
 * Represents the command that ends the execution of the program.
 */
public class ExitCommand implements Command {

    @Override
    public CommandStatus executeCommand(String arguments) {
        return CommandStatus.TERMINATE;
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

}
