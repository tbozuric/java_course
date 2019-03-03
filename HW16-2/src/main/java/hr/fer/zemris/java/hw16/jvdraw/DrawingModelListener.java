package hr.fer.zemris.java.hw16.jvdraw;

/**
 * This interface represents drawing model listener.
 */
public interface DrawingModelListener {
    /**
     * * <code>DrawingModel</code> subclasses must call this method
     * <b>after</b> one or more elements are added.
     *
     * @param source the <code>DrawingModel</code> that changed, typically "this"
     * @param index0 one end of the added interval,
     *               including <code>index0</code>
     * @param index1 the other end of the added interval,
     *               including <code>index1</code>
     */
    void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * * <code>DrawingModel</code> subclasses must call this method
     * <b>after</b> one or more elements are removed from the model.
     *
     * @param source the <code>DrawingModel</code> that changed, typically "this"
     * @param index0 one end of the removed interval,
     *               including <code>index0</code>
     * @param index1 the other end of the removed interval,
     *               including <code>index1</code>
     */
    void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * * <code>DrawingModel</code> subclasses must call this method
     * <b>after</b> one or more elements are changed.
     *
     * @param source the <code>DrawingModel</code> that changed, typically "this"
     * @param index0 one end of the changed interval,
     *               including <code>index0</code>
     * @param index1 the other end of the changed interval,
     *               including <code>index1</code>
     */
    void objectsChanged(DrawingModel source, int index0, int index1);
}
