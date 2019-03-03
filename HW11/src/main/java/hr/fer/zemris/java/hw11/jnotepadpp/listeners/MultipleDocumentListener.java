package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Represents a listener on multiple document models.
 */
public interface MultipleDocumentListener {
    /**
     * Informs the observers  that the current document model has been changed.
     *
     * @param previousModel the previous {@link SingleDocumentModel}
     * @param currentModel  the current {@link SingleDocumentModel}.
     */
    void currentDocumentChanged(SingleDocumentModel previousModel,
                                SingleDocumentModel currentModel);

    /**
     * Informs the observers  that a new document has been added.
     *
     * @param model the {@link SingleDocumentModel}
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Informs the observers that the document has been removed.
     *
     * @param model the {@link SingleDocumentModel}.
     */
    void documentRemoved(SingleDocumentModel model);
}
