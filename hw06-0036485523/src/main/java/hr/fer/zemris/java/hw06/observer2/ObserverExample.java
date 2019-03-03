package hr.fer.zemris.java.hw06.observer2;

/**
 * A simple demo program that illustrates the work of our integer storage .
 * In this demonstration program  we add some observers to register changes in integer storage.
 */
public class ObserverExample {
    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        IntegerStorage istorage = new IntegerStorage(20);
        IntegerStorageObserver observer = new SquareValue();
        IntegerStorageObserver doubleValueObserver = new DoubleValue(2);
        IntegerStorageObserver changeCounter = new ChangeCounter();
        istorage.addObserver(observer);
        istorage.addObserver(doubleValueObserver);
        istorage.addObserver(changeCounter);
        istorage.setValue(5);
        istorage.setValue(2);
        istorage.setValue(25);
        istorage.setValue(13);
        istorage.setValue(22);
        istorage.setValue(15);
    }
}
