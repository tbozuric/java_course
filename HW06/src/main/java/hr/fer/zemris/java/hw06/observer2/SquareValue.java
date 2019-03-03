package hr.fer.zemris.java.hw06.observer2;

/**
 * Instances of SquareValue class write a square of the integer stored in the IntegerStorage
 * to the standard output (but the stored integer itself is not modified!)
 */
public class SquareValue implements IntegerStorageObserver {
    /**
     * Prints the square of the currently stored value in the integer storage.
     *
     * @param istorageChange an object that monitors value changes.
     */
    @Override
    public void valueChanged(IntegerStorageChange istorageChange) {
        System.out.println("Provided new value : " + istorageChange.getCurrentStoredValue() + ", square is "
                + istorageChange.getCurrentStoredValue() * istorageChange.getCurrentStoredValue());
    }
}
