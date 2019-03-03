package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

import java.awt.Color;

/**
 * Represents a color command.
 */
public class ColorCommand implements Command {
    /**
     * The color with which the turtle draws.
     */
    private Color color;

    /**
     * Creates an instance of color command.
     *
     * @param color with which the turtle draws.
     */
    public ColorCommand(Color color) {
        this.color = color;
    }

    /**
     * Sets the color with which the turtle draws in the current state of turtle.
     * this method in the current state of the turtle records the submitted color
     *
     * @param context current context.
     * @param painter that allows drawing lines.
     */
    @Override
    public void execute(Context context, Painter painter) {
        context.getCurrentState().setColor(color);
    }
}
