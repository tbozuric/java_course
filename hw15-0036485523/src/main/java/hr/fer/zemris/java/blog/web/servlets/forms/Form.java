package hr.fer.zemris.java.blog.web.servlets.forms;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> Model form corresponding to a web representation of a domain object.<p>
 * <p> For each property, the {@link #errors} folder allows validation
 * (method {@link #validate()}  ()}) to enter if there is an error in the data.
 * </p>
 */
public abstract class Form {
    /**
     * Error map. It is expected that the keys are names of properties and values
     * error texts.
     */
    protected Map<String, String> errors = new HashMap<>();

    /**
     * Returns the error associated with the given key.
     *
     * @param name the key.
     * @return the error associated with the given key.
     */
    public String getError(String name) {
        return errors.get(name);
    }

    /**
     * Checks if the map of errors is empty.
     *
     * @return true if the map of errors is empty.
     */
    public boolean isEmpty() {
        return errors.isEmpty();
    }

    /**
     * Checks if the map of errors contains the given key.
     *
     * @param key the key.
     * @return true if the map of errors contains the given key.
     */
    public boolean containsKey(String key) {
        return errors.containsKey(key);
    }

    /**
     * Processes the input, if <code>null</code> returns an empty string.
     *
     * @param text the input text.
     * @return
     */
    protected String process(String text) {
        return text == null ? "" : text.trim();
    }

    /**
     * Returns the map of errors.
     *
     * @return the map of errors.
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * Populates the form parameters from the given {@link HttpServletRequest}.
     *
     * @param req the {@link HttpServletRequest}.
     */
    public abstract void populateFromRequest(HttpServletRequest req);

    /**
     * Validates the input parameters.
     */
    public abstract void validate();

}
