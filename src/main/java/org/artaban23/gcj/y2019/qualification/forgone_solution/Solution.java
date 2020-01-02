package org.artaban23.gcj.y2019.qualification.forgone_solution;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Solution for Forgone solution task from qualification round of 2019
 */
public class Solution {

    private Scanner sc;
    private int caseNum;

    private Solution(Scanner sc, int caseNum) {
        this.sc = sc;
        this.caseNum = caseNum;
    }

    Solution() {
        this(null, 0);
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        solveStreams(inputStream, outputStream);
    }

    static void solveStreams(InputStream is, OutputStream os) throws IOException {
        Scanner sc = new Scanner(is);
        int testCount = sc.nextInt();

        Solution foregoneSolution = new Solution(sc, testCount);
        foregoneSolution.solveAll();

        os.close();
    }

    private void solveAll() {
        for (int i = 0; i < caseNum; i++) {
            long l = sc.nextLong();
            List<Long> longs = solveOne(l);
            System.out.println("Case #" + (i + 1) + ": " + longs.get(0) + " " + longs.get(1));
        }
    }

    List<Long> solveOne(final long l) {
        long curLong = l;
        long first = 0;
        long second = 0;
        long mul = 1;
        while (curLong > 0) {
            long v = curLong % 10;
            if (v == 4) {
                first += 3 * mul;
                second += mul;
            } else {
                first += v * mul;
            }

            mul *= 10;
            curLong = curLong / 10;
        }
        List<Long> res = new ArrayList<>();
        res.add(first);
        res.add(second);

        return res;
    }
}
