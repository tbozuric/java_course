package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;

import javax.swing.*;

/**
 * Auxiliary class that is wrapper around {@link JList}.
 */
public class JListGeometricalObject extends JList<GeometricalObject> {

    /**
     *The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an instance of {@link JListGeometricalObject}.
     *
     * @param model the drawing model
     */
    public JListGeometricalObject(DrawingModel model) {
        this(new DrawingObjectListModel(model));
    }

    /**
     * Creates an instance of {@link JListGeometricalObject}.
     *
     * @param dataModel the data model.
     */
    private JListGeometricalObject(ListModel<GeometricalObject> dataModel) {
        super(dataModel);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
