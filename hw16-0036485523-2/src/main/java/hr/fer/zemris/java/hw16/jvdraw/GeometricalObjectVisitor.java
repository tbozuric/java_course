package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

/**
 * Represents the Visitor in visitor design pattern.
 * For more information:
 *
 * @see <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Visitor</a>
 */

public interface GeometricalObjectVisitor {
    /**
     * Visits the line.
     *
     * @param line the line
     */
    void visit(Line line);

    /**
     * Visits the circle.
     *
     * @param circle the circle
     */
    void visit(Circle circle);

    /**
     * Visits the filled circle.
     *
     * @param filledCircle the filled circle
     */
    void visit(FilledCircle filledCircle);
}
