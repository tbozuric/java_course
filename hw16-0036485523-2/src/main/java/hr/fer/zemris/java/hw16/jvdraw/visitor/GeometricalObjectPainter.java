package hr.fer.zemris.java.hw16.jvdraw.visitor;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

import java.awt.*;

/**
 * Represents a visitor that draws {@link hr.fer.zemris.java.hw16.jvdraw.GeometricalObject}s.
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

    /**
     * The graphics.
     */
    private Graphics2D graphics2D;

    /**
     * Creates an instance of {@link GeometricalObjectPainter}.
     *
     * @param graphics2D the graphics.
     */
    public GeometricalObjectPainter(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    /**
     * Returns the graphics.
     *
     * @return the graphics.
     */
    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    /**
     * Sets the graphics.
     *
     * @param graphics2D the graphics.
     */
    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public void visit(Line line) {
        graphics2D.setColor(line.getColor());
        graphics2D.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }

    @Override
    public void visit(Circle circle) {
        int x = circle.getX();
        int y = circle.getY();
        int radius = circle.getRadius();
        graphics2D.setColor(circle.getColor());
        graphics2D.drawOval(x - radius / 2, y - radius / 2, radius, radius);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        int x = filledCircle.getX();
        int y = filledCircle.getY();
        int radius = filledCircle.getRadius();

        graphics2D.setColor(filledCircle.getFgColor());

        int borderRadius = radius + 2;
        graphics2D.fillOval(x - borderRadius / 2, y - borderRadius / 2, borderRadius, borderRadius);

        graphics2D.setColor(filledCircle.getBgColor());
        graphics2D.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }


}
