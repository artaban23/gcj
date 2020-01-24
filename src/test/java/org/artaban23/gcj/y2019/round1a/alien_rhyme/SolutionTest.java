package org.artaban23.gcj.y2019.round1a.alien_rhyme;

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
import java.util.List;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SolutionTest {

    @Test
    public void testInput_2_0() {

        ArrayList<String> input = new ArrayList<>();
        input.add("L");
        input.add("P");

        Solution.AlienRhyme alienRhyme = new Solution.AlienRhyme(input);
        int res = alienRhyme.solveOne();
        assertEquals(0, res);

    }

    @Test
    public void testInput_2_2() {

        ArrayList<String> input = new ArrayList<>();
        input.add("TARPOL");
        input.add("PROL");

        Solution.AlienRhyme alienRhyme = new Solution.AlienRhyme(input);
        int res = alienRhyme.solveOne();
        assertEquals(2, res);

    }

    @Test
    public void testInput_3_2() {
        ArrayList<String> input = new ArrayList<>();
        input.add("TARPOR");
        input.add("PROL");
        input.add("TARPRO");

        Solution.AlienRhyme alienRhyme = new Solution.AlienRhyme(input);
        int res = alienRhyme.solveOne();
        assertEquals(0, res);

    }

    @Test
    public void testInput_4_2() {
        ArrayList<String> input = new ArrayList<>();
        input.add("PI");
        input.add("HI");
        input.add("WI");
        input.add("FI");

        Solution.AlienRhyme alienRhyme = new Solution.AlienRhyme(input);
        int res = alienRhyme.solveOne();
        assertEquals(2, res);

    }

    @Test
    public void testInput_6_6() {
        ArrayList<String> input = prepareInput_6_6();

        Solution.AlienRhyme alienRhyme = new Solution.AlienRhyme(input);
        int res = alienRhyme.solveOne();
        assertEquals(6, res);
    }

    @Test
    public void testInput_6_6_increment() {
        ArrayList<String> input = new ArrayList<>();
        input.add("A");
        input.add("QA");
        input.add("AQA");
        input.add("ZAQA");
        input.add("RZAQA");
        input.add("IRZAQA");

        Solution.AlienRhyme alienRhyme = new Solution.AlienRhyme(input);
        int res = alienRhyme.solveOne();
        assertEquals(6, res);
    }

    @Test
    public void testInput_8_6() {
        ArrayList<String> input = prepareInput_6_6();
        input.add("M");
        input.add("PM");

        Solution.AlienRhyme alienRhyme = new Solution.AlienRhyme(input);
        int res = alienRhyme.solveOne();
        assertEquals(6, res);
    }

    private ArrayList<String> prepareInput_6_6() {
        ArrayList<String> input = new ArrayList<>();
        input.add("CODEJAM");
        input.add("JAM");
        input.add("HAM");
        input.add("NALAM");
        input.add("HUM");
        input.add("NOLOM");
        return input;
    }

    @Test
    public void testPerformance() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            List<String> input = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < 50; k++) {
                    int i1 = 'A' + random.nextInt(26);
                    sb.append((char) i1);
                }
                input.add(sb.toString());
            }
            System.out.println(input);
            Solution.AlienRhyme alienRhyme = new Solution.AlienRhyme(input);
            System.out.println("Cache is built");
            int res = alienRhyme.solveOne();
            System.out.println("Res is: " + res);
            assertTrue(res >= 0);
        }
    }

    @Test
    public void testSolveStreams_simple() throws Exception {
        InputStream inputAsStream = this.getClass().getResourceAsStream("/y2019/round1a/alien_rhyme/input1.txt");
        File resultFile = new File("input1_out" + System.currentTimeMillis() + ".txt");
        FileOutputStream fileOutputStream = new FileOutputStream(resultFile);
        try (PrintStream printStream = new PrintStream(fileOutputStream)) {
            org.artaban23.gcj.y2019.round1a.alien_rhyme.Solution.solveStreams(inputAsStream, printStream);
        }

        String resultString = FileUtils.readFileToString(resultFile, UTF_8);
        InputStream expectedOutputAsStream = this.getClass().getResourceAsStream("/y2019/round1a/alien_rhyme/input1_out.txt");
        String expectedOutput = IOUtils.toString(expectedOutputAsStream, StandardCharsets.UTF_8);

        assertEquals(expectedOutput, resultString);
    }
}
