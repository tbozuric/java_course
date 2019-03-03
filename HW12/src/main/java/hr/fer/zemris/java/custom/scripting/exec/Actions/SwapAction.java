package hr.fer.zemris.java.custom.scripting.exec.Actions;

import java.util.Stack;

/**
 * Replaces the order of two top most items on stack.
 */
public class SwapAction implements Action {

    @Override
    public void execute(Stack<Object> temporary) {
        Object first = temporary.pop();
        Object second = temporary.pop();
        temporary.push(first);
        temporary.push(second);
    }
}
