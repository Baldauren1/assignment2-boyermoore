package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Benchmark runner for Boyer-Moore. Writes CSV metrics.
 * CSV: algo,n,trial,time_ns,comparisons,updates,array_accesses,majority
 */
public class BenchmarkRunner {
    public static void main(String[] args) throws IOException {
        final int[] ns = new int[]{100, 1000, 10000, 100000};
        final int warmups = 3;
        final int trials = 10;
        final long baseSeed = 123456L;

        try (FileWriter fw = new FileWriter("metrics_boyer_ns.csv")) {
            fw.write("algo,n,trial,time_ns,comparisons,updates,array_accesses,majority\n");
            for (int n : ns) {
                System.out.println("Running n = " + n);
                for (int t = 0; t < warmups + trials; t++) {
                    long seed = baseSeed + t + (n * 131542391L);
                    Random rnd = new Random(seed);

                    int[] arr = new int[n];
                    for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(10); // values 0..9

                    boolean isWarm = t < warmups;
                    int trialIndex = isWarm ? -1 : (t - warmups);

                    PerformanceTracker tracker = new PerformanceTracker();
                    BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(tracker);

                    long t0 = System.nanoTime();
                    Integer maj = bm.findMajority(arr);
                    long t1 = System.nanoTime();

                    long dt = t1 - t0;
                    if (!isWarm) {
                        String majStr = (maj == null ? "None" : String.valueOf(maj));
                        fw.write(String.format("boyermoore,%d,%d,%d,%d,%d,%d,%s\n",
                                n, trialIndex, dt, tracker.getComparisons(), tracker.getUpdates(), tracker.getArrayAccesses(), majStr));
                    }
                    fw.flush();
                }
            }
        }
        System.out.println("Done. metrics_boyer_ns.csv generated.");
    }
}

