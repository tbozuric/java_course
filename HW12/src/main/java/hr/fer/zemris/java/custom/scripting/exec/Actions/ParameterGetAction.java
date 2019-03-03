package hr.fer.zemris.java.custom.scripting.exec.Actions;

import java.util.Map;
import java.util.Stack;

/**
 * Obtains from requestContext (temporary/persistent) parameters map a value mapped for
 * name and pushes it onto stack.
 */
public class ParameterGetAction implements Action {


    @Override
    public void execute(Stack<Object> temporary) {
        Map<String, String> parameters = (Map<String, String>) temporary.pop();
        String defaultValue = String.valueOf(temporary.pop());
        String name = String.valueOf(temporary.pop());
        String value = parameters.get(name);
        temporary.push(value == null ? defaultValue : value);
    }
}
