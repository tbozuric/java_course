package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import static hr.fer.zemris.java.raytracer.RayCasterUtil.*;

/**
 * The class is used for drawing certain 3D scene with objects in scene.
 * In order to achieve that, we are using ray-tracing technique.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Ray_tracing_(graphics)">Ray tracer</a>
 */
public class RayCaster {

    /**
     * Method invoked when running the program.
     *
     * @param args command line arguments(not used in this program).
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
                new Point3D(0, 0, 10), 20, 20);
    }

    /**
     * Returns a new ray tracer producer,  {@link IRayTracerProducer} is interface that specifies objects
     * which are capable to create scene snapshots by using ray-tracing technique.
     *
     * @return the new ray tracer producer.
     */
    private static IRayTracerProducer getIRayTracerProducer() {
        return (eye, view, viewUp, horizontal, vertical, width, height, requestNo, observer) -> {
            System.out.println("Start calculating...");

            short[] red = new short[width * height];
            short[] green = new short[width * height];
            short[] blue = new short[width * height];


            Point3D eyePositionToViewPoint = view.sub(eye).normalize();
            Point3D normalizedViewUp = viewUp.normalize();

            Point3D yAxis = getYAxis(eyePositionToViewPoint, normalizedViewUp);
            Point3D xAxis = getXAxis(eyePositionToViewPoint, yAxis);
            Point3D zAxis = getZAxis(yAxis, xAxis);
            Point3D screenCorner = getScreenCorner(view, horizontal, vertical, yAxis, xAxis);

            Scene scene = RayTracerViewer.createPredefinedScene();
            calculate(eye, horizontal, vertical, width, height, 0, 0, height - 1, red, green,
                    blue, yAxis, xAxis, screenCorner, scene);
            System.out.println("Calculation finished...");
            observer.acceptResult(red, green, blue, requestNo);
        };
    }
}
