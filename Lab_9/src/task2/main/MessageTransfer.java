package task2.main;


public class MessageTransfer extends Thread {
    private final ThreadSafeRingBuffer<String> firstBuffer;
    private final ThreadSafeRingBuffer<String> secondBuffer;
    private final int threadNumber;
    private int messageNumber;

    public MessageTransfer(ThreadSafeRingBuffer<String> first,
                           ThreadSafeRingBuffer<String> second,
                           int number) {
        this.firstBuffer = first;
        this.secondBuffer = second;
        this.threadNumber = number;
        this.messageNumber = 0;

        setDaemon(true);
        setName("Transfer-" + number);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String originalMessage = firstBuffer.take();
                messageNumber++;

                String transferredMessage = String.format(
                        "Потік No %d переклав повідомлення %d [%s]",
                        threadNumber,
                        messageNumber,
                        originalMessage
                );
                secondBuffer.put(transferredMessage);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}