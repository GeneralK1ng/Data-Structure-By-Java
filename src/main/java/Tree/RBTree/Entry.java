package Tree.RBTree;

public class Entry<K, V>{
    private final K key;
    private final V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public boolean equals(Entry<K, V> other) {
        return this.key.equals(other.key) && this.value.equals(other.value);
    }

    public boolean notEquals(Entry<K, V> other) {
        return !this.key.equals(other.key) || !this.value.equals(other.value);
    }
    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
