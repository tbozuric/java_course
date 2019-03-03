package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents the variable in the expression
 */
public class ElementVariable extends Element {
    /**
     * Name of the variable.
     */
    private String name;

    /**
     * Creates an instance with name set to given name.
     *
     * @param name of the variable
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    /**
     * Returns name of the variable.
     *
     * @return name of the variable.
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return name;
    }
}
