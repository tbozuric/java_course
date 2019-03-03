package hr.fer.zemris.java.hw07.shell.commands.actions;

import hr.fer.zemris.java.hw07.shell.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Represents execute action for mass-renaming/moving files(but not directories).
 * The files will be moved from the source path to the destination path with appropriate names.
 */
public class MassRenameExecuteAction implements Action {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Environment env, Path source, Path destination) throws IOException {
        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }
}
