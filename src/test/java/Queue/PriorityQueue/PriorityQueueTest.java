package Queue.PriorityQueue;

import Queue.QueueImpl.PriorityQueue.PriorityQueueImpl;

import java.util.Random;

public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueueImpl<Integer> queue = new PriorityQueueImpl<>();
        Random random = new Random();
        queue.insert(10);
        queue.insert(5);
        queue.insert(30);
        queue.insert(8);
        queue.insert(20);
        queue.print();

        System.out.println(queue.extractMin());
        queue.print();

        System.out.println(queue.peekMin());

        System.out.println(queue.size());

        // 测试插入大量数据
        for (int i = 1; i <= 100; i++) {
            // 插入随机数
            queue.insert(random.nextInt(100));
        }
        queue.print();

        // 打印新的最小元素
        System.out.println(queue.peekMin());
    }
}
