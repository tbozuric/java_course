package hr.fer.zemris.java.hw16.jvdraw.visitor;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

import java.awt.*;

/**
 * * Represents a visitor that generates a "description" of {@link hr.fer.zemris.java.hw16.jvdraw.GeometricalObject}
 * in JVD format.
 */
public class GeometricalObjectJvdGenerator implements GeometricalObjectVisitor {

    /**
     * The string builder.
     */
    private StringBuilder sb;

    /**
     * Creates a new instance of {@link GeometricalObjectJvdGenerator}.
     */
    public GeometricalObjectJvdGenerator() {
        sb = new StringBuilder();
    }

    @Override
    public void visit(Line line) {
        sb.append(String.format("LINE %d %d %d %d %d %d %d\n", line.getStartX(), line.getStartY(), line.getEndX(),
                line.getEndY(), line.getColor().getRed(), line.getColor().getGreen(), line.getColor().getBlue()));
    }

    @Override
    public void visit(Circle circle) {
        sb.append(String.format("CIRCLE %d %d %d %d %d %d\n", circle.getX(), circle.getY(), circle.getRadius(),
                circle.getColor().getRed(), circle.getColor().getGreen(), circle.getColor().getBlue()));
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        Color bgColor = filledCircle.getBgColor();
        Color fgColor = filledCircle.getFgColor();
        sb.append(String.format("FCIRCLE %d %d %d %d %d %d %d %d %d\n", filledCircle.getX(), filledCircle.getY(),
                filledCircle.getRadius(), fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue(),
                bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));
    }

    /**
     * Returns the generated JVD string.
     *
     * @return the generated JVD string.
     */
    public String getGeneratedJvd() {
        return sb.toString();
    }
}
