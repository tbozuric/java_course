package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents an primes collection. A next prime will be calculated when needed.
 */
public class PrimesCollection implements Iterable<Integer> {
    /**
     * Represents total number of primes in this collection.
     */
    private int totalNumberOfPrimes;

    /**
     * Creates an instance of primes collection.
     *
     * @param numberOfConsecutivePrimes a number of consecutive primes that must be in this collection.
     * @throws IllegalArgumentException if the number of consecutive primes is less than 1.
     */
    public PrimesCollection(int numberOfConsecutivePrimes) {
        if (numberOfConsecutivePrimes < 1) {
            throw new IllegalArgumentException("Total number of consecutive primes must be greater or equal to 1.");
        }
        this.totalNumberOfPrimes = numberOfConsecutivePrimes;
    }

    /**
     * Returns an iterator over collection.
     *
     * @return an iterator over collection
     */
    @Override
    public Iterator<Integer> iterator() {
        return new IteratorImplementation();
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


    /**
     * Represents iterators that can be used for iteration over primes,
     */
    private class IteratorImplementation implements Iterator<Integer> {
        /**
         * The number of printed prime numbers.
         */
        private int counter;
        /**
         * Current prime number.
         */
        private int currentPrime = 1;

        /**
         * Returns true if the iteration has more elements.
         *
         * @return the if the iteration has more elements.
         */
        @Override
        public boolean hasNext() {
            return counter < totalNumberOfPrimes;
        }

        /**
         * Returns the next prime number.
         *
         * @return next prime number.
         * @throws NoSuchElementException if the iteration has no more elements.
         */
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The iteration has no more elements.");
            }
            currentPrime = getNextPrime(currentPrime);
            counter++;
            return currentPrime;
        }
    }
}
