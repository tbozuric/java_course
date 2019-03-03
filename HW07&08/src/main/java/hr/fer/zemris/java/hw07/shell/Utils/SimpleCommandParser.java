package hr.fer.zemris.java.hw07.shell.Utils;

import hr.fer.zemris.java.hw07.shell.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

/**
 * Represents simple command parser for MyShell.
 */
public class SimpleCommandParser {
    /**
     * Represents user input.
     */
    private String input;
    /**
     * Represents a single shell command.
     */
    private ShellCommand command;
    /**
     * List of arguments.
     */
    private List<String> arguments;
    /**
     * Sorted map of all available commands.
     */
    private SortedMap<String, ShellCommand> commands;

    /**
     * Creates an instance of {@link SimpleCommandParser}.
     *
     * @param commands all available commands in our shell.
     */
    public SimpleCommandParser(SortedMap<String, ShellCommand> commands) {
        arguments = new ArrayList<>();
        this.commands = commands;
    }

    /**
     * Parses the input command.
     *
     * @param command input command
     * @throws ParserException if the command is in the wrong format or does not exist.
     */
    public void parse(String command) {
        arguments = new ArrayList<>();
        this.input = command;
        
        skipSpaces();        
        this.command = extractCommand();
        skipSpaces();
        extractArguments();


    }

    /**
     * Tries to extract the argument contained in the quotation marks.
     *
     * @throws ParserException if the argument is in the wrong format.
     */
    private void extractArguments() {
        while (input.length() > 0) {
            if (input.startsWith("\"")) {
                input = input.substring(1);
                int lastIndexOfQuotationMark = input.indexOf("\"");
                if (lastIndexOfQuotationMark == -1) {
                    throw new ParserException("Invalid  string literal.");
                }
                arguments.add(input.substring(0, lastIndexOfQuotationMark));
                input = input.substring(lastIndexOfQuotationMark + 1);
                if(input.length()!=0 && !input.substring(0,1).contains(" ")){
                    throw new ParserException("Invalid format of input arguments.");
                }
                skipSpaces();
            } else {
                if (input.contains(" ")) {
                    arguments.add(input.substring(0, input.indexOf(" ")));
                    input = input.substring(input.indexOf(" "));
                    skipSpaces();
                } else {
                    arguments.add(input);
                    return;
                }
            }
        }
    }

    /**
     * Extracts the command name. The name of command must be within all available commands.
     *
     * @return extracted shell command.
     */
    private ShellCommand extractCommand() {
        int length;
        ShellCommand command;
        if (!input.contains(" ")) {
            command = commands.getOrDefault(input.toLowerCase(), null);
        } else {
            String commandName = input.substring(0, input.indexOf(" "));
            command = commands.getOrDefault(commandName.toLowerCase(), null);
        }
        if (command == null) {
            throw new ParserException("Unknown command.");
        }

        length = command.getCommandName().length();
        input = input.substring(length);
        return command;
    }

    /**
     * Skips all spaces in the input string.
     */
    private void skipSpaces() {
        input = input.trim();
    }

    /**
     * Returns extracted shell command.
     *
     * @return shell command.
     */
    public ShellCommand getCommand() {
        return command;
    }

    /**
     * Returns arguments of extracted shell command.
     *
     * @return arguments of shell command.
     */
    public List<String> getArguments() {
        return arguments;
    }
}
