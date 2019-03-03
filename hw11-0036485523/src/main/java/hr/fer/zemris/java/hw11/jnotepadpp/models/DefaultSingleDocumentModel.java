package hr.fer.zemris.java.hw11.jnotepadpp.models;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a default single document model. Each model has its own text component, path to the file that
 * it represents, modification status, and list of observers to the document.
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * Textual component of the document.
     */
    private JTextArea textArea;

    /**
     * Path to the document.
     */
    private Path filePath;

    /**
     * Document modification status.
     */
    private boolean modified;

    /**
     * List of {@link SingleDocumentListener} observers.
     */
    private List<SingleDocumentListener> observers;

    /**
     * Creates an instance of {@link DefaultSingleDocumentModel}.
     *
     * @param filePath the path to the document.
     * @param content  the content of the document.
     */
    public DefaultSingleDocumentModel(Path filePath, String content) {
        this.filePath = filePath;
        this.textArea = new JTextArea(content);
        observers = new ArrayList<>();
        addDocumentListener();
    }


    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(Path path) {
        if (filePath == null || !filePath.equals(path)) {
            filePath = path;
            notifyFilePathChanged();
        }
    }


    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        if(modified != this.modified) {
            this.modified = modified;
            notifyDocumentModified();
        }
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        if (!observers.contains(l)) {
            observers.add(l);
        }
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        if (observers.contains(l)) {
            observers.remove(l);
        }
    }

    /**
     * Informs the observers that the modification status has changed.
     */
    private void notifyDocumentModified() {
        for (SingleDocumentListener listener : observers) {
            listener.documentModifyStatusUpdated(this);
        }
    }

    /**
     * Informs the observers that the path to the file has changed.
     */
    private void notifyFilePathChanged() {
        for (SingleDocumentListener listener : observers) {
            listener.documentFilePathUpdated(this);
        }
    }

    /**
     * Adds a new listener to the textual component of the model.
     */
    private void addDocumentListener() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified(true);

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified(true);

            }
        });
    }

}
