package List.ListsImpl.SkipList;

import java.util.Random;
import java.util.function.Consumer;

import List.Lists.FuckSkipList;


public class SkipListImpl<K extends Comparable<K>, V> implements FuckSkipList<K, V> {
    private static final int MAX_LEVEL = 32;
    private final SkipNode<K, V> head;
    private Integer level;
    private final Random random; // Random number generator

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


    /**
     * 查找
     *
     * @param key 键
     * @return 查找结果
     */
    private SkipNode<K, V> search(K key) {
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

    /**
     * 插入新结点
     *
     * @param key 键
     * @param value 值
     * @return true表示插入成功，false表示插入失败
     */
    @Override
    public boolean add(K key, V value) {
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

    // 随机生成层数
    private int getRandomLevel() {
        int level = 1;
        while (random.nextDouble() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    /**
     * 删除结点
     *
     * @param key 键
     * @return true表示删除成功，false表示删除失败
     */
    @Override
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

    /**
     * 打印SkipList
     */
    @Override
    public void print() {
        SkipNode<K, V> node = head.getForward()[0];
        while (node != null) {
            System.out.print(node.getValue() + " ");
            node = node.getForward()[0];
        }
        System.out.println();
    }

    /**
     * 替换某个元素的值
     *
     * @param key 键
     * @param value 值
     * @return 是否成功
     */
    @Override
    public boolean replace(K key, V value){
        SkipNode<K, V> node = search(key);
        if(node == null){
            return false;
        }
        node.setValue(value);
        return true;
    }

    /**
     * 判断是否包含某个元素
     *
     * @param key 键
     * @return 是否包含
     */
    @Override
    public boolean contains(K key){
        return search(key) != null;
    }

    /**
     * 遍历
     *
     * @param action 每个元素的处理逻辑
     */
    @Override
    public void forEach(Consumer<? super KVPair<K, V>> action){
        SkipNode<K, V> node = head.getForward()[0];
        while(node != null){
            action.accept(new KVPair<>(node.getKey(), node.getValue()));
            node = node.getForward()[0];
        }
    }

    /**
     * 获取指定key的值
     *
     * @param key 键
     * @return 值
     */
    @Override
    public KVPair<K, V> get(K key){
        SkipNode<K, V> node = search(key);
        if(node == null){
            return null;
        }
        return new KVPair<>(node.getKey(), node.getValue());
    }

    /**
     * 判断是否为空
     *
     * @return 是否为空
     */
    @Override
    public boolean isEmpty(){
        return nodeCount == 0;
    }

    /**
     * 判断是否包含所有元素
     *
     * @param pairs 键值对
     * @return 是否包含所有元素
     */
    @SafeVarargs
    @Override
    public final boolean containsAll(KVPair<K, V>... pairs){
        for(KVPair<K, V> pair : pairs){
            if(!contains(pair.getKey())){
                return false;
            }
        }
        return true;
    }

}
