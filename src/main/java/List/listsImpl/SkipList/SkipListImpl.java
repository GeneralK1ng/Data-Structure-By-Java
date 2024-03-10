package List.listsImpl.SkipList;

import java.util.Random;


public class SkipListImpl<K extends Comparable<K>, V> {
    private static final int MAX_LEVEL = 16;
    private SkipNode<K, V> head;
    private int level;
    private Random random; // Random number generator

    private int nodeCount;

    public SkipListImpl() {
        head = new SkipNode<>(null, null, MAX_LEVEL);
        level = 1;
        random = new Random();
        nodeCount = 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public SkipNode<K, V> search(K key) {
        SkipNode<K, V> node = head;
        for (int i = level - 1; i >= 0; i--) {
            while (node.getForward()[i] != null && node.getForward()[i].getKey().compareTo(key) < 0) {
                node = node.getForward()[i];
            }
        }
        node = node.getForward()[0];
        if (node != null && node.getKey().equals(key)) {
            return node;
        } else {
            return null;
        }
    }

    public boolean insert(K key, V value) {
        SkipNode<K, V>[] update = new SkipNode[MAX_LEVEL];
        SkipNode<K, V> node = head;
        for (int i = level - 1; i >= 0; i--) {
            while (node.getForward()[i] != null && node.getForward()[i].getKey().compareTo(key) < 0) {
                node = node.getForward()[i];
            }
            update[i] = node;
        }
        //首个结点插入时，node->forward[0]其实就是footer
        node = node.getForward()[0];
        //如果key已存在，则直接返回false
        if (node != null && node.getKey().equals(key)) {
            return false;
        }
        int nodeLevel = getRandomLevel();
        if (nodeLevel > level) {
            nodeLevel = ++level;
            update[nodeLevel] = head;
        }
        //创建新结点
        SkipNode<K, V> newNode = new SkipNode<>(key, value, nodeLevel);
        //调整forward指针

        for (int i = 0; i <= nodeLevel - 1; i++) {
            if (update[i] != null) {
                newNode.getForward()[i] = update[i].getForward()[i];
                update[i].getForward()[i] = newNode;
            } else {
                // 处理 update[i] 为 null 的情况，可能需要调整逻辑
                update[i] = head;
                newNode.getForward()[i] = update[i].getForward()[i];
                update[i].getForward()[i] = newNode;
            }
        }

        nodeCount++;
        return true;
    }

    // Method to generate random level for new node
    private int getRandomLevel() {
        int level = 1;
        while (random.nextDouble() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public boolean remove(K key) {
        SkipNode<K, V>[] update = new SkipNode[MAX_LEVEL];
        SkipNode<K, V> node = head;
        for (int i = level - 1; i >= 0; i--) {
            while (node.getForward()[i] != null && node.getForward()[i].getKey().compareTo(key) < 0) {
                node = node.getForward()[i];
            }
            update[i] = node;
        }
        //如果结点不存在就返回false
        node = node.getForward()[0];
        if (node == null || !node.getKey().equals(key)) {
            return false;
        }
        for (int i = 0; i < level; i++) {
            if (update[i].getForward()[i] != node) {
                break;
            }
            update[i].getForward()[i] = node.getForward()[i];
        }
        //释放结点
        node = null;
        //更新level的值，因为有可能在移除一个结点之后，level值会发生变化，及时移除可避免造成空间浪费
        while (level > 1 && head.getForward()[level - 1] == null) {
            level--;
        }
        nodeCount--;
        return true;
    }

    public void print() {
        SkipNode<K, V> node = head.getForward()[0];
        while (node != null) {
            System.out.print(node.getValue() + " ");
            node = node.getForward()[0];
        }
        System.out.println();
    }

    // listOf

    @SafeVarargs
    public static <K extends Comparable<K>, V> SkipListImpl<K, V> listOf(KVPair<K, V>... pairs) {
        SkipListImpl<K, V> list = new SkipListImpl<>();
        for (KVPair<K, V> pair : pairs) {
            list.insert(pair.getKey(), pair.getValue());
        }
        return list;
    }
}
