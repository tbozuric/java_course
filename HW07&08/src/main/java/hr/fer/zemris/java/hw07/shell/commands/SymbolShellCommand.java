package hr.fer.zemris.java.hw07.shell.commands;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the symbol shell command. This command prints or changes the current symbol for prompt, multiline or
 * more-lines.
 */
public class SymbolShellCommand extends AbstractCommand {
    /**
     * Represents a prompt symbol.
     */
    private static final String PROMPT_SYMBOL = "PROMPT";
    /**
     * Represents a more-lines symbol.
     */
    private static final String MORELINES_SYMBOL = "MORELINES";
    /**
     * Represents a multiline symbol.
     */
    private static final String MULTILINE_SYMBOL = "MULTILINE";

    /**
     * Creates an instance of symbol shell command.
     */
    public SymbolShellCommand() {
        super(PROMPT_SYMBOL, new ArrayList<>(Arrays.asList("symbol name_of_symbol [new_symbol]",
                "Changes current PROMPT/MULTILINE/MORELINES symbol.")));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = getArgumentsAsList(arguments);
        int size = args.size();
        boolean one = size == 1;
        if (size < 1 || size > 2) {
            env.writeln("symbol command takes 1 or two arguments.");
            return ShellStatus.CONTINUE;
        }

        String nameOfSymbol = args.get(0);
        char newSymbol = 0;


        if (size == 2) {
            if (args.get(1).length() != 1) {
                env.writeln("Symbol must be one character.");
                return ShellStatus.CONTINUE;
            }
            newSymbol = args.get(1).charAt(0);
        }
        char symbol;

        switch (nameOfSymbol.toUpperCase()) {
            case PROMPT_SYMBOL:
                if (one) {
                    printSymbol(env, env.getPromptSymbol(), PROMPT_SYMBOL);
                    return ShellStatus.CONTINUE;
                }
                symbol = env.getPromptSymbol();
                env.setPromptSymbol(newSymbol);
                printSetSymbol(env, symbol, newSymbol, PROMPT_SYMBOL);
                break;
            case MORELINES_SYMBOL:
                if (one) {
                    printSymbol(env, env.getMorelinesSymbol(), MORELINES_SYMBOL);
                    return ShellStatus.CONTINUE;
                }
                symbol = env.getMorelinesSymbol();
                env.setMorelinesSymbol(newSymbol);
                printSetSymbol(env, symbol, newSymbol, MORELINES_SYMBOL);
                break;
            case MULTILINE_SYMBOL:
                if (one) {
                    printSymbol(env, env.getMultilineSymbol(), MULTILINE_SYMBOL);
                    return ShellStatus.CONTINUE;
                }
                symbol = env.getMultilineSymbol();
                env.setMultilineSymbol(newSymbol);
                printSetSymbol(env, symbol, newSymbol, MULTILINE_SYMBOL);
                break;
            default:
                env.writeln("Symbol " + nameOfSymbol + " does not exits.");
                return ShellStatus.CONTINUE;
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Prints the current symbol.
     *
     * @param env    reference to {@link Environment}
     * @param symbol current symbol.
     * @param text   indicates the symbol.
     */
    private void printSymbol(Environment env, Character symbol, String text) {
        env.writeln("Symbol for " + text + " is '" + symbol + "'");
    }

    /**
     * Prints a change of symbol to another symbol.
     *
     * @param env       a reference to {@link Environment}
     * @param oldSymbol old symbol.
     * @param newSymbol new symbol
     * @param text      indicates the symbol.
     */
    private void printSetSymbol(Environment env, Character oldSymbol, Character newSymbol, String text) {
        env.writeln("Symbol for " + text + " changed from '" + oldSymbol + "' to '" + newSymbol + "'");
    }
}
