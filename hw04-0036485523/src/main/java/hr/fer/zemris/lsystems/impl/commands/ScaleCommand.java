package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents a scale command.
 */
public class ScaleCommand implements Command {
    /**
     * Scaling factor.
     */
    private double factor;

    /**
     * Creates an instance of scale command.
     *
     * @param factor scaling factor.
     */
    public ScaleCommand(double factor) {
        this.factor = factor;
    }

    /**
     * Updates effective the displacement length by scaling with the given factor.
     *
     * @param context current context.
     * @param painter that allows drawing lines.
     */
    @Override
    public void execute(Context context, Painter painter) {
        context.getCurrentState().setEffectiveDisplacementLength(context.getCurrentState().getEffectiveDisplacementLength() * factor);
    }
}
