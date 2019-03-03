package hr.fer.zemris.java.hw05.collections.demo;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

import java.util.Iterator;

/**
 * A simple demo program that illustrates the work of our simple hash table.
 * In this demonstration program we illustrates work of our custom implementation of iterator.
 */
public class Demo2 {
    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments
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

        for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        }

        for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
            for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
                System.out.printf(
                        "(%s => %d) - (%s => %d)%n",
                        pair1.getKey(), pair1.getValue(),
                        pair2.getKey(), pair2.getValue()
                );
            }
        }


        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            if (pair.getKey().equals("Ivana")) {
                iter.remove(); // sam iterator kontrolirano uklanja trenutni element
            }
        }

        for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        }

    }
}
