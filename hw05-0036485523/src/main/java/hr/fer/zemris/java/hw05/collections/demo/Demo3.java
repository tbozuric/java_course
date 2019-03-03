package hr.fer.zemris.java.hw05.collections.demo;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

import java.util.Iterator;

/**
 * A simple demo program that illustrates the work of our simple hash table.
 * In this demonstration program we illustrates work of our custom implementation of iterator, especially remove method.
 */
public class Demo3 {
    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        // create collection:
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
            iter.remove();
        }
        System.out.printf("Veličina: %d%n", examMarks.size());

    }
}
