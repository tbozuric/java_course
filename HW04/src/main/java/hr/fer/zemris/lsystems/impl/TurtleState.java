package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.*;

/**
 * Represents the condition in which the turtle is currently located.
 */
public class TurtleState {
    /**
     * Current turtle position.
     */
    private Vector2D currentPosition;
    /**
     * Turtle look direction.
     */
    private Vector2D direction;

    /**
     * The color with which the turtle draws.
     */
    private Color color;

    /**
     * Effective displacement length.
     */
    private double effectiveDisplacementLength;

    /**
     * Creates an instance of turtle state.
     *
     * @param currentPosition             current turtle position.
     * @param direction                   turtle look direction.
     * @param color                       with which the turtle draws.
     * @param effectiveDisplacementLength effective displacement length.
     */
    public TurtleState(Vector2D currentPosition, Vector2D direction, Color color, double effectiveDisplacementLength) {
        this.currentPosition = currentPosition;
        this.direction = direction;
        this.color = color;
        this.effectiveDisplacementLength = effectiveDisplacementLength;
    }

    /**
     * Returns current turtle position.
     *
     * @return current turtle position.
     */
    public Vector2D getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Returns turtle look direction.
     *
     * @return turtle look direction.
     */
    public Vector2D getDirection() {
        return direction;
    }

    /**
     * Returns the color with which the turtle draws.
     *
     * @return color with which the turtle draws.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns effective displacement length.
     *
     * @return effective displacement length.
     */
    public double getEffectiveDisplacementLength() {
        return effectiveDisplacementLength;
    }

    /**
     * Sets a new turtle position.
     *
     * @param position new turtle position.
     */
    public void setCurrentPosition(Vector2D position) {
        this.currentPosition = position;
    }

    /**
     * Sets a new turtle look direction.
     *
     * @param direction new turtle look direction.
     */
    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    /**
     * Sets a new color with which turtle draws.
     *
     * @param color new color with which turtle draws.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets a new effective displacement length.
     *
     * @param effectiveDisplacementLength new effective displacement length.
     */
    public void setEffectiveDisplacementLength(double effectiveDisplacementLength) {
        this.effectiveDisplacementLength = effectiveDisplacementLength;
    }

    /**
     * Returns a copy of the current state of the turtle.
     *
     * @return a copy of the current state of the turtle.
     */
    public TurtleState copy() {
        return new TurtleState(currentPosition.copy(), direction.copy(), color, effectiveDisplacementLength);
    }
}
