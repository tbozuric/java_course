package hr.fer.zemris.java.hw06.observer2;


/**
 * Instances of DoubleValue class write to the standard output double value (i.e. “value * 2”) of the current value
 * which is stored in subject, but only first n times since its registration with the subject.
 * After writing the double value for the n-thtime, the observer automatically de-registers itself from the subject.
 */
public class DoubleValue implements IntegerStorageObserver {
    /**
     * Maximum number of prints.
     */
    private int n;
    /**
     * Number of changes.
     */
    private int counter;

    /**
     * Creates an instance of concrete observer.
     *
     * @param n maximum number of prints.
     */
    public DoubleValue(int n) {
        this.n = n;
    }

    /**
     * Prints twice the value of the current stored in the integer storage.
     *
     * @param istorageChange an object that monitors value changes.
     */
    @Override
    public void valueChanged(IntegerStorageChange istorageChange) {
        if (counter < n) {
            System.out.println("Double value : " + istorageChange.getCurrentStoredValue() * 2);
            counter++;
            return;
        } else {
            istorageChange.getIntegerStorage().removeObserver(this);
        }
    }
}
