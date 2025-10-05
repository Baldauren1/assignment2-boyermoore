package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreMajorityVoteTest {

    @Test
    void testMajorityExists() {
        int[] arr = {2, 2, 1, 1, 2, 2, 2};
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        Integer result = bm.findMajority(arr);
        assertEquals(2, result);
    }

    @Test
    void testNoMajority() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        Integer result = bm.findMajority(arr);
        assertNull(result);
    }

    @Test
    void testAllSame() {
        int[] arr = {5, 5, 5, 5, 5};
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        Integer result = bm.findMajority(arr);
        assertEquals(5, result);
    }

    @Test
    void testEmptyArrayThrows() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(new PerformanceTracker());
        assertThrows(IllegalArgumentException.class, () -> bm.findMajority(new int[]{}));
    }
}
