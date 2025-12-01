import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PiTest {

    private static final double PI_VALUE = Math.PI;
    private static final double ALLOWED_ERROR = 0.01;

    @Test
    void testPiAccuracy() throws InterruptedException {
        long iterations = 500_000_000L;
        int threads = 4;

        double result = ParallelMonteCarloPi.estimatePi(threads, iterations);
        double relativeError = Math.abs(result - PI_VALUE) / PI_VALUE;

        System.out.printf("Test 1 (Accuracy) Result: %.5f, Error: %.3f%%%n",
                result, relativeError * 100);

        assertTrue(relativeError < ALLOWED_ERROR,
                "Result is outside the allowed error range (expected PI +/- 1%)");
    }

    @Test
    void testSingleThread() throws InterruptedException {
        long iterations = 100_000_000L;
        int threads = 1;

        double result = ParallelMonteCarloPi.estimatePi(threads, iterations);
        double relativeError = Math.abs(result - PI_VALUE) / PI_VALUE;

        System.out.printf("Test 2 (Single Thread) Result: %.5f, Error: %.3f%%%n",
                result, relativeError * 100);

        assertTrue(relativeError < ALLOWED_ERROR * 2,
                "Single thread result is too inaccurate.");
    }

    @Test
    void testOptimalThreadsCorrectness() throws InterruptedException {
        long iterations = 100_000_000L;
        int threads = Runtime.getRuntime().availableProcessors();

        double result = ParallelMonteCarloPi.estimatePi(threads, iterations);
        double relativeError = Math.abs(result - PI_VALUE) / PI_VALUE;

        System.out.printf("Test 3 (Optimal Threads) Result: %.5f, Error: %.3f%%%n",
                result, relativeError * 100);

        assertTrue(relativeError < ALLOWED_ERROR * 2,
                "Multiple thread result is too inaccurate.");
    }

    @Test
    void testHighConcurrencyStability() throws InterruptedException {
        long iterations = 100_000_000L;
        int threads = 32;

        double result = ParallelMonteCarloPi.estimatePi(threads, iterations);
        double relativeError = Math.abs(result - PI_VALUE) / PI_VALUE;

        System.out.printf("Test 4 (High Concurrency) Result: %.5f, Error: %.3f%%%n",
                result, PI_VALUE, relativeError * 100);

        assertTrue(relativeError < ALLOWED_ERROR * 2,
                "High concurrency failed to maintain reasonable accuracy.");
    }

    @Test
    void testZeroIterations() throws InterruptedException {
        long iterations = 0L;
        int threads = 4;

        double result = ParallelMonteCarloPi.estimatePi(threads, iterations);

        assertEquals(0.0, result, "Result for zero iterations should be 0.0");
    }

    @Test
    void testUnevenDistribution() throws InterruptedException {
        long iterations = 100_000_001L;
        int threads = 4;

        double result = ParallelMonteCarloPi.estimatePi(threads, iterations);
        double relativeError = Math.abs(result - PI_VALUE) / PI_VALUE;

        System.out.printf("Test 6 (Uneven Distribution) Result: %.5f, Error: %.3f%%%n",
                result, PI_VALUE, relativeError * 100);

        assertTrue(relativeError < ALLOWED_ERROR * 2,
                "Uneven iteration distribution failed to maintain accuracy.");
    }
}