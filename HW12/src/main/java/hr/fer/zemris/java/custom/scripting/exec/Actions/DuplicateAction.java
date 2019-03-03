package hr.fer.zemris.java.custom.scripting.exec.Actions;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import java.util.Stack;

/**
 * Duplicates current top value from stack.
 */
public class DuplicateAction implements Action {

    @Override
    public void execute(Stack<Object> temporary) {
        Object dup = temporary.pop();
        temporary.push(new ValueWrapper(dup).getValue());
        temporary.push(new ValueWrapper(dup).getValue());
    }
}
