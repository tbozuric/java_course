package hr.fer.zemris.java.math;


/**
 * Models a polynomial over complex numbers. A complex polynomial is a polynomial with complex coefficients.
 * It is a polynomial of the following form:
 * <code><p>f(z) = zn *z^n +z(n-1) *z^(n-1) +...+z2 *z^2 +z1*z+z0</p></code>
 * This is an n-order polynomial. All coefficients are given as complex numbers, and even z is a complex number.
 */
public class ComplexPolynomial {

    /**
     * Complex coefficients "that stand" with the corresponding potentials of complex polynomial.
     */
    private Complex[] factors;

    /**
     * Creates an instance of complex polynomial with the given complex factors.
     *
     * @param factors complex coefficients.
     * @throws IllegalArgumentException if the number of factors is less than 1 or.
     */
    public ComplexPolynomial(Complex... factors) {
        if (factors.length == 0) {
            throw new IllegalArgumentException("Number of factors must be greater or equal to 1.");
        }
        this.factors = factors;
    }

    /**
     * Returns a order of complex polynomial, eg. for <code><p>(7+2i)z^3+2z^2+5z+1 returns 3. </p> </code>
     *
     * @return a order of complex polynomial.
     */
    public short order() {
        return (short) (factors.length - 1);
    }

    /**
     * Computes a product of two complex polynomials and returns a new complex polynomial.
     *
     * @param p other complex polynomial.
     * @return a product of two complex polynomials.
     * @throws NullPointerException if the complex polynomial is a null reference.
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        if (p == null) {
            throw new NullPointerException("Complex polynomial must not be null.");
        }
        Complex[] newFactors = new Complex[factors.length + p.getFactors().length - 1];
        for (int i = 0; i < newFactors.length; i++) {
            newFactors[i] = Complex.ZERO;
        }
        Complex[] other = p.getFactors();
        for (int i = factors.length - 1; i >= 0; i--) {
            for (int j = other.length - 1; j >= 0; j--) {
                newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(other[j]));
            }
        }
        return new ComplexPolynomial(newFactors);
    }

    /**
     * Computes the first derivation of the complex polynomial and returns it, for example :
     * <code><p> (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5</p></code> .
     *
     * @return the first derivation of the complex polynomial.
     */

    public ComplexPolynomial derive() {
        Complex[] newFactors = new Complex[factors.length - 1];
        int j;
        for (int i = factors.length - 1; i > 0; i--) {
            j = i - 1;
            newFactors[j] = factors[i].multiply(new Complex(i, 0));
        }
        return new ComplexPolynomial(newFactors);
    }

    /**
     * Computes the complex polynomial value at the given complex number.
     *
     * @param z some complex number.
     * @return value of complex polynomial at the given point.
     * @throws NullPointerException if the complex number is a null reference.
     */
    public Complex apply(Complex z) {
        if (z == null) {
            throw new NullPointerException("Complex number must not be null.");
        }
        Complex result = Complex.ZERO;
        for (int i = factors.length - 1; i >= 0; i--) {
            result = result.add(z.power(i).multiply(factors[i]));
        }
        return result;
    }

    /**
     * Returns complex factors.
     *
     * @return complex factors.
     */
    private Complex[] getFactors() {
        return factors;
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
     * Returns a string representation of complex polynomial in format:
     * <code><p>zn *z^n +z(n-1) *z^(n-1) +...+z2 *z^2 +z1*z+z0</p></code>.
     *
     * @return a string representation of complex polynomial.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = factors.length - 1; i >= 0; i--) {
            if (i == 0) {
                if (!isZero(factors[i].module())) {
                    sb.append(factors[i]);
                } else {
                    sb.deleteCharAt(sb.lastIndexOf("+"));
                }
                break;
            }
            if (!isZero(factors[i].module())) {
                sb.append(factors[i]).append(" * z^").append(i).append(" + ");
            }
        }
        return sb.toString();
    }


}
