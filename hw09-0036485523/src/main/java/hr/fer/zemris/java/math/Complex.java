package hr.fer.zemris.java.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.*;

/**
 * Represents complex number with its real and imaginary part. The complex number format is : a + bi ,
 * where a and b are real numbers.
 * For more info  : @see <a href="https://en.wikipedia.org/wiki/Complex_number">Complex numbers</a>
 *
 * @author Tomislav Bozuric
 */

public class Complex {
    /**
     * Represents complex number in the origin of the coordinate system, 0 + 0i.
     */
    public static final Complex ZERO = new Complex(0, 0);
    /**
     * Represents complex number 1 + 0i;
     */
    public static final Complex ONE = new Complex(1, 0);
    /**
     * Represents complex number -1 + 0i;
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);
    /**
     * Represents complex number 0 + 1i;
     */
    public static final Complex IM = new Complex(0, 1);
    /**
     * Represents  complex number 0 -1i;
     */
    public static final Complex IM_NEG = new Complex(0, -1);
    private static final String COMPLEX_NUMBER_EXCEPTION_MESSAGE = "Complex number must not be null.";

    /**
     * The real part of the complex number.
     */
    private double real;
    /**
     * The imaginary part of the complex number.
     */
    private double imaginary;

    /**
     * Creates an instance of complex number with default values(the imaginary and real part are set to 0).
     */
    public Complex() {
        this.real = 0;
        this.imaginary = 0;
    }

    /**
     * Creates an instance of complex number.
     *
     * @param re real part of complex number.
     * @param im imaginary part of complex number.
     */
    public Complex(double re, double im) {
        this.real = re;
        this.imaginary = im;
    }

    /**
     * Returns the real part of a complex number.
     *
     * @return the real part of a complex number.
     */
    public double getReal() {
        return real;
    }


    /**
     * Returns the imaginary part of a complex number.
     *
     * @return the imaginary part of a complex number.
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Returns a complex number module
     *
     * @return a complex number module.
     */
    public double module() {
        return sqrt(real * real + imaginary * imaginary);
    }

    /**
     * Multiplies two complex numbers(this * c).
     *
     * @param c other complex number.
     * @return a new complex number that represents product of two complex numbers.
     * @throws NullPointerException if the other complex number is a null reference.
     */
    public Complex multiply(Complex c) {
        if (c == null) {
            throw new NullPointerException(COMPLEX_NUMBER_EXCEPTION_MESSAGE);
        }
        return new Complex(real * c.real - imaginary * c.imaginary,
                real * c.imaginary + imaginary * c.real);
    }

    /**
     * Divides two complex numbers(this/c).
     *
     * @param c other complex number.
     * @return a new complex number that represents quotient of two complex numbers.
     * @throws NullPointerException if the other complex number is a null reference.
     */
    public Complex divide(Complex c) {
        Objects.requireNonNull(c, COMPLEX_NUMBER_EXCEPTION_MESSAGE);

        double numeratorReal = real * c.real + imaginary * c.imaginary;
        double numeratorImaginary = -real * c.imaginary + imaginary * c.real;
        double denominator = c.real * c.real + c.imaginary * c.imaginary;
        return new Complex(numeratorReal / denominator, numeratorImaginary / denominator);

    }

    /**
     * Adds two complex numbers(this+c).
     *
     * @param c other complex number.
     * @return a new complex number that represents sum of two complex numbers.
     * @throws NullPointerException if the other complex number is a null reference.
     */
    public Complex add(Complex c) {
        Objects.requireNonNull(c, COMPLEX_NUMBER_EXCEPTION_MESSAGE);
        return new Complex(real + c.real, imaginary + c.imaginary);
    }

    /**
     * Subtracts two complex numbers(this-c).
     *
     * @param c other complex number.
     * @return a new complex number that represents difference of two complex numbers.
     * @throws NullPointerException if the other complex number is a null reference.
     */
    public Complex sub(Complex c) {
        Objects.requireNonNull(c, COMPLEX_NUMBER_EXCEPTION_MESSAGE);

        return new Complex(real - c.real, imaginary - c.imaginary);
    }

    /**
     * Returns a complex number whose real and imaginary parts are multiplied by -1.
     *
     * @return a complex number whose real and imaginary parts are multipled by -1.
     */
    public Complex negate() {
        return new Complex(-real, -imaginary);
    }

    /**
     * Calculates  n-th power of a complex number.
     *
     * @param n nth power
     * @return a new complex number that is raised to the n-th power
     * @throws IllegalArgumentException if given power is less than 0.
     * @see <a href="https://brilliant.org/wiki/de-moivres-theorem/">De Moivres Theorem</a>
     */
    public Complex power(int n) {
        if (n < 0)
            throw new IllegalArgumentException("The exponent must be greater or equal to zero." + " Was " + n);
        double magnitude = module();
        double angle = atan2(imaginary, real);
        double factor = pow(magnitude, n);
        // De Moivre's Theorem
        return new Complex(factor * cos(n * angle), factor * sin(n * angle));
    }

    /**
     * Calculates n roots of a complex number.
     *
     * @param n number of roots.
     * @return array of roots of a complex number.
     * @throws IllegalArgumentException if the given number is less or equal to zero.
     */
    public List<Complex> root(int n) {
        if (n <= 0)
            throw new IllegalArgumentException(
                    "The roots of the complex number can only be calculated for positive number of roots!");
        List<Complex> roots = new ArrayList<>();
        double magnitude = module();
        double angle = atan2(imaginary, real);
        double factor = pow(magnitude, 1.0 / n);
        for (int k = 0; k < n; k++) {
            double real = cos((2 * k * PI + angle) / n);
            double imaginary = sin((2 * k * PI + angle) / n);
            roots.add(new Complex(factor * real, factor * imaginary));
        }
        return roots;
    }

    /**
     * Calculates the distance between two complex numbers.
     *
     * @param c other complex number.
     * @return distance between two complex numbers.
     * @throws NullPointerException if the other complex number is a null reference.
     */
    public double distance(Complex c) {
        Objects.requireNonNull(c, COMPLEX_NUMBER_EXCEPTION_MESSAGE);

        return sqrt(pow(real - c.real, 2.0) + pow(imaginary - c.imaginary, 2.0));
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

    /**
     * Returns string representation of complex number in formats : "a" , "bi" , "(a+bi)".
     *
     * @return string representation of complex number.
     */
    @Override
    public String toString() {
        if (isZero(imaginary) && isZero(real)) {
            return "0.0";
        }
        if (isZero(real)) {
            return imaginary + "i";
        } else if (isZero(imaginary)) {
            return real + "";
        } else if (!isZero(real) && !isZero(imaginary)) {
            return imaginary > 0 ? "( " + real + " + " + imaginary + "i )" : "( " + real + "" + imaginary + "i )";
        } else {
            return "";
        }
    }
}
