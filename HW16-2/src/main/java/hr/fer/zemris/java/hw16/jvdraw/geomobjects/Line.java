package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Represents a {@link GeometricalObject} line.
 */
public class Line extends GeometricalObject {

    /**
     * The start x, start y , end x and end y coordinates of the line.
     */
    private int startX, startY, endX, endY;

    /**
     * The color of the line.
     */
    private Color color;

    /**
     * Creates an instance of {@link Line}
     *
     * @param startX the start x-coordinate of the line.
     * @param startY the end y-coordinate of the line.
     * @param endX   the end x coordinate of the line.
     * @param endY   the end y coordinate of the line.
     * @param color  the color of the line.
     */
    public Line(int startX, int startY, int endX, int endY, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
    }

    /**
     * Returns the start x coordinate.
     *
     * @return the start x coordinate of the line.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Returns the start y coordinate of the line.
     *
     * @return the start y coordinate of the line.
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Returns the end x coordinate of the line.
     *
     * @return the end x coordinate of the line.
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Returns the end y coordinate.
     *
     * @return the end y coordinate.
     */
    public int getEndY() {
        return endY;
    }

    /**
     * Returns the color of the line.
     *
     * @return the color of the line.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the start x coordinate of the line.
     *
     * @param startX the start x coordinate of the line.
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * Sets the start y coordinate of the line.
     *
     * @param startY the start y coordinate of the line.
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * Sets the end x coordinate of the line.
     *
     * @param endX the end x coordinate of the line.
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * Sets the end y coordinate of the line.
     *
     * @param endY the end y coordinate of the line.
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    /**
     * Sets the color of the line.
     *
     * @param color the color of the line.
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
        return new LineEditor(this);
    }

    @Override
    public String toString() {
        return String.format("Line(%d,%d)-(%d,%d)", startX, startY, endX, endY);
    }
}
