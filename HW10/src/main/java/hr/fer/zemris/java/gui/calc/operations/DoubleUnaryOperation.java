package hr.fer.zemris.java.gui.calc.operations;

import java.util.function.DoubleUnaryOperator;

/**
 * Represents two unary operations("normal" operator and inverse operator).
 * For example, the inverse operator of sin is  arcsin etc.
 */
public class DoubleUnaryOperation implements Operation {

    /**
     * Represents "normal" double unary operator.
     */
    private DoubleUnaryOperator operator;

    /**
     * Represents inverse of a double unary operator.
     */
    private DoubleUnaryOperator inverseOperator;

    /**
     * Creates an instance of {@link DoubleUnaryOperation}.
     *
     * @param operator "normal" double unary operator.
     * @param inverse  inverse of a double unary operator.
     */
    public DoubleUnaryOperation(DoubleUnaryOperator operator, DoubleUnaryOperator inverse) {
        this.operator = operator;
        this.inverseOperator = inverse;
    }

    @Override
    public DoubleUnaryOperator getOperation(boolean selected) {
        return selected ? inverseOperator : operator;
    }
}
