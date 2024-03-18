package Queue.QueueImpl.NormalQueue;


import Queue.Queues.NormalQueue;

public class NormalQueueImpl<T> implements NormalQueue<T> {
    private NormalQueueNode<T> front;
    private NormalQueueNode<T> rear;


    public NormalQueueImpl() {
        front = null;
        rear = null;
    }

    // 入队
    @Override
    public boolean add(T data) {
        NormalQueueNode<T> node = new NormalQueueNode<>(data);
        if (isEmpty()) {
            front = node;
            rear = node;
        } else {
            rear.setNext(node);
            rear = node;
        }
        return true;
    }

    // 出队
    @Override
    public T remove() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        T data = front.getData();
        front = front.getNext();

        if (isEmpty()) {
            rear = null;
        }

        return data;
    }

    // 获取队首元素（不出队）
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        return front.getData();
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    public void print() {
        NormalQueueNode<T> node = front;
        while (node != null) {
            System.out.print(node.getData() + " ");
            node = node.getNext();
        }
        System.out.println();
    }

    // size
    public int size() {
        NormalQueueNode<T> node = front;
        int size = 0;
        while (node != null) {
            size++;
            node = node.getNext();
        }
        return size;
    }


}
