package Tree.Trees;

import List.Lists.FuckLinkedList;
import Tree.TreesImpl.RBTreesImpl.Entry;

public interface RBTree<K extends Comparable<K>, V> {
    boolean isEmpty();

    Integer getSize();

    V get(K key);

    Integer getLevel(K key);

    FuckLinkedList<Entry<K, V>> getList();

    V getOrDefault(K key);

    void clear();

    boolean contains(K key);

    void insert(K key, V value);

    boolean insertIfAbsent(K key, V value);

    V getOrInsert(K key, V value);

    boolean remove(K key);

    V getAndRemove(K key);
}
