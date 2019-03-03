package hr.fer.zemris.java.hw16.jvdraw.models;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;

import javax.swing.*;

/**
 * Represents the drawing object list model.
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

    /**
     *The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The drawing model.
     */
    private DrawingModel model;

    /**
     * Creates an instance of {@link DrawingObjectListModel}.
     *
     * @param model the drawing model
     */
    public DrawingObjectListModel(DrawingModel model) {
        this.model = model;
        this.model.addDrawingModelListener(this);
    }

    @Override
    public int getSize() {
        return model.getSize();
    }

    @Override
    public GeometricalObject getElementAt(int index) {
        return model.getObject(index);
    }


    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fireIntervalAdded(source, index0, index1);
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        fireIntervalRemoved(source, index0, index1);
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fireContentsChanged(source, index0, index1);
    }
}
