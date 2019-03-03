package hr.fer.zemris.java.hw07.shell;

import hr.fer.zemris.java.hw07.shell.Utils.ParserException;
import hr.fer.zemris.java.hw07.shell.Utils.SimpleCommandParser;
import hr.fer.zemris.java.hw07.shell.commands.*;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *Represents a demonstration program for displaying shell work.
 *This shell supports the following commands:
 *                         <p>"cat"</p>
 *                         <p>"charset"</p>
 *                         <p>"cat"</p>
 *                         <p>"copy"</p>
 *                         <p>"help"</p>
 *                         <p>"hexdump"</p>
 *                         <p>"mkdir"</p>
 *                         <p>"tree"</p>
 *                         <p>"exit"</p>
 *                         <p>"ls"</p>
 *                         <p>"symbol"</p>
 * */
public class MyShell {
    /**
     * Represents a welcome message for MyShell.
     */
    private static final String WELCOME_MESSAGE = "Welcome to MyShell v 1.0";
    /**
     * Represents a delimiter for arguments.
     */
    private static final String DELIMITER = "¤";

    /**
     * Method invoked when running the program.
     * Reads the commands from the input and performs corresponding actions.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {


        SortedMap<String, ShellCommand> commands = new TreeMap<>();

        commands.put("cat", new CatShellCommand());
        commands.put("charset", new CharsetShellCommand());
        commands.put("copy", new CopyShellCommand());
        commands.put("help", new HelpShellCommand());
        commands.put("hexdump", new HexDumpShellCommand());
        commands.put("mkdir", new MkdirShellCommand());
        commands.put("tree", new TreeShellCommand());
        commands.put("exit", new ExitShellCommand());
        commands.put("ls", new LsShellCommand());
        commands.put("symbol", new SymbolShellCommand());
        commands.put("pwd", new PwdCommand());
        commands.put("cd" , new CdCommand());
        commands.put("pushd", new PushdCommand());
        commands.put("popd", new PopdCommand());
        commands.put("listd", new ListdCommand());
        commands.put("dropd", new DropdCommand());
        commands.put("rmtree", new RmTreeCommand());
        commands.put("cptree", new CpTreeCommand());
        commands.put("massrename", new MassRenameCommand());


        Environment environment = new MyEnvironment(commands);


        environment.writeln(WELCOME_MESSAGE);

        SimpleCommandParser parser = new SimpleCommandParser(commands);
        ShellCommand command;
        ShellStatus status = ShellStatus.CONTINUE;

        while (status != ShellStatus.TERMINATE) {
            environment.write(environment.getPromptSymbol() + " ");


            StringBuilder line = new StringBuilder(environment.readLine());
            Character promptSymbol = environment.getPromptSymbol();
            while (true) {
                if (line.toString().endsWith(String.valueOf(environment.getMorelinesSymbol()))) {
                    line = line.deleteCharAt(line.lastIndexOf(String.valueOf(environment.getMorelinesSymbol())));
                    environment.setPromptSymbol(environment.getMultilineSymbol());
                    environment.write(environment.getPromptSymbol() + " ");
                    line.append(environment.readLine());
                } else {
                    break;
                }
            }
            environment.setPromptSymbol(promptSymbol);
            try {
                parser.parse(line.toString().trim());
                command = parser.getCommand();
                List<String> arguments = parser.getArguments();
                status = command.executeCommand(environment, String.join(DELIMITER, arguments));
            } catch (ParserException | IllegalArgumentException e) {
                environment.writeln(e.getMessage());
            }

        }
    }
}
