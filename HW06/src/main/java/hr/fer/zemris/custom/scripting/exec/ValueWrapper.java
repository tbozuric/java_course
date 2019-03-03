package hr.fer.zemris.custom.scripting.exec;


import java.util.function.Function;

/**
 * Class represents a wraper around any object type. Although instances of
 * ValueWrapper do allow us to work with objects of any types but   add, subtract, multiply, divide  method can only be
 * invoked against Integer, Double and String classes.
 * <p>
 * Here are the rules that apply when calling the above methods:
 * <p>
 * 1.) Allowed values for current content of ValueWrapper object and for argument are null and instances of Integer , Double and String classes
 * 2.) If any of current value or argument is null ,we treat that value as being equal to Integer with value 0.
 * 3.) If either current value or argument is Double , operation is performed on Double s, and result
 * is stored as an instance of Double . If not, both arguments must be Integer's so operation is
 * performed on Integer s and result stored as an Integer .
 * 4.) If current value and argument are not null , they can be instances of Integer , Double or String . For each
 * value that is String ,we try to convert it to a decimal number. If it is a decimal value, we treat it as such;
 * otherwise,  we treat it as an Integer .
 */
public class ValueWrapper {
    /**
     * Threshold for checking whether the given number is zero.
     */
    private static final double ZERO_THRESHOLD = 1E-6;
    /**
     * Currently stored value.
     */
    private Object value;

    /**
     * Creates an instance of value wrapper.
     *
     * @param value object of any type, or null.
     */
    public ValueWrapper(Object value) {
        this.value = value;
    }

    /**
     * Returns current value.
     *
     * @return current value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets a new value.
     *
     * @param value a new value.
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Adds the currently stored value to the given value using the specified rules.
     *
     * @param incValue increment value.
     * @throws IllegalArgumentException if the given value  are not null , Integer , Double or String.
     */
    public void add(Object incValue) {
        incValue = transformValueAndArgument(incValue);
        incValue = tryToGetCorrectTypeOfArgument(incValue);
        if (incValue instanceof Double) {
            performOperation(incValue, operand -> (double) operand + (double) value);
            return;
        }
        performOperation(incValue, operand -> (int) operand + (int) value);
    }


    /**
     * Subtracts the currently stored value to the given value using the specified rules.
     *
     * @param decValue decrement value.
     * @throws IllegalArgumentException if the given value  are not null , Integer , Double or String.
     */
    public void subtract(Object decValue) {
        decValue = transformValueAndArgument(decValue);
        decValue = tryToGetCorrectTypeOfArgument(decValue);
        if (decValue instanceof Double) {
            performOperation(decValue, operand -> (double) value - (double) operand);
            return;
        }
        performOperation(decValue, operand -> (int) value - (int) operand);
    }

    /**
     * Multiplies the currently stored value to the given value using the specified rules.
     *
     * @param mulValue multiply value.
     * @throws IllegalArgumentException if the given value  are not null , Integer , Double or String.
     */
    public void multiply(Object mulValue) {
        mulValue = transformValueAndArgument(mulValue);
        mulValue = tryToGetCorrectTypeOfArgument(mulValue);
        if (mulValue instanceof Double) {
            performOperation(mulValue, operand -> (double) operand * (double) value);
            return;
        }
        performOperation(mulValue, operand -> (int) operand * (int) value);
    }

    /**
     * Divides the currently stored value to the given value using the specified rules.
     *
     * @param divValue divide value.
     * @throws IllegalArgumentException if the given value  are not null , Integer , Double or String.
     * @throws IllegalArgumentException if denominator is equal to 0.
     */
    public void divide(Object divValue) {
        divValue = transformValueAndArgument(divValue);
        divValue = tryToGetCorrectTypeOfArgument(divValue);
        if (divValue instanceof Double) {
            if (isZero((Double) divValue)) {
                throw new ArithmeticException("Denominator must not be 0.");
            }
            performOperation(divValue, operand -> (double) value / (double) operand);
            return;
        }
        if (isZero((Integer) divValue)) {
            throw new ArithmeticException("Denominator must not be 0.");
        }
        performOperation(divValue, operand -> (int) value / (int) operand);
    }

