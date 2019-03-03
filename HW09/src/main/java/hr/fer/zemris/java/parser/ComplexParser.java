package hr.fer.zemris.java.parser;

import hr.fer.zemris.java.math.Complex;

import java.util.regex.Pattern;

/**
 * Represents a parser for complex numbers. General syntax for complex numbers is of form <code><p>a+ib </p></code> or <code><p>a-ib </p></code>
 * where parts that are zero can be dropped, but not both (empty string is not legal complex number);
 * for example, zero can be given as <code><p> 0, i0, 0+i0, 0-i0.</p></code>
 * If there is 'i' present but no b is given, you must assume that b=1.
 */
public class ComplexParser {
    /**
     * The tag that refers to the imaginary part of the complex numbers.
     */
    private static final String IMAGINARY_MARK = "i";
    /**
     * Represents a pattern for comparing a complex number that consists only of a real part.
     */
    private static Pattern realPart;
    /**
     * Represents a pattern for comparing a complex number that consists only of a imaginary part.
     */
    private static Pattern imaginaryPart;
    /**
     * Represents a pattern for comparing a complex number that consists of a real and imaginary part.
     */
    private static Pattern realAndImaginaryPart;

    /**
     * Reference to our {@link ComplexParser}.
     */
    private static ComplexParser parser;

    /**
     * Creates an instance of complex parser.
     */
    private ComplexParser() {
        realPart = Pattern.compile("-?[0-9]+(\\.[0-9][0-9]*)?");
        imaginaryPart = Pattern.compile("-?i[0-9]*(\\.[0-9][0-9]*)?");
        realAndImaginaryPart = Pattern.compile("-?[0-9]+(\\.[0-9][0-9]*)?(\\s*)[+|\\-]" +
                "(\\s*)-?i[0-9]*(\\.[0-9][0-9]*)?");
    }

    /**
     * Returns the instance of complex numbers parser.
     *
     * @return the instance of complex number parser.
     */
    public static ComplexParser getInstance() {
        if (parser == null) {
            parser = new ComplexParser();
            return parser;
        }
        return parser;
    }


    /**
     * Parses the given string and returns a new complex number.
     *
     * @param line input string.
     * @return a new complex number.
     * @throws IllegalArgumentException if it is not possible to parse the input string to the complex number.
     */
    public Complex parseComplexNumber(String line) {
        if (realPart.matcher(line).matches()) {
            return new Complex(Double.parseDouble(line), 0);
        } else if (imaginaryPart.matcher(line).matches()) {
            return getComplexWithImaginaryPart(line);
        } else if (realAndImaginaryPart.matcher(line).matches()) {
            int indexOfPlus = line.indexOf("+");
            return new Complex(getRealPart(line, indexOfPlus), getImaginaryPart(line, indexOfPlus));
        }
        throw new IllegalArgumentException("It is not possible to parse the input string. Wrong form of complex number.");
    }

    /**
     * Returns the imaginary part of a complex number.
     *
     * @param line        input string.
     * @param indexOfPlus position of the character "+" in the input string, or -1.
     * @return the imaginary part of a complex number.
     */
    private double getImaginaryPart(String line, int indexOfPlus) {
        return indexOfPlus != -1 ? getComplexWithImaginaryPart(line.substring(indexOfPlus + 1).trim()).getImaginary() :
                getComplexWithImaginaryPart(line.substring(line.indexOf("-")).trim()).getImaginary();
    }

    /**
     * Returns the real part of a complex number.
     *
     * @param line        input string.
     * @param indexOfPlus position of the character "+" in the input string, or -1.
     * @return the real part of a complex number.
     */
    private double getRealPart(String line, int indexOfPlus) {
        return indexOfPlus != -1 ? Double.parseDouble(line.substring(0, line.indexOf("+"))) :
                Double.parseDouble(line.substring(0, line.indexOf("-")));
    }

    /**
     * Returns a new {@link Complex} number that consists only of the imaginary part.
     *
     * @param line input string.
     * @return a new {@link Complex} number that consists only of the imaginary part.
     */
    private Complex getComplexWithImaginaryPart(String line) {
        line = line.replaceAll("\\s+", "");
        if (line.startsWith("-")) {
            line = line.replaceAll(IMAGINARY_MARK, "").trim();
            if (line.length() > 1) {
                return new Complex(0, Double.parseDouble(line));
            }
            return Complex.IM_NEG;
        }
        line = line.replaceAll(IMAGINARY_MARK, "").trim();
        if (line.length() == 0) {
            return Complex.IM;
        }
        return new Complex(0, Double.parseDouble(line));
    }

}
