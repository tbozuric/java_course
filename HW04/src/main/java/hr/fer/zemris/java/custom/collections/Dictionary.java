package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Represents a dictionary. Dictionary is used for storing objects that map keys to values. It
 * contains a private "Entry" class which is used for storing elements to this
 * dictionary. It cannot contain duplicate nor null keys but it can store both
 * duplicate and null values. Dictionary class provides user with standard
 * methods for managing maps such as size, clear, get and put. Capacity of the
 * array is automatically doubled whenever the array grows full.
 */
public class Dictionary {

    /**
     * Represents one record in the dictionary.
     */
    private class DictionaryEntry {
        /**
         * Key of dictionary entry.
         */
        private Object key;
        /**
         * Value that belongs to the key.
         */
        private Object value;

        /**
         * Creates an instance with key set to given key and value set to given value.
         *
         * @param key   of dictionary entry.
         * @param value that belongs to the key.
         */
        public DictionaryEntry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Entries are equal if their key values are the same
         *
         * @param o object that we check for equality
         * @return whether the keys are equal.
         */
        @Override
        public boolean equals(Object o) {
            return Objects.equals(key, o);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }

    /**
     * An array of {@link DictionaryEntry} references.
     */
    private ArrayIndexedCollection elements;

    /**
     * Creates an instance of "dictionary".
     */
    public Dictionary() {
        elements = new ArrayIndexedCollection();
    }

    /**
     * Checks if the dictionary is empty.
     *
     * @return whether the dictionary is empty.
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns the number of pairs(key:value) in the dictionary..
     *
     * @return number of elements in the dictionary.
     */
    public int size() {
        return elements.size();
    }

    /**
     * Deletes all elements in the dictionary.
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Adds a new entry to the dictionary if the key is different than null. The key must not be null.
     *
     * @param key   of dictionary entry.
     * @param value that belongs to the key.
     * @throws IllegalArgumentException if the key is null.
     */
    public void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }
        int index = elements.indexOf(key);
        DictionaryEntry entry = new DictionaryEntry(key, value);
        if (index == -1) {
            elements.add(entry);
            return;
        }
        elements.remove(index);
        elements.insert(entry, index);
    }

    /**
     * Returns the value stored "under" the key or null if such a key does not exist in the dictionary.
     *
     * @param key of dictionary entry.
     * @return value stored "under" this key or null.
     */
    public Object get(Object key) {
        int index = elements.indexOf(key);
        if (index == -1) {
            return null;
        }
        return ((DictionaryEntry) elements.get(elements.indexOf(key))).value;
    }
}
