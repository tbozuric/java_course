package hr.fer.zemris.java.math;

/**
 * Models the polynomial over complex numbers, according to the template below.
 * It is a polynomial of <code><p>f(z) = (z-z1)(z-z2)(z-z3)...(z-zn) </p></code> where z1 to zn are complex polynomial roots.
 * This is n-order complex polynomial. All roots are given as complex numbers, and even z is a complex number.
 */
public class ComplexRootedPolynomial {
    /**
     * The roots of the complex rooted polynomial.
     */
    private Complex[] roots;

    /**
     * Creates an instance of complex rooted polynomial.
     *
     * @param roots roots of the complex rooted polynomial.
     * @throws IllegalArgumentException if the number of roots is less than 1.
     */
    public ComplexRootedPolynomial(Complex... roots) {
        if (roots.length == 0) {
            throw new IllegalArgumentException("Number of roots must be greater or equal to 1.");
        }
        this.roots = roots;
    }

    /**
     * Computes a polynomial value at the given point and returns computed value.
     *
     * @param z some complex number.
     * @return a polynomial value at the given point.
     * @throws NullPointerException if the complex number is a null reference.
     */
    public Complex apply(Complex z) {
        if (z == null) {
            throw new NullPointerException("Complex number must not be null.");
        }
        Complex result = Complex.ONE;
        for (Complex root : roots) {
            result = result.multiply(z.sub(root));
        }
        return result;
    }

    /**
     * Converts this representation to the {@link ComplexPolynomial} type.
     *
     * @return converted representation of this format to the {@link ComplexPolynomial} type.
     */
    public ComplexPolynomial toComplexPolynom() {

        ComplexPolynomial[] polynomials = new ComplexPolynomial[roots.length];
        for (int i = 0; i < roots.length; i++) {
            polynomials[i] = new ComplexPolynomial(roots[i].negate(), Complex.ONE);
        }

        ComplexPolynomial result = polynomials[0];
        for (int i = 1; i < polynomials.length; i++) {
            result = result.multiply(polynomials[i]);
        }

        return result;

    }

    /**
     * Finds index of closest root for the given complex number z that is within threshold.
     * If there is no such root, returns -1.
     *
     * @param z         some complex number.
     * @param threshold within which we are checking.
     * @return -1 if there is no such root, or index of closest root.
     * @throws NullPointerException if the given complex number is a null reference.
     */

    public int indexOfClosestRootFor(Complex z, double threshold) {
        if (z == null) {
            throw new NullPointerException("Complex number must not be null");
        }
        int index = -1;
        double distance = Double.MAX_VALUE;

        for (int i = 0; i < roots.length; i++) {
            double currentDistance = z.distance(roots[i]);
            if (currentDistance < distance) {
                distance = currentDistance;
                index = i;
            }
        }
        if (index >= 0 && distance <= threshold) {
            return index;
        }
        return -1;
    }

    /**
     * Returns a string representation of a complex rooted polynomial.
     *
     * @return a string representation of a complex rooted polynomial.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Complex root : roots) {
            sb.append("(z-").append(root).append(")");
        }
        String term = sb.toString();
        //replace -- with + so for example (z--1.0) is transformed to (z+1.0).
        term = term.replaceAll("--", "+");
        return term;
    }


}
