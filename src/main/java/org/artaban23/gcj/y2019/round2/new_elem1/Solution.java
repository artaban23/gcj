package org.artaban23.gcj.y2019.round2.new_elem1;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Solution for Alien Rhyme task 2019 round 1A.
 * <br/>
 *
 * <b>Warning</b> Looks like there is a floating point bug. Task is not solved.
 */
public class Solution {

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        solveStreams(inputStream, System.out);
    }

    static void solveStreams(InputStream is, PrintStream os) {
        Scanner sc = new Scanner(is);
        solveAll(sc, os);

        os.close();
    }

    private static void solveAll(Scanner sc, PrintStream os) {
        int testCnt = sc.nextInt();
        for (int i = 0; i < testCnt; i++) {
            int molCount = sc.nextInt();
            long[][] ar = new long[molCount][2];
            for (int j = 0; j < molCount; j++) {
                ar[j][0] = sc.nextInt();
                ar[j][1] = sc.nextInt();
            }

            NewElement newElement = new NewElement(ar);
            int res = newElement.getAllRelations().size() + 1;
            os.println("Case #" + (i + 1) + ": " + res);
        }
    }

    static class NewElement {

        private List<Molecule> moleculeList;

        NewElement(List<Molecule> molecules) {
            moleculeList = new ArrayList<>(molecules);
        }

        NewElement(long[][] molecules) {
            moleculeList = new ArrayList<>();
            for (long[] ar : molecules) {
                moleculeList.add(new Molecule(ar[0], ar[1]));
            }
        }

        int solveOne() {
            return solveInner(new HashSet<>(moleculeList), new ArrayList<>(), Double.MAX_VALUE,
                    -1);
        }

        int solveInner(Set<Molecule> molecules, List<Molecule> curPath, double relationLessThan,
                       double relationMoreThan) {
            if (molecules.size() == 0) {
//                System.err.println(curPath + " with  : [" + relationMoreThan + " : " + relationLessThan + "]");
                return 1;
            }

            int result = 0;

            for (Molecule curMol : molecules) {
                if (curPath.isEmpty()) {
                    result = getResult(molecules, curPath, relationLessThan, relationMoreThan, result, curMol);
                } else {
                    Molecule lastMol = curPath.get(curPath.size() - 1);
                    if (lastMol.isAlwaysSmallerThan(curMol)) {
                        result = getResult(molecules, curPath, relationLessThan, relationMoreThan, result, curMol);
                    } else if (lastMol.isAlwaysBiggerThan(curMol)) {
                        return 0;
                    } else {
                        double weightRel = lastMol.getWeightRelation(curMol);
                        {
                            double newLessThen = relationLessThan;
                            double newMoreThen = relationMoreThan;
                            if (lastMol.codAmount < curMol.codAmount) {
                                newLessThen = Math.min(weightRel, relationLessThan);
                            } else {
                                newMoreThen = Math.max(weightRel, relationMoreThan);
                            }
                            if (newMoreThen >= newLessThen) {
                                return 0;
                            } else {
                                result = getResult(molecules, curPath, newLessThen, newMoreThen, result, curMol);
                            }
                        }
                    }
                }
            }

            return result;
        }

        SortedSet<Double> getAllRelations() {
            TreeSet<Double> res = new TreeSet<>();
            for (int curMolInd = 0; curMolInd < moleculeList.size(); curMolInd++) {
//                List<Double> doubles = new ArrayList<>();
                for (int j = curMolInd + 1; j < moleculeList.size(); j++) {
                    Molecule curMol = moleculeList.get(curMolInd);
                    Molecule newMol = moleculeList.get(j);
                    double weightRelation = curMol.getWeightRelation(newMol);
                    if (weightRelation != 0) {
                        res.add(weightRelation);
                    }

                }
            }

            TreeSet<Double> tempRes = new TreeSet<>(res);
            tempRes.add(1e9-1);

            Double prev = 0d;
            for (Double d : tempRes) {
                    double diff = d - prev;
//                    if (Integer.MAX_VALUE * d - Integer.MAX_VALUE*prev >= 1) {
                    if (Integer.MAX_VALUE * diff < 1) {
//                        tempRes.add(prev);
//                        tempRes.add(d);
                        res.remove(d);
                        prev = d;
                        System.err.println("wrong diff: " + diff + "(" + d + " " + prev + ")");
//                    } else {

                    }

            }

            return res;

        }


        private int getResult(Set<Molecule> molecules, List<Molecule> curPath, double relationLessThen, double relationMoreThan, int result, Molecule curMol) {
            HashSet<Molecule> newMolSet = new HashSet<>(molecules);
            newMolSet.remove(curMol);
            curPath.add(curMol);
            result += solveInner(newMolSet, curPath, relationLessThen, relationMoreThan);
            removeLast(curPath);
            return result;
        }

        private void removeLast(List<Molecule> curPath) {
            curPath.remove(curPath.size() - 1);
        }

        List<Molecule> getMoleculeList() {
            return moleculeList;
        }
    }

    static class Molecule {
        private long codAmount;
        private long jamAmount;

        Molecule(long codAmount, long jamAmount) {
            this.codAmount = codAmount;
            this.jamAmount = jamAmount;
        }

        boolean isAlwaysSmallerThan(Molecule thatMol) {
            return this.codAmount <= thatMol.codAmount && this.jamAmount <= thatMol.jamAmount;
        }

        boolean isAlwaysBiggerThan(Molecule thatMol) {
            return this.codAmount >= thatMol.codAmount && this.jamAmount >= thatMol.jamAmount;
        }

        double getWeightRelation(Molecule molecule) {
            double codDif = molecule.codAmount - this.codAmount;
            double jamDif = this.jamAmount - molecule.jamAmount;

            if (codDif * jamDif < 0 || jamDif == 0) {
                return 0;
            }

            return codDif / jamDif;
        }

        @Override
        public String toString() {
            return "{" +
                    +codAmount +
                    ", " + jamAmount +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Molecule molecule = (Molecule) o;
            return codAmount == molecule.codAmount &&
                    jamAmount == molecule.jamAmount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(codAmount, jamAmount);
        }
    }

}