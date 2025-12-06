package task2.main;


public class ListNode<T> {
    private T content;
    private ListNode<T> successor;

    public ListNode(T initialContent) {
        this.content = initialContent;
    }

    public T getContent() {
        return content;
    }

    public void updateContent(T newContent) {
        this.content = newContent;
    }

    public ListNode<T> getSuccessor() {
        return successor;
    }

    public void linkTo(ListNode<T> node) {
        this.successor = node;
    }
}