package org.artaban23.gcj.y2019.qualification.dat_bae;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;

public class DatBaeTest3 {
    private static final int WRONG_BIT_COUNT_MAX = 15;
    private static final int NODES_MAX = 1024;
    private Solution.DatBae datBae = new Solution.DatBae();

    @Test
    public void testSimpleVerifier() {
        datBae.setVerifier(new Solution.SimpleVerifier(1, 3, 5));
        int[] sample = {0, 0, 0, 1, 1, 1};
        int[] res = datBae.sendToVerify(sample);
        assertArrayEquals(new int[]{0, 0, 1}, res);
    }

    @Test
    public void testSolve_n2_b1() {
        datBae.setVerifier(new Solution.SimpleVerifier(0));
        int[] solve = datBae.solve(2, 1, 10);

        assertArrayEquals(new int[]{0}, solve);
    }

    @Test
    public void testSolve_n2_b1_second() {
        datBae.setVerifier(new Solution.SimpleVerifier(1));
        int[] solve = datBae.solve(2, 1, 1);

        assertArrayEquals(new int[]{1}, solve);
    }

    @Test
    public void testSolve_n3_b1() {
        datBae.setVerifier(new Solution.SimpleVerifier(0));
        int[] solve = datBae.solve(3, 1, 1);

        assertArrayEquals(new int[]{0}, solve);
    }

    @Test
    public void testSolve_n3_b1_second() {
        datBae.setVerifier(new Solution.SimpleVerifier(1));
        int[] solve = datBae.solve(3, 1, 1);

        assertArrayEquals(new int[]{1}, solve);
    }

    @Test
    public void testSolve_n3_b1_third() {
        datBae.setVerifier(new Solution.SimpleVerifier(2));
        int[] solve = datBae.solve(3, 1, 1);

        assertArrayEquals(new int[]{2}, solve);
    }

    @Test
    public void testSolve_n3_b2() {
        datBae.setVerifier(new Solution.SimpleVerifier(0, 1));
        int[] solve = datBae.solve(3, 2, 1);

        assertArrayEquals(new int[]{0, 1}, solve);
    }

    @Test
    public void testSolve_n3_b2_second() {
        datBae.setVerifier(new Solution.SimpleVerifier(1, 2));
        int[] solve = datBae.solve(3, 2, 1);

        assertArrayEquals(new int[]{1, 2}, solve);
    }

    @Test
    public void testSolve_n3_b2_third() {
        datBae.setVerifier(new Solution.SimpleVerifier(0, 2));
        int[] solve = datBae.solve(3, 2, 1);

        assertArrayEquals(new int[]{0, 2}, solve);
    }


    @Test
    public void testSolve_n6_b3() {
        datBae.setVerifier(new Solution.SimpleVerifier(1, 3, 5));
        int[] solve = datBae.solve(6, 3, 3);

        assertArrayEquals(new int[]{1, 3, 5}, solve);
    }

    @Test
    public void testSolve_n6_b3_two() {
        datBae.setVerifier(new Solution.SimpleVerifier(0, 2, 4));
        int[] solve = datBae.solve(6, 3, 3);

        assertArrayEquals(new int[]{0, 2, 4}, solve);
    }

    @Test
    public void testSolve_n6_b3_first() {
        datBae.setVerifier(new Solution.SimpleVerifier(3, 4, 5));
        int[] solve = datBae.solve(6, 3, 3);

        assertArrayEquals(new int[]{3, 4, 5}, solve);
    }


    @Test
    public void testSolve_n6_b3_last() {
        datBae.setVerifier(new Solution.SimpleVerifier(3, 4, 5));
        int[] solve = datBae.solve(6, 3, 3);

        assertArrayEquals(new int[]{3, 4, 5}, solve);
    }

    @Test
    public void testSolve_n7_b4() {
        datBae.setVerifier(new Solution.SimpleVerifier(2, 3, 4, 5));
        int[] solve = datBae.solve(7, 4, 3);

        assertArrayEquals(new int[]{2, 3, 4, 5}, solve);
    }

    @Test
    public void testSolve_n100_b4() {
        int[] ints = {0, 97, 98, 99};
        datBae.setVerifier(new Solution.SimpleVerifier(ints));
        final int n = 100;
        int[] solve = datBae.solve(n, 4, 50);

        assertArrayEquals(ints, solve);
    }

    @Test
    public void testSolve_n1024_b15() {
        int[] brokenBits = {0, 97, 98, 99, 100, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012,
                1013, 1014, 1015};
        doTestWithVariables(brokenBits, NODES_MAX);
    }

    @Test
    public void testSolve_n1024_b15_last() {
        int[] brokenBits = new int[WRONG_BIT_COUNT_MAX];
        for (int i = 0; i < WRONG_BIT_COUNT_MAX; i++) {
            brokenBits[i] = NODES_MAX - WRONG_BIT_COUNT_MAX + i;
        }

        doTestWithVariables(brokenBits, NODES_MAX);
    }

    @Test
    public void testSolve_n1024_b15_first() {
        int[] brokenBits = new int[WRONG_BIT_COUNT_MAX];
        for (int i = 0; i < WRONG_BIT_COUNT_MAX; i++) {
            brokenBits[i] =  i;
        }
        doTestWithVariables(brokenBits, NODES_MAX);
    }


    private void doTestWithVariables(int[] brokenBits, int n) {
        datBae.setVerifier(new Solution.SimpleVerifier(brokenBits));
        int[] solve = datBae.solve(n, brokenBits.length, 20);
        assertArrayEquals(brokenBits, solve);
    }

    @Test
    public void testPerformance() {
        Random r = new Random();
        int brokenBitCount = 15;
        int n = 1024;
        for (int i = 0; i < 200; i++) {
            SortedSet<Integer> brokenBitsSet = generateBrokenBits(r, brokenBitCount, n);
            List<Integer> list = new ArrayList<>(brokenBitsSet);
            int[] brokenBits = list.stream().mapToInt(iv -> iv).toArray();

            doTestWithVariables(brokenBits, n);
        }
    }

    private SortedSet<Integer> generateBrokenBits(Random r, int brokenBitCount, int n) {
        SortedSet<Integer> brokenBitsSet = new TreeSet<>();
        while (brokenBitsSet.size() != brokenBitCount) {
            brokenBitsSet.add(r.nextInt(n));
        }

        return brokenBitsSet;
    }

    @Test
    public void testFormStringResult() {
        int[] ar = {1, 1, -1, -1, -1, -1, 1};
        String res = Solution.formStringResult(ar);
        Assert.assertEquals("2 3 4 5", res);
    }

//    @Test
//    public void testGcpVerifier() {
//        Solution.InputReader in = new Solution.InputReader(System.in);
//        Solution.OutputWriter out = new Solution.OutputWriter(System.out);
//
//        datBae.setVerifier(new Solution.GcpVerifier(in, out));
//        int[] sample = {0, 0, 0, 1, 1, 1};
//
//        datBae.getVerifier().getOs().printLine(sample);
//        datBae.getVerifier().getOs().flush();
//    }
}
