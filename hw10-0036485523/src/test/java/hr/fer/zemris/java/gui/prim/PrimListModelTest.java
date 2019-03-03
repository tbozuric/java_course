package hr.fer.zemris.java.gui.prim;

import org.junit.Assert;
import org.junit.Before;

import javax.swing.*;

import hr.fer.zemris.java.gui.prim.PrimDemo.PrimListModel;
import org.junit.Test;

public class PrimListModelTest {

    private static final double THRESHOLD = 1e-3;
    private static PrimListModel model;

    @Before
    public  void initialization(){
        model = new PrimListModel();
        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);
    }

    @Test
    public void testInit(){
       Assert.assertEquals(1, model.getElementAt(0) , THRESHOLD);
    }

    @Test
    public void testNext(){
        model.next();
        model.next();
        model.next();
        model.next();
        model.next();

        Assert.assertEquals(6 , model.getSize());
        Assert.assertEquals(1 , model.getElementAt(0), THRESHOLD);
        Assert.assertEquals(2 , model.getElementAt(1),THRESHOLD);
        Assert.assertEquals(3 , model.getElementAt(2),THRESHOLD);
        Assert.assertEquals(5 , model.getElementAt(3),THRESHOLD);
        Assert.assertEquals(7 , model.getElementAt(4),THRESHOLD);
        Assert.assertEquals(11 , model.getElementAt(5),THRESHOLD);
    }


}
