package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Represents a {@link GeometricalObject} circle.
 */
public class Circle extends GeometricalObject {

    /**
     * The center coordinates of the circle.
     */
    protected int x, y;

    /**
     * The radius.
     */
    protected int radius;

    /**
     * The color of the circle
     */
    protected Color color;

    /**
     * Creates an instance of {@link Circle}.
     *
     * @param x      the x coordinate.
     * @param y      the y coordinate.
     * @param radius the radius.
     * @param color  the color of the circle.
     */
    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Returns the x coordinate.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Reutrns the y coordinate.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the radius.
     *
     * @return the radius.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Returns the color of the circle.
     *
     * @return the color of the circle.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the x coordinate.
     *
     * @param x the x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the radius of the circle.
     *
     * @param radius the radius.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Sets the color of the circle.
     *
     * @param color the color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new CircleEditor(this);
    }

    @Override
    public String toString() {
        return String.format("Circle (%d,%d), %d", x, y, radius);
    }
}
