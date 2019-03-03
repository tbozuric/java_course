package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents a rotate command.
 */
public class RotateCommand implements Command {
    /**
     * Angle of rotation.
     */
    private double angle;

    /**
     * Creates an instance of rotate command.
     *
     * @param angle of rotation.
     */
    public RotateCommand(double angle) {
        this.angle = angle;
    }

    /**
     * Rotates the direction in which the turtle looks.
     *
     * @param context current context.
     * @param painter that allows drawing lines.
     */
    @Override
    public void execute(Context context, Painter painter) {
        context.getCurrentState().setDirection(context.getCurrentState().getDirection().rotated(angle));
    }
}
