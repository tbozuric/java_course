package hr.fer.zemris.java.hw11.jnotepadpp.models;

import hr.fer.zemris.java.hw11.jnotepadpp.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.listeners.SingleDocumentListener;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Represents a default multiple document model. The model has references to all existing {@link SingleDocumentModel}s,
 * the current {@link SingleDocumentModel} , and list of {@link MultipleDocumentListener} observers.
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -6446117399669701366L;

    /**
     * The title of the document that does not exist on the HDD.
     */

    private static final String UNTITLED_DOCUMENT = "Untitled";

    /**
     * List of all {@link SingleDocumentModel}s.
     */
    private List<SingleDocumentModel> documentModels;

    /**
     * The current {@link SingleDocumentModel}.
     */
    private SingleDocumentModel currentDocument;

    /**
     * List of {@link MultipleDocumentListener} observers.
     */
    private List<MultipleDocumentListener> observers;


    /**
     * The icon that indicates that a document has not been modified.
     */
    private Icon unmodified;

    /**
     * The icon that indicates that a document has been modified.
     */
    private Icon modified;

    /**
     * Creates an instance of {@link DefaultMultipleDocumentModel}.
     */
    public DefaultMultipleDocumentModel() {
        this.documentModels = new ArrayList<>();
        this.observers = new ArrayList<>();

        addTabChangeListener();
        unmodified = Icons.getInstance().getIcon("unmodified");
        modified = Icons.getInstance().getIcon("modified");

    }


    @Override
    public SingleDocumentModel createNewDocument() {

        currentDocument = new DefaultSingleDocumentModel(null, "");
        documentModels.add(currentDocument);
        addTab(UNTITLED_DOCUMENT, unmodified,
                new JScrollPane(currentDocument.getTextComponent()), UNTITLED_DOCUMENT);
        setSelectedIndex(documentModels.size() - 1);

        addSingleDocumentListener();

        currentDocument.setModified(false);
        notifyDocumentAdded();
        return currentDocument;
    }


    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public SingleDocumentModel loadDocument(Path path) throws IOException {
        if (path == null) {
            throw new NullPointerException("Path must not be null!");
        }

        Optional<SingleDocumentModel> optional = documentModels.stream()
                .filter(s -> s.getFilePath() != null && s.getFilePath().equals(path)).findFirst();
        if (optional.isPresent()) {
            currentDocument = optional.get();
            setSelectedIndex(documentModels.indexOf(currentDocument));
            return currentDocument;
        }
        byte[] bytes;
        bytes = Files.readAllBytes(path);
        String content = new String(bytes, StandardCharsets.UTF_8);

        currentDocument = new DefaultSingleDocumentModel(path, content);

        documentModels.add(currentDocument);
        addTab(path.getFileName().toString(), unmodified,
                new JScrollPane(currentDocument.getTextComponent()), path.toAbsolutePath().toString());
        setSelectedIndex(indexOfTab(path.getFileName().toString()));

        notifyDocumentAdded();
        addSingleDocumentListener();
        return new DefaultSingleDocumentModel(path, content);
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) throws IOException {

        if (documentModels.stream().anyMatch(s -> !s.equals(model) && s.getFilePath() != null &&
                s.getFilePath().equals(newPath))) {
            throw new IllegalArgumentException(newPath.toAbsolutePath().toString());
        }

        Path path = model.getFilePath();
        boolean same = false;
        if (newPath != null) {
            if (path != null && path.equals(newPath)) {
                same = true;
            }
            path = newPath;
        }
        byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
        Files.write(path, data);
        if (!same) {
            model.setFilePath(path);
        }

        SingleDocumentModel previousDocument = new DefaultSingleDocumentModel(model.getFilePath(),
                model.getTextComponent().getText());
        previousDocument.setModified(model.isModified());

        model.setModified(false);
        notifyCurrentDocumentChanged(previousDocument, model);

    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        if (documentModels.contains(model)) {
            int index = documentModels.indexOf(model);
            if (index == documentModels.size() - 1) {
                if (closeTab(index)) {
                    return;
                }
                setSelectedIndex(documentModels.size() - 1);
                currentDocument = documentModels.get(documentModels.size() - 1);
                notifyDocumentRemoved();
                return;
            }
            if (closeTab(index)) {
                return;
            }
            setSelectedIndex(index);
            currentDocument = documentModels.get(index);
            notifyDocumentRemoved();
        }
    }

    /**
     * Removes the document from a set of documents and checks whether the number of remaining documents is equal to 0.
     *
     * @param index the index of document.
     * @return whether the number of documents is zero.
     */
    private boolean closeTab(int index) {
        remove(index);
        documentModels.remove(index);
        return numberOfDocumentsIsZero();
    }


    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        if (!observers.contains(l)) {
            observers.add(l);
        }
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        if (observers.contains(l)) {
            observers.remove(l);
        }
    }

    @Override
    public int getNumberOfDocuments() {
        return documentModels.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        if (index >= 0 && index < documentModels.size()) {
            return documentModels.get(index);
        }
        return null;
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documentModels.iterator();
    }


    /**
     * Adds a tab change listener.
     */
    private void addTabChangeListener() {

        this.addChangeListener(e -> {

            int index = getSelectedIndex();
            if (index < 0) {
                return;
            }
            SingleDocumentModel previousDocument = currentDocument;
            currentDocument = documentModels.get(index);

            if (previousDocument.equals(currentDocument)) {
                previousDocument = getDocument(index - 1);
            }

            for (MultipleDocumentListener listener : observers) {
                listener.currentDocumentChanged(previousDocument, currentDocument);
            }
        });
    }

    /**
     * Informs the observers that the document is added.
     */
    private void notifyDocumentAdded() {
        for (MultipleDocumentListener listener : observers) {
            listener.documentAdded(currentDocument);
        }
    }

    /**
     * Adds a single document listener to the current document.
     */
    private void addSingleDocumentListener() {
        currentDocument.addSingleDocumentListener(new SingleDocumentListener() {

            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                if (model.isModified()) {
                    setIconAt(getSelectedIndex(), modified);
                    SingleDocumentModel previous = new DefaultSingleDocumentModel(model.getFilePath(),
                            model.getTextComponent().getText());
                    previous.setModified(false);
                    notifyCurrentDocumentChanged(previous, model);
                    return;
                }
                setIconAt(getSelectedIndex(), unmodified);
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
                if (!getToolTipTextAt(getSelectedIndex()).equals(model.getFilePath().toString())) {
                    setToolTipTextAt(getSelectedIndex(), model.getFilePath().toString());
                    setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
                }
            }
        });

    }

    /**
     * Auxiliary method that checks if the number of  documents  is zero.
     *
     * @return true if the number of documents is equal to zero.
     */
    private boolean numberOfDocumentsIsZero() {
        if (getNumberOfDocuments() == 0) {
            currentDocument = null;
            notifyDocumentRemoved();
            return true;
        }
        return false;
    }

    /**
     * Informs the observers that the current document changed.
     *
     * @param previousDocument the previous single document model.
     * @param currentDocument  the current single document model.
     */
    private void notifyCurrentDocumentChanged(SingleDocumentModel previousDocument, SingleDocumentModel currentDocument) {
        for (MultipleDocumentListener listener : observers) {
            listener.currentDocumentChanged(previousDocument, currentDocument);
        }
    }

    /**
     * Informs the observers that the document is removed.
     */
    private void notifyDocumentRemoved() {
        for (MultipleDocumentListener listener : observers) {
            listener.documentRemoved(currentDocument);
        }
    }

}
