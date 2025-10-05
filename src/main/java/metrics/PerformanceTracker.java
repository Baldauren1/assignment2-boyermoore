package metrics;

/**
 * Simple performance tracker: comparisons, updates, array accesses.
 * Added "add*" methods to support batched updates from hot loops.
 */
public class PerformanceTracker {
    private long comparisons = 0;
    private long updates = 0;
    private long arrayAccesses = 0;

    // existing single-step methods (backwards compatible)
    public void incrementComparisons() { comparisons++; }
    public void incrementUpdates() { updates++; }
    public void incrementArrayAccesses() { arrayAccesses++; }
    public void incrementArrayAccesses(int n) { arrayAccesses += n; }

    // new batch-add methods (used to flush local counters once per operation)
    public void addComparisons(long v) { comparisons += v; }
    public void addUpdates(long v) { updates += v; }
    public void addArrayAccesses(long v) { arrayAccesses += v; }

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


