package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The demonstration of the {@link ListModel} that generates prime numbers sequentially
 * and puts them into all registered observers (lists).
 */
public class PrimDemo extends JFrame {

    /**
     * A unique serial version identifier.
     */
    private static final long serialVersionUID = -1305147476023758530L;

    /**
     * Creates an instance of {@link PrimDemo}.
     */
    public PrimDemo() {
        setLocation(20, 50);
        setSize(800, 400);
        setTitle("Prim demo!");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    /**
     * Represents list model for generating prime numbers.
     */
    public static class PrimListModel implements ListModel<Integer> {
        /**
         * List of prime numbers.
         */
        private List<Integer> values = new ArrayList<>();

        /**
         * List of observers.
         */
        private List<ListDataListener> observers = new ArrayList<>();

        /**
         * Current prime number.
         */
        private int currentPrim = 1;



        public PrimListModel(){
            values.add(1);
        }

        @Override
        public int getSize() {
            return values.size();
        }

        @Override
        public Integer getElementAt(int index) {
            return values.get(index);
        }

        @Override
        public void addListDataListener(ListDataListener l) {
            if (!observers.contains(l)) {
                observers.add(l);
            }
        }

        @Override
        public void removeListDataListener(ListDataListener l) {
            if (observers.contains(l)) {
                observers.remove(l);
            }
        }


        /**
         * Adds the next prime number to the list of all generated primes and informs observers.
         */
        public void next() {
            currentPrim = getNextPrime(currentPrim);
            values.add(currentPrim);
            int position = values.size();
            ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
            for (ListDataListener l : observers) {
                l.intervalAdded(event);
            }
        }

        /**
         * Checks if the given number is prime.
         *
         * @param number to be checked.
         * @return true if the number is prime.
         */
        private boolean isPrime(int number) {
            if (number < 2) return false;
            if (number == 2) return true;
            if (number % 2 == 0) return false;
            for (int i = 3; i * i <= number; i += 2)
                if (number % i == 0) return false;
            return true;
        }

        /**
         * Returns the next prime number that comes after the given number.
         *
         * @param n the limit for calculating the next ordinary number.
         * @return next prime number.
         */
        private int getNextPrime(int n) {
            for (int i = n + 1; i <= 2 * n; i++) {
                if (isPrime(i)) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * Initiates a graphical user interface with two scrollable {@link JList} and the push button
     * for generating prime numbers.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        PrimListModel model = new PrimListModel();

        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);


        JButton next = new JButton("Next");
        next.addActionListener(l -> model.next());


        JPanel central = new JPanel(new GridLayout(1, 0));
        central.add(new JScrollPane(list1));
        central.add(new JScrollPane(list2));

        cp.add(central, BorderLayout.CENTER);
        cp.add(next, BorderLayout.PAGE_END);

    }

    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments. Arguments are not used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PrimDemo();
            frame.setVisible(true);
        });
    }
}
