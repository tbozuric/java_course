package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a bar chart component that can be drawn.
 */
public class BarChartComponent extends JComponent {

    /**
     * The empty space between the x and the y axis of the window.
     */
    private static final int TOP_BOTTOM_GAP = 20;

    /**
     * The fixed gap between the description under x axis and x axis, and the description under y axis and y axis.
     */
    private static final int FIXED_GAP = 10;

    /**
     * Default maximum y value.
     */
    private static final int DEFAULT_MAX_Y_VALUE = 20;

    /**
     * Reference to {@link BarChart}.
     */
    private BarChart barChart;


    /**
     * The color of mesh.
     */
    private static final Color MESH_COLOR = new Color(255, 230, 179);

    /**
     * The color of bars.
     */
    private static final Color COLUMN_COLOR = new Color(244, 119, 71);

    /**
     * The default font.
     */
    private static final Font DEFAULT_FONT = new Font("default", Font.BOLD, 16);


    /**
     * Creates an instance onf {@link BarChartComponent}.
     *
     * @param barChart reference to bar chart.
     */
    public BarChartComponent(BarChart barChart) {
        this.barChart = barChart;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int availableWidth = getWidth();
        int availableHeight = getHeight();
        Graphics2D g2d = (Graphics2D) g;
        //default font
        g2d.setFont(DEFAULT_FONT);
        FontMetrics fm = g2d.getFontMetrics();
        int stringHeight = fm.getHeight();

        drawAxesDescriptionAndPath(availableWidth, availableHeight, g2d, fm);


        List<XYValue> values = barChart.getValues();
        g2d.setColor(Color.BLACK);

        float x = (float) (TOP_BOTTOM_GAP + stringHeight / 2.0);
        float y = availableHeight - stringHeight - TOP_BOTTOM_GAP - 2 * FIXED_GAP;

        List<Integer> yValues = getYValues(barChart.getMinY(), barChart.getMaxY(), barChart.getStep());

        int maxValue = DEFAULT_MAX_Y_VALUE;
        Optional<Integer> val = yValues.stream().max(Integer::compareTo);
        if (val.isPresent()) {
            maxValue = val.get();
        }


        int widthOfYNumbers = fm.stringWidth(String.valueOf(maxValue));
        double realStepY = (double) (availableHeight - stringHeight - TOP_BOTTOM_GAP
                - FIXED_GAP - 10) / yValues.size();
        double unitStepY = realStepY / barChart.getStep();

        x += FIXED_GAP + widthOfYNumbers;
        y = drawNumbersOnYAxis(availableWidth, g2d, fm, x, y, yValues, realStepY);

        double maxY = y + realStepY;
        y = availableHeight - stringHeight - TOP_BOTTOM_GAP - 2 * FIXED_GAP;
        x += FIXED_GAP / 2.0;
        drawYAxis(g2d, x, y);
        drawXAxis(availableWidth, g2d, fm, x, y);

        double widthOfOneColumn = (availableWidth - x - FIXED_GAP * 4) / values.size();
        double startXOfColumns = x;
        int minValue = 0;
        if (barChart.getMinY() < 0) {
            minValue = -barChart.getMinY();
        }
        drawColumnsAndData(g2d, fm, values, y, unitStepY, maxY, minValue, widthOfOneColumn, startXOfColumns);
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    /**
     * Draws columns from the given data, draws shadows and vertical part of mesh.
     *
     * @param g2d              the graphics used for drawing
     * @param fm               font metrics.
     * @param values           pairs x,y.
     * @param y                y coordinate.
     * @param unitStepY        the difference between two points on y axis.
     * @param maxY             the maximum y value.
     * @param widthOfOneColumn the width of one column.
     * @param startXOfColumns  the start x coordinate of columns.
     */
    private void drawColumnsAndData(Graphics2D g2d, FontMetrics fm, List<XYValue> values,
                                    float y, double unitStepY, double maxY, int minY, double widthOfOneColumn,
                                    double startXOfColumns) {
        values = values.stream().sorted(Comparator.comparingInt(XYValue::getX)).collect(Collectors.toList());
        for (XYValue value : values) {

            // draw shadow
            g2d.setColor(Color.LIGHT_GRAY);
            Rectangle2D shadowColumn = new Rectangle2D.Double(startXOfColumns + FIXED_GAP,
                    y - fm.getAscent() / 2.0 + 1 - value.getY() * unitStepY - minY * unitStepY + FIXED_GAP / 2.0,
                    widthOfOneColumn, unitStepY * value.getY() + minY * unitStepY - FIXED_GAP / 2.0);
            g2d.fill(shadowColumn);

            //draw column
            g2d.setColor(COLUMN_COLOR);
            Rectangle2D column = new Rectangle2D.Double(startXOfColumns + 1,
                    y - fm.getAscent() / 2.0 + 1 - value.getY() * unitStepY - minY * unitStepY, widthOfOneColumn,
                    unitStepY * value.getY() + minY * unitStepY);
            g2d.fill(column);

            //draw x value of column
            g2d.setColor(Color.BLACK);
            String val = String.valueOf(value.getX());
            g2d.drawString(val, (float) (startXOfColumns + widthOfOneColumn / 2.0 - fm.stringWidth(val) / 2.0),
                    y + FIXED_GAP);

            //draw columns separator
            startXOfColumns += widthOfOneColumn;
            Line2D delimiter = new Line2D.Double(startXOfColumns, y - fm.getAscent() / 2.0 + 1,
                    startXOfColumns, y - fm.getAscent() / 2.0 + 1 - value.getY() * unitStepY - minY * unitStepY);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.draw(delimiter);

            //draw mesh by x axis
            g2d.setColor(MESH_COLOR);
            Line2D xMeshLine = new Line2D.Double(startXOfColumns,
                    y - fm.getAscent() / 2.0 + 1 - value.getY() * unitStepY - minY*unitStepY, startXOfColumns, maxY);
            g2d.draw(xMeshLine);

            //draw end of the column
            g2d.setColor(Color.BLACK);
            Line2D xColumnEnd = new Line2D.Double(startXOfColumns, y - fm.getAscent() / 2.0 + 1,
                    startXOfColumns, y - fm.getAscent() / 2.0 + 1 + FIXED_GAP / 2.0);
            g2d.draw(xColumnEnd);

        }
    }

    /**
     * Draws x axis and arrow on the end of x axis.
     *
     * @param availableWidth the size of window.
     * @param g2d            the graphics used for drawing.
     * @param fm             font metrics.
     * @param x              the x coordinate.
     * @param y              the y coordinate.
     */
    private void drawXAxis(int availableWidth, Graphics2D g2d, FontMetrics fm, float x, float y) {
        //draw x axis
        Line2D xAxis = new Line2D.Double(x, y - fm.getAscent() / 2.0 + 1, availableWidth - 2 * FIXED_GAP,
                y - fm.getAscent() / 2.0 + 1);
        g2d.draw(xAxis);

        //draw arrow on x axis
        Path2D arrowX = new Path2D.Double();
        double firstXX = availableWidth - 2 * FIXED_GAP;
        double firstXY = y - fm.getAscent() / 2.0 + 1 - FIXED_GAP / 2.0;
        arrowX.moveTo(firstXX, firstXY);
        arrowX.lineTo(firstXX, y - fm.getAscent() / 2.0 + 1 + FIXED_GAP / 2.0);
        arrowX.lineTo(firstXX + FIXED_GAP / 2.0, y - fm.getAscent() / 2.0 + 1);
        arrowX.closePath();
        g2d.fill(arrowX);
    }

    /**
     * Draws y axis and arrow on the end of y axis.
     *
     * @param g2d the graphics used for drawing
     * @param x   x coordinate.
     * @param y   y coordinate.
     */
    private void drawYAxis(Graphics2D g2d, float x, float y) {
        //draw y axis
        Line2D yAxis = new Line2D.Double(x, FIXED_GAP / 2.0 + 1, x, y);
        g2d.draw(yAxis);

        //draw arrow on y axis
        Path2D arrowY = new Path2D.Double();
        double firstXYAxis = x - FIXED_GAP / 2.0;
        double firstYYAxis = FIXED_GAP;
        arrowY.moveTo(firstXYAxis, firstYYAxis);
        arrowY.lineTo(x + FIXED_GAP / 2.0, firstYYAxis);
        arrowY.lineTo(x, FIXED_GAP / 2.0 - 1);
        arrowY.closePath();
        g2d.fill(arrowY);
    }

    /**
     * Draws numbers ond y axis and horizontal part of mesh.
     *
     * @param availableWidth the width of window
     * @param g2d            the graphics used for drawing
     * @param fm             font metrics.
     * @param x              x coordinate.
     * @param y              y coordinate.
     * @param yValues        the values on y axis.
     * @param realStepY      the real step on y axis.
     * @return y coordinate of last number.
     */
    private float drawNumbersOnYAxis(int availableWidth, Graphics2D g2d, FontMetrics fm, float x, float y,
                                     List<Integer> yValues, double realStepY) {
        //draw values on y axis and mesh by y axis
        for (Integer value : yValues) {
            String str = String.valueOf(value);
            Line2D lineNextToNumber = new Line2D.Double(x, y - fm.getAscent() / 2.0 + 1, x + FIXED_GAP / 2.0,
                    y - fm.getAscent() / 2.0 + 1);
            Line2D meshLine = new Line2D.Double(x + FIXED_GAP / 2.0, y - fm.getAscent() / 2.0 + 1,
                    availableWidth - TOP_BOTTOM_GAP, y - fm.getAscent() / 2.0 + 1);
            g2d.drawString(str, x - fm.stringWidth(str), y);
            g2d.draw(lineNextToNumber);
            g2d.setColor(MESH_COLOR);
            g2d.draw(meshLine);
            g2d.setColor(Color.BLACK);
            y -= realStepY;
        }
        return y;
    }

    /**
     * Draws the description under y and x axes and the path to the file from which the data was loaded.
     *
     * @param availableWidth  the width of window.
     * @param availableHeight the height of window.
     * @param g2d             the graphics used for drawing.
     * @param fm              font metrics.
     */
    private void drawAxesDescriptionAndPath(int availableWidth, int availableHeight, Graphics2D g2d, FontMetrics fm) {
        //draw the description under y axis
        AffineTransform defaultAt = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(-Math.PI / 2);
        g2d.setTransform(transform);

        String descriptionY = barChart.getDescriptionY();
        int stringWidthY = fm.stringWidth(descriptionY);
        g2d.drawString(descriptionY, (float) (-availableHeight / 2.0 - stringWidthY / 2.0), TOP_BOTTOM_GAP);

        //draw the description under x axis and path to the file
        g2d.setTransform(defaultAt);

        String descriptionX = barChart.getDescriptionX();
        int stringWidthX = fm.stringWidth(descriptionX);
        g2d.drawString(descriptionX, (float) (availableWidth / 2.0 - stringWidthX / 2.0),
                (float) availableHeight - TOP_BOTTOM_GAP);
    }


    /**
     * Generates values on y axis.
     *
     * @param minY the minimum y value.
     * @param maxY the maximum y value.
     * @param step the difference between two points on y axis.
     * @return values on y axis that will be marked on the graph.
     */
    private List<Integer> getYValues(int minY, int maxY, int step) {
        List<Integer> list = new ArrayList<>();
        for (int y = minY; y <= maxY; y += step) {
            list.add(y);
        }
        return list;
    }
}