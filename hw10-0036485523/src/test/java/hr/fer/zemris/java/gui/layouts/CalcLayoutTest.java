package hr.fer.zemris.java.gui.layouts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class CalcLayoutTest {

    private static JPanel p;
    private static JLabel l1;
    private static JLabel l2;

    @Before
    public void initialization(){
         p = new JPanel(new CalcLayout(2));
         l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
         l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));

    }

    @Test
    public void testDimensions1(){
        p.add(l1, new RCPosition(2,2));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();
        Assert.assertEquals(dim.width , 152);
        Assert.assertEquals(dim.height , 158);
    }

    @Test
    public void testDimensions2(){
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();
        Assert.assertEquals(dim.width , 152);
        Assert.assertEquals(dim.height , 158);
    }

    @Test(expected = CalcLayoutException.class)
    public void testAddToSamePositionDifferentComponents(){
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(1,1));

    }

    @Test(expected = CalcLayoutException.class)
    public void testNegativePosition(){
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(-1,1));

    }

    @Test(expected = CalcLayoutException.class)
    public void testTooBigPosition(){
        p.add(l1, new RCPosition(6,9));
    }

    @Test(expected = CalcLayoutException.class)
    public void testDisplayPosition(){
        p.add(l1, new RCPosition(1,3));
    }

    @Test
    public void addToSamePositionSameComponents(){
        p.add(l1 , new RCPosition(1,1));
        p.add(l1 , new RCPosition(1,1));
    }
}
