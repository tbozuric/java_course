package hr.fer.zemris.java.custom.collections;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {


    private Dictionary dictionary;

    @Before
    public void initialization() {
        dictionary = new Dictionary();
    }


    @Test
    public void testEmpty() {
        Assert.assertTrue(dictionary.isEmpty());
        Assert.assertEquals(0, dictionary.size());
    }

    @Test
    public void putObject() {
        ArrayIndexedCollection elements = new ArrayIndexedCollection();
        elements.add("Matko");
        elements.add("Mirko");
        elements.add("Fabo");
        dictionary.put("polaznici", elements);
        Assert.assertTrue(!dictionary.isEmpty());
        Assert.assertEquals(1, dictionary.size());
        Assert.assertEquals(3, ((ArrayIndexedCollection) dictionary.get("polaznici")).size());
        dictionary.put(2, 4);
        Assert.assertTrue((int) dictionary.get(2) == 4);
        dictionary.put(2, 5);
        Assert.assertTrue((int) dictionary.get(2) == 5);
        Assert.assertEquals(2, dictionary.size());
        dictionary.put("polaznici", "ivan");
        Assert.assertTrue(dictionary.get("polaznici").equals("ivan"));
        Assert.assertTrue(dictionary.size() == 2);
        dictionary.put(5, null);
        Assert.assertTrue(dictionary.get("non-exists") == null);
        Assert.assertTrue(dictionary.get(5) == null);
    }

    @Test
    public void clear() {
        dictionary.put("ivanka", null);
        dictionary.put("stefanija", null);
        dictionary.put("stefica", "rundic");
        Assert.assertTrue(dictionary.size() == 3);
        dictionary.clear();
        Assert.assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        dictionary.put(5, 5);
        Assert.assertFalse(dictionary.isEmpty());
    }

    @Test
    public void testSize() {
        dictionary.put(5, 5);
        dictionary.put(5, 4);
        dictionary.put(3, 3);
        Assert.assertEquals(2, dictionary.size());
    }

    @Test
    public void testClear() {
        dictionary.put(5, 5);
        Assert.assertFalse(dictionary.isEmpty());
        dictionary.clear();
        Assert.assertTrue(dictionary.isEmpty());
    }

    @Test
    public void testGet() {
        dictionary.put("PERO", "Ivica");
        dictionary.put('%', null);
        Assert.assertEquals("Ivica", dictionary.get("PERO"));

        dictionary.put("PERO", 42);
        Assert.assertEquals(42, dictionary.get("PERO"));
        Assert.assertEquals(null, dictionary.get('%'));
        Assert.assertEquals(null, dictionary.get(12345));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalPut() {
        dictionary.put(null, 5);
    }


}
