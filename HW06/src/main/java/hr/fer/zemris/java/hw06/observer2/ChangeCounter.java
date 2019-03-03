package hr.fer.zemris.java.hw06.observer2;

/**
 * Instances of ChangeCounter counts (and writes to the standard output) the number of times the
 * value stored has been changed since the registration.
 */
public class ChangeCounter implements IntegerStorageObserver {
    /**
     * Internal counter of changes.
     */
    private int counter;

    /**
     * Prints to the standard output when the value is changed.
     *
     * @param istorageChange an object that monitors value changes.
     */
    @Override
    public void valueChanged(IntegerStorageChange istorageChange) {
        counter++;
        System.out.println("Number of value changes since tracking : " + counter);
    }
}
