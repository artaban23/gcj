package org.artaban23.gcj.y2019.round1a;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Solution for Pylons task 2019 round 1A.
 * Optimized greedy algorithm. Expected  complexity ~ O(r*c^2)
 * <br/>
 * Algorithm main idea : row by row find a cell with "least" amount of neighbors (and as central as possible).
 */
public class Solution {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        solveStreams(inputStream, System.out);
    }

    static void solveStreams(InputStream is, PrintStream os) {
        Scanner sc = new Scanner(is);
        int testCount = sc.nextInt();

        solveAll(testCount, sc, os);
    }

    private static void solveAll(int testCnt, Scanner sc, PrintStream os) {
        for (int i = 0; i < testCnt; i++) {
            int r = sc.nextInt();
            int c = sc.nextInt();

            Pylons solution = new Pylons(r, c);
            List<Coord> coords = solution.solveOne();
            if (coords.isEmpty()) {
                os.println("Case #" + (i + 1) + ": IMPOSSIBLE");
            } else {
                os.println("Case #" + (i + 1) + ": " + "POSSIBLE");
                for (Coord coord : coords) {
                    os.println((coord.getRow() + 1) + " " + (coord.getCol() + 1));
                }
            }
        }
    }

    static int getCenter(int maxSize) {
        return maxSize / 2;
    }

    static class Pylons {
        private int rowCount;
        private int colCount;


        Pylons(int rowCount, int colCount) {
            this.rowCount = rowCount;
            this.colCount = colCount;
        }

        List<Coord> solveOne() {
            if (rowCount * colCount < 10 || rowCount == 1 || colCount == 1) {
                return new ArrayList<>();
            }

            if (colCount < 4) {
                Pylons pylons = new Pylons(colCount, rowCount);
                List<Coord> coords = pylons.solveOne();
                return reverseCoords(coords);
            }

            List<List<Coord>> rows = initializeGalaxy();

            Cache cache = new Cache(rowCount, colCount);
            List<Coord> path = new LinkedList<>();

            List<Coord> row = rows.get(getCenter(rowCount));
            Coord curPoint = row.remove(getCenter(colCount));
            path.add(curPoint);
            move(curPoint, rows, cache);

            while (path.size() < rowCount * colCount) {
                Coord nextMove = getNextMove(curPoint, rows, cache, path);
                if (nextMove == null) {
                    throw new IllegalStateException("error path: " + path + "\n. Remains: "
                            + rows.stream().flatMap(List::stream).collect(Collectors.toList()));
                }
                move(nextMove, rows, cache);
                path.add(nextMove);
                curPoint = nextMove;
            }

            return path;
        }

        static List<Coord> reverseCoords(List<Coord> coords) {
            List<Coord> newCoordList = new ArrayList<>();
            for (Coord oldCoord : coords) {
                Coord newCoord = reverseCoord(oldCoord);
                newCoordList.add(newCoord);
            }
            return newCoordList;
        }

        static Coord reverseCoord(Coord oldCoord) {
            return new Coord(oldCoord.colCount, oldCoord.rowCount, oldCoord.colCount - oldCoord.col - 1, oldCoord.row);
        }

        private List<List<Coord>> initializeGalaxy() {
            List<List<Coord>> rows = new ArrayList<>();
            for (int i = 0; i < rowCount; i++) {
                LinkedList<Coord> row = new LinkedList<>();
                for (int j = 0; j < colCount; j++) {
                    Coord coord = new Coord(rowCount, colCount, i, j);
                    row.add(coord);
                }
                rows.add(row);
            }
            return rows;
        }

        private Coord getNextMove(Coord from, List<List<Coord>> rows, Cache cache, List<Coord> path) {
            int curRow = incrementCurRow(from.getRow(), from.rowCount);
            Coord cur;
            int cnt = 0;
            do {
                if (path.size() > rowCount * colCount - 5) { // solution workaround f.e. for 7x9 case
                    List<Coord> collect = rows.stream().flatMap(List::stream).collect(Collectors.toList());
                    cur = findBestCoord(from, cache, collect);
                } else {
                    List<Coord> row = rows.get(curRow);
                    cur = findBestCoord(from, cache, row);
                }
                if (cur != null) {
                    break;
                } else {
                    curRow = incrementCurRow(curRow, from.rowCount);
                }
                cnt++;
            } while (cnt < colCount * rowCount);

            if (cnt == colCount * rowCount) {
                return null;
            }

            return cur;
        }

        private Coord findBestCoord(Coord from, Cache cache, List<Coord> row) {
            Coord cur = null;
            for (Coord c : row) {
                if (from.isValidDestination(c)) {
                    if (cur == null) {
                        cur = c;
//                                curWeight = getCoordWeight(c, cache);
                    } else {
                        int curWeight = cache.getCoordWeight(cur);
                        int newWeight = cache.getCoordWeight(c);
                        if (newWeight > curWeight || (newWeight == curWeight && c.distToCenter < cur.distToCenter)) {
                            cur = c;
//                            curWeight = newWeight;
                        }
                    }
                }
            }
            return cur;
        }

        private int incrementCurRow(int row, int rowCount) {
            return (row + 1) % rowCount;
        }

        private void move(Coord dest, List<List<Coord>> rows, Cache cache) {
            List<Coord> row = rows.get(dest.getRow());
            row.remove(dest);
            cache.markVisited(dest);
        }
    }

    /**
     * Coordinate in the galaxy
     */
    static class Coord {
        int row;
        int col;
        int rowCount;
        int colCount;
        int distToCenter;

        Coord(int rowCount, int colCount, int row, int col) {
            this.row = row;
            this.col = col;
            this.rowCount = rowCount;
            this.colCount = colCount;
            int rDist = getDist(row, rowCount);
            int cDist = getDist(col, colCount);
            this.distToCenter = rDist + cDist;
        }

        private int getDist(int pos, int poxMax) {
            if (poxMax % 2 == 1) {
                return Math.abs(poxMax / 2 + 1 - pos);
            } else {
                return Math.min(Math.abs(poxMax / 2 - pos), Math.abs((poxMax) / 2 + 1 - pos));
            }
        }

        boolean isValidDestination(Coord destination) {
            return isValidDestination(row, col, destination.row, destination.col);
        }

        static boolean isValidDestination(int r1, int c1, int r2, int c2) {
            return r1 != r2 && c1 != c2 && r1 - c1 != r2 - c2 && r1 + c1 != r2 + c2;
        }

        int getRow() {
            return row;
        }

        int getCol() {
            return col;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Coord)) return false;
            Coord that = (Coord) obj;
            return this.col == that.col && this.getRow() == that.row;
        }

        @Override
        public String toString() {
            return "{" + row + ", " + col + "}";
        }
    }

    static class Cache {
        private Map<Integer, Integer> colCache = new HashMap<>();
        private Map<Integer, Integer> rowCache = new HashMap<>();
        private Map<Integer, Integer> diagMinusCache = new HashMap<>();
        private Map<Integer, Integer> diagSumCache = new HashMap<>();

        Cache(int rowCount, int colCount) {
            for (int i = 0; i < colCount; i++) {
                colCache.put(i, rowCount);
            }
            for (int i = 0; i < rowCount; i++) {
                rowCache.put(i, colCount);
            }
            // SUMS
            for (int i = 0; i < colCount; i++) {
                int v = Math.min(Math.min(i + 1, rowCount), colCount);
                diagSumCache.put(i, v);
            }
            for (int i = 1; i < rowCount; i++) {
                int v = rowCount - i;
                v = Math.min(Math.min(v, rowCount), colCount);
                diagSumCache.put(i + colCount - 1, v);
            }
            // MINUSES
            for (int i = 0; i < colCount; i++) {
                int v = colCount - i;
                v = Math.min(Math.min(v, rowCount), colCount);
                diagMinusCache.put(i * -1, v);
            }
            for (int i = rowCount - 1; i > 0; i--) {
                int v = rowCount - i;
                v = Math.min(Math.min(v, rowCount), colCount);
                diagMinusCache.put(i, v);
            }

        }

        void markVisited(Coord coord) {
            Integer col = colCache.get(coord.getCol());
            colCache.put(coord.getCol(), --col);
            Integer row = rowCache.get(coord.getRow());
            rowCache.put(coord.getRow(), --row);

            int sum = coord.getRow() + coord.getCol();
            Integer oldVal = diagSumCache.get(sum);
            diagSumCache.put(sum, --oldVal);

            int minus = coord.getRow() - coord.getCol();
            Integer oldValMin = diagMinusCache.get(minus);
            diagMinusCache.put(minus, --oldValMin);
        }

        int getCoordWeight(Coord coord) {
            int colWeight = colCache.get(coord.col);
            int rowWeight = rowCache.get(coord.row);
            int diagSumWeight = diagSumCache.get(coord.getRow() + coord.getCol());
            int diagMinusWeight = diagMinusCache.get(coord.getRow() - coord.getCol());

            return colWeight + rowWeight + diagMinusWeight + diagSumWeight;
        }

        Map<Integer, Integer> getRowCache() {
            return rowCache;
        }

        Map<Integer, Integer> getColCache() {
            return colCache;
        }

        Map<Integer, Integer> getDiagMinusCache() {
            return diagMinusCache;
        }

        Map<Integer, Integer> getDiagSumCache() {
            return diagSumCache;
        }
    }

}
