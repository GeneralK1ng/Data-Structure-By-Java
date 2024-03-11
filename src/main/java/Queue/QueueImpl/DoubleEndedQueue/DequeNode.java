package Queue.QueueImpl.DoubleEndedQueue;

public class DequeNode<T> {
    private T data;
    private DequeNode<T> prev;
    private DequeNode<T> next;

    public DequeNode(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public DequeNode<T> getPrev() {
        return prev;
    }

    public void setPrev(DequeNode<T> prev) {
        this.prev = prev;
    }

    public DequeNode<T> getNext() {
        return next;
    }

    public void setNext(DequeNode<T> next) {
        this.next = next;
    }
}
