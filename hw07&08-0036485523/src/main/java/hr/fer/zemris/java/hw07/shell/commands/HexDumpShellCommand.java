package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.crypto.Util;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The hexdump command expects a single argument: file name, and produces hex-output as illustrated
 * below.
 * <p>
 * 00000000: 31 2E 41 6B 6F 20 6E 69|6A 65 20 6D 6F 67 75 63 | 1.Ako nije moguc
 * </p>
 * <p>
 * 00000010: 65 20 6F 74 76 6F 72 69|74 69 20 7A 69 70 20 61 | e otvoriti zip a
 * </p>
 * <p>
 * 00000020: 72 68 69 76 75 20 73 20|65 63 6C 69 70 73 65 6F | rhivu s eclipseo
 * </p>
 * On the right side only a standard subset of characters will be printed.
 */
public class HexDumpShellCommand extends AbstractCommand {
    /**
     * Expected number of bytes we want to read and the boundary for printing.
     */
    private static final int NUMBER_OF_BYTES = 16;
    /**
     * Lower limit for the standard subset of characters.
     */
    private static final int LOWER_LIMIT = 32;
    /**
     * Upper limit for the standard subset of characters.
     */
    private static final int UPPER_LIMIT = 127;

    /**
     * Creates an instance of hex dump shell command.
     */
    public HexDumpShellCommand() {
        super("hexdump", new ArrayList<>(Arrays.asList("hexdump path_to_file",
                "Expects a single argument: file name," +
                        " and produces hex-output", "The characters will be decoded by default charset.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);

        if (args.size() != 1) {
            env.writeln("Hex dump command takes one argument.");
            return ShellStatus.CONTINUE;
        }

        Path p = env.getCurrentDirectory().resolve(args.get(0).trim());

        if (Files.notExists(p) || p.toFile().isDirectory()) {
            env.writeln("Path to the file is not valid.");
            return ShellStatus.CONTINUE;
        }

        try (InputStream is = Files.newInputStream(p, StandardOpenOption.READ)) {
            byte[] buffer = new byte[NUMBER_OF_BYTES];
            int offset = 0x0;
            int length;
            env.write(String.format("%08X", offset));
            env.write(": ");

            while ((length = is.read(buffer)) > 0) {
                buffer = Arrays.copyOfRange(buffer, 0, length);
                String hex = Util.bytesToHex(buffer).toUpperCase();

                StringBuilder firstEightBytes = new StringBuilder();
                StringBuilder lastEightBytes = new StringBuilder();

                int i = 1;
                for (char c : hex.toCharArray()) {
                    if (i <= NUMBER_OF_BYTES) {
                        appendCharacterToString(firstEightBytes, i, c);
                    } else {
                        appendCharacterToString(lastEightBytes, i, c);
                    }
                    i++;
                }

                String first = firstEightBytes.toString().trim();
                String last = lastEightBytes.toString().trim();

                for (int j = 0; j < buffer.length; j++) {
                    if (buffer[j] < LOWER_LIMIT || buffer[j] > UPPER_LIMIT) {
                        buffer[j] = '.';
                    }
                }
                env.write(String.format("%-23s|%-23s | ", first, last));

                String text = new String(buffer, Charset.defaultCharset());
                env.write(text);

                env.writeln("");
                offset += length;

                if (length == NUMBER_OF_BYTES) {
                    env.write(String.format("%08X", offset));
                    env.write(": ");
                }
            }
        } catch (IOException e) {
            env.writeln("An error occurred reading the file.");
            return ShellStatus.CONTINUE;
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Auxiliary method to add spaces between bytes.
     *
     * @param builder string builder.
     * @param i       position of byte in byte array.
     * @param c       hexadecimal value.
     */
    private void appendCharacterToString(StringBuilder builder, int i, char c) {
        builder.append(c);
        if (i % 2 == 0) {
            builder.append(" ");
        }
    }
}
