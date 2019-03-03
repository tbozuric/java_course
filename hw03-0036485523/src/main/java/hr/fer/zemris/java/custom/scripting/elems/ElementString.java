package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents the string in the expression.
 */
public class ElementString extends Element {
    /**
     * Value of the string.
     */
    private String value;

    /**
     * Creates an instance of string element with value set to given value.
     *
     * @param value of the string.
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * Returns value of the string.
     *
     * @return value of the string
     */
    public String getValue() {
        return value;
    }

    @Override
    public String asText() {
        return value;
    }
}
