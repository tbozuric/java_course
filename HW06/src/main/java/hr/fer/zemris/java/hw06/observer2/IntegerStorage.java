package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the integer storage.
 */
public class IntegerStorage {
    /**
     * The current value that is stored in the storage.
     */
    private int value;
    /**
     * The list of observers that will be notified after each change of value.
     */
    private List<IntegerStorageObserver> observers;
    /**
     * Represents an iterator that iters through concrete observers.
     */
    private Iterator<IntegerStorageObserver> iterator;

    /**
     * Creates an instance of an integer storage.
     *
     * @param initialValue initial value.
     */
    public IntegerStorage(int initialValue) {
        this.value = initialValue;
        observers = new ArrayList<>();

    }


    /**
     * Adds a new observer to the list of all observers.
     *
     * @param observer new observer.
     * @throws NullPointerException if the submitted observer is a null.
     */
    public void addObserver(IntegerStorageObserver observer) {
        if(observer == null){
            throw new NullPointerException("Submitted observer is a null reference.");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes the observer from the list of all observers(if the observer exists in the list).
     *
     * @param observer some observer
     */
    public void removeObserver(IntegerStorageObserver observer) {
        if (iterator != null) {
            iterator.remove();
        } else if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }


    /**
     * Clears the list of observers.
     */
    public void clearObservers() {
        observers.clear();
    }

    /**
     * Returns the currently stored value.
     *
     * @return the currently stored value.
     */
    public int getValue() {
        return value;
    }


    /**
     * Sets a new value and notifies all observers that a change in value has occurred.
     *
     * @param value new value.
     */
    public void setValue(int value) {
        // Only if new value is different than the current value:
        if (this.value != value) {
            // Update current value
            int previousValue = this.value;
            this.value = value;
            IntegerStorageChange change = new IntegerStorageChange(this, previousValue);
            // Notify all registered observers
            if (observers != null) {
                iterator = observers.listIterator();
                while (iterator.hasNext()) {
                    iterator.next().valueChanged(change);
                }
                iterator = null;
            }
        }
    }
}