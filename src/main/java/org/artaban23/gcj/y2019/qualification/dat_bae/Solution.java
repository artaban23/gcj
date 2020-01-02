package org.artaban23.gcj.y2019.qualification.dat_bae;

import java.io.*;
import java.util.*;

/**
 * Solution for Dat Bae task from qualification round of 2019
 */
public class Solution {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;

        solveStreams(inputStream, outputStream);
    }

    static void solveStreams(InputStream inputStream, OutputStream outputStream) {
        InputReader ir = new InputReader(inputStream);
        OutputWriter os = new OutputWriter(outputStream);
        int t = ir.readInt();

        DatBae datBae = new DatBae();
        datBae.setVerifier(new GcpVerifier(ir, os));

        for (int i = 0; i < t; i++) {
            int n = ir.readInt();
            int b = ir.readInt();
            int f = ir.readInt();
            int[] solve = datBae.solve(n, b, f);
            os.printLine(solve);
            os.flush();
            if (ir.readInt() != 1) {
                return;
            }
        }

        os.close();
    }

    static String formStringResult(int[] solve) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < solve.length; j++) {
            if (solve[j] == -1) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(j);
            }

        }
        return sb.toString();
    }

    static class DatBae {

        //attempts num
        private static final int F = 5;

        private Verifier verifier;

        //NEW
        int[][] prepareCandidates(int n) {
            final int attempts = 5;
            int curInd = 0;
            int[][] res = new int[attempts][];
            int val = 0;
            for (int i = 0; i < attempts; i++) {
                res[i] = new int[n];
            }
            while (curInd < n) {
                String s = Integer.toBinaryString(32 + val).substring(1);
                for (int i = 0; i < s.length(); i++) {
                    int numericValue = Character.getNumericValue(s.charAt(i));
                    res[i][curInd] = numericValue;
                }
                val = (val + 1) % 32;
                curInd++;
            }
            return res;
        }

        int[] solve(int n, int b, int f) {
            int[][] stampToSendArr = prepareCandidates(n);
            int[][] recvStamps = new int[F][];
            for (int i = 0; i < F; i++) {
                recvStamps[i] = sendToVerify(stampToSendArr[i]);
            }
            for (int i = 0; i < f - F; i++) {
                sendToVerify(stampToSendArr[0]);
            }

            List<Integer> resList = verifyAllNodes(stampToSendArr, recvStamps, n, b);
            return resList.stream().mapToInt(i -> i).toArray();
        }

        List<Integer> verifyAllNodes(int[][] sentBits, int[][] receivedBits, int n, int b) {
            int foundBits = 0;
            List<Integer> res = new ArrayList<>();

            for (int i = 0; i < n && foundBits < b; i++) {
                int brokenBitsNum = verifyOneStamp(sentBits, receivedBits, i, i - foundBits, b, foundBits);
                if (brokenBitsNum != 0) {
                    foundBits += brokenBitsNum;

                    int curBit = i;
                    while (curBit < i + brokenBitsNum) {
                        res.add(curBit);
                        curBit++;
                    }
                    i += brokenBitsNum;
                }
            }

            return res;
        }

        void setVerifier(Verifier verifier) {
            this.verifier = verifier;
        }

        //NEW
        int verifyOneStamp(int[][] sentBits, int[][] receivedStamps, int sentInd, int recInd, int b, int foundBits) {
            if (recInd >= receivedStamps[0].length) {
                return b - foundBits;
            }
            int[] sentNumAr = new int[F];
            int[] recvNumAr = new int[F];
            for (int i = 0; i < F; i++) {
                sentNumAr[i] = sentBits[i][sentInd];
                recvNumAr[i] = receivedStamps[i][recInd];
            }
            int sentNum = convertBinaryArrayToInt(sentNumAr);
            int recvNum = convertBinaryArrayToInt(recvNumAr);
            if (sentNum == recvNum) {
                return 0;
            } else if (recvNum > sentNum) {
                return recvNum - sentNum;
            } else {
                return 32 - sentNum + recvNum;
            }
        }

        static int convertBinaryArrayToInt(int[] binAr) {
            StringBuilder s = new StringBuilder();
            for (int i : binAr) {
                s.append(i);
            }
            return Integer.parseInt(s.toString(), 2);
        }

        int[] sendToVerify(int[] ar) {
            return verifier.verify(ar);
        }
    }

    interface Verifier {
        int[] verify(int[] ar);
    }

    static class GcpVerifier implements Verifier {

        InputReader is;
        OutputWriter os;

        GcpVerifier(InputReader is, OutputWriter os) {
            this.is = is;
            this.os = os;
        }

        @Override
        public int[] verify(int[] ar) {
            os.printLine(arToString(ar));
            os.flush();
            String s = is.readString();
            return stringToAr(s);
        }

        public OutputWriter getOs() {
            return os;
        }

        static String arToString(int[] ar) {
            StringBuilder sb = new StringBuilder();
            for (int i : ar) {
                sb.append(i);
            }

            return sb.toString();
        }

        static int[] stringToAr(String str) {
            int[] ar = new int[str.length()];
            char[] chars = str.toCharArray();
            for (int i = 0; i < str.length(); i++) {
                ar[i] = Character.getNumericValue(chars[i]);
            }

            return ar;
        }

    }

    static class SimpleVerifier implements Verifier {
        private Set<Integer> brokenBits;

        SimpleVerifier(int... brokenBits) {
            this.brokenBits = new HashSet<>();
            for (int i : brokenBits) {
                this.brokenBits.add(i);
            }
        }

        public int[] verify(int[] ar) {
            int[] res = new int[ar.length - brokenBits.size()];
            for (int i = 0, y = 0; i < ar.length; i++) {
                if (brokenBits.contains(i)) {
                    continue;
                }
                res[y] = ar[i];

                y++;
            }

            return res;
        }
    }

    /**
     * I/O Utility classes - specific to GCJ interactive problems
     */
    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(int[] objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(String str) {
            writer.println(str);
        }

        public void printLine(int[] objects) {
            print(objects);
            writer.println();
        }

        public void printLine(char[] array) {
            writer.println(array);
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }

    }
}