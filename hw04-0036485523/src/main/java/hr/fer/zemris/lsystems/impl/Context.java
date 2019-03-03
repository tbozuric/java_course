package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Represent the current context.
 * Instances of this class enable the execution of the fractal display procedure.
 */
public class Context {
    /**
     * The stack on which we put and pop the turtle state.
     */
    private ObjectStack stack;

    /**
     * Creates an instance of context.
     */
    public Context() {
        this.stack = new ObjectStack();
    }

    /**
     * Returns the  current turtle state.
     *
     * @return current turtle state.
     */
    public TurtleState getCurrentState() {
        return (TurtleState) stack.peek();
    }

    /**
     * Pushes a new turtle state on the stack.
     *
     * @param state turtle state.
     */
    public void pushState(TurtleState state) {
        stack.push(state);
    }

    /**
     * Removes last turtle state pushed on stack from the stack.
     */
    public void popState() {
        stack.pop();
    }
}
