package org.artaban23.gcj.y2019.round1a.alien_rhyme;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * Solution for Alien Rhyme task 2019 round 1A.
 * <br/>
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
            int wordCount = sc.nextInt();
            List<String> input = new ArrayList<>();
            for (int j = 0; j < wordCount; j++) {
                input.add(sc.next());
            }

            AlienRhyme solution = new AlienRhyme(input);
            int res = solution.solveOne();
            os.println("Case #" + (i + 1) + ": " + res);
        }
    }


    static class AlienRhyme {
        List<String> reversedInput = new ArrayList<>();
        HashMap<String, Integer> stats = new HashMap<>();
        TreeSet<String> prefixes = new TreeSet<>();

        AlienRhyme(List<String> input) {
            for (String s : input) {
                reversedInput.add(new StringBuilder(s).reverse().toString());
            }
//            Collections.sort(reversedInput);
//            System.err.println("REV INP: " + reversedInput);

            HashMap<String, Integer> localStat = new HashMap<>();
            for (String s : reversedInput) {
                for (int i = 1; i <= s.length(); i++) {
                    String prefix = s.substring(0, i); // aka suffix for pure input
                    if (localStat.containsKey(prefix)) {
                        Integer wordsCount = localStat.get(prefix);
                        localStat.put(prefix, ++wordsCount);
                    } else {
                        localStat.put(prefix, 1);
                    }
                }
            }

            localStat.keySet().stream().filter(key -> localStat.get(key) > 1).forEach(key -> stats.put(key,
                    localStat.get(key)));
//            System.err.println("STAT: " + new TreeMap(stats));

            prefixes.addAll(stats.keySet());
        }

        int solveOne() {
            int result = 0;
            for (String pref : prefixes.descendingSet()) {
                Integer val = stats.get(pref);
                if (val > 1) {
                    result += 2;

                    List<String> strings = toPrefixes(pref);
                    for (String subPrefix : strings) {
                        stats.computeIfPresent(subPrefix, (k, v) -> v - 2);
                    }

                }

            }
            return result;
        }

        List<String> toPrefixes(String s) {
            List<String> res = new ArrayList<>();
            for (int i = 1; i <= s.length(); i++) {
                String prefix = s.substring(0, i); // aka suffix for pure input
                res.add(prefix);
            }
            return res;
        }
    }

}
