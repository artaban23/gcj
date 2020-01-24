package org.artaban23.gcj.y2019.round2.new_elem1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void testIntType_Boundaries() {

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE + "" + Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);

        System.out.println(0.000000001 * Integer.MAX_VALUE);
        double x = 10E9 / Integer.MAX_VALUE;
        System.out.println(x);
        System.out.println((long) 10E9);
        System.out.println((long) 4 * Integer.MAX_VALUE);
        System.out.println((long) 5 * Integer.MAX_VALUE);

        System.out.println((1.000000001E-9) + " --> " + (1 / 1.000000001E-9));
        System.out.println((long) 1E9);
        System.out.println((long) 9.99999999E8);
        System.out.println(Integer.MAX_VALUE);
    }

//    @Test
//    public void testRelation() {
//        int[][] ar = {
//                {1, 9},
//                {2, 1},
//                {3, 8},
//                {4, 2},
//                {5, 7},
//                {6, 3},
//                {7, 6},
//                {8, 4},
//                {9, 5},
//        };
//
//
//        List<Solution.Molecule> molecules = Solution.create(ar);
//        for (int i = 0; i < molecules.size(); i++) {
//            List<Double> relations = Solution.printRelations(i, molecules);
//        }
//
//        System.out.println(Double.MIN_VALUE == Double.MIN_VALUE);
//    }

    @Test
    public void testGetWeightRelation() {
        Solution.Molecule mol1 = new Solution.Molecule(10, 20);
        Solution.Molecule mol2 = new Solution.Molecule(11, 21);

        assertTrue(mol1.isAlwaysSmallerThan(mol2));
        assertFalse(mol1.isAlwaysBiggerThan(mol2));
    }

    @Test
    public void testSolveOne_2_1() {
        long[][] ar = {
                {1, 2},
                {2, 1},
        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(2, res);

        assertEquals(2, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_2_1a() {
        long[][] ar = {
                {1, 9},
                {2, 1},
        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
//        int res = newElement.solveOne();
//        assertEquals(2, res);

        assertEquals(2, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_2_2() {
        long[][] ar = {
                {2, 1},
                {1, 9},
        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
//        int res = newElement.solveOne();
//        assertEquals(2, res);
        assertEquals(2, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_2_3() {
        long[][] ar = {
                {2, 1},
                {1, 1},
        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
//        int res = newElement.solveOne();
//        assertEquals(1, res);
        assertEquals(1, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_3_2() {
        long[][] ar = {
                {1, 10},
                {2, 9},
                {3, 10},

        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(2, res);
        assertEquals(2, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_3_4() {
        long[][] ar = {
                {1, 10},
                {2, 9},
                {4, 8},
        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(4, res);
        assertEquals(4, newElement.getAllRelations().size() + 1);
    }


    @Test
    public void testSolveOne_4_1() {
        long[][] ar = {
                {1, 10},
                {2, 9},
                {4, 8},
                {5, 9},

        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(5, res);
        assertEquals(5, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_4_2() {
        long[][] ar = {
                {1, 2},
                {2, 4},
                {2, 1},
                {4, 2},

        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(2, res);

        assertEquals(2, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_4_3() {
        long[][] ar = {
                {1, 10},
                {2, 9},
                {3, 10},
                {4, 9},

        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(3, res);
        assertEquals(3, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_5_4() {
        long[][] ar = {
                {1, 10},
                {2, 9},
                {3, 10},
                {4, 9},
                {5, 8},
        };
        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(4, res);
        assertEquals(4, newElement.getAllRelations().size() + 1);
    }


    @Test
    public void testSolveOne_8_1() {
        long[][] ar = {
                {1, 20},
                {3, 2},
                {5, 18},
                {7, 4},
                {9, 16},
                {11, 6},
        };

        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(8, res);

        assertEquals(8, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_8_8() {
        long[][] ar = {
                {1, 20},
                {3, 2},
                {5, 18},
                {7, 4},
                {9, 16},
                {11, 6},
        };

        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(8, res);

        assertEquals(8, newElement.getAllRelations().size() + 1);
    }

    @Test
    public void testSolveOne_9_8() {
        long[][] ar = {
                {1, 20},
                {3, 2},
                {5, 18},
                {7, 4},
                {9, 16},
                {11, 6},
                {12, 22},
        };

        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(8, res);

        assertEquals(8, newElement.getAllRelations().size() + 1);
    }


    @Ignore
    @Test
    public void testPerformance() {
        Random r = new Random();
        final int testCases = 100;
        final int maxMols = 300;

        for (int i = 0; i < testCases; i++) {
            Set<Solution.Molecule> moleculeList = new HashSet<>();
            while (moleculeList.size() < maxMols) {
                long x = Math.abs(r.nextInt());
                long y = Math.abs(r.nextInt());
                Solution.Molecule molecule = new Solution.Molecule(x, y);
                moleculeList.add(molecule);
            }
            Solution.NewElement newElement = new Solution.NewElement(new ArrayList<>(moleculeList));
            SortedSet<Double> allRelations = newElement.getAllRelations();
            System.out.println(newElement.getMoleculeList() + "-> " + allRelations.size());
//            System.out.println(i + "-> " + newElement.getAllRelations().size() + 1);

            List<Double> invalidRels = validateRelations(allRelations);
            if (!invalidRels.isEmpty()) {
                System.out.println(invalidRels);
                Assert.assertTrue(false);
            }
        }


    }

    public List<Double> validateRelations(SortedSet<Double> relSet) {
        List<Double> res = new ArrayList<>();
        Double prev = null;
        for (Double d : relSet) {
            if (prev != null) {
                double diff = d - prev;
                if (Integer.MAX_VALUE * d - Integer.MAX_VALUE*prev < 1) {
                    res.add(prev);
                    res.add(d);
                }
            }
            prev = d;
        }
        return res;
    }

    @Test
    public void testBoundaries() {
        int COUNT_MAX = 1000000000;
        long[][] ar = {
                {1, COUNT_MAX},
                {2, 1},
                {2, COUNT_MAX - 1},
        };

        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(3, res);

        SortedSet<Double> allRelations = newElement.getAllRelations();
        assertEquals(3, allRelations.size() + 1);
        System.out.println(allRelations);
        ArrayList<Double> relList = new ArrayList<>(allRelations);
        for (int i = 0; i < relList.size() - 1; i++) {
            double diff = relList.get(i + 1) - relList.get(i);
            System.out.println(diff + "->" + (Integer.MAX_VALUE * diff));
        }
    }

    @Test
    public void testBoundaries2() {
        int COUNT_MAX = 1000000000;
        long[][] ar = {
                {COUNT_MAX, 1},
                {1, 2},
                {COUNT_MAX - 1, 2},
        };

        Solution.NewElement newElement = new Solution.NewElement(ar);
        int res = newElement.solveOne();
        assertEquals(3, res);
        assertEquals(3, newElement.getAllRelations().size() + 1);
    }


    @Test
    public void testSolveStreams_simple() throws Exception {
        InputStream inputAsStream = this.getClass().getResourceAsStream("/y2019/round2/new_elem1/input1.txt");
        File resultFile = new File("input1_out" + System.currentTimeMillis() + ".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        try (PrintStream printStream = new PrintStream(fileOutputStream)) {
            Solution.solveStreams(inputAsStream, printStream);
        }

        String resultString = FileUtils.readFileToString(resultFile, UTF_8);
        InputStream expectedOutputAsStream = this.getClass().getResourceAsStream("/y2019/round2/new_elem1/input1_out.txt");
        String expectedOutput = IOUtils.toString(expectedOutputAsStream, StandardCharsets.UTF_8);

        assertEquals(expectedOutput, resultString);
    }
}
