package hr.fer.zemris.java.custom.scripting.exec.Actions;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * Formats decimal number using given format f which is compatible with {@link DecimalFormat}.
 */
public class DecimalFormatAction implements Action {

    @Override
    public void execute(Stack<Object> temporary) {
        String pattern = String.valueOf(temporary.pop());
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        Object number = temporary.pop();
        String result = decimalFormat.format(number);
        temporary.push(result);
    }
}
