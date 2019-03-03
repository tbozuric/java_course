package hr.fer.zemris.java.hw07.shell.commands.actions;

import hr.fer.zemris.java.hw07.shell.Environment;

import java.nio.file.Path;

/**
 * Represents show action that only prints a new path to a file.
 */
public class MassRenameShowAction implements Action {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Environment env, Path source, Path destination) {
        env.writeln(source + " => " + destination);
    }
}
