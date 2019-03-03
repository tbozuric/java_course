package hr.fer.zemris.java.hw07.crypto;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class UtilTest {

    @Test
    public void testSimpleHexToByte(){
        Assert.assertTrue(Arrays.equals(new byte[]{1,-82,34}, Util.hexToByte("01aE22")));
    }

    @Test
    public void testSimpleByteToHex(){
        Assert.assertEquals(Util.bytesToHex(new byte[]{1,-82,34}), "01ae22");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHexToByte(){
        Util.hexToByte("01Ge");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOddHexToByte(){
        Util.hexToByte("01a");
    }

    @Test
    public void testBiggerHexToByte(){
        Assert.assertTrue(Arrays.equals(new byte[]{-86 , 1 ,2 , -86, -82, -82, -1}, Util.hexToByte("AA0102aaaeAEFf")));
    }

    @Test
    public void testBiggerByteToHex(){
        Assert.assertEquals(Util.bytesToHex(new byte[]{-86,1,2,-86,-82,-82,-1}),"AA0102aaaeAEFf".toLowerCase());
    }
}
