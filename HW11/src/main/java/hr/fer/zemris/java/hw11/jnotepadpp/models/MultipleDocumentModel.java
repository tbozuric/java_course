package hr.fer.zemris.java.hw11.jnotepadpp.models;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Represents a model capable of holding zero, one or more documents,
 * where each document and having a concept of current document – the one which is shown to the
 * user and on which user works.
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

    /**
     * Creates a new blank document.
     *
     * @return returns a new blank document represented as a {@link SingleDocumentModel}.
     */
    SingleDocumentModel createNewDocument();

    /**
     * Returns the current document.
     *
     * @return the current document represented as a {@link SingleDocumentModel} .
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Loads  a load specified file from disk.
     *
     * @param path path to the document.
     * @return a new {@link SingleDocumentModel}.
     * @throws IOException if an error occurs while reading.
     */
    SingleDocumentModel loadDocument(Path path) throws IOException;

    /**
     * Saves the given {@link SingleDocumentModel} to disk on the given location.
     *
     * @param model   the model we want to save.
     * @param newPath location.
     * @throws IOException if an error occurs while saving.
     */
    void saveDocument(SingleDocumentModel model, Path newPath) throws IOException;

    /**
     * Closes the given {@link SingleDocumentModel}.
     *
     * @param model the model we want to close.
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Adds a new {@link MultipleDocumentListener}.
     *
     * @param l the {@link MultipleDocumentListener}.
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Removes a {@link MultipleDocumentListener}.
     *
     * @param l the {@link MultipleDocumentListener} that we want to remove.
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Returns the number of existing documents.
     *
     * @return the number of existing documents.
     */
    int getNumberOfDocuments();

    /**
     * Returns the document at a certain position.
     *
     * @param index the position.
     * @return the {@link SingleDocumentModel} at the given position.
     */
    SingleDocumentModel getDocument(int index);

}
