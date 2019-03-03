package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.toRadians;

/**
 * Represents a vector in a two-dimensional space.
 */
public class Vector2D {

    /**
     * x value of the vector.
     */
    private double x;

    /**
     * y value of the vector.
     */
    private double y;

    /**
     * Creates an instance of vector in a two-dimensional space.
     *
     * @param x value of the vector.
     * @param y value of the vector.
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns x value of the vector.
     *
     * @return x value of the vector.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns y value of the vector.
     *
     * @return y value of the vector.
     */
    public double getY() {
        return y;
    }

    /**
     * Translates the vector for the given offset.
     *
     * @param offset desired offset in a two-dimensional space.
     * @throws IllegalArgumentException if the offset vector is null.
     */
    public void translate(Vector2D offset) {
        if (offset == null) {
            throw new IllegalArgumentException("Offset must not be null!");
        }
        x += offset.x;
        y += offset.y;
    }

    /**
     * Returns a new vector that is translated for the given offset.
     *
     * @param offset desired offset in a two-dimensional space.
     * @return a new vector that is translated for a given offset.
     * @throws IllegalArgumentException if the offset vector is null.
     */
    public Vector2D translated(Vector2D offset) {
        if (offset == null) {
            throw new IllegalArgumentException("Offset must not be null!");
        }
        return new Vector2D(x + offset.x, y + offset.y);
    }

    /**
     * Rotates the vector in two-dimensional space for the given angle(in degrees)
     *
     * @param angle desired vector rotation angle(in degrees).
     */
    public void rotate(double angle) {
        double length = sqrt(x * x + y * y);
        double currentAngle = atan2(y, x);
        x = length * cos(currentAngle + toRadians(angle));
        y = length * sin(currentAngle + toRadians(angle));

    }

    /**
     * Returns a new vector that is translated for the given angle in degrees.
     *
     * @param angle desired vector rotation angle(in degrees).
     * @return a new vector that is rotated for the given angle in degrees.
     */
    public Vector2D rotated(double angle) {
        double length = sqrt(x * x + y * y);
        double currentAngle = atan2(y, x);
        return new Vector2D(length * cos(currentAngle + toRadians(angle)), length * sin(currentAngle + toRadians(angle)));
    }

    /**
     * Scales the x and y values of the vector with the given scaler.
     *
     * @param scaler desired scaling value.
     */
    public void scale(double scaler) {
        x *= scaler;
        y *= scaler;
    }

    /**
     * Returns a new vector that is scaled with the given scaler.
     *
     * @param scaler desired scaling value.
     * @return a new vector that is scaled with the given scaler.
     */
    public Vector2D scaled(double scaler) {
        return new Vector2D(x * scaler, y * scaler);
    }

    /**
     * Returns a new vector that is a copy of the current vector.
     *
     * @return a new vector that is a copy of the current vector.
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

}

