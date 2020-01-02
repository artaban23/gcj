package org.artaban23.gcj.y2019.qualification.dat_bae;

import org.artaban23.gcj.y2019.qualification.dat_bae.Solution;
import org.junit.Assert;
import org.junit.Test;

public class GcpVerifierTest {

    @Test
    public void testStringToAr() {
        int[] res = Solution.GcpVerifier.stringToAr("1010");
        Assert.assertArrayEquals(new int[]{1, 0, 1, 0}, res);
    }

    @Test
    public void testArToString() {
        String res = Solution.GcpVerifier.arToString(new int[]{1, 0, 1, 0});
        Assert.assertEquals("1010", res);
    }
}
