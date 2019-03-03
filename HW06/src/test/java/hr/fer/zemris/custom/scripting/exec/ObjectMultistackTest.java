package hr.fer.zemris.custom.scripting.exec;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectMultistackTest {

    private ObjectMultistack multistack;

    @Before
    public void initialization(){
            multistack = new ObjectMultistack();
            ValueWrapper year = new ValueWrapper(2000);
            multistack.push("year", year);
            ValueWrapper price = new ValueWrapper(200.51);
            multistack.push("price", price);

    }

    @Test
    public void testIsEmpty(){
        Assert.assertTrue(multistack.isEmpty("London"));
        Assert.assertFalse(multistack.isEmpty("year"));
        Assert.assertFalse(multistack.isEmpty("price"));
    }

    @Test(expected = NullPointerException.class)
    public void testIfEmptyForNullValue(){
        multistack.isEmpty(null);
    }

    @Test
    public void testPush(){
        ValueWrapper grade = new ValueWrapper(5);
        multistack.push("grade" , grade);
        Assert.assertFalse(multistack.isEmpty("grade"));
    }
    @Test(expected = NullPointerException.class)
    public void testPushNullValue(){
        multistack.push("london", null);
    }

    @Test(expected = NullPointerException.class)
    public void testPushNullName(){
        multistack.push(null , new ValueWrapper(500));
    }

    @Test
    public void testPop(){
        multistack.push("year", new ValueWrapper(2050));
        multistack.push("year", new ValueWrapper(2048));
        Assert.assertTrue((Integer)multistack.pop("year").getValue() == 2048);
        Assert.assertTrue((Integer)multistack.pop("year").getValue() == 2050);
        Assert.assertFalse(multistack.isEmpty("year"));
        Assert.assertTrue((Integer)multistack.pop("year").getValue() == 2000);
        Assert.assertTrue(multistack.isEmpty("year"));
        Assert.assertTrue((Double)multistack.pop("price").getValue() == 200.51);
    }

    @Test(expected = EmptyStackException.class)
    public void testPopFromEmptyMultiStack(){
        multistack.pop("london");
    }

    @Test(expected = NullPointerException.class)
    public void testPopForNullName(){
        multistack.pop(null);
    }
    @Test
    public void testPeek(){
        multistack.push("year", new ValueWrapper(2050));
        Assert.assertTrue((Integer)multistack.peek("year").getValue()==2050);
        multistack.push("year" , new ValueWrapper(null));
        Assert.assertTrue(multistack.peek("year").getValue() == null);
        multistack.pop("year");
        Assert.assertFalse(multistack.isEmpty("year"));
    }

    @Test(expected = NullPointerException.class)
    public void testPeekForNull(){
        multistack.peek(null);
    }

    @Test(expected = EmptyStackException.class)
    public void testPeekFromEmptyMultistack(){
        multistack.peek("london");
    }

    @Test
    public void testMultistackEntryNext(){
        multistack.push("year", new ValueWrapper(2058));
        ObjectMultistack.MultistackEntry entry = new ObjectMultistack.MultistackEntry(new ValueWrapper(2058),
                new ObjectMultistack.MultistackEntry(new ValueWrapper(2050),null));
        Assert.assertTrue((Integer)entry.getNext().getValueWrapper().getValue() == 2050);
        Assert.assertTrue(entry.getNext().getNext() == null);
    }

}
