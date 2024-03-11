package List.lists;

import List.listsImpl.SkipList.KVPair;
import List.listsImpl.SkipList.SkipListImpl;

import java.util.function.Consumer;

public interface FuckSkipList<K extends Comparable<K>, V> {
    /**
     * 创建一个SkipList
     *
     * @param pairs 元素
     * @return SkipList
     */
    @SafeVarargs
    static <K extends Comparable<K>, V> SkipListImpl<K, V> listOf(KVPair<K, V>... pairs) {
        SkipListImpl<K, V> list = new SkipListImpl<>();
        for (KVPair<K, V> pair : pairs) {
            list.add(pair.getKey(), pair.getValue());
        }
        return list;
    }

    boolean add(K key, V value);

    boolean remove(K key);

    void print();

    boolean replace(K key, V value);

    boolean contains(K key);

    void forEach(Consumer<? super KVPair<K, V>> action);

    KVPair<K, V> get(K key);

    boolean isEmpty();

    boolean containsAll(KVPair<K, V>... pairs);
}
