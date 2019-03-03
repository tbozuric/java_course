package hr.fer.zemris.java.hw06.observer2;

/**
 * Represents one change in the integer storage.
 */
public class IntegerStorageChange {
    /**
     * Reference to integer storage.
     */
    private IntegerStorage integerStorage;
    /**
     * Previous stored value.
     */
    private int previousStoredValue;
    /**
     * Current stored value.
     */
    private int currentStoredValue;

    /**
     * Creates an instance of integer storage change.
     *
     * @param integerStorage      reference to integer storage.
     * @param previousStoredValue previous stored value.
     */
    public IntegerStorageChange(IntegerStorage integerStorage, int previousStoredValue) {
        this.integerStorage = integerStorage;
        this.previousStoredValue = previousStoredValue;
        this.currentStoredValue = integerStorage.getValue();
    }

    /**
     * Returns reference to integer storage.
     *
     * @return reference to integer storage.
     */
    public IntegerStorage getIntegerStorage() {
        return integerStorage;
    }

    /**
     * Returns previous stored value.
     *
     * @return previous stored value.
     */
    public int getPreviousStoredValue() {
        return previousStoredValue;
    }

    /**
     * Returns current stored value.
     *
     * @return current stored value.
     */
    public int getCurrentStoredValue() {
        return currentStoredValue;
    }
}


