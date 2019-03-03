package hr.fer.zemris.java.gui.charts;

/**
 * Represents an x-y value. {@link XYValue} is used in {@link BarChart} for columns representation.
 */
public class XYValue {

    /**
     * The x value.
     */
    private int x;

    /**
     * The y value.
     */
    private int y;

    /**
     * Creates an instance of {@link XYValue}.
     *
     * @param x the x-value.
     * @param y the y-value.
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value.
     *
     * @return the x value.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y value.
     *
     * @return the y value.
     */
    public int getY() {
        return y;
    }
}
