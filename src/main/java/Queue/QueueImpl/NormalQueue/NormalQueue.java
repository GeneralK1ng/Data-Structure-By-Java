package Queue.QueueImpl.NormalQueue;



public class NormalQueue<T> {
    private NormalQueueNode<T> front;
    private NormalQueueNode<T> rear;


    public NormalQueue() {
        front = null;
        rear = null;
    }

    // 入队
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
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        return front.getData();
    }

    private boolean isEmpty() {
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

}
