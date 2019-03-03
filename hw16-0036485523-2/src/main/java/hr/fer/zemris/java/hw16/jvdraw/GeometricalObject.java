package hr.fer.zemris.java.hw16.jvdraw;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents an geometrical object( Circle, Filled circle, line, ...)
 */
public abstract class GeometricalObject {

    /**
     * The listeners.
     */
    private List<GeometricalObjectListener> listeners;

    /**
     * Creates new instance of {@link GeometricalObject}.
     */
    public GeometricalObject() {
        listeners = new ArrayList<>();
    }

    /**
     * Adds a new {@link GeometricalObjectListener}.
     *
     * @param l a new  geometrical object listener.
     */
    public void addGeometricalObjectListener(GeometricalObjectListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    /**
     * Removes the {@link GeometricalObjectListener}.
     *
     * @param l the geometrical object listener.
     */
    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    /**
     * Informs all listeners that {@link GeometricalObject} has been changed.
     */
    public void notifyListeners() {
        for (GeometricalObjectListener listener : listeners) {
            listener.geometricalObjectChanged(this);
        }
    }

    /**
     * Accepts the geometrical object visitor.
     *
     * @param v the visitor
     */
    public abstract void accept(GeometricalObjectVisitor v);

    /**
     * Creates a new {@link GeometricalObjectEditor}.
     *
     * @return a new {@link GeometricalObjectEditor}.
     */
    public abstract GeometricalObjectEditor createGeometricalObjectEditor();
}
