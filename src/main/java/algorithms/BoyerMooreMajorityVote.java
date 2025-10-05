package algorithms;

import metrics.PerformanceTracker;

/**
 * Boyer-Moore Majority Vote Algorithm
 * Finds an element that appears > n/2 times (if exists).
 * Returns Integer (candidate) or null if none.
 */
public class BoyerMooreMajorityVote {

    private final PerformanceTracker tracker;

    public BoyerMooreMajorityVote(PerformanceTracker tracker) {
        this.tracker = tracker;
    }

    public Integer findMajority(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        // Phase 1: find candidate
        int candidate = arr[0];
        int count = 1;
        tracker.incrementArrayAccesses(1); // initial read

        for (int i = 1; i < arr.length; i++) {
            tracker.incrementArrayAccesses(1);
            tracker.incrementComparisons();
            if (arr[i] == candidate) {
                count++;
                tracker.incrementUpdates();
            } else {
                count--;
                tracker.incrementUpdates();
                if (count == 0) {
                    candidate = arr[i];
                    count = 1;
                    tracker.incrementUpdates();
                }
            }
        }

        // Phase 2: verify
        int freq = 0;
        for (int x : arr) {
            tracker.incrementArrayAccesses(1);
            tracker.incrementComparisons();
            if (x == candidate) freq++;
        }

        if (freq > arr.length / 2) return candidate;
        else return null;
    }
}

