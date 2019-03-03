package hr.fer.zemris.java.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Represents the wrapper around {@link JFreeChart}.
 * Class offers methods for creating pie graphs and converting graphs into png format.
 */
public class PieChart {

    /**
     * Represents a pie chart.
     */
    private JFreeChart chart;

    /**
     * Data for initialization of the pie graph.
     */
    private PieDataset dataset;

    /**
     * The title of the graph.
     */
    private String title;

    /**
     * Creates an instance of {@link PieChart}.
     *
     * @param dataset the data for initialization of the pie grapf.
     * @param title   the title of the graph.
     */
    public PieChart(PieDataset dataset, String title) {
        this.dataset = dataset;
        this.title = title;
        this.chart = createChart();
    }

    /**
     * Creates an instance of {@link PieChart}.
     *
     * @param title the title of the graph.
     */
    public PieChart(String title) {
        this.dataset = createDataset();
        this.title = title;
        this.chart = createChart();
    }

    /**
     * Creates a sample(default) dataset.
     */
    private PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 35);
        result.setValue("Mac", 20);
        result.setValue("Windows", 45);
        return result;

    }

    /**
     * Creates a chart using a loaded dataset.
     */
    private JFreeChart createChart() {

        JFreeChart chart = ChartFactory.createPieChart3D(
                title,                  // chart title
                dataset,                // data
                true,            // include legend
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setDirection(Rotation.CLOCKWISE);
        return chart;

    }

    /**
     * Returns the generated circular graph in the form of bytes decoded in png format.
     *
     * @return the generated pie graph in the form of bytes decoded in png format.
     */
    public byte[] getAsBytes() {
        BufferedImage objBufferedImage = chart.createBufferedImage(600, 800);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", bas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bas.toByteArray();
    }

    /**
     * Returns a circular chart.
     *
     * @return a circular chart.
     */
    public JFreeChart getChart() {
        return chart;
    }
}
