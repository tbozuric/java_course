package hr.fer.zemris.java.hw06.demo2;

/**
 * A simple demo program that illustrates the work of our primes collection .
 */
public class PrimesDemo2 {
    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(3);
        for (Integer prime : primesCollection) {
            for (Integer prime2 : primesCollection) {
                System.out.println("Got prime pair: " + prime + ", " + prime2);
            }
        }
    }
}