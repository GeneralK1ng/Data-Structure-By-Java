package Queue.NormalQueue;

import Queue.QueueImpl.NormalQueue.NormalQueue;

public class NormalQueueTest {
    public static void main(String[] args) {
        NormalQueue<Integer> queue = new NormalQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);

        queue.print();

        queue.remove();
        queue.print();

        System.out.println(queue.peek());
    }
}
