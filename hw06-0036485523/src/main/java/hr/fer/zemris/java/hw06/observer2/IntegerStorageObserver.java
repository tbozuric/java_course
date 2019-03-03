package hr.fer.zemris.java.hw06.observer2;

/**
 * Represents an observer.
 */
public interface IntegerStorageObserver {
    /**
     * Every time the value is changed in integer storage, the valueChanged method is invoked.
     *
     * @param integerStorageChange an object that monitors value changes.
     */
    void valueChanged(IntegerStorageChange integerStorageChange);
}