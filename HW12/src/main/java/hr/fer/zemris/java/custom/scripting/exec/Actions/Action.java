package hr.fer.zemris.java.custom.scripting.exec.Actions;

import java.util.Stack;

/**
 * The interface that represents an executable action.
 */
public interface Action {

    /**
     * Executes the action.
     *
     * @param stack the stack.
     */
    void execute(Stack<Object> stack);
}
