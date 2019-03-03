package hr.fer.zemris.java.hw06.observer1;

/**
 * Instances of SquareValue class write a square of the integer stored in the IntegerStorage
 * to the standard output (but the stored integer itself is not modified!)
 */
public class SquareValue implements IntegerStorageObserver {
    /**
     * Prints the square of the currently stored value in the integer storage.
     *
     * @param istorage reference to integer storage.
     */
    @Override
    public void valueChanged(IntegerStorage istorage) {
        System.out.println("Provided new value : " + istorage.getValue() + ", square is " + istorage.getValue() * istorage.getValue());
    }
}
