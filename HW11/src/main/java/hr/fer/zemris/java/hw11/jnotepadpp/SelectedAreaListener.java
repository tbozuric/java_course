package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Represents an interface for tracking changes to the selected text.
 */
public interface SelectedAreaListener {
    /**
     * Informs the listeners that the selected part of the text has changed.
     */
    void selectedAreaChanged();
}
