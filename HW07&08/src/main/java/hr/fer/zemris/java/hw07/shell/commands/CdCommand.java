package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CdCommand extends  AbstractCommand {

    public CdCommand() {
        super("cd", new ArrayList<>(Arrays.asList("cd path" , "" +
                "Changes the current directory in the directory given over the argument")));
    }

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);

        if(args.size() != 1){
            env.writeln("cd command takes one argument.");
            return ShellStatus.CONTINUE;
        }
        try {
            Path newCurrentDirectory = env.getCurrentDirectory().resolve(args.get(0));
            env.setCurrentDirectory(newCurrentDirectory);
        }catch(InvalidPathException e){
            env.writeln("Path is not valid. Please try again.");
            return ShellStatus.CONTINUE;
        }
        return ShellStatus.CONTINUE;
    }
}
