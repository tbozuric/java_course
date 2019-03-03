package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * This class represents a simple hash table that allows you to store paired pairs(key,value). Parameter K is a key type, parameter V is a value type.
 * The number of paired couples(key,value) that are stored in this collection can be greater than number of slot of tables, because "overflows" are added to a single
 * linked list for a particular slot .
 * The implementation of this class does not allow the keys to be null reference. Values, on the other hand, can be null references.
 *
 * @param <K> the type of keys maintained by this hash table.
 * @param <V> the type of mapped values.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
    /**
     * Default capacity of collection.
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * The threshold after which we double the capacity.
     */
    private static final double INCREASE_CAPACITY_THRESHOLD = 0.75;
    /**
     * The total number of elements in the collection.
     */
    private int size;
    /**
     * Current hash table capacity. If the size of the collection reaches 75 % capacity, the capacity increases twice.
     */
    private int capacity;
    /**
     * Counts how many times we added a new element to the table, and how many times we deleted the element from the table.
     * If only value of an existing element is changed, the variable does not increase.
     */
    private int modificationCount;
    /**
     * Array of table entries. This array represents the slots in our simple hash table.
     */
    private TableEntry<K, V>[] table;

    /**
     * Creates an instance of simple hash table with the capacity set to 16.
     */
    public SimpleHashtable() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an instance of simple hash table with capacity set to the given capacity.
     * If the given capacity is not the power of 2, the capacity is set to the next number that is power of 2.
     *
     * @param capacity of our collection.
     * @throws IllegalArgumentException if the given capacity is less than 1.
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("The given capacity must be 1 or greater. Was " + capacity);
        }
        this.capacity = nextPowerOfTwo(capacity);
        table = (TableEntry<K, V>[]) new TableEntry[this.capacity];

    }

    /**
     * Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   with which the specified value is to be associated.
     * @param value value to be associated with the specified key
     * @throws NullPointerException if key is null reference.
     */
    @SuppressWarnings("unchecked")
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key must not be null.");
        }
        TableEntry<K, V> entry = new TableEntry<>(key, value, null);
        int slot = getSlot(key);

        if (table[slot] != null) {
            TableEntry<K, V> current = table[slot];
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            while (current.next != null) {
                current = current.next;
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
            }
            current.next = entry;
            size++;
            modificationCount++;
            checkCapacity();
            return;
        }

        table[slot] = entry;
        modificationCount++;
        size++;
        checkCapacity();
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this simple hash table  contains no mapping for the key.
     *
     * @param key for which we are looking for a mapped value.
     * @return the value to which the specified key is mapped.
     */
    @SuppressWarnings("unchecked")
    public V get(Object key) {

        int slot = getSlot(key);
        if (table[slot] == null) {
            return null;
        }

        TableEntry<K, V> entry = table[slot];
        if (entry.key.equals(key)) {
            return entry.value;
        }

        while (entry.next != null) {
            entry = entry.next;
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Returns the total number of elements in the collection.
     *
     * @return the total number of elements in the collection.
     */
    public int size() {
        return size;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key for which we are looking for a mapped value.
     * @return true if this map contains a mapping for the specified key.
     */
    @SuppressWarnings("unchecked")
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }

        int slot = getSlot(key);
        TableEntry<K, V> entry = table[slot];

        if (entry != null) {
            if (entry.key.equals(key)) {
                return true;
            }

            while (entry.next != null) {
                entry = entry.next;
                if (entry.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if this simple hash table contains a given value, if the hash table maps one or more keys to the specified value.
     *
     * @param value we are looking for in the collection.
     * @return true if the collection contains a given value.
     */
    @SuppressWarnings("unchecked")
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> entry : table) {
            if (entry != null) {
                if ((entry.value == null && value == null) || Objects.equals(entry.value, value)) {
                    return true;
                }
                TableEntry<K, V> current = entry;
                while (current.next != null) {
                    current = current.next;
                    if (current.value == null && value == null || Objects.equals(current.value, value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Removes the mapping for the specified key from this hash table if present.
     *
     * @param key for which we are looking for a mapped value.
     */
    @SuppressWarnings("unchecked")
    public void remove(Object key) {
        if (key == null) {
            return;
        }

        int slot = getSlot(key);
        if (table[slot] == null) {
            return;
        }

        TableEntry<K, V> entry = table[slot];
        if (entry.key.equals(key)) {
            table[slot] = entry.next;
            modificationCount++;
            size--;
            return;
        }
        TableEntry<K, V> previous = null;
        while (entry.next != null) {
            if (entry.next.key.equals(key)) {
                entry.next = entry.next.next;
                modificationCount++;
                size--;
                return;
            }
            previous = entry;
            entry = entry.next;
        }
        if (entry.key.equals(key)) {
            modificationCount++;
            size--;
            previous.next = null;
        }

    }

    /**
     * Returns true if the collection is empty.
     *
     * @return true if the collection is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the slot in which a new entry will be stored.
     *
     * @param key for which we calculate where it will be located.
     * @return the slot in which a new entry with given key will be located.
     */
    private int getSlot(Object key) {
        return abs(key.hashCode()) % capacity;
    }

    /**
     * Doubles the collection capacity if more than 75 % of the current capacity is "filled".
     */
    private void checkCapacity() {
        if (size >= capacity * INCREASE_CAPACITY_THRESHOLD) {
            increaseCapacity();
        }
    }

    /**
     * Doubles the current collection capacity.
     */
    @SuppressWarnings("unchecked")
    private void increaseCapacity() {
        TableEntry<K, V>[] oldTable = table;
        size = 0;
        capacity = capacity * 2;
        table = new TableEntry[capacity];
        for (TableEntry<K, V> entry : oldTable) {
            if (entry != null) {
                TableEntry<K, V> current = entry;
                put(current.key, current.value);
                while (current.next != null) {
                    current = current.next;
                    put(current.key, current.value);
                }
            }
        }
    }

    /**
     * Returns string representation of the collection in format [key1=value1 , ...].
     *
     * @return string in  format [ key1=value1 , key2=value2 , key3=value3 [....]].
     */
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {

        if (size == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder("[ ");
        for (TableEntry<K, V> entry : table) {
            if (entry != null) {
                sb.append(entry).append(", ");
                TableEntry<K, V> current = entry;
                while (current.next != null) {
                    current = current.next;
                    sb.append(current).append(", ");
                }
            }
        }
        sb = new StringBuilder(sb.substring(0, sb.lastIndexOf(",")));
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * Returns smallest power of 2 greater than or equal to the  given capacity.
     *
     * @param capacity the value we want to check.
     * @return smallest power of 2 greater than or equal to the given capacity.
     */
    private int nextPowerOfTwo(int capacity) {
        int count = 0;

        if ((capacity & (capacity - 1)) == 0) {
            return capacity;
        }

        while (capacity != 0) {
            capacity >>= 1;
            count++;
        }

        return 1 << count;
    }

    /**
     * Removes all of the mappings from this collection. The collection will be empty after this call returns.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            table[i] = null;
        }
        size = 0;
    }

    /**
     * Returns a new iterator that can navigate the collection elements.
     *
     * @return a new iterator that can navigate the collection elements.
     */
    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new IteratorImpl();
    }

    /**
     * Represents one entry in the simple hash table.
     *
     * @param <K> the type of keys maintained by simple hash table.
     * @param <V> the type of mapped values.
     */
    public static class TableEntry<K, V> {

        /**
         * Key with which the specified value is to be associated.
         */
        private K key;

        /**
         * Value to be associated with the specified key.
         */
        private V value;

        /**
         * Points to the next instance of the TableEntry<K,V> which is located in the same table slot.
         * By building this linked list, we will solve the problem of overflow.
         **/
        private TableEntry next;

        /**
         * Creates an instance of table entry. The next value of the last table entry in the "list" is null reference.
         *
         * @param key   with which the specified value is to be associated.
         * @param value to be associated with the specified key
         * @param next  reference to the next element in the slot.
         */
        public TableEntry(K key, V value, TableEntry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Returns the key of a table entry.
         *
         * @return a key of table entry.
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value of a table entry.
         *
         * @return the value of a table entry.
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets a value to the given value.
         *
         * @param value value to be associated with the specified key.
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Indicates whether some other object entry is "equal to" this one. Table entries are equal if they have the same key value.
         *
         * @param o other object.
         * @return true if this object is the same as the obj argument; false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableEntry<?, ?> entry = (TableEntry<?, ?>) o;
            return Objects.equals(key, entry.key) &&
                    Objects.equals(value, entry.value);
        }

        /**
         * Returns hash code of this table entry.
         *
         * @return hash code of this table entry.
         */
        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }


        /**
         * Returns a string representation of table entry in  a format "key=value".
         *
         * @return string in a format "key=value".
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * Represents iterators that can be used for iteration on all pairs currently stored in the table,
     * in the order they are stored in the table.
     */
    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
        /**
         * The number of changes in the internal data structure of the collection at the moment of creating an instance of this class.
         */
        private int modificationCountAtStart;
        /**
         * The number of elements of the internal data structure of the collection at the moment of creating an instance of this class.
         */
        private int sizeAtStart;
        /**
         * A boolean variable by checking whether the <b>remove</b> method is called two or more times in a row.
         */
        private boolean nextCalled;

        /**
         * Current table entry.
         */
        private TableEntry<K, V> current;
        /**
         * The current slot of our hash table.
         */
        private int currentIndex;
        /**
         * The number of elements we visited in the original hash table.
         */
        private int elements;

        /**
         * Crates an instance of iterator and sets default values to for variables.
         */
        public IteratorImpl() {
            this.modificationCountAtStart = modificationCount;
            sizeAtStart = size;
        }

        /**
         * Returns true if the iteration has more elements.
         *
         * @return true if the iteration has more elements.
         * @throws ConcurrentModificationException if the collection is directly modified during iteration.
         */
        @Override
        public boolean hasNext() {
            if (modificationCount != modificationCountAtStart) {
                throw new ConcurrentModificationException("It is not allowed to directly modify the collection during iteration.");
            }
            return elements != sizeAtStart;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration.
         * @throws NoSuchElementException if the collection is has no more elements.
         */
        @SuppressWarnings("unchecked")
        @Override
        public TableEntry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There are no more elements within the collection.");
            }
            nextCalled = true;

            if (elements == 0) {
                while (current == null) {
                    current = table[currentIndex++];
                }
                elements++;
                return current;
            }
            current = current.next;
            if (current == null) {
                current = table[currentIndex++];
            }
            elements++;
            return current;

        }

        /**
         * Removes from the underlying collection the last element returned by this iterator. This method can be called only once per call to next().
         *
         * @throws IllegalStateException           if the next method has not yet been called, or the remove method has already been called after the last call to the next method.
         * @throws ConcurrentModificationException if the collection is directly modified during iteration.
         */
        @Override
        public void remove() {
            if (!nextCalled) {
                throw new IllegalStateException("It is not allowed to remove more than one time for the current pair.");
            }
            if (modificationCountAtStart != modificationCount) {
                throw new ConcurrentModificationException("It is not allowed to directly modify the collection during iteration.");
            }
            SimpleHashtable.this.remove(current.key);
            modificationCountAtStart++;
            nextCalled = false;

        }
    }
}
