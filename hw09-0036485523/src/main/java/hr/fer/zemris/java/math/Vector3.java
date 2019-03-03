package hr.fer.zemris.java.math;

import java.util.Objects;

/**
 * Models an immutable three-component vector in a three-dimensional space. Each vector is represented by  its
 * <code>x, y and z </code> components.
 */
public class Vector3 {
    private static final String VECTOR_EXCEPTION_MESSAGE = "Vector must not be null.";
    /**
     * X component of vector.
     */
    private double x;
    /**
     * Y component of vector.
     */
    private double y;
    /**
     * Z component of vector.
     */
    private double z;

    /**
     * Creates an instance of 3-dimensional vector in 3-D space.
     *
     * @param x x component of vector.
     * @param y y component of vector.
     * @param z z component of vector.
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the x-component of a vector.
     *
     * @return x-component of a vector.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-component of a vector.
     *
     * @return y-component of a vector.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the z-component of a vector.
     *
     * @return z-component of a vector.
     */
    public double getZ() {
        return z;
    }


    /**
     * Returns the norm of a vector .
     *
     * @return the norm of a vector norm.
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Returns a new normalized vector.
     * @throws ArithmeticException if the denominator is zero.
     * @return a new normalized vector.
     */
    public Vector3 normalized() {
        double norm = norm();
        if(isZero(norm)){
            throw new ArithmeticException("The denominator is zero!");
        }
        return new Vector3(x / norm, y / norm, z / norm);
    }


    /**
     * Adds two 3-dimensional vectors.
     *
     * @param other other 3D vector.
     * @return a new 3D vector that represents sum of two 3D vectors..
     * @throws NullPointerException if the vector is a null reference.
     */
    public Vector3 add(Vector3 other) {
        Objects.requireNonNull(other, VECTOR_EXCEPTION_MESSAGE);
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }


    /**
     * Subtracts two 3-dimensional vectors.
     *
     * @param other other 3D vector.
     * @return a new 3D vector that represents difference of two 3D vectors.
     * @throws NullPointerException if the  other vector is a null reference.
     */
    public Vector3 sub(Vector3 other) {
        Objects.requireNonNull(other, VECTOR_EXCEPTION_MESSAGE);
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Returns a new vector  that representing the dot product of this vector and the given vector.
     *
     * @param other some 3D vector.
     * @return the dot product of this vector and the given vector.
     * @throws NullPointerException if the other vector is a null reference.
     */
    public double dot(Vector3 other) {
        Objects.requireNonNull(other, VECTOR_EXCEPTION_MESSAGE);
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Returns a new vector that representing the cross product of this vector and the given vector.
     *
     * @param other some 3D vector.
     * @return the cross product of this vector and the given vector.
     * @throws NullPointerException if the other vector is a null reference.
     */
    public Vector3 cross(Vector3 other) {
        Objects.requireNonNull(other, VECTOR_EXCEPTION_MESSAGE);
        return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }

    /**
     * Returns a scaled vector with a given coefficient. For example:
     * <code><p> a = (1,1,1), coefficient = 2 , new vector = (2,2,2) </p></code>
     *
     * @param s scaling coefficient.
     * @return a scaled vector.
     */
    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }


    /**
     * Returns the cos angle between two 3D vectors.
     *
     * @param other some other 3D vector.
     * @return cos angle between two 3D vectors.
     * @throws NullPointerException if the other vector is a null reference.
     * @throws ArithmeticException if the denominator is zero.
     */
    public double cosAngle(Vector3 other) {
        Objects.requireNonNull(other, VECTOR_EXCEPTION_MESSAGE);
        double norm = norm();
        double otherNorm = other.norm();
        if(isZero(norm) || isZero(otherNorm)){
            throw new ArithmeticException("The denominator is zero.");
        }
        return this.dot(other) / (norm() * other.norm());
    }

    /**
     * "Converts" vector components to double array. For example:
     * <code><p>a = (1,1,1) , double[] array elements : [1,1,1] </p></code>
     *
     * @return vector components as double array.
     */
    public double[] toArray() {
        return new double[]{x, y, z};
    }

    /**
     * Returns a string representation of a 3-dimensional vector in format : <p>(a, c, b).</p>
     *
     * @return a string representation of a 3-dimensional vector.
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    /**
     * Checks if the given value is equal to zero.
     *
     * @param value value for check.
     * @return whether the given value is equal to zero.
     */
    private boolean isZero(double value) {
        double threshold = 1E-3;
        return value >= -threshold && value <= threshold;
    }

}
