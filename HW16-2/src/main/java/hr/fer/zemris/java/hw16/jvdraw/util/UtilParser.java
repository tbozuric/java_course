package hr.fer.zemris.java.hw16.jvdraw.util;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

import java.awt.*;

/**
 * Auxiliary class for parsing JVD files for creating {@link GeometricalObject} objects.
 */
public class UtilParser {

    /**
     * Number of components that each circle must contains.
     */
    private static final int NUMBER_JVD_CIRCLE_COMPONENTS = 7;

    /**
     * number of components that each circle must contains.
     */
    private static final int NUMBER_JVD_LINE_COMPONENTS = 8;

    /**
     * Number of components that each  filled circle must contains.
     */
    private static final int NUMBER_JVD_FCIRCLE_COMPONENTS = 10;

    /**
     * Returns a new {@link GeometricalObject} that is  a {@link FilledCircle}.
     *
     * @param parts the line parts.
     * @return a new {@link GeometricalObject} that is a {@link FilledCircle} or a null reference.
     */
    public static GeometricalObject getFilledCircle(String[] parts) {
        if (parts.length != NUMBER_JVD_FCIRCLE_COMPONENTS) {
            return null;
        }
        GeometricalObject filledCircle;
        try {
            Color borderColor = getColor(parts[4], parts[5], parts[6]);
            Color areaColor = getColor(parts[7], parts[8], parts[9]);
            filledCircle = new FilledCircle(getInt(parts[1]), getInt(parts[2]), getInt(parts[3]), borderColor, areaColor);
        } catch (Exception e) {
            return null;
        }
        return filledCircle;
    }

    /**
     * Returns a new {@link GeometricalObject} that is  a {@link Line}.
     *
     * @param parts the line parts.
     * @return a new {@link GeometricalObject} that is a {@link Line} or a null reference.
     */
    public static GeometricalObject getLine(String[] parts) {
        if (parts.length != NUMBER_JVD_LINE_COMPONENTS) {
            return null;
        }
        GeometricalObject line;

        try {
            Color color = getColor(parts[5], parts[6], parts[7]);
            line = new Line(getInt(parts[1]), getInt(parts[2]),
                    getInt(parts[3]), getInt(parts[4]), color);
        } catch (Exception e) {
            return null;
        }
        return line;
    }

    /**
     * Returns a new {@link GeometricalObject} that is  a {@link Circle}.
     *
     * @param parts the line parts.
     * @return a new {@link GeometricalObject} that is a {@link Circle} or a null reference.
     */
    public static GeometricalObject getCircle(String[] parts) {
        if (parts.length != NUMBER_JVD_CIRCLE_COMPONENTS) {
            return null;
        }
        GeometricalObject circle;
        try {
            Color color = getColor(parts[4], parts[5], parts[6]);
            circle = new Circle(getInt(parts[1]), getInt(parts[2]), getInt(parts[3]), color);
        } catch (Exception e) {
            return null;
        }
        return circle;
    }

    /**
     * Returns a new {@link Color} from RGB components.
     *
     * @param red   the red component.
     * @param green the green component.
     * @param blue  the blue component.
     * @return a new {@link Color} from RGB components.
     */
    private static Color getColor(String red, String green, String blue) {
        return new Color(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue));
    }

    /**
     * Returns int value of the given string.
     *
     * @param str the input number.
     * @return int value of the given string.
     */
    private static int getInt(String str) {
        return Integer.parseInt(str);
    }
}

