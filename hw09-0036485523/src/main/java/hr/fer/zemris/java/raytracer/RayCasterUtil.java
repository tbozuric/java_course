package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.*;

import java.util.List;

import static java.lang.Math.pow;

/**
 * Auxiliary class that offers the methods required for ray-tracer technique.
 *
 * @see <a href="Ray tracer">https://en.wikipedia.org/wiki/Ray_tracing_(graphics)</a>
 */
public class RayCasterUtil {
    /**
     * Threshold for comparision of numbers.
     */
    private static final double THRESHOLD = 1e-3;
    /**
     * The intensity of ambient component.
     */
    private static final int AMBIENT_COMPONENT = 15;

    /**
     * Calculates
     *
     * @param eye           3 dimensional position of human observer.
     * @param horizontal    horizontal width of observed space.
     * @param vertical      vertical width of observed space.
     * @param width         Number of pixels per screen row.
     * @param height        Number of pixel per screen column.
     * @param currentOffset current offset.
     * @param yMin          pixel row from which we begin computation.
     * @param yMax          pixel row with which we end computation.
     * @param red           the amount of red in each pixel of observed space.
     * @param green         the amount of green in each pixel of observed space.
     * @param blue          the amount of blue in each pixel of observed space.
     * @param yAxis         x-axis in a 2D coordinate system, <code>i</code> vector of screen plane.
     * @param xAxis         y-axis in a 2D coordinate system, <code>j</code> vector of screen plane.
     * @param screenCorner  3 dimensional coordinates of our upper-left corner in our 2D plane.
     * @param scene         scene in which we observe objects.
     */
    public static void calculate(Point3D eye, double horizontal, double vertical, int width, int height, int currentOffset,
                                 int yMin, int yMax, short[] red, short[] green, short[] blue,
                                 Point3D yAxis, Point3D xAxis, Point3D screenCorner, Scene scene) {
        short[] rgb = new short[3];

        int offset = currentOffset;
        for (int y = yMin; y <= yMax; y++) {
            for (int x = 0; x < width; x++) {
                Point3D screenPoint = getScreenPoint(horizontal, vertical, width, height, yAxis, xAxis, screenCorner, y, x);
                Ray ray = Ray.fromPoints(eye, screenPoint);
                tracer(scene, ray, rgb);
                red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
                offset++;
            }
        }
    }

