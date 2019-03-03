package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents a skip command.
 */
public class SkipCommand implements Command {
    /**
     * Step size.
     */
    private double step;

    /**
     * Creates an instance of skip command.
     *
     * @param step size
     */
    public SkipCommand(double step) {
        this.step = step;
    }

    /**
     * Calculates where the turtle must go and save the new turtle position to the current state
     *
     * @param context current context.
     * @param painter that allows drawing lines.
     */
    @Override
    public void execute(Context context, Painter painter) {
        TurtleState turtleState = context.getCurrentState();

        Vector2D currentPosition = turtleState.getCurrentPosition();
        Vector2D stepVector = turtleState.getDirection().scaled(turtleState.getEffectiveDisplacementLength() * step);
        Vector2D newPosition = currentPosition.translated(stepVector);

        context.getCurrentState().setCurrentPosition(newPosition);
    }
}
