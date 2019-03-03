package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Represents a {@link GeometricalObject} filled circle.
 */
public class FilledCircle extends Circle {

    /**
     * The border color of the {@link FilledCircle}.
     */
    private Color fgColor;

    /**
     * Creates an instance of {@link FilledCircle}.
     *
     * @param x       x coordinate of the  filled circle.
     * @param y       y coordinate of the filled circle.
     * @param radius  the radius.
     * @param fgColor the border color.
     * @param bgColor the area color.
     */
    public FilledCircle(int x, int y, int radius, Color fgColor, Color bgColor) {
        super(x, y, radius, bgColor);
        this.fgColor = fgColor;
    }


    /**
     * Returns the area color of the filled circle.
     *
     * @return the area color of the filled circle.
     */
    public Color getBgColor() {
        return color;
    }

    /**
     * Returns the border color of the filled circle.
     *
     * @return the border color of the filled circle
     */
    public Color getFgColor() {
        return fgColor;
    }


    /**
     * Sets the area color of the filled circle.
     *
     * @param bgColor the area color of the filled circle.
     */
    public void setBgColor(Color bgColor) {
        this.color = bgColor;
    }

    /**
     * Sets the border color of the filled circle.
     *
     * @param fgColor the border color of the filled circle.
     */
    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledCircleEditor(this);
    }

    @Override
    public String toString() {
        return String.format("Filled circle (%d,%d), %d, #%02x%02x%02x", x, y, radius, color.getRed(),
                color.getGreen(), color.getBlue());
    }
}
