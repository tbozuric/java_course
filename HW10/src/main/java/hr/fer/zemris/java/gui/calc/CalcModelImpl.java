package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Represents a concrete calculator model {@link CalcModel}.
 */
public class CalcModelImpl implements CalcModel {

    /**
     * Represents decimal number prefix.
     */
    private static final String DECIMAL_VALUE = "0.";

    /**
     * Represents current stored value.
     */
    private String currentValue;

    /**
     * Represents pending double binary operation.
     */
    private DoubleBinaryOperator pendingOperation;

    /**
     * Current active operand.
     */
    private double activeOperand;

    /**
     * Flag indicating whether the active operand is set.
     */
    private boolean isActiveOperand;

    /**
     * A list of observers subscribed to changing the calculator value
     */
    private List<CalcValueListener> observers;

    /**
     * Creates an instance of {@link CalcModelImpl}.
     */
    public CalcModelImpl() {
        currentValue = null;
        isActiveOperand = false;
        observers = new ArrayList<>();
    }

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        if (!observers.contains(l)) {
            observers.add(l);
        }
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        if (observers.contains(l)) {
            observers.remove(l);
        }
    }

    @Override
    public double getValue() {
        if (currentValue == null) {
            return 0.0;
        }

        if (currentValue.startsWith("0")) {
            removeMultipleZeros();
        }
        return Double.parseDouble(currentValue);
    }

    @Override
    public void setValue(double value) {
        if (checkNumber(value)) {

            if (value % 1 == 0) {
                currentValue = String.valueOf(((Double) value).intValue());
                notifyObservers();
                return;
            }
            if(currentValue != null) {
                int indexOfDecimalPoint = currentValue.indexOf(".");
                if (indexOfDecimalPoint != -1 && indexOfDecimalPoint != (currentValue.length() - 1) &&
                        (Double.parseDouble(currentValue) % 1) == 0) {
                    currentValue = currentValue.substring(0, indexOfDecimalPoint);
                }
            }

            currentValue = String.valueOf(value);
            notifyObservers();
        }
    }

    @Override
    public void clear() {
        currentValue = null;
        notifyObservers();
    }

    @Override
    public void clearAll() {
        currentValue = null;
        activeOperand = 0.0;
        isActiveOperand = false;
        pendingOperation = null;
        notifyObservers();
    }

    @Override
    public void swapSign() {
        if (currentValue != null) {
            String temp = String.valueOf(-Double.parseDouble(currentValue));
            if (!currentValue.contains(".")) {
                temp = temp.substring(0, temp.indexOf("."));
            }
            currentValue = temp;
            notifyObservers();
        }
    }

    @Override
    public void insertDecimalPoint() {
        if (currentValue == null) {
            currentValue = DECIMAL_VALUE;
            notifyObservers();
            return;
        }

        if (!currentValue.contains(".")) {
            currentValue += ".";
        }
        notifyObservers();
    }

    @Override
    public void insertDigit(int digit) {
        if (currentValue == null) {
            currentValue = "";
        }
        if (checkNumber(Double.parseDouble(currentValue + String.valueOf(digit)))) {
            currentValue += digit;
        }
        notifyObservers();
    }

    @Override
    public boolean isActiveOperandSet() {
        return isActiveOperand;
    }

    @Override
    public double getActiveOperand() {
        if (isActiveOperand) {
            return activeOperand;
        }
        throw new IllegalStateException("Active operand is not set.");
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        isActiveOperand = true;
    }

    @Override
    public void clearActiveOperand() {
        activeOperand = 0.0;
        isActiveOperand = false;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
    }

    @Override
    public String toString() {
        if (currentValue != null) {
            int indexOfDecimalPoint = currentValue.indexOf(".");
            if (indexOfDecimalPoint == -1 && Integer.parseInt(currentValue) == 0) {
                removeMultipleZeros();
            }
        }
        return currentValue == null ? "0" : currentValue;
    }

    /**
     * Informs observers that the value has changed.
     */
    private void notifyObservers() {
        for (CalcValueListener observer : observers) {
            observer.valueChanged(this);
        }
    }

    /**
     * Auxiliary method for removing  unnecessary zeros within a string.
     * <p><code>
     * Some examples : 0000 = 0 , 01.0  = 1 ...
     * </code></p>
     */
    private void removeMultipleZeros() {
        char[] data = currentValue.toCharArray();
        int i = 0;
        int startIndex = 0;
        while (i < data.length && data[i] == '0') {
            startIndex++;
            i++;

        }
        if (startIndex >= data.length) {
            currentValue = "0";
            return;
        }
        if (data[startIndex] == '.') {
            currentValue = new String(data, startIndex - 1, data.length - startIndex + 1);
            return;
        }
        currentValue = new String(data, startIndex, data.length - startIndex);

    }

    /**
     * Checks that the number is not infinite or NaN.
     *
     * @param value value for verification.
     * @return true if the given value is finite.
     */
    private boolean checkNumber(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value);
    }

    /**
     * Clears the current value and does not notify the observers.
     */
    public void clearWithoutNotify() {
        currentValue = null;
    }
}
