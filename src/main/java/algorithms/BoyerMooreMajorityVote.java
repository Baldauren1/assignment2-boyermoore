package algorithms;

import metrics.PerformanceTracker;

/**
 * Boyer-Moore majority vote with batched metric updates and optional verification.
 * Constructor: new BoyerMooreMajorityVote(new PerformanceTracker()).
 */
public class BoyerMooreMajorityVote {
    private final PerformanceTracker tracker;
    private final boolean verify;

    public BoyerMooreMajorityVote(PerformanceTracker tracker) {
        this(tracker, true);
    }

    public BoyerMooreMajorityVote(PerformanceTracker tracker, boolean verify) {
        if (tracker == null) throw new IllegalArgumentException("tracker must not be null");
        this.tracker = tracker;
        this.verify = verify;
    }

    /**
     * Returns majority element (Integer) if exists; otherwise returns null.
     * Throws IllegalArgumentException for null or empty array (matches tests).
     */
    public Integer findMajority(int[] arr) {
        if (arr == null || arr.length == 0) throw new IllegalArgumentException("array must be non-empty");

        // local counters to avoid hot-path Tracker calls
        long localComparisons = 0;
        long localUpdates = 0;

        int candidate = 0;
        int count = 0;

        for (int v : arr) {
            // we account 1 comparison per element for simplicity
            localComparisons++;
            if (count == 0) {
                candidate = v;
                count = 1;
                localUpdates++; // candidate assignment/initialization
            } else if (v == candidate) {
                count++;
                localUpdates++;
            } else {
                count--;
            }
        }

        // flush local counters to global tracker once
        tracker.addComparisons(localComparisons);
        tracker.addUpdates(localUpdates);

        if (!verify) {
            return candidate;
        }

        // verification pass (counts array accesses)
        long localArrayAccesses = 0;
        long occ = 0;
        for (int v : arr) {
            localArrayAccesses++;
            if (v == candidate) occ++;
        }
        tracker.addArrayAccesses(localArrayAccesses);

        if (occ > arr.length / 2) {
            return candidate;
        } else {
            return null;
        }
    }
}
