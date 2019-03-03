package hr.fer.zemris.java.hw06.demo2;

/**
 * A simple demo program that illustrates the work of our primes collection .
 */
public class PrimesDemo1 {
    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(10); // 10: how many of them
        for (Integer prime : primesCollection) {
            System.out.println("Got prime : " + prime);
        }

    }

}
