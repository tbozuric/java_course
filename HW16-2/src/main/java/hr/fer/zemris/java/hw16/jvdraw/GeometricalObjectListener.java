package hr.fer.zemris.java.hw16.jvdraw;

/**
 *This interface represents {@link GeometricalObject} listener.
 */
public interface GeometricalObjectListener {
    /**
     * The method must be called when the {@link GeometricalObject} is changed.
     * @param o the {@link GeometricalObject}
     */
    void geometricalObjectChanged(GeometricalObject o);
}
