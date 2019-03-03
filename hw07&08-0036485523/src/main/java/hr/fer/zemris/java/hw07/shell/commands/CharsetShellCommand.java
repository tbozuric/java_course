package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command charsets takes no arguments and lists names of supported charsets for your Java platform.
 * {@link Charset#availableCharsets()}
 */
public class CharsetShellCommand extends AbstractCommand {
    /**
     * Creates an instance of charset shell command.
     */
    public CharsetShellCommand() {
        super("charset", new ArrayList<>(Arrays.asList("charset",
                "Lists names of supported charsets for your Java platform.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() > 0) {
            env.writeln("Command charset takes no arguments!");
            return ShellStatus.CONTINUE;
        }

        for (String charset : Charset.availableCharsets().keySet()) {
            env.writeln(charset);
        }
        return ShellStatus.CONTINUE;
    }
}
