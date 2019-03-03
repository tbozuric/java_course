package hr.fer.zemris.java.hw06.observer1;

/**
 * Represents an observer.
 */
public interface IntegerStorageObserver {
    /**
     * Every time the value is changed in integer storage, the valueChanged method is invoked.
     *
     * @param istorage reference to integer storage.
     */
    void valueChanged(IntegerStorage istorage);
}