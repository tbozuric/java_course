package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents a pop command.
 */
public class PopCommand implements Command {
    /**
     * Removes last turtle state pushed on stack.
     *
     * @param context current context.
     * @param painter that allows drawing lines.
     * @throws EmptyStackException if the stack is empty.
     */
    @Override
    public void execute(Context context, Painter painter) {
        context.popState();
    }
}
