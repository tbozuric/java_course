package hr.fer.zemris.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents  a special kind of Map .While Map allows you only to store for each key a single value, ObjectMultistack
 * allow the user to store multiple values for same key. Keys for
 * ObjectMultistack are  instances of the class String . Values that will be associated with those keys are
 * instances of class ValueWrapper (you will also create this class).
 */
public class ObjectMultistack {
    /**
     * Map names to instances of @{@link MultistackEntry} class.
     */
    private Map<String, MultistackEntry> map;

    /**
     * Creates an instance of multistack.
     */
    public ObjectMultistack() {
        map = new HashMap<>();
    }

    /**
     * Pushes given  value wrapper  on the stack and associate it with the given name.
     *
     * @param name         desired name.
     * @param valueWrapper wrapper around the vale.
     * @throws NullPointerException if the name or  value wrapper is a null reference.
     */
    public void push(String name, ValueWrapper valueWrapper) {

        if (valueWrapper == null || name == null) {
            throw new NullPointerException("Name and value can not be null references!");
        }

        MultistackEntry entry = map.get(name);
        map.put(name, new MultistackEntry(valueWrapper, entry));
    }

    /**
     * Removes last value pushed on multistack which is associated with the given name from the multistack and returns it.
     *
     * @param name the desired name for which we get the valueWrapper.
     * @return last value pushed on multistack which is associated with the given name.
     * @throws NullPointerException if the given name is a null reference.
     * @throws EmptyStackException  if the multistack is empty for the given name.
     */
    public ValueWrapper pop(String name) {
        if (name == null) {
            throw new NullPointerException("Name can not be null!");
        }

        if (map.isEmpty() || map.get(name) == null) {
            throw new EmptyStackException("Multistack is empty for the given name!");
        }
        MultistackEntry entry = map.get(name);
        map.replace(name, entry, entry.next);

        return entry.getValueWrapper();
    }

    /**
     * Returns last element placed on the multistack which is associated with the given name.
     *
     * @param name the desired name for which we get the valueWrapper.
     * @return last element placed on the multistack which is associated with the given name.
     * @throws NullPointerException if the given name is a null reference.
     * @throws EmptyStackException if the multistack is empty for the given name.
     */
    public ValueWrapper peek(String name) {
        if(name == null){
            throw new NullPointerException("Name can not be a null reference!");
        }
        if (map.isEmpty() || map.get(name) == null) {
            throw new EmptyStackException("Multistack is empty!");
        }
        return map.get(name).getValueWrapper();
    }

    /**
     * Checks if the multistack is empty for the given name.
     *
     * @param name name for which we check whether there is a connected value wrapper.
     * @return true if the multistack is empty for the givne name.
     * @throws NullPointerException if the given name is a null reference.
     */
    public boolean isEmpty(String name) {
        if (name == null) {
            throw new NullPointerException("Name can not be null!");
        }
        return map.get(name) == null;
    }

    /**
     * Represents an multistack entry. Each record consists of references to the valueWrapper and pointer to the next valueWrapper.
     * We use @{@link MultistackEntry} objects to form linked lists which represent stacks; each list one stack.
     */
    public static class MultistackEntry {
        /**
         * Reference to value wrapper.
         */
        private ValueWrapper valueWrapper;
        /**
         * Reference to the next value wrapper which is associated with  the same name.
         */
        private MultistackEntry next;

        /**
         * Creates an instance of multistack entry.
         *
         * @param valueWrapper reference to value wrapper.
         * @param next         next value wrapper which is associated with the same name.
         */
        public MultistackEntry(ValueWrapper valueWrapper, MultistackEntry next) {

            this.valueWrapper = valueWrapper;
            this.next = next;
        }

        /**
         * Returns value wrapper.
         *
         * @return value wrapper.
         */
        public ValueWrapper getValueWrapper() {
            return valueWrapper;
        }

        /**
         * Returns next multistack entry.
         * @return next multistack entry.
         */
        public MultistackEntry getNext() {
            return next;
        }
    }

}
