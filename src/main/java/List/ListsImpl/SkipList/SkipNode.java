package List.ListsImpl.SkipList;


public class SkipNode<K extends Comparable<K>, V> {
    private K key;
    private V value;
    private SkipNode<K, V>[] forward; // Array to hold references to next nodes at different levels
    private int nodeLevel; // Level of the node in the skip list

    public SkipNode(K key, V value, int level) {
        this.key = key;
        this.value = value;
        this.forward = new SkipNode[level];
        this.nodeLevel = level;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public SkipNode<K, V>[] getForward() {
        return forward;
    }

    public void setForward(SkipNode<K, V>[] forward) {
        this.forward = forward;
    }

    public int getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }
}
