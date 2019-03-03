package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static hr.fer.zemris.java.raytracer.RayCasterUtil.*;

/**
 * The class is used for drawing certain 3D scene( in parallel to speed up the process of drawing) with objects in scene.
 * In order to achieve that, we are using ray-tracing technique.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Ray_tracing_(graphics)">Ray tracer</a>
 */
public class RayCasterParallel {

    /**
     * Method invoked when running the program.
     *
     * @param args command line arguments(not used in this program).
     */
    public static void main(String[] args) {
        RayTracerViewer.show(new RayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 10), 20, 20);

    }

    /**
     * Represents a recursive worker used for parallel drawing.
     */
    public static class RecursiveWorker extends RecursiveAction {

        /**
         * Serial version unique identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Represents the threshold for direct computation.
         */
        private static final int THRESHOLD = 16;

        /**
         * Represents x-axis in a 2D coordinate system.
         */
        Point3D xAxis;

        /**
         * Represents y-axis in a 2D coordinate system.
         */
        Point3D yAxis;

        /**
         * Represents 3 dimensional coordinates of our upper-left corner in our 2D plane.
         */
        Point3D screenCorner;

        /**
         * Represents 3 dimensional  position of human observer.
         */
        Point3D eye;

        /**
         * Represents scene in which we observe objects.
         */
        Scene scene;


        /**
         * Horizontal width of observed space.
         */
        double horizontal;

        /**
         * Vertical height of observed space.
         */
        double vertical;

        /**
         * Number of pixels per screen row.
         */
        int width;

        /**
         * Number of pixel per screen column.
         */
        int height;

        /**
         * Used in parallelization to distribute a certain part of the work to each thread.
         * The pixel row from which we begin computation.
         */
        int yMin;

        /**
         * Used in parallelization to distribute a certain part of the work to each thread.
         * The pixel row with which we end computation.
         */
        int yMax;

        /**
         * Represents the amount of red in each pixel.
         */
        short[] red;

        /**
         * represents the amount of green color in each pixel.
         */
        short[] green;

        /**
         * Represents the amount of blue color in each pixel.
         */
        short[] blue;

        /**
         * Creates an instance of recursive worker.
         *
         * @param xAxis        x-axis in a 2D coordinate system.
         * @param yAxis        y-axis in a 2D coordinate system.
         * @param screenCorner 3 dimensional position of our upper-left corner in our 2D plane.
         * @param eye          3 dimensional  position of human observer.
         * @param scene        scene in which we observe objects.
         * @param horizontal   horizontal width of observed space.
         * @param vertical     vertical height of observed space.
         * @param width        number of pixels per screen row.
         * @param height       number of pixel per screen column.
         * @param yMin         pixel row from which we begin computation.
         * @param yMax         pixel row with which we end computation.
         * @param red          the amount of red in each pixel of observed space.
         * @param green        the amount of green in each pixel of observed space.
         * @param blue         the amount of blue in each pixel of observed space.
         */
        public RecursiveWorker(Point3D xAxis, Point3D yAxis, Point3D screenCorner, Point3D eye, Scene scene,
                               double horizontal, double vertical, int width, int height, int yMin, int yMax,
                               short[] red, short[] green, short[] blue) {
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.screenCorner = screenCorner;
            this.eye = eye;
            this.scene = scene;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        @Override
        public void compute() {
            if (yMax - yMin + 1 <= THRESHOLD) {
                computeDirect();
                return;
            }
            invokeAll(
                    new RecursiveWorker(xAxis, yAxis, screenCorner, eye, scene, horizontal, vertical, width, height,
                            yMin, yMin + (yMax - yMin) / 2, red, green, blue),
                    new RecursiveWorker(xAxis, yAxis, screenCorner, eye, scene, horizontal, vertical, width, height,
                            yMin + (yMax - yMin) / 2 + 1, yMax, red, green, blue)
            );
        }

        /**
         * Direct computation from yMin to yMax. Parallel computation for all the "tapes" of our "screen".
         */
        public void computeDirect() {
            System.out.println("Calculating from " + yMin + " to " + yMax);
            RayCasterUtil.calculate(eye, horizontal, vertical, width, height, yMin * width, yMin, yMax,
                    red, green, blue, yAxis, xAxis, screenCorner, scene);

        }
    }

    /**
     * Implements {@link IRayTracerProducer} interface that specifies objects
     * which are capable to create scene snapshots by using ray-tracing technique.
     */
    public static class RayTracerProducer implements IRayTracerProducer {
        @Override
        public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
                            int height, long requestNo, IRayTracerResultObserver observer) {
            System.out.println("Start calculating...");

            short[] red = new short[width * height];
            short[] green = new short[width * height];
            short[] blue = new short[width * height];

            Point3D eyePositionToViewPoint = view.sub(eye).normalize();
            Point3D normalizedViewUp = viewUp.normalize();

            Point3D yAxis = getYAxis(eyePositionToViewPoint, normalizedViewUp);
            Point3D xAxis = getXAxis(eyePositionToViewPoint, yAxis);
            Point3D screenCorner = getScreenCorner(view, horizontal, vertical, yAxis, xAxis);

            Scene scene = RayTracerViewer.createPredefinedScene();
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(new RecursiveWorker(xAxis, yAxis, screenCorner, eye, scene, horizontal, vertical, width,
                    height, 0, height - 1, red, green, blue));
            pool.shutdown();

            System.out.println("Computation is finished. I will inform the observer ie. GUI!");
            observer.acceptResult(red, green, blue, requestNo);

        }
    }
}
