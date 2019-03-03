package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Represents an calculator model. The model offers methods such as {@link #swapSign()}  for swapping signs,
 * {@link #insertDecimalPoint()} for inserting a decimal point, {@link #insertDigit(int)} for inserting digits,
 * {@link #setActiveOperand(double)} setting an active operand, and so on.
 */
public interface CalcModel {

    /**
     * Adds new calculator value listener.
     *
     * @param l calculator value listener.
     * @see CalcValueListener#valueChanged(CalcModel)
     */
    void addCalcValueListener(CalcValueListener l);

    /**
     * Removes calculator value listener if it exist..
     *
     * @param l calculator value listener.
     * @see CalcValueListener#valueChanged(CalcModel)
     */
    void removeCalcValueListener(CalcValueListener l);

    /**
     * Returns "0" if the stored value is null and otherwise returns the remembered string(stored value in the calculator)..
     *
     * @return string representation of stored value in the calculator.
     */
    String toString();

    /**
     * The stored string parses into double and returns (if it is not a  null reference), otherwise it returns 0.0.
     *
     * @return 0.0 or parsed string.
     * @see #toString()
     */
    double getValue();

    /**
     * This method converts the given value to the string(if it is not infinity or Nan) and
     * sets this string as a stored value.
     *
     * @param value value.
     */
    void setValue(double value);

    /**
     * Clears the current value.
     */
    void clear();

    /**
     * Clears the current value, the active operand and the pending operation.
     */
    void clearAll();

    /**
     * Swaps the sign of the stored number.
     */
    void swapSign();

    /**
     * In the number inserts the decimal point if it does not exist(otherwise it does nothing).
     */
    void insertDecimalPoint();

    /**
     * In the number inserts the digit.
     *
     * @param digit the digit we want to add to the end of the number.
     */
    void insertDigit(int digit);

    /**
     * Checks if an active operand is set.
     *
     * @return true if an active operand is set.
     */
    boolean isActiveOperandSet();

    /**
     * Returns the active operand.
     *
     * @return the active operand.
     * @throws IllegalStateException if the active operand is not set.
     * @see #setActiveOperand(double)
     */
    double getActiveOperand();

    /**
     * Sets the current active operand.
     *
     * @param activeOperand the active operand.
     */
    void setActiveOperand(double activeOperand);

    /**
     * Clears the active operand.
     */
    void clearActiveOperand();

    /**
     * Returns the pending binary operation.
     *
     * @return the pending binary operation.
     */
    DoubleBinaryOperator getPendingBinaryOperation();

    /**
     * Sets the current pending binary operation.
     *
     * @param op the double binary operator {@link DoubleBinaryOperator}
     */
    void setPendingBinaryOperation(DoubleBinaryOperator op);
}