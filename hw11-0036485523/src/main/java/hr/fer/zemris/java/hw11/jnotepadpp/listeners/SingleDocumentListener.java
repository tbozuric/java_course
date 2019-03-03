package hr.fer.zemris.java.hw11.jnotepadpp.listeners;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * Represents a listener on one {@link SingleDocumentModel}.
 */
public interface SingleDocumentListener {

    /**
     * Informs the observers that the modification status  has been updated.
     *
     * @param model the current {@link SingleDocumentModel}.
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Informs the observers that the document file path  has been changed.
     *
     * @param model the current {@link SingleDocumentModel}.
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
