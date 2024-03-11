package Queue.QueueImpl.NormalQueue;

public class NormalQueueNode<T> {
    private T data;
    private NormalQueueNode<T> next;

    public NormalQueueNode(T data) {
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public NormalQueueNode<T> getNext() {
        return next;
    }

    public void setNext(NormalQueueNode<T> next) {
        this.next = next;
    }

}
