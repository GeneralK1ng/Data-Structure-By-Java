package Queue.NormalQueue;

import Queue.QueueImpl.NormalQueue.NormalQueueImpl;

public class NormalQueueTest {
    public static void main(String[] args) {
        NormalQueueImpl<Integer> queue = new NormalQueueImpl<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);

        queue.print();

        System.out.println(queue.remove());
        queue.print();

        System.out.println(queue.peek());
    }
}
