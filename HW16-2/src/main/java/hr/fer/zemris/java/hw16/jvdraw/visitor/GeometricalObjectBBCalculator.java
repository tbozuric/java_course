package hr.fer.zemris.java.hw16.jvdraw.visitor;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

import java.awt.*;

/**
 * Represents a visitor that calculates the bounding box around all objects.
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

    /**
     * The minimum x coordinate value.
     */
    private static int minX = Integer.MAX_VALUE;

    /**
     * The maximum x coordinate value.
     */
    private static int maxX = Integer.MIN_VALUE;

    /**
     * The minimum y coordinate value.
     */
    private static int minY = Integer.MAX_VALUE;

    /**
     * The maximum y coordinate value.
     */
    private static int maxY = Integer.MIN_VALUE;

    /**
     * The rectangle.
     */
    private Rectangle rectangle;

    /**
     * Returns the bounding box around all objects.
     *
     * @return the bounding box around all objects.
     */
    public Rectangle getBoundingBox() {
        rectangle.setSize((int) rectangle.getWidth() + 1, (int) rectangle.getHeight() + 1);
        return rectangle;
    }

    /**
     * Creates a union of rectangles or creates a new {@link Rectangle}.
     */
    private void updateRectangle() {
        if (rectangle == null) {
            rectangle = createRectangle();
        } else {
            rectangle = rectangle.union(createRectangle());
        }
    }

    /**
     * Returns the minimum coordinate from the given points.
     *
     * @param start the first coordinate.
     * @param end   the second coordinate.
     * @param item  the item.
     * @return the minimum.
     */
    private int getMinimum(int start, int end, int item) {
        int min = Integer.MAX_VALUE;
        if (start < item) {
            min = start;
        }
        if (end < min) {
            min = end;
        }
        return min == Integer.MAX_VALUE ? item : min;
    }

    /**
     * Returns the maximum  coordinate from the given points.
     *
     * @param start the first coordinate.
     * @param end   the second coordinate.
     * @param item  the item.
     * @return maximum.
     */
    private int getMaximum(int start, int end, int item) {
        int max = Integer.MIN_VALUE;
        if (start > item) {
            max = start;
        }
        if (end > max) {
            max = end;
        }
        return max == Integer.MIN_VALUE ? item : max;
    }

    /**
     * Creates a new {@link Rectangle}
     *
     * @return a new {@link Rectangle}
     */
    private Rectangle createRectangle() {
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Calculates the bounding box around the {@link Circle}.
     *
     * @param x      the x coordinate of the center.
     * @param y      the y coordinate of the center.
     * @param radius the radius.
     */
    private void getMaximumBoundingBoxCircle(int x, int y, int radius) {
        if (x + radius / 2 > maxX) {
            maxX = x + radius / 2;
        }

        if (y + radius / 2 > maxY) {
            maxY = y + radius / 2;
        }

        if (x - y / 2 < minX) {
            minX = x - radius / 2;
        }

        if (y - radius / 2 < minY) {
            minY = y - radius / 2;
        }
    }


    @Override
    public void visit(Line line) {
        minX = getMinimum(line.getStartX(), line.getEndX(), minX);
        minY = getMinimum(line.getStartY(), line.getEndY(), minY);

        maxX = getMaximum(line.getStartX(), line.getEndX(), maxX);
        maxY = getMaximum(line.getStartY(), line.getEndY(), maxY);

        updateRectangle();
    }

    @Override
    public void visit(Circle circle) {
        getMaximumBoundingBoxCircle(circle.getX(), circle.getY(), circle.getRadius());
        updateRectangle();
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        getMaximumBoundingBoxCircle(filledCircle.getX(), filledCircle.getY(), filledCircle.getRadius());
        updateRectangle();
    }
}
