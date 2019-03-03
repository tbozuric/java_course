package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Represents the environment through which we communicate with the shell.It supports various shell methods,
 * such as reading input, writing in a shell, changing a prompt symbol, changing a multiline symbol, and so on.
 */
public interface Environment {
    /**
     * Reads the input from the console.
     *
     * @return a read string.
     * @throws ShellIOException if reading from the shell fails.
     */
    String readLine() throws ShellIOException;

    /**
     * Writes the given text to the shell.
     *
     * @param text text we want to write to console.
     * @throws ShellIOException if writing to the shell fails.
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes the given text to the shell and moves to a new line.
     *
     * @param text text we want to write to the console.
     * @throws ShellIOException if writing to the shell fails.
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Returns a sorted map of all possible commands.
     *
     * @return sorted map of all possible commands.
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Returns the  current multiline symbol.
     *
     * @return multiline symbol.
     */
    Character getMultilineSymbol();

    /**
     * Sets a new multiline symbol.
     *
     * @param symbol a new multiline symbol.
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Returns  the current prompt symbol.
     *
     * @return prompt symbol.
     */
    Character getPromptSymbol();

    /**
     * Sets a new prompt symbol.
     *
     * @param symbol a new prompt symbol.
     */
    void setPromptSymbol(Character symbol);

    /**
     * Returns a new more-lines symbol.
     *
     * @return a new more-lines symbol.
     */
    Character getMorelinesSymbol();

    /**
     * Sets a new more-lines symbol.
     *
     * @param symbol a new more-lines symbol.
     */
    void setMorelinesSymbol(Character symbol);

    /**
     * Returns the path to the current directory.
     *
     * @return the path to the current directory.
     */
    Path getCurrentDirectory();

    /**
     * Sets a new path to the current directory.
     *
     * @param path a new path.
     */
    void setCurrentDirectory(Path path);

    /**
     * Returns a shared object that is stored under the key or returns null.
     *
     * @param key map key.
     * @return a shared object that is stored under the given key or null reference.
     */
    Object getSharedData(String key);

    /**
     * Sets the shared object for the given key.
     *
     * @param key   map key.
     * @param value shared object.
     */
    void setSharedData(String key, Object value);

}
