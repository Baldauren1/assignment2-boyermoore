package metrics;

/**
 * Simple performance tracker: comparisons, updates, array accesses.
 */
public class PerformanceTracker {
    private long comparisons = 0;
    private long updates = 0;
    private long arrayAccesses = 0;

    public void incrementComparisons() { comparisons++; }
    public void incrementUpdates() { updates++; }
    public void incrementArrayAccesses() { arrayAccesses++; }
    public void incrementArrayAccesses(int n) { arrayAccesses += n; }

    public void reset() {
        comparisons = updates = arrayAccesses = 0;
    }

    public long getComparisons() { return comparisons; }
    public long getUpdates() { return updates; }
    public long getArrayAccesses() { return arrayAccesses; }

    @Override
    public String toString() {
        return String.format("Comparisons: %d | Updates: %d | ArrayAccesses: %d",
                comparisons, updates, arrayAccesses);
    }
}

