package task2.main;


public class StringGenerator extends Thread {
    private final ThreadSafeRingBuffer<String> buffer;
    private final int threadNumber;
    private int messageNumber;

    public StringGenerator(ThreadSafeRingBuffer<String> buffer, int number) {
        this.buffer = buffer;
        this.threadNumber = number;
        this.messageNumber = 0;

        setDaemon(true);
        setName("Generator-" + number);
    }

    @Override
    public void run() {
        try {
            while (true) {
                messageNumber++;
                String message = String.format(
                        "Потік No %d згенерував повідомлення %d",
                        threadNumber,
                        messageNumber
                );

                buffer.put(message);
                Thread.sleep((long) (Math.random() * 50));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}