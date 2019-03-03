package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents a integer element in the expression
 */
public class ElementConstantInteger extends Element {

    /**
     * Value of integer constant element.
     */
    private int value;

    /**
     * Creates an instance with value set to given value.
     *
     * @param value of element.
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Returns value of element.
     *
     * @return value of element.
     */
    public int getValue() {
        return value;
    }


    @Override
    public String asText() {
        return String.valueOf(value);
    }
}
