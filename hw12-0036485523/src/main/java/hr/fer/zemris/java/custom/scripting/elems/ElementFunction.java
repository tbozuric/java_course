package hr.fer.zemris.java.custom.scripting.elems;


/**
 * Represents the function in the expression.
 */
public class ElementFunction extends Element {
    /**
     * Name of the function.
     */
    private String name;

    /**
     * Creates an instance with function name set to given name.
     *
     * @param name of the function.
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Returns name of the function.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return name;
    }
}
