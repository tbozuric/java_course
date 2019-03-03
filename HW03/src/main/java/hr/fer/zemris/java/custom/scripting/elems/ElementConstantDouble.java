package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents a double element in the expression
 */
public class ElementConstantDouble extends Element {
    /**
     * Value of double constant element.
     */
    private double value;

    /**
     * Creates an instance with value set to given value.
     *
     * @param value of element.
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Returns value of double constant element.
     *
     * @return value of double constant element.
     */
    public double getValue() {
        return value;
    }

    @Override
    public String asText() {
        return String.valueOf(value);
    }
}
