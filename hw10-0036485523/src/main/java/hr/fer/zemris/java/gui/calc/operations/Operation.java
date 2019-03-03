package hr.fer.zemris.java.gui.calc.operations;

/**
 * This interface represents a strategy in the Strategy pattern.
 * This interface is used to retrieve a {@link java.util.function.BinaryOperator} or {@link java.util.function.UnaryOperator}
 * that needs to be performed.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Strategy_pattern">Strategy pattern</a>
 */
public interface Operation {

    /**
     * Returns the operation that needs to be performed.
     *
     * @param selected flag indicating whether to retrieve an inverse operation
     * @return the operation that needs to be performed.
     */
    Object getOperation(boolean selected);
}