    /**
     * Performs numerical comparison between currently stored value in ValueWrapper and given argument.
     * Here are the rules:
     * 1)If both values are null, we treat them as equal.
     * 2)If one is null and the other is not we treat the null as an integer with value 0.
     * 3)Otherwise, we promote both values to same type.
     *
     * @param withValue value for comparision.
     * @return an integer less than zero if currently stored value is smaller than argument, an integer greater
     * than zero if currently stored value is larger than argument or an integer 0 if they are equal.
     */
    public int numCompare(Object withValue) {
        if (value == null && withValue == null) {
            return 0;
        }

        Object copyOfValue = value;
        withValue = transformValueAndArgument(withValue);
        withValue = tryToGetCorrectTypeOfArgument(withValue);

        int compare;
        if (withValue instanceof Integer) {
            compare = Integer.compare((int) value, (int) withValue);
            value = copyOfValue;
            return compare;
        }
        compare = Double.compare((double) value, (double) withValue);
        value = copyOfValue;
        return compare;
    }

    /**
     * Performs a given operation over the given argument.
     *
     * @param argument of the function.
     * @param function desired operation.
     * @param <T>      type of operators.
     */
    private <T> void performOperation(T argument, Function<T, T> function) {
        value = function.apply(argument);
    }

    /**
     * Tries to transfer the submitted object to one of the allowed types to meet the specified rules.
     *
     * @param argument we want to "transform" into some of the permissible types(Integer, Double or String)
     * @return transformed argument.
     * @throws IllegalArgumentException if the given value  are not null , Integer , Double or String.
     */
    private Object tryToGetCorrectTypeOfArgument(Object argument) {
        if (!isValidInstanceType(argument)) {
            throw new IllegalArgumentException("Allowed values for argument are null and instances of" +
                    " Integer, Double and String class!");
        }

        if (argument instanceof String) {
            argument = transformStringToNumber(argument);
        }

        if (value instanceof String) {
            value = transformStringToNumber(value);
        }

        if (argument instanceof Double || value instanceof Double) {
            if (argument instanceof Integer) {
                return ((Integer) argument).doubleValue();
            }
            if (value instanceof Integer) {
                value = ((Integer) value).doubleValue();
            }
            return argument;
        }
        if (!(argument instanceof Integer) || !(value instanceof Integer)) {
            throw new IllegalArgumentException("Illegal type of instances");
        }
        return argument;
    }

    /**
     * Tries to convert the string into a decimal number or integer.
     *
     * @param argument string we want to convert to a number.
     * @return the numeric value of the input argument.
     * @throws IllegalArgumentException if it is not possible to transform the  given string into the number.
     */
    private Object transformStringToNumber(Object argument) {
        Object result = tryTransformStringToInteger(argument);
        if (result == null) {
            argument = tryTransformToDouble(argument);
        } else {
            argument = result;
        }
        return argument;
    }

    /**
     * Method checks whether the given object is a null reference  or instance of Integer, Double or String.
     *
     * @param value we want to check.
     * @return whether the given  object is a null reference or instance of Integer, Double or String.
     */
    private boolean isValidInstanceType(Object value) {
        return value instanceof Integer || value instanceof Double || value instanceof String || value == null;
    }

    /**
     * Transforms a null reference into integer. Integer value will be 0.
     *
     * @return integer with value 0.
     */
    private Object transformNullToInteger() {
        return 0;
    }

    /**
     * Tries to convert the string into a integer.
     *
     * @param argument string we want to convert to a number.
     * @return the numeric value of the input argument or null.
     * @throws IllegalArgumentException if it is not possible to transform the  given string into the number.
     */
    private Object tryTransformStringToInteger(Object argument) {
        try {
            return Integer.parseInt(argument.toString());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Tries to convert the string into a decimal number.
     *
     * @param argument string we want to convert to a double.
     * @return the numeric value of the input argument.
     * @throws IllegalArgumentException if it is not possible to transform the  given string into the number.
     */
    private Object tryTransformToDouble(Object argument) {
        try {
            return Double.parseDouble(argument.toString());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("It is not possible  to transform " + argument.toString() + " into the number!");
        }
    }

    /**
     * Transforms the given object if any of current value or argument is null into Integer with value 0.
     *
     * @param value argument which we want to check (and transform)
     * @return transformed argument if it was a null reference.
     */
    private Object transformValueAndArgument(Object value) {
        if (value == null) {
            value = transformNullToInteger();
        }
        if (this.value == null) {
            this.value = transformNullToInteger();
        }
        return value;
    }

    /**
     * Checks if the given number is zero.
     *
     * @param number we want to check.
     * @return whether the number is equal to zero
     */
    private boolean isZero(double number) {
        return number >= -ZERO_THRESHOLD && number <= ZERO_THRESHOLD;
    }


}
