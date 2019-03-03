package hr.fer.zemris.java.hw11.jnotepadpp.models;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Represents a model of single document, having information about file path
 * from which document was loaded (can be null for new document), document modification status
 * and reference to Swing component which is used for editing (each document has its own editor
 * component).
 */
public interface SingleDocumentModel {

    /**
     * Returns the text component of the model
     *
     * @return the text component of the model.
     * @see JTextArea
     */
    JTextArea getTextComponent();

    /**
     * Returns the path to the document represented by this {@link SingleDocumentModel}
     *
     * @return the path to the document represented by this {@link SingleDocumentModel}
     */
    Path getFilePath();

    /**
     * Sets the path to the document represented by this {@link SingleDocumentModel}
     *
     * @param path path to the document on disk.
     */
    void setFilePath(Path path);

    /**
     * Returns whether the document has been modified
     *
     * @return true if the document has been modified.
     */
    boolean isModified();

    /**
     * Sets document modification status.
     *
     * @param modified the modification status.
     */
    void setModified(boolean modified);

    /**
     * Adds a new {@link SingleDocumentListener}.
     *
     * @param l the {@link SingleDocumentListener}
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Removes a {@link SingleDocumentListener}
     *
     * @param l the {@link SingleDocumentListener} that we want to remove.
     */
    void removeSingleDocumentListener(SingleDocumentListener l);

}
