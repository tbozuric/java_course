package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the cat command. Command cat takes one or two arguments.
 * The first argument is path to some file and is mandatory. The
 * second argument is charset name that will be used to interpret chars from bytes. If not provided, a default
 * platform charset is used  {@link Charset#defaultCharset()}. This command opens given file and writes its content to console.
 */
public class CatShellCommand extends AbstractCommand {
    /**
     * Creates an instance of cat command.
     */
    public CatShellCommand() {
        super("cat", new ArrayList<>(Arrays.asList("cat path_to_file [charset]", "The first argument is path " +
                "to some file and is mandatory.", "The" +
                " second argument is charset name that will be used to interpret chars from bytes. " +
                "If not provided, a default" +
                " platform charset is used.", "Opens " +
                "given file and writes its content to console.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);

        int size = args.size();
        if (size < 1 || size > 2) {
            env.writeln("Cat command takes one or two arguments.");
            return ShellStatus.CONTINUE;
        }

        Path p = env.getCurrentDirectory().resolve(args.get(0).trim());
        if (Files.exists(p) && p.toFile().isFile()) {
            Charset charset = Charset.defaultCharset();
            if (size == 2) {
                try {
                    charset = Charset.forName(args.get(1).trim());
                } catch (IllegalCharsetNameException e) {
                    env.writeln("Illegal charset name. Please try again.");
                    return ShellStatus.CONTINUE;
                }
            }

            try (BufferedReader br = Files.newBufferedReader(p, charset)) {
                String line;
                while ((line = br.readLine()) != null) {
                    env.writeln(line);
                }
                return ShellStatus.CONTINUE;

            } catch (IOException e) {
                env.writeln("An error occurred while reading the file.");
                return ShellStatus.CONTINUE;
            }
        }
        env.writeln("No such file.");
        return ShellStatus.CONTINUE;
    }
}
