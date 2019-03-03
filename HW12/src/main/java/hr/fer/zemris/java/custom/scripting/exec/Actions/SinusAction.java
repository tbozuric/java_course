package hr.fer.zemris.java.custom.scripting.exec.Actions;

import java.util.Stack;

/**
 * Calculates sinus from given argument and stores the result back to stack.
 */
public class SinusAction implements Action {


    @Override
    public void execute(Stack<Object> temporary) {
        double result;
        Object value = temporary.pop();
        if (value instanceof Double) {
            result = Math.sin(Math.toRadians((Double) value));
        } else {
            int val = (Integer) value;
            result = Math.sin(Math.toRadians(val));
        }
        temporary.push(result);
    }
}
