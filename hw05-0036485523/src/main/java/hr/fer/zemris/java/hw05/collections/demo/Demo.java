package hr.fer.zemris.java.hw05.collections.demo;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * A simple demo program that illustrates the work of our simple hash table.
 * In this demonstration program, we add some elements to the table, print them, and remove some elements.
 */
public class Demo {
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
        // query collection:
        Integer kristinaGrade = examMarks.get("Kristina");
        System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
        // What is collection's size? Must be four!
        System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4

        System.out.println(examMarks);

        examMarks.remove("Ivana");
        examMarks.remove("Kristina");
        examMarks.put("Ivana", null);


        System.out.println(examMarks);
        System.out.println(examMarks.size());
        System.out.println(examMarks.containsValue(5));
    }
}
