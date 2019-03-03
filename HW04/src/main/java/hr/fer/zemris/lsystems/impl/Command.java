package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Represents an abstract command.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @param context current context.
     * @param painter that allows drawing lines.
     */
    void execute(Context context, Painter painter);
}
