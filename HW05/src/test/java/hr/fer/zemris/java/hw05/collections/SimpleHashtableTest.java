package hr.fer.zemris.java.hw05.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtableTest {

    SimpleHashtable<String,Integer> examMarks;

    @Before
    public void initialization(){
        // create collection:
        examMarks = new SimpleHashtable<>(2);
        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);


    }
    @Test
    public void testGet(){
        Assert.assertTrue(examMarks.get("Kristina") == 5);
        Assert.assertTrue(examMarks.get("Ivana") == 2);
        Assert.assertTrue(examMarks.get("Jasna") == 2);
        Assert.assertTrue(examMarks.get("Ante") == 2);
    }

    @Test
    public void testGetNull(){
        Assert.assertTrue(examMarks.get(5)==null);
        Assert.assertTrue(examMarks.get("Ivanka")==null);
    }

    @Test(expected = NullPointerException.class)
    public void testPut(){
        examMarks.put(null, 5);
    }

    @Test
    public void testPutOverwrite(){
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana
        Assert.assertTrue(examMarks.get("Ivana") == 5);
        examMarks.put("Ivana", null);
        Assert.assertTrue(examMarks.get("Ivana")==null);
    }

    @Test
    public void testRemoveExistsItem(){
        examMarks.remove("Ivana");
        Assert.assertTrue(examMarks.size()==3);
        examMarks.remove("Ante");
        Assert.assertTrue(examMarks.size()==2);
        examMarks.remove("Kristina");
        Assert.assertTrue(examMarks.size()==1);
        examMarks.remove("Jasna");
        Assert.assertTrue(examMarks.size()==0);
        examMarks.remove("Jasna");
        Assert.assertTrue(examMarks.size()==0);
    }

    @Test
    public void testRemoveNonExistsItem(){
        examMarks.remove("Marko");
        Assert.assertTrue(examMarks.size()==4);
        examMarks.remove(null);
        Assert.assertTrue(examMarks.size()==4);

    }

    @Test
    public void testContainsKey(){
        Assert.assertTrue(examMarks.containsKey("Ivana"));
        Assert.assertTrue(examMarks.containsKey("Jasna"));
        Assert.assertTrue(examMarks.containsKey("Kristina"));
        Assert.assertFalse(examMarks.containsKey(null));
    }

    @Test
    public void testContainsValue(){
        Assert.assertTrue(examMarks.containsValue(5));
        Assert.assertFalse(examMarks.containsValue(null));
        examMarks.put("Ivana", null);
        Assert.assertTrue(examMarks.containsValue(null));
        Assert.assertTrue(examMarks.containsValue(2));

    }

    @Test
    public void testRemoveWithIterator(){
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            if (pair.getKey().equals("Ivana")) {
                iter.remove(); // sam iterator kontrolirano uklanja trenutni element
            }
        }

        Assert.assertTrue(examMarks.size()==3);
        Assert.assertFalse(examMarks.isEmpty());

    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveTwoTimes(){
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter2 = examMarks.iterator();
        while (iter2.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter2.next();
                iter2.remove();
                iter2.remove();
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public  void testDirectModification(){
        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
        while(iter.hasNext()) {
            SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
            if(pair.getKey().equals("Ivana")) {
                examMarks.remove("Ivana");
            }
        }
    }

    @Test
    public void testIteratorRemoveMethod(){
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            iter.remove();//This method can be called only once per call to next()
        }
        Assert.assertTrue(examMarks.isEmpty());
        Assert.assertTrue(examMarks.size()==0);

    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorWithoutHsNext(){
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (true) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
        }
    }

    @Test
    public void testRemoveWithoutHasNext(){
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
        iter.remove();
        Assert.assertTrue(examMarks.size()==3);
        iter.next();
        iter.remove();
        Assert.assertTrue(examMarks.size()==2);
        iter.next();
        iter.remove();
        Assert.assertTrue(examMarks.size()==1);
        iter.next();
        iter.remove();
        Assert.assertTrue(examMarks.size()==0);


    }

    @Test
    public void testClear(){
        examMarks.clear();
        Assert.assertTrue(examMarks.size()==0);
        Assert.assertTrue(examMarks.isEmpty());
    }


}
