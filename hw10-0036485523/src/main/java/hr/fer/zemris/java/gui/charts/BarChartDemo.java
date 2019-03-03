package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a frame that displays a bar chart over its entire surface.
 * The data that is displayed in the graph is loaded from the file that is passed through the command line argument.
 * File format:
 * <p><code>
 * description of x axis(String)
 * description of y axis(String)
 * {@link XYValue} values in format : x,y x,y x,y
 * {@link BarChart#minY} one integer
 * {@link BarChart#maxY} one integer
 * {@link BarChart#step} one integer
 * </code></p>
 */
public class BarChartDemo extends JFrame {

    /**
     * Reference to bar chart.
     */
    private static BarChart chart;

    /**
     * The file from which the graph definition is loaded.
     */
    private static Path file;

    /**
     * Creates an instance of {@link BarChartDemo}.
     */
    public BarChartDemo() {

        setSize(500, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bar chart diagram");
        setLocation(20, 20);
        setSize(1000, 500);
        setMinimumSize(new Dimension(500,500));
        initGui();

    }

    /**
     * Initiates a graphical user interface with {@link BarChartComponent}.
     */
    private void initGui() {
        Container cp = getContentPane();
        BarChartComponent barChartComponent = new BarChartComponent(chart);

        JLabel path = new JLabel( file.toAbsolutePath().toString(), SwingConstants.CENTER );
        cp.add(path, BorderLayout.PAGE_START);
        cp.add(barChartComponent);
    }

    /**
     * Method invoked when running the program.
     *
     * @param args command line arguments. The argument must be the path to the file with the graph definition.
     * @throws FileNotFoundException if the file does not exist.
     * @throws IOException           if an error occurs while reading the file.
     * @throws NumberFormatException if the file is in invalid format.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("You need to enter the path to the file with the graph description.");
            return;
        }

        String descriptionY = null;
        String descriptionX = null;
        int minY = 0;
        int maxY = 0;
        int step = 0;
        List<XYValue> values = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            descriptionX = getLine(br);
            descriptionY = getLine(br);

            String line = getLine(br);
            String[] elems = line.split("\\s+");
            values = Arrays.stream(elems)
                    .map(s -> new XYValue(Integer.parseInt(s.split(",")[0]), Integer.parseInt(s.split(",")[1])))
                    .collect(Collectors.toList());

            minY = Integer.parseInt(getLine(br));
            maxY = Integer.parseInt(getLine(br));
            step = Integer.parseInt(getLine(br));
        } catch (FileNotFoundException e) {
            System.out.println("The file does not exist. Check the entered path.");
            return;
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file. Please try again.");
            return;
        } catch (NumberFormatException e) {
            System.out.println("The file is invalid.");
        }

        chart = new BarChart(values, descriptionX, descriptionY, minY, maxY, step);
        file = Paths.get(args[0]);

        SwingUtilities.invokeLater(() -> new BarChartDemo().setVisible(true));
    }

    /**
     * Reads one line from the file.
     *
     * @param br reference to {@link BufferedReader}.
     * @return read the line from the file.
     * @throws IOException if an error occurs while reading the file.
     */
    private static String getLine(BufferedReader br) throws IOException {
        String line = br.readLine();
        checkLine(line);
        return line;
    }

    /**
     * Verifies whether the string is null.
     *
     * @param line read line.
     * @throws IllegalArgumentException if the string is a null reference.
     */
    private static void checkLine(String line) {
        if (line == null) {
            throw new IllegalArgumentException("The file is invalid.");
        }
    }
}
