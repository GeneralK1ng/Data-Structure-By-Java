package Map.Maps;

import Set.Sets.Set;

public interface Map<K, V> {

    public V put(K key, V value);

    public V putIfAbsent(K key, V value);

    public V remove(K key);

    public V get(K key);

    public boolean containsKey(K key);

    public boolean containsValue(V value);

    public boolean isEmpty();

    void clear();

    int size();
}
