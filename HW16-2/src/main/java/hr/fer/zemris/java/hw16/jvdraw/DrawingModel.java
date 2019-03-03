package hr.fer.zemris.java.hw16.jvdraw;

/**
 * This interface represents a drawing model.
 */
public interface DrawingModel {

    /**
     * The number of {@link GeometricalObject} objects.
     *
     * @return the number of objects in the drawing model.
     */
    int getSize();

    /**
     * The {@link GeometricalObject} at the given index.
     *
     * @param index the index.
     * @return GeometricalObject at the given index.
     */
    GeometricalObject getObject(int index);

    /**
     * Adds a new {@link GeometricalObject} to the drawing model.
     *
     * @param object a new {@link GeometricalObject}.
     */
    void add(GeometricalObject object);

    /**
     * Adds a new drawing model listener.
     *
     * @param l a new drawing model listener.
     */
    void addDrawingModelListener(DrawingModelListener l);

    /**
     * Removes the given {@link DrawingModelListener}.
     *
     * @param l the drawing model listener.
     */
    void removeDrawingModelListener(DrawingModelListener l);

    /**
     * Removes the object from the drawing model.
     *
     * @param object the {@link GeometricalObject}.
     */
    void remove(GeometricalObject object);

    /**
     * Changes the order of elements in the drawing model
     *
     * @param object the {@link GeometricalObject}.
     * @param offset the offset.
     */
    void changeOrder(GeometricalObject object, int offset);
}
