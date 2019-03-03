package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents the operator in the expression.
 */
public class ElementOperator extends Element {
    /**
     * Symbol of the operator
     */
    private String symbol;

    /**
     * Creates an instance with symbol set to given symbol.
     *
     * @param symbol of the operator.
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the operator symbol.
     *
     * @return operator symbol
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }
}
