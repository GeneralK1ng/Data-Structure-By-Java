package Queue.QueueImpl.DoubleEndedQueue;

import Queue.Queues.DoubleEndedQueue;

public class DequeImpl<T> implements DoubleEndedQueue<T> {
    private DequeNode<T> front;
    private DequeNode<T> rear;
    private static Integer size;


    public DequeImpl() {
        front = null;
        rear = null;
        size = 0;
    }
    @Override
    public void addFirst(T element) {
        DequeNode<T> node = new DequeNode<>(element);
        if (isEmpty()) {
            front = node;
            rear = node;
        } else {
            node.setNext(front);
            front.setPrev(node);
            front = node;
        }
        size++;
    }

    @Override
    public void addLast(T element) {
        DequeNode<T> node = new DequeNode<>(element);
        if (isEmpty()){
            front = node;
            rear = node;
        } else {
            node.setPrev(rear);
            rear.setNext(node);
            rear = node;
        }
        size++;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()){
            throw new IllegalStateException("The Deque is null");
        }

        T data = front.getData();
        front = front.getNext();

        if (!isEmpty()) {
            front.setPrev(null);
        } else {
            rear = null;
        }
        size--;

        return data;
    }

    @Override
    public T removeLast() {
        if (isEmpty()){
            throw new IllegalStateException("The Deque is null");
        }

        T data = rear.getData();
        rear = rear.getPrev();

        if (rear != null){
            rear.setNext(null);
        } else {
            front = null;
        }

        size--;
        return data;
    }

    @Override
    public T peekFirst() {
        if (isEmpty()){
            throw new IllegalStateException("The Deque is null");
        }

        return front.getData();
    }

    @Override
    public T peekLast() {
        if (isEmpty()){
            throw new IllegalStateException("The Deque is null");
        }

        return rear.getData();
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public Integer size() {
        return size;
    }

    public void print() {
        DequeNode<T> temp = front;
        while (temp != null) {
            System.out.print(temp.getData() + " ");
            temp = temp.getNext();
        }
        System.out.println();
    }

}
