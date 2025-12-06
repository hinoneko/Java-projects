package task2.main;


public class ThreadSafeRingBuffer<T> {
    private ListNode<T> headIndex;
    private ListNode<T> tailIndex;

    public ThreadSafeRingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Ємність має бути додатною");
        }
        headIndex = new ListNode<>(null);
        tailIndex = headIndex;

        ListNode<T> current = headIndex;
        for (int i = 1; i < capacity; i++) {
            current.linkTo(new ListNode<>(null));
            current = current.getSuccessor();
        }
        current.linkTo(headIndex);
    }

    public synchronized void put(T data) throws InterruptedException {
        while (tailIndex.getSuccessor() == headIndex) {
            wait();
        }
        tailIndex.updateContent(data);
        tailIndex = tailIndex.getSuccessor();

        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (headIndex == tailIndex) {
            wait();
        }
        T data = headIndex.getContent();
        headIndex.updateContent(null);
        headIndex = headIndex.getSuccessor();

        notifyAll();

        return data;
    }
}