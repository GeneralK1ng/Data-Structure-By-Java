package List.ListsImpl.SinglyLinkedList;


public class SinglyNode<T> {
    private T data;
    private SinglyNode<T> next;

    public SinglyNode(T data) {
        this.data = data;
        this.next = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SinglyNode<T> getNext() {
        return next;
    }

    public void setNext(SinglyNode<T> next) {
        this.next = next;
    }
}
