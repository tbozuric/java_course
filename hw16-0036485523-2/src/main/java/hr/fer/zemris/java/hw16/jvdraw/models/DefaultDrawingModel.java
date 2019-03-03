package hr.fer.zemris.java.hw16.jvdraw.models;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.GeometricalObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the default drawing model that knows all about current {@link GeometricalObject} objects.
 */
public class DefaultDrawingModel implements DrawingModel {

    /**
     * The list of current {@link GeometricalObject}s
     */
    private List<GeometricalObject> objects;

    /**
     * The list of drawing model listeners.
     */
    private List<DrawingModelListener> listeners;


    /**
     * Creates an instance od {@link DefaultDrawingModel}.
     */
    public DefaultDrawingModel() {
        objects = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return objects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {
        if (index >= objects.size()) {
            throw new RuntimeException("Invalid index.");
        }
        return objects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        objects.add(object);
        object.addGeometricalObjectListener(this::notifyObjectChanged);
        notifyObjectAdded(object);
    }


    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    @Override
    public void remove(GeometricalObject object) {
        object.removeGeometricalObjectListener(this::notifyObjectChanged);
        notifyObjectRemoved(object);
        objects.remove(object);

    }

    @Override
    public void changeOrder(GeometricalObject object, int offset) {
        int indexOfObject = objects.indexOf(object);
        if (offset == -1) {
            if (indexOfObject > 0) {
                Collections.swap(objects, indexOfObject - 1, indexOfObject);
                notifyOrderChanged(indexOfObject - 1, indexOfObject);
            }
        } else {
            if (indexOfObject < objects.size() - 1) {
                if (offset == 1) {
                    Collections.swap(objects, indexOfObject, indexOfObject + 1);
                    notifyOrderChanged(indexOfObject, indexOfObject + 1);
                }
            }
        }
    }


    /**
     * Informs all listeners that a new {@link GeometricalObject} has been added.
     *
     * @param object new {@link GeometricalObject}.
     */
    private void notifyObjectAdded(GeometricalObject object) {
        for (DrawingModelListener listener : listeners) {
            listener.objectsAdded(this, objects.indexOf(object), objects.indexOf(object));
        }
    }

    /**
     * Informs all listeners that {@link GeometricalObject} has been changed.
     *
     * @param o the {@link GeometricalObject}.
     */
    private void notifyObjectChanged(GeometricalObject o) {
        for (DrawingModelListener listener : listeners) {
            listener.objectsChanged(this, objects.indexOf(o), objects.indexOf(o));
        }
    }

    /**
     * Informs all listeners that {@link GeometricalObject} has been removed.
     *
     * @param object the {@link GeometricalObject}.
     */
    private void notifyObjectRemoved(GeometricalObject object) {
        for (DrawingModelListener listener : listeners) {
            listener.objectsRemoved(this, objects.indexOf(object), objects.indexOf(object));
        }
    }

    /**
     * Informs all listeners that order  has been changed.
     *
     * @param index0 the start index.
     * @param index1 the end index.
     */
    private void notifyOrderChanged(int index0, int index1) {
        for (DrawingModelListener listener : listeners) {
            listener.objectsChanged(this, index0, index1);
        }
    }

}