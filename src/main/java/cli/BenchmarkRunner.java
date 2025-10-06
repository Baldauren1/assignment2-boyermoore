package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BenchmarkRunner {
    public static void main(String[] args) throws IOException {
        final int[] ns = new int[]{100, 1000, 10000, 100000};
        final int warmups = 3;
        final int trials = 10;
        final long baseSeed = 123456L;
        final boolean verifyCandidate = !Arrays.asList(args).contains("--no-verify");

        // cache generated arrays by seed (so each algorithm uses same data for fairness)
        Map<Long, int[]> baseCache = new HashMap<>();

        // collect per-(algo,n) times
        Map<String, List<Long>> summary = new HashMap<>();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("metrics_boyer_ns.csv"))) {
            bw.write("algo,n,trial,time_ns,comparisons,updates,array_accesses,majority\n");

            for (int n : ns) {
                System.out.println("Running n = " + n);
                for (int t = 0; t < warmups + trials; t++) {
                    long seed = baseSeed + t + (n * 131542391L);

                    // generate or reuse base array
                    int[] base = baseCache.computeIfAbsent(seed, s -> {
                        Random rnd = new Random(s);
                        int[] arr = new int[n];
                        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(10);
                        return arr;
                    });

                    boolean isWarm = t < warmups;
                    int trialIndex = isWarm ? -1 : (t - warmups);

                    // run Boyer-Moore
                    int[] arr = base.clone();
                    PerformanceTracker tracker = new PerformanceTracker();
                    BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(tracker, verifyCandidate);

                    long t0 = System.nanoTime();
                    Integer maj = bm.findMajority(arr);
                    long t1 = System.nanoTime();

                    long dt = t1 - t0;

                    if (!isWarm) {
                        String majStr = (maj == null ? "None" : String.valueOf(maj));
                        String line = String.format("boyermoore,%d,%d,%d,%d,%d,%d,%s\n",
                                n, trialIndex, dt, tracker.getComparisons(), tracker.getUpdates(), tracker.getArrayAccesses(), majStr);
                        bw.write(line);
                        String key = "boyermoore#" + n;
                        summary.computeIfAbsent(key, k -> new ArrayList<>()).add(dt);
                    }
                }
                // flush once per n (reduces disk I/O)
                bw.flush();
            }
        }

        // write summary CSV
        try (BufferedWriter sb = new BufferedWriter(new FileWriter("metrics_summary_ns.csv"))) {
            sb.write("algo,n,avg_ns,median_ns,std_ns,count\n");
            for (Map.Entry<String, List<Long>> e : summary.entrySet()) {
                String key = e.getKey();
                List<Long> vals = e.getValue();
                Collections.sort(vals);
                int count = vals.size();
                double avg = vals.stream().mapToLong(Long::longValue).average().orElse(0.0);
                double median = (count % 2 == 1) ? vals.get(count/2) : ((vals.get(count/2 - 1) + vals.get(count/2)) / 2.0);
                double mean = avg;
                double sumsq = 0.0;
                for (Long v : vals) sumsq += (v - mean) * (v - mean);
                double std = Math.sqrt(sumsq / (count > 1 ? count : 1));
                int sep = key.indexOf('#');
                String algo = key.substring(0, sep);
                int n = Integer.parseInt(key.substring(sep + 1));
                sb.write(String.format("%s,%d,%.2f,%.2f,%.2f,%d\n", algo, n, avg, median, std, count));
            }
            sb.flush();
        }

        System.out.println("Done. metrics_boyer_ns.csv and metrics_summary_ns.csv generated.");
    }
}
