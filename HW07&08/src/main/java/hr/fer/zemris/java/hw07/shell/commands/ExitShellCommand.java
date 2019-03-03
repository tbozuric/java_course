package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the command that ends the execution of the shell.
 */
public class ExitShellCommand extends AbstractCommand {
    /**
     * Creates an instance of exit shell command.
      */
    public ExitShellCommand() {
        super("exit", new ArrayList<>(Arrays.asList("exit" , "Exits from the MyShell!")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if(arguments.length() > 0 ){
            env.writeln("Exit command takes no arguments.");
            return ShellStatus.CONTINUE;
        }
        env.writeln("Goodbye!");
        return ShellStatus.TERMINATE;
    }
}
