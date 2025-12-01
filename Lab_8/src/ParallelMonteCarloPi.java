import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class ParallelMonteCarloPi {

    private static final long DEFAULT_TOTAL_ITERATIONS = 10_000_000_000L;

    private static long totalPointsInCircle = 0;
    private static final Object lock = new Object();

    private static class PiEstimatorThread implements Runnable {
        private final long iterations;
        private final Random random;
        private long localPointsInCircle = 0;

        public PiEstimatorThread(long iterations, long seed) {
            this.iterations = iterations;
            this.random = new Random(seed);
        }

        @Override
        public void run() {
            for (long i = 0; i < iterations; i++) {
                double x = random.nextDouble();
                double y = random.nextDouble();

                if (x * x + y * y <= 1.0) {
                    localPointsInCircle++;
                }
            }

            synchronized (lock) {
                totalPointsInCircle += localPointsInCircle;
            }
        }
    }

    public static double estimatePi(int numThreads, long totalIterations) throws InterruptedException {
        totalPointsInCircle = 0;

        long iterationsPerThread = totalIterations / numThreads;
        long remainingIterations = totalIterations % numThreads;

        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            long currentIterations = iterationsPerThread + (i < remainingIterations ? 1 : 0);

            PiEstimatorThread task = new PiEstimatorThread(currentIterations, System.nanoTime() + i);
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        if (totalIterations == 0) return 0.0;
        return 4.0 * totalPointsInCircle / totalIterations;
    }

    public static void main(String[] args) {

        int numThreads = 0;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of threads: ");
            numThreads = scanner.nextInt();

            if (numThreads <= 0) {
                System.err.println("Error: Number of threads must be a positive integer.");
                return;
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Invalid input. Please enter a valid integer.");
            return;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return;
        }

        long startTime = System.nanoTime();
        double piEstimate = 0.0;

        try {
            piEstimate = estimatePi(numThreads, DEFAULT_TOTAL_ITERATIONS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Calculation interrupted.");
            return;
        }

        long endTime = System.nanoTime();
        long nanoTimeTaken = endTime - startTime;
        double timeTakenMs = (double)nanoTimeTaken / 1_000_000.0;

        System.out.printf("PI is %.5f%n", piEstimate);
        System.out.printf("THREADS %d%n", numThreads);
        System.out.printf("ITERATIONS %,d%n", DEFAULT_TOTAL_ITERATIONS);
        System.out.printf("TIME %.2fms%n", timeTakenMs);
    }
}