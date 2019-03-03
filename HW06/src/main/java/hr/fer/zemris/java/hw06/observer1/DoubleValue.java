package hr.fer.zemris.java.hw06.observer1;

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
     * @param istorage reference to integer storage.
     */
    @Override
    public void valueChanged(IntegerStorage istorage) {
        if (counter < n) {
            System.out.println("Double value : " + istorage.getValue() * 2);
            counter++;
        } else {
            istorage.removeObserver(this);
        }
    }
}
