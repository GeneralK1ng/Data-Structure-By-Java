package List.listsImpl.CircularLinkedList;

public class CircularNode<T> {
    public T data;
    public CircularNode<T> next;
    public CircularNode<T> prev;
    public CircularNode(T data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CircularNode<T> getNext() {
        return next;
    }

    public void setNext(CircularNode<T> next) {
        this.next = next;
    }

    public CircularNode<T> getPrev() {
        return prev;
    }

    public void setPrev(CircularNode<T> prev) {
        this.prev = prev;
    }
}
