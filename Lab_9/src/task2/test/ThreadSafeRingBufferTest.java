package task2.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import task2.main.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;


class ThreadSafeRingBufferTest {

    @Test
    void testInvalidCapacity() {
        assertThrows(IllegalArgumentException.class,
                () -> new ThreadSafeRingBuffer<>(0));
        assertThrows(IllegalArgumentException.class,
                () -> new ThreadSafeRingBuffer<>(-5));
    }

    @Test
    void testPutAndTake() throws InterruptedException {
        ThreadSafeRingBuffer<String> buffer = new ThreadSafeRingBuffer<>(5);

        buffer.put("Test");
        String result = buffer.take();

        assertEquals("Test", result);
    }

    @Test
    void testFifoOrder() throws InterruptedException {
        ThreadSafeRingBuffer<Integer> buffer = new ThreadSafeRingBuffer<>(10);

        buffer.put(1);
        buffer.put(2);
        buffer.put(3);

        assertEquals(1, buffer.take());
        assertEquals(2, buffer.take());
        assertEquals(3, buffer.take());
    }

    @Test
    void testCircularBehavior() throws InterruptedException {
        ThreadSafeRingBuffer<Integer> buffer = new ThreadSafeRingBuffer<>(4);

        for (int i = 0; i < 10; i++) {
            buffer.put(i);
            assertEquals(i, buffer.take());
        }
    }

    @Test
    @Timeout(2)
    void testBlocksOnEmpty() {
        ThreadSafeRingBuffer<String> buffer = new ThreadSafeRingBuffer<>(5);

        Thread consumer = new Thread(() -> {
            try {
                buffer.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();

        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        assertEquals(Thread.State.WAITING, consumer.getState(),
                "Потік має чекати, якщо буфер порожній");

        consumer.interrupt();
    }

    @Test
    @Timeout(2)
    void testBlocksOnFull() throws InterruptedException {
        ThreadSafeRingBuffer<Integer> buffer = new ThreadSafeRingBuffer<>(3);

        buffer.put(1);
        buffer.put(2);

        Thread producer = new Thread(() -> {
            try {
                buffer.put(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();

        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        assertEquals(Thread.State.WAITING, producer.getState(),
                "Потік має чекати, якщо буфер повний");

        producer.interrupt();
    }

    @Test
    void testProducerConsumer() throws InterruptedException {
        ThreadSafeRingBuffer<Integer> buffer = new ThreadSafeRingBuffer<>(5);
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            try {
                int value = buffer.take();
                if (value == 42) {
                    latch.countDown();
                }
            } catch (InterruptedException e) {
                fail("Consumer interrupted", e);
            }
        }).start();

        Thread.sleep(50);
        buffer.put(42);

        assertTrue(latch.await(1, TimeUnit.SECONDS));
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        final int CAPACITY = 100;
        final int THREADS = 20;
        final int OPERATIONS = 1000;

        ThreadSafeRingBuffer<Integer> buffer = new ThreadSafeRingBuffer<>(CAPACITY);

        AtomicInteger produced = new AtomicInteger(0);
        AtomicInteger consumed = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(THREADS * 2);

        try (ExecutorService executor = Executors.newFixedThreadPool(THREADS * 2)) {

            for (int i = 0; i < THREADS; i++) {
                executor.submit(() -> {
                    try {
                        for (int j = 0; j < OPERATIONS; j++) {
                            buffer.put(1);
                            produced.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            for (int i = 0; i < THREADS; i++) {
                executor.submit(() -> {
                    try {
                        for (int j = 0; j < OPERATIONS; j++) {
                            int value = buffer.take();
                            consumed.addAndGet(value);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            assertTrue(latch.await(10, TimeUnit.SECONDS));
        }

        assertEquals(produced.get(), consumed.get());
    }

    @Test
    void testMessageFormat() throws InterruptedException {
        ThreadSafeRingBuffer<String> buffer1 = new ThreadSafeRingBuffer<>(10);
        ThreadSafeRingBuffer<String> buffer2 = new ThreadSafeRingBuffer<>(10);

        StringGenerator generator = new StringGenerator(buffer1, 1);
        generator.start();
        Thread.sleep(100);

        String produced = buffer1.take();
        assertTrue(produced.matches("Потік No \\d+ згенерував повідомлення \\d+"),
                "Формат генератора не відповідає вимогам");

        buffer1.put(produced);
        MessageTransfer transfer = new MessageTransfer(buffer1, buffer2, 1);
        transfer.start();
        Thread.sleep(100);

        String transferred = buffer2.take();
        assertTrue(transferred.matches("Потік No \\d+ переклав повідомлення \\d+ \\[.*\\]"),
                "Формат перекладача не відповідає вимогам");
    }
}