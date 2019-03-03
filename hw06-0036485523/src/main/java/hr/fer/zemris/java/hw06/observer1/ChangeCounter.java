package hr.fer.zemris.java.hw06.observer1;

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
     * @param istorage reference to integer storage.
     */
    @Override
    public void valueChanged(IntegerStorage istorage) {
        counter++;
        System.out.println("Number of value changes since tracking : " + counter);
    }
}