    /**
     * Calculates exact point on the screen with given parameters.
     *
     * @param horizontal   horizontal width of observed space.
     * @param vertical     vertical width of observed space.
     * @param width        Number of pixels per screen row.
     * @param height       Number of pixel per screen column.
     * @param yAxis        x-axis in a 2D coordinate system.
     * @param xAxis        y-axis in a 2D coordinate system.
     * @param screenCorner 3 dimensional coordinates of our upper-left corner in our 2D plane.
     * @param y            current y.
     * @param x            current x.
     * @return exact point on the screen.
     */
    public static Point3D getScreenPoint(double horizontal, double vertical, int width, int height, Point3D yAxis,
                                         Point3D xAxis, Point3D screenCorner, int y, int x) {
        return screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1.0)))
                .sub(yAxis.scalarMultiply(y * vertical / (height - 1.0)));
    }

    /**
     * Calculates point of the screen upper left corner.
     *
     * @param view       position that is observed.
     * @param horizontal horizontal width of observed space.
     * @param vertical   vertical width of observed space.
     * @param yAxis      x-axis in a 2D coordinate system.
     * @param xAxis      y-axis in a 2D coordinate system.
     * @return
     */
    public static Point3D getScreenCorner(Point3D view, double horizontal, double vertical, Point3D yAxis, Point3D xAxis) {
        return view.sub(xAxis.scalarMultiply(horizontal / 2.0)).add(yAxis.scalarMultiply(vertical / 2.0));
    }

    /**
     * Calculate <code>k</code> vector of screen plane.
     *
     * @param yAxis x-axis in a 2D coordinate system.
     * @param xAxis y-axis in a 2D coordinate system.
     * @return <code>k</code> vector of screen plane.
     */
    public static Point3D getZAxis(Point3D yAxis, Point3D xAxis) {
        return yAxis.vectorProduct(xAxis);
    }

    /**
     * Calculates <code>i</code> vector of screen plane.
     *
     * @param eyePositionToViewPoint position of human observer to position that is observed.
     * @param yAxis                  y-axis , <code>j</code> vector of screen plane.
     * @return <code>i</code> vector of screen plane.
     */
    public static Point3D getXAxis(Point3D eyePositionToViewPoint, Point3D yAxis) {
        return eyePositionToViewPoint.vectorProduct(yAxis).normalize();
    }

    /**
     * Calculate <code>j</code> vector of screen plane.
     *
     * @param eyePositionToViewPoint position of human observer to position that is observed.
     * @param normalizedViewUp       specification of view-up vector which is used to determine y-axis for screen.
     * @return <code>j</code> vector of screen plane.
     */
    public static Point3D getYAxis(Point3D eyePositionToViewPoint, Point3D normalizedViewUp) {
        return normalizedViewUp.sub(eyePositionToViewPoint.scalarMultiply(eyePositionToViewPoint
                .scalarProduct(normalizedViewUp))).normalize();
    }

    /**
     * Calculates  the amount of red, green, and blue (RGB) in each pixel.
     * If there is no ray-object intersection, pixel's every color component
     * intensity is set to 0. However, if intersection exists, we calculate
     * the color  as a combination of diffuse, ambient and reflective component
     * <code>(color = ambient component + diffuse component + reflective component)</code>
     *
     * @param scene current scene.
     * @param ray   the ray from position of human observer to the position that is observed.
     * @param rgb   red, green blue array for pixel.
     */
    public static void tracer(Scene scene, Ray ray, short[] rgb) {

        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;

        RayIntersection eyeToScreenPoint = findClosestIntersection(scene, ray);
        if (eyeToScreenPoint == null) {
            return;
        }

        rgb[0] = AMBIENT_COMPONENT;
        rgb[1] = AMBIENT_COMPONENT;
        rgb[2] = AMBIENT_COMPONENT;

        for (LightSource lightSource : scene.getLights()) {

            Ray lightSourceToClosestIntersection = Ray.fromPoints(lightSource.getPoint(), eyeToScreenPoint.getPoint());
            RayIntersection fromLightSourceClosest = findClosestIntersection(scene, lightSourceToClosestIntersection);

            if (fromLightSourceClosest != null) {

                double distance1 = eyeToScreenPoint.getPoint().sub(lightSource.getPoint()).norm();
                double distance2 = fromLightSourceClosest.getPoint().sub(lightSource.getPoint()).norm();

                if (distance2 + THRESHOLD > distance1) {

                    Point3D closestIntersectionToLightSource = lightSource.getPoint().sub(eyeToScreenPoint.getPoint());
                    double productLightSourceAndNormal = closestIntersectionToLightSource.normalize()
                            .scalarProduct(eyeToScreenPoint.getNormal().normalize());

                    if (productLightSourceAndNormal < 0) {
                        continue;
                    }
                    short diffuseRed = determineColorFor(productLightSourceAndNormal, lightSource.getR(),
                            eyeToScreenPoint.getKdr());
                    short diffuseGreen = determineColorFor(productLightSourceAndNormal, lightSource.getG(),
                            eyeToScreenPoint.getKdg());
                    short diffuseBlue = determineColorFor(productLightSourceAndNormal, lightSource.getB(),
                            eyeToScreenPoint.getKdb());

                    Point3D toEye = ray.direction.negate().normalize();
                    Point3D reflective = getReflectiveVector(eyeToScreenPoint, closestIntersectionToLightSource);
                    double productReflectiveAndEye = pow(reflective.normalize().scalarProduct(toEye),
                            eyeToScreenPoint.getKrn());

                    short reflectiveRed = determineColorFor(productReflectiveAndEye, lightSource.getR(),
                            eyeToScreenPoint.getKrr());
                    short reflectiveGreen = determineColorFor(productReflectiveAndEye, lightSource.getG(),
                            eyeToScreenPoint.getKrg());
                    short reflectiveBlue = determineColorFor(productReflectiveAndEye, lightSource.getB(),
                            eyeToScreenPoint.getKrb());

                    rgb[0] += diffuseRed + reflectiveRed;
                    rgb[1] += diffuseGreen + reflectiveGreen;
                    rgb[2] += diffuseBlue + reflectiveBlue;
                }
            }
        }
    }

    /**
     * Returns vector/point of reflected ray.The reflected vector is calculated with regard to the ray from the observed
     * point to the light source and to the normal in the observed point.
     *
     * @param intersection ray intersection.
     * @param point        observed point.
     * @return reflected vector.
     */
    public static Point3D getReflectiveVector(RayIntersection intersection, Point3D point) {
        return intersection.getNormal().normalize().scalarMultiply((2 *
                point.scalarProduct(intersection.getNormal())) /
                intersection.getNormal().norm()).sub(point);
    }

    /**
     * Returns a diffuse or reflective component for the given parameters.
     *
     * @param scalarProduct scalar product that appears in the diffuse/reflective component.
     * @param intensity     intensity of light source.
     * @param coef          coefficient that appears in the diffuse/reflective component.
     * @return diffuse/reflective component.
     * @see <a href="Phong reflection model">https://en.wikipedia.org/wiki/Phong_reflection_model</a>
     */
    public static short determineColorFor(double scalarProduct, int intensity, double coef) {
        return (short) (intensity * coef * scalarProduct);
    }

    /**
     * Calculates intersection between given ray and all object in scene. In case there exists more than one intersection,
     * this method returns first (closest) intersection encountered by observer.
     * If there exists no acceptable intersection between given ray and this object, the method must returns null.
     *
     * @param scene current scene.
     * @param ray   ray.
     * @return instance of {@link RayIntersection}.
     */
    public static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
        List<GraphicalObject> graphicalObjects = scene.getObjects();
        double distance = Double.MAX_VALUE;
        RayIntersection rayIntersection = null;

        for (GraphicalObject graphicalObject : graphicalObjects) {
            RayIntersection current = graphicalObject.findClosestRayIntersection(ray);
            if (current != null) {
                if (current.getDistance() < distance) {
                    distance = current.getDistance();
                    rayIntersection = current;
                }
            }
        }
        return rayIntersection;
    }
}
