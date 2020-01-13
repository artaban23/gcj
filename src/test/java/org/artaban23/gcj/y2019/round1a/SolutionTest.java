package org.artaban23.gcj.y2019.round1a;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SolutionTest {

    @Test
    public void testGetCenter() {
        int center = Solution.getCenter(2);
        assertEquals(1, center);
        center = Solution.getCenter(3);
        assertEquals(1, center);
        center = Solution.getCenter(4);
        assertEquals(2, center);
    }

    @Test
    public void testCenterDist4_to_4() {
        CoordBuilder coordBuilder = new CoordBuilder(4, 4);
        Solution.Coord coord = coordBuilder.build(2, 2);
        assertEquals(0, coord.distToCenter);
        Solution.Coord coord2 = coordBuilder.build(2, 3);
        assertEquals(0, coord2.distToCenter);
        Solution.Coord coord3 = coordBuilder.build(3, 2);
        assertEquals(0, coord3.distToCenter);
        Solution.Coord coord4 = coordBuilder.build(3, 3);
        assertEquals(0, coord4.distToCenter);
        Solution.Coord coord5 = coordBuilder.build(3, 4);
        assertEquals(1, coord5.distToCenter);
        Solution.Coord coord6 = coordBuilder.build(4, 4);
        assertEquals(2, coord6.distToCenter);
    }

    @Test
    public void testCenterDist5_to_4() {
        CoordBuilder coordBuilder = new CoordBuilder(5, 4);
        Solution.Coord coord = coordBuilder.build(3, 2);
        assertEquals(0, coord.distToCenter);
        Solution.Coord coord2 = coordBuilder.build(3, 3);
        assertEquals(0, coord2.distToCenter);

        Solution.Coord coord3 = coordBuilder.build(2, 2);
        assertEquals(1, coord3.distToCenter);
        Solution.Coord coord4 = coordBuilder.build(4, 3);
        assertEquals(1, coord4.distToCenter);
    }

    @Test
    public void testCache5_2() {
        Solution.Cache cache = new Solution.Cache(5, 2);
        assertEquals(6, cache.getDiagSumCache().size());
        assertEquals(6, cache.getDiagMinusCache().size());
        assertEquals(1, (int) cache.getDiagSumCache().get(0));
        assertEquals(1, (int) cache.getDiagSumCache().get(5));
        assertEquals(2, (int) cache.getDiagSumCache().get(1));
        assertEquals(2, (int) cache.getDiagSumCache().get(2));
        assertEquals(2, (int) cache.getDiagSumCache().get(3));
        assertEquals(2, (int) cache.getDiagSumCache().get(4));

        assertEquals(2, (int) cache.getDiagMinusCache().get(0));
        assertEquals(1, (int) cache.getDiagMinusCache().get(-1));
        assertEquals(2, (int) cache.getDiagMinusCache().get(1));
        assertEquals(2, (int) cache.getDiagMinusCache().get(2));
        assertEquals(2, (int) cache.getDiagMinusCache().get(3));
        assertEquals(1, (int) cache.getDiagMinusCache().get(4));
    }

    @Test
    public void testCache5_7() {
        Solution.Cache cache = new Solution.Cache(5, 7);
        assertEquals(11, cache.getDiagSumCache().size());
        assertEquals(11, cache.getDiagMinusCache().size());
        assertEquals(1, (int) cache.getDiagSumCache().get(0));
        assertEquals(1, (int) cache.getDiagSumCache().get(10));
        assertEquals(2, (int) cache.getDiagSumCache().get(1));
        assertEquals(3, (int) cache.getDiagSumCache().get(2));
        assertEquals(5, (int) cache.getDiagSumCache().get(6));
        assertEquals(4, (int) cache.getDiagSumCache().get(7));
        assertEquals(2, (int) cache.getDiagSumCache().get(9));

        assertEquals(5, (int) cache.getDiagMinusCache().get(0));
        assertEquals(5, (int) cache.getDiagMinusCache().get(-1));
        assertEquals(5, (int) cache.getDiagMinusCache().get(-2));
        assertEquals(4, (int) cache.getDiagMinusCache().get(-3));
        assertEquals(3, (int) cache.getDiagMinusCache().get(-4));
        assertEquals(2, (int) cache.getDiagMinusCache().get(-5));
        assertEquals(1, (int) cache.getDiagMinusCache().get(-6));
        assertEquals(4, (int) cache.getDiagMinusCache().get(1));
        assertEquals(3, (int) cache.getDiagMinusCache().get(2));
        assertEquals(2, (int) cache.getDiagMinusCache().get(3));
        assertEquals(1, (int) cache.getDiagMinusCache().get(4));
    }

    @Test
    public void testCache_MarkVisited() {
        CoordBuilder coordBuilder = new CoordBuilder(5, 7);
        Solution.Cache cache = coordBuilder.buildCache();
        assertEquals(5, (int) cache.getColCache().get(2));
        assertEquals(7, (int) cache.getRowCache().get(4));
        assertEquals(5, (int) cache.getDiagSumCache().get(6));
        assertEquals(3, (int) cache.getDiagMinusCache().get(2));
        cache.markVisited(coordBuilder.build(4, 2));
        assertEquals(4, (int) cache.getColCache().get(2));
        assertEquals(6, (int) cache.getRowCache().get(4));
        assertEquals(4, (int) cache.getDiagSumCache().get(6));
        assertEquals(2, (int) cache.getDiagMinusCache().get(2));
    }

    @Test
    public void testReverse() {
        CoordBuilder coordBuilder = new CoordBuilder(2, 1);
        List<Solution.Coord> coordList = new ArrayList<>();
        coordList.add(coordBuilder.build(0, 0));
        coordList.add(coordBuilder.build(1, 0));

        System.out.println(coordList);
        List<Solution.Coord> coordList1 = Solution.Pylons.reverseCoords(coordList);
        System.out.println(coordList1);
        List<Solution.Coord> coordList2 = Solution.Pylons.reverseCoords(coordList1);
        System.out.println(coordList2);
    }

    @Test
    public void testReverse_3_2() {
        int rowCount = 3;
        int colCount = 2;
        CoordBuilder coordBuilder = new CoordBuilder(rowCount, colCount);
        List<Solution.Coord> coordList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                coordList.add(coordBuilder.build(i, j));
            }
        }
        List<Solution.Coord> newList = Solution.Pylons.reverseCoords(coordList);

        assertEquals(6, newList.size());
        assertEquals(1, newList.get(0).row);
        assertEquals(0, newList.get(0).col);
        assertEquals(0, newList.get(1).row);
        assertEquals(0, newList.get(1).col);
        assertEquals(1, newList.get(4).row);
        assertEquals(2, newList.get(4).col);
        assertEquals(0, newList.get(5).row);
        assertEquals(2, newList.get(5).col);
    }

    @Test
    public void testSolution2_2() {
        int rowCount = 2;
        int colCount = 2;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertEquals(0, coords.size());
    }

    @Test
    public void testSolution2_5() {
        int rowCount = 2;
        int colCount = 5;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }


    @Test
    public void testSolution3_4() {
        int rowCount = 3;
        int colCount = 4;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }

    @Test
    public void testSolution3_5() {
        int rowCount = 3;
        int colCount = 4;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }

    @Test
    public void testSolution5_2() {
        int rowCount = 5;
        int colCount = 2;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }

    @Test
    public void testSolution5_3() {
        int rowCount = 5;
        int colCount = 3;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }

    @Test
    public void testSolution5_4() {
        int rowCount = 5;
        int colCount = 4;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }

    @Test
    public void testSolution5_7() {
        int rowCount = 5;
        int colCount = 7;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }

    @Test
    public void testSolution6_3() {
        int rowCount = 6;
        int colCount = 3;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }


    @Test
    public void testSolution20_20() {
        int rowCount = 20;
        int colCount = 20;
        Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
        List<Solution.Coord> coords = pylons.solveOne();

        assertCoords(rowCount, colCount, coords);
    }

    private void assertCoords(int rowCount, int colCount, List<Solution.Coord> coords) {
        assertEquals(rowCount * colCount, coords.size());
        Set<Solution.Coord> coordSet = new HashSet<>(coords);
        assertEquals(rowCount * colCount, coordSet.size());
        for (int i = 0; i < coords.size() - 1; i++) {
            Solution.Coord first = coords.get(i);
            Solution.Coord second = coords.get(i + 1);

            assertTrue(first.isValidDestination(second));
            assertTrue(first.col >= 0);
            assertTrue(first.col < colCount);
            assertTrue(first.row >= 0);
            assertTrue(first.row < rowCount);

        }
    }

    @Test
    public void testSolveStreams_simple() throws Exception {
        InputStream inputAsStream = this.getClass().getResourceAsStream("/y2019/round1a/pylons/input1.txt");
        File resultFile = new File("input1_out" + System.currentTimeMillis() + ".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        try (PrintStream printStream = new PrintStream(fileOutputStream)) {
            Solution.solveStreams(inputAsStream, printStream);
        }

        String resultString = FileUtils.readFileToString(resultFile, UTF_8);
        InputStream expectedOutputAsStream = this.getClass().getResourceAsStream("/y2019/round1a/pylons/input1_out.txt");
        String expectedOutput = IOUtils.toString(expectedOutputAsStream, StandardCharsets.UTF_8);

        assertEquals(expectedOutput, resultString);
    }

    @Test
    public void testSolveStreams_Reverted() throws Exception {
        InputStream inputAsStream = this.getClass().getResourceAsStream("/y2019/round1a/pylons/input_reverted.txt");
        File resultFile = new File("input_reverted_out" + System.currentTimeMillis() + ".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        try (PrintStream printStream = new PrintStream(fileOutputStream)) {
            Solution.solveStreams(inputAsStream, printStream);
        }

        String resultString = FileUtils.readFileToString(resultFile, UTF_8);
        InputStream expectedOutputAsStream = this.getClass().getResourceAsStream("/y2019/round1a/pylons/input_reverted_out.txt");
        String expectedOutput = IOUtils.toString(expectedOutputAsStream, StandardCharsets.UTF_8);

        assertEquals(expectedOutput, resultString);
    }

    @Ignore
    @Test
    public void testPerformance() {
        final int testCasesMax = 100;
        int rowCount = 50;
        int colCount = 150;
        for (int i = 0; i < testCasesMax; i++) {
            Solution.Pylons pylons = new Solution.Pylons(rowCount, colCount);
            List<Solution.Coord> coords = pylons.solveOne();
            assertCoords(rowCount, colCount, coords);
        }
    }

    @Ignore
    @Test
    public void testVariations() {
        int rowCount = 20;
        int colCount = 20;
        for (int curRow = 2; curRow < rowCount; curRow++) {
            for (int curCol = 2; curCol < colCount; curCol++) {
                Solution.Pylons pylons = new Solution.Pylons(curRow, curCol);
                List<Solution.Coord> coords = pylons.solveOne();
                if (curRow * curCol < 10) {
                    try {
                        assertEquals(0, coords.size());
                    } catch (Throwable e) {
                        throw new IllegalStateException();
                    }
                } else {
                    assertCoords(curRow, curCol, coords);
                }
            }
        }
    }
}