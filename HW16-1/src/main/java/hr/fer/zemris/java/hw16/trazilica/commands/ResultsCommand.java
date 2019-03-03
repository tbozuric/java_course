package hr.fer.zemris.java.hw16.trazilica.commands;

import hr.fer.zemris.java.hw16.trazilica.Engine;

/**
 * Represents the command that prints the rank list of results found by the last-executed {@link QueryCommand}.
 */
public class ResultsCommand implements Command {


    @Override
    public CommandStatus executeCommand(String arguments) {
        Engine engine = Engine.getInstance();
        String result = engine.getLastResultByQueryCommand();
        if (result != null) {
            System.out.println(result);
            return CommandStatus.CONTINUE;
        }
        System.out.println("Please run query command first.");
        return CommandStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "results";
    }
}
