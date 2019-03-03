package hr.fer.zemris.java.gui.calc.operations;

import java.util.function.DoubleBinaryOperator;

/**
 * Represents two binary operations("normal" operator and inverse operator).
 * For example, the inverse operator of x^n is  n-th root of x etc.
 */
public class DoubleBinaryOperation implements Operation {

    /**
     * Represents "normal" double binary operator.
     */
    private DoubleBinaryOperator operator;


    /**
     * Represents inverse of a double binary operator.
     */
    private DoubleBinaryOperator inverseOperator;

    /**
     * Creates an instance of {@link DoubleBinaryOperation}.
     *
     * @param operator "normal" double binary operator.
     * @param inverse  inverse of a double binary operator.
     */
    public DoubleBinaryOperation(DoubleBinaryOperator operator, DoubleBinaryOperator inverse) {
        this.operator = operator;
        this.inverseOperator = inverse;
    }

    @Override
    public DoubleBinaryOperator getOperation(boolean selected) {
        return selected ? inverseOperator : operator;
    }
}
