package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.Utils.NameBuilder;
import hr.fer.zemris.java.hw07.shell.Utils.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.Utils.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.commands.actions.MassRenameExecuteAction;
import hr.fer.zemris.java.hw07.shell.commands.actions.MassRenameShowAction;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Represents the mass rename command that is used for mass FILE renaming / moving.
 * Allowed sub-commands are:
 * <p>
 * filter->prints the file names that are selected with mask.
 * </p>
 * <p>
 * show-> prints the new file names.
 * </p>
 * <p>
 * groups-> prints all the groups for all selected ones files.
 * </p>
 * <p>
 * <p>
 * execute-> renames/moves files.
 * </p>
 */
public class MassRenameCommand extends AbstractCommand {
    /**
     * Represents a filter sub-command.
     */
    private static final String FILTER = "FILTER";
    /**
     * Represents a show sub-command.
     */
    private static final String SHOW = "SHOW";
    /**
     * Represents a groups sub-command.
     */
    private static final String GROUPS = "GROUPS";
    /**
     * Represents a execute sub-command.
     */
    private static final String EXECUTE = "EXECUTE";

    /**
     * Creates an instance of mass rename command.
     */
    public MassRenameCommand() {
        super("massrename", new ArrayList<>(Arrays.asList("massrename source destination" +
                        " CMD MASK [expression]",
                "Command is used for mass file renaming / moving(transfer) that are directly in directory \"source\".",
                "MASK is a regular expression that selects files from \"source\" directory",
                "If CMD is \"filter\" , the command will prints the file names that are selected with \"MASK\".",
                "If CMD is \"show\" , the command receives another argument \"expression\" which defines how a new name is generated.",
                "If CMD is \"groups\", the command prints all the groups for all selected ones files.",
                "If CMD is \"execute\", the command renames/moves files.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);
        int size = args.size();
        if (size < 4) {
            env.writeln("massrename command takes at least four arguments.");
            return ShellStatus.CONTINUE;
        }

        Path source;
        Path destination;
        String commandType = args.get(2).toUpperCase();
        Pattern pattern;
        try {
            source = env.getCurrentDirectory().resolve(args.get(0).trim());
            destination = env.getCurrentDirectory().resolve(args.get(1).trim());
        } catch (InvalidPathException e) {
            env.writeln("Please check given paths and try again.");
            return ShellStatus.CONTINUE;
        }

        if (!source.toFile().isDirectory() || !source.toFile().exists() || !destination.toFile().isDirectory() ||
                !destination.toFile().exists()) {
            env.writeln("Please check given paths and try again.");
            return ShellStatus.CONTINUE;
        }

        try {
            pattern = Pattern.compile(args.get(3), Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            env.writeln("Invalid pattern syntax. Please try again.");
            return ShellStatus.CONTINUE;
        }

        try {
            switch (commandType) {
                case FILTER:
                    Files.walkFileTree(source, buildFilterFileVisitor(env, pattern));
                    break;
                case SHOW:
                    if (size != 5) {
                        env.writeln("You must enter an expression with the show command");
                        return ShellStatus.CONTINUE;
                    }
                    String expression = args.get(4);
                    NameBuilderParser parser = new NameBuilderParser(expression);
                    NameBuilder builder = parser.getNameBuilder();
                    Files.walkFileTree(source, buildShowAndExecuteFileVisitor(env, destination, pattern, builder, false));
                    break;
                case GROUPS:
                    Files.walkFileTree(source, buildGroupsFileVisitor(env, pattern));
                    break;
                case EXECUTE:
                    if (size != 5) {
                        env.writeln("You must enter an expression with the execute command");
                        return ShellStatus.CONTINUE;
                    }
                    expression = args.get(4);
                    parser = new NameBuilderParser(expression);
                    builder = parser.getNameBuilder();
                    Files.walkFileTree(source, buildShowAndExecuteFileVisitor(env, destination, pattern, builder, true));
                    break;
                default:
                    env.writeln("CMD part of command must be : filter , show, groups or execute.");
                    return ShellStatus.CONTINUE;
            }

        } catch (IOException e) {
            env.writeln("An error occurred while visiting files.");
            return ShellStatus.CONTINUE;
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Builds a filter file visitor that prints the file names that are selected with "MASK".
     *
     * @param env     a reference to our {@link Environment}
     * @param pattern a comparison pattern.
     * @return a new filter file visitor.
     */
    private FileVisitor<Path> buildFilterFileVisitor(Environment env, Pattern pattern) {
        return new AbstractFileVisitor() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (pattern.matcher(file.getFileName().toString()).matches()) {
                    env.writeln(file.getFileName().toString());
                }
                return FileVisitResult.CONTINUE;
            }
        };
    }

    /**
     * Builds a groups file visitor that  prints all the groups for all selected ones files.
     *
     * @param env     a reference to our {@link Environment}.
     * @param pattern a comparison pattern
     * @return a new groups file visitor.
     */
    private FileVisitor<Path> buildGroupsFileVisitor(Environment env, Pattern pattern) {
        return new AbstractFileVisitor() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                Matcher matcher = pattern.matcher(file.getFileName().toString());
                if (matcher.matches()) {
                    env.write(file.getFileName().toString() + " ");
                    for (int i = 0, numberOfGroups = matcher.groupCount(); i <= numberOfGroups; i++) {
                        env.write(i + ": " + matcher.group(i) + " ");
                    }
                    env.writeln("");
                }
                return FileVisitResult.CONTINUE;
            }
        };
    }

    /**
     * Builds a show/execute file visitor that defines how a new name is generated / renames(/moves) files.
     *
     * @param env     a reference to our {@link Environment}.
     * @param dest    destination path.
     * @param pattern a comparison pattern.
     * @param builder name builder.
     * @param execute a flag that indicates the type of action.
     * @return a new show/execute file visitor.
     * @throws IndexOutOfBoundsException if the group index is invalid.
     */
    private FileVisitor<Path> buildShowAndExecuteFileVisitor(Environment env, Path dest, Pattern pattern, NameBuilder builder, boolean execute)
            throws IndexOutOfBoundsException {

        return new AbstractFileVisitor() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Matcher matcher = pattern.matcher(file.getFileName().toString());
                if (matcher.matches()) {
                    NameBuilderInfo info = getBuilderInfo(matcher);
                    builder.execute(info);
                    String newName = info.getStringBuilder().toString();
                    Path destination = Paths.get(dest.toString(), newName);
                    new MassRenameShowAction().execute(env, file, destination);
                    if (execute) {
                        new MassRenameExecuteAction().execute(env, file, destination);
                    }

                }
                return FileVisitResult.CONTINUE;
            }
        };
    }

    /**
     * Returns a new {@link NameBuilderInfo}.
     *
     * @param matcher to retrieve a group.
     * @return a new {@link NameBuilderInfo}.
     */
    private NameBuilderInfo getBuilderInfo(Matcher matcher) {
        return new NameBuilderInfo() {
            StringBuilder builder = new StringBuilder();

            @Override
            public StringBuilder getStringBuilder() {
                return builder;
            }

            @Override
            public String getGroup(int index) {
                return matcher.group(index);
            }
        };
    }

}
