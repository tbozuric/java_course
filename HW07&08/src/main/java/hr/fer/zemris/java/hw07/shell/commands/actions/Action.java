package hr.fer.zemris.java.hw07.shell.commands.actions;

import hr.fer.zemris.java.hw07.shell.Environment;

import java.io.IOException;
import java.nio.file.Path;

import hr.fer.zemris.java.hw07.shell.commands.MassRenameCommand;

/**
 * This interface models an action that can be executed by {@link MassRenameCommand}
 */
public interface Action {
    /**
     * Executes the actions that belong to the {@link MassRenameCommand} commands.
     *
     * @param env         reference to our {@link Environment}.
     * @param source      path to source file.
     * @param destination path to destination file.
     * @throws IOException if a write/read error occurs.
     */
    void execute(Environment env, Path source, Path destination) throws IOException;
}
