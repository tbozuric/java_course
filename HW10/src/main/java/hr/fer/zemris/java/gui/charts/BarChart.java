package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents a bar chart.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bar_chart">Bar chart</a>
 */
public class BarChart {

    /**
     * A list that represents the data for the bar chart columns.
     */
    private List<XYValue> values;

    /**
     * The description under x axis.
     */
    private String descriptionX;

    /**
     * The description under y axis.
     */
    private String descriptionY;

    /**
     * Minimum value on y axis.
     */
    private int minY;

    /**
     * Maximum value on y axis.
     */
    private int maxY;

    /**
     * The difference between two point on y axis.
     */
    private int step;

    /**
     * Creates an instance of {@link BarChart}.
     *
     * @param values       the data for the bar chart columns.
     * @param descriptionX the description under x axis.
     * @param descriptionY the description under y axis.
     * @param minY         the minimum value on y axis.
     * @param maxY         the maximum value on y axis.
     * @param step         the difference between two points on y axis.
     */
    public BarChart(List<XYValue> values, String descriptionX, String descriptionY, int minY, int maxY, int step) {
        this.values = values;
        this.descriptionX = descriptionX;
        this.descriptionY = descriptionY;
        this.minY = minY;
        this.maxY = maxY;
        this.step = step;
    }

    /**
     * Returns the list of {@link XYValue}s for each graph column.
     *
     * @return the list of {@link XYValue}s for each graph column.
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Returns the description under x axis.
     *
     * @return the description under x axis.
     */
    public String getDescriptionX() {
        return descriptionX;
    }

    /**
     * Returns the description under y axis.
     *
     * @return the description under y axis.
     */
    public String getDescriptionY() {
        return descriptionY;
    }

    /**
     * Returns the minimum value on y axis.
     *
     * @return the minimum value on y axis.
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Returns the maximum value on y axis.
     *
     * @return the maximum value on y axis.
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Returns the difference between two points on y axis.
     *
     * @return the difference between two points on y axis.
     */
    public int getStep() {
        return step;
    }
}
