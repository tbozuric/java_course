package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents a draw command.
 */
public class DrawCommand implements Command {
    /**
     * The size of the line.
     */
    private static final int SIZE = 1;

    /**
     * Step size.
     */
    private double step;

    /**
     * Creates an instance of draw command.
     *
     * @param step size.
     */
    public DrawCommand(double step) {
        this.step = step;
    }

    /**
     * Calculates where the turtle must go, draws a line from the current turtle position to the calculated and remembered new position.
     *
     * @param context current context.
     * @param painter that allows drawing lines.
     */
    @Override
    public void execute(Context context, Painter painter) {

        TurtleState turtleState = context.getCurrentState();

        Vector2D currentPosition = turtleState.getCurrentPosition();
        Vector2D stepVector = turtleState.getDirection().scaled(turtleState.getEffectiveDisplacementLength() * step);
        Vector2D newPosition = turtleState.getCurrentPosition().translated(stepVector.copy());
        painter.drawLine(currentPosition.getX(), currentPosition.getY(), newPosition.getX(), newPosition.getY(), turtleState.getColor(), SIZE);

        turtleState.setCurrentPosition(newPosition);
    }
}
