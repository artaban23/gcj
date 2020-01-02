package org.artaban23.gcj.y2019.qualification.forgone_solution;

import org.junit.Ignore;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void testSolveOne_simple() {
        Solution foregoneSolution = new Solution();
        List<Long> longs = foregoneSolution.solveOne(4);

        assertEquals(2, longs.size());
        assertEquals((Long) 3L, longs.get(0));
        assertEquals((Long) 1L, longs.get(1));
    }

    @Test
    public void testSolveOne_4InTheEnd() {
        Solution foregoneSolution = new Solution();
        List<Long> longs = foregoneSolution.solveOne(14);

        assertEquals(2, longs.size());
        assertEquals((Long) 13L, longs.get(0));
        assertEquals((Long) 1L, longs.get(1));
    }

    @Test
    public void testSolveOne_4InTheStart() {
        Solution foregoneSolution = new Solution();
        List<Long> longs = foregoneSolution.solveOne(453);

        assertEquals(2, longs.size());
        assertEquals((Long) 353L, longs.get(0));
        assertEquals((Long) 100L, longs.get(1));
    }

    @Test
    public void testSolveOne_4InTheMiddle() {
        Solution foregoneSolution = new Solution();
        List<Long> longs = foregoneSolution.solveOne(140);

        assertEquals(2, longs.size());
        assertEquals((Long) 130L, longs.get(0));
        assertEquals((Long) 10L, longs.get(1));
    }

    @Test
    public void testSolveOne_Complex() {
        Solution foregoneSolution = new Solution();
        List<Long> longs = foregoneSolution.solveOne(41243564);

        assertEquals(2, longs.size());
        assertEquals((Long) 31233563L, longs.get(0));
        assertEquals((Long) 10010001L, longs.get(1));
    }

    @Test
    public void testSolveOne_Max() {
        Solution foregoneSolution = new Solution();
        Long l = 40000000000L;
        List<Long> longs = foregoneSolution.solveOne(l);

        assertEquals(2, longs.size());
        assertEquals((Long) 30000000000L, longs.get(0));
        assertEquals((Long) 10000000000L, longs.get(1));
    }

    @Test
    public void testSimple() {
        int[][] ar = new int[3][4];
        ar[2][3] = 100;
        for (int i = 0; i < ar.length; i++) {
            for (int y = 0; y < ar[i].length; y++) {
                System.out.print(ar[i][y] + " ");
            }
            System.out.println("");
        }

        int ar2[] = new int[]{1, 2};
        int ar3[] = new int[]{1, 2};
        System.out.println("AA:" + ar2.equals(ar3));

        System.out.println("C:" + Arrays.toString(Arrays.copyOf(ar2, 3)));
    }

    @Ignore
    @Test
    public void testSolveStreams() throws Exception {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/input1.txt");
        Solution.solveStreams(resourceAsStream, System.out);

        assertTrue(true);
    }



}