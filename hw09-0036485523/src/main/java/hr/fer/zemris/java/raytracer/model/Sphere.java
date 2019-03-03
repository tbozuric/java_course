package hr.fer.zemris.java.raytracer.model;

/**
 * Represents the sphere in the 3d  determined by the center and radius.
 */
public class Sphere extends GraphicalObject {
    /**
     * Threshold for comparison of numbers.
     */
    private static final double THRESHOLD = 1e-3;
    /**
     * The center of the sphere.
     */
    private Point3D center;
    /**
     * Radius of the sphere
     */
    private double radius;
    /**
     * Coefficient for diffuse component for red color; used in lightning model to calculate point color. Legal values are [0.0,1.0].
     */
    private double kdr;
    /**
     * Coefficient for diffuse component for green color; used in lightning model to calculate point color. Legal values are [0.0,1.0].
     */
    private double kdg;
    /**
     * Coefficient for diffuse component for blue color; used in lightning model to calculate point color. Legal values are [0.0,1.0].
     */
    private double kdb;
    /**
     * Coefficient for reflective component for red color; used in lightning model to calculate point color. Legal values are [0.0,1.0].
     */
    private double krr;
    /**
     * Coefficient for reflective component for green color; used in lightning model to calculate point color. Legal values are [0.0,1.0].
     */
    private double krg;
    /**
     * Coefficient for reflective component for blue color; used in lightning model to calculate point color. Legal values are [0.0,1.0].
     */
    private double krb;
    /**
     * Coefficient n for reflective component; used in lightning model to calculate point color. Legal values are [0.0,1.0].
     */
    private double krn;

    /**
     * Creates an instance of sphere.
     *
     * @param center center of the sphere
     * @param radius sphere radius.
     * @param kdr    coefficient for diffuse component for red color.
     * @param kdg    coefficient for diffuse component for green color.
     * @param kdb    coefficient for diffuse component for blue color.
     * @param krr    reflective component for red color.
     * @param krg    reflective component for green color.
     * @param krb    reflective component for blue color.
     * @param krn    coefficient n for reflective component.
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb, double krn) {
        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    /**
     * @see hr.fer.zemris.java.raytracer.model.GraphicalObject#findClosestRayIntersection(hr.fer.zemris.java.raytracer.model.Ray)
     * Assumption: the observer is not in the sphere.
     */
    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {
        double b = 2 * ray.direction.normalize().scalarProduct(ray.start.sub(center));
        double c = ray.start.sub(center).scalarProduct(ray.start.sub(center)) - radius * radius;
        double discriminant = b * b - 4 * c;

        if (discriminant < 0) {
            return null;
        }

        double lambdaOne = (-b + Math.sqrt(discriminant)) / 2.0;

        double lambdaTwo = (-b - Math.sqrt(discriminant)) / 2.0;

        Point3D pointOne = ray.start.add(ray.direction.normalize().scalarMultiply(lambdaOne));
        Point3D pointTwo = ray.start.add(ray.direction.normalize().scalarMultiply(lambdaTwo));

        double distanceOne = pointOne.sub(ray.start).norm();
        double distanceTwo = pointTwo.sub(ray.start).norm();

        Point3D normalOne = pointOne.sub(center);
        Point3D normalTwo = pointTwo.sub(center);

        if (lambdaOne > 0 && lambdaTwo > 0) {
            if (lambdaOne < lambdaTwo) {
                return new RayIntersectionImpl(pointOne, distanceOne, true, normalOne);
            }
            return new RayIntersectionImpl(pointTwo, distanceTwo, true, normalTwo);
        } else if (Math.abs(lambdaOne - lambdaTwo) < THRESHOLD) {
            if (lambdaOne > 0) {
                return new RayIntersectionImpl(pointOne, distanceOne, true, normalOne);
            }
            return new RayIntersectionImpl(pointOne, distanceOne, false, normalTwo);
        }
        return null;
    }

    /**
     * Concrete implementation of {@link RayIntersection}.
     */
    private class RayIntersectionImpl extends RayIntersection {
        /**
         * Represnets normal to object surface at this intersection point.
         */
        Point3D normal;

        /**
         * Constructor for intersection.
         *
         * @param point    point of intersection between ray and object
         * @param distance distance between start of ray and intersection
         * @param outer    specifies if intersection is outer
         */
        protected RayIntersectionImpl(Point3D point, double distance, boolean outer, Point3D normal) {
            super(point, distance, outer);
            this.normal = normal;
        }

        @Override
        public Point3D getNormal() {
            return normal;
        }

        @Override
        public double getKdr() {
            return kdr;
        }

        @Override
        public double getKdg() {
            return kdg;
        }

        @Override
        public double getKdb() {
            return kdb;
        }

        @Override
        public double getKrr() {
            return krr;
        }

        @Override
        public double getKrg() {
            return krg;
        }

        @Override
        public double getKrb() {
            return krb;
        }

        @Override
        public double getKrn() {
            return krn;
        }
    }
}
