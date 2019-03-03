package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * Represents a concrete environment for communication with the shell.
 * Default prompt symbol is : ">" , default more-lines symbol is : "\" , and multiline symbol is : "|".
 */
public class MyEnvironment implements Environment {
    /**
     * Sorted map of all available commands.
     */
    private SortedMap<String, ShellCommand> commands;
    /**
     * Represents a reader for a user input.
     */
    private BufferedReader in;
    /**
     * Represents a writer.
     */
    private BufferedWriter out;

    /**
     * Represents multiline symbol.
     */
    private Character multilineSymbol;
    /**
     * Represents more-lines symbol.
     */
    private Character morelinesSymbol;
    /**
     * Represents prompt symbol.
     */
    private Character promptSymbol;


    private Path currentDirectory;


    private Map<String, Object> sharedData;

    /**
     * Creates an instance of {@link MyEnvironment}.
     *
     * @param commands all available commands in our shell.
     */
    public MyEnvironment(SortedMap<String, ShellCommand> commands) {
        this.commands = Collections.unmodifiableSortedMap(commands);
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new BufferedWriter(new OutputStreamWriter(System.out));
        multilineSymbol = '|';
        promptSymbol = '>';
        morelinesSymbol = '\\';
        currentDirectory = Paths.get(".").toAbsolutePath().normalize();
        sharedData = new HashMap<>();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String readLine() throws ShellIOException {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new ShellIOException("An error occurred during reading!");
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void write(String text) throws ShellIOException {
        try {
            out.write(text);
            out.flush();
        } catch (IOException e) {
            throw new ShellIOException("An error occurred during writing to output!");
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void writeln(String text) throws ShellIOException {
        try {
            out.write(text);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new ShellIOException("An error occurred during writing to output!");
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public SortedMap<String, ShellCommand> commands() {
        return commands;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Character getMultilineSymbol() {
        return multilineSymbol;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMultilineSymbol(Character symbol) {
        multilineSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Character getMorelinesSymbol() {
        return morelinesSymbol;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setMorelinesSymbol(Character symbol) {
        morelinesSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if path is not valid or  does not exist.
     */
    @Override
    public void setCurrentDirectory(Path path) {
        if (path.toFile().isDirectory() && path.toFile().exists()) {
            currentDirectory = path;
            return;
        }
        throw new IllegalArgumentException("Path is not valid.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getSharedData(String key) {
        return sharedData.getOrDefault(key, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSharedData(String key, Object value) {
        sharedData.put(key, value);
    }
}
