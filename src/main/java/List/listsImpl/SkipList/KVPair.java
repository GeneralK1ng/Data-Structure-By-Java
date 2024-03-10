package List.listsImpl.SkipList;

import java.util.Iterator;

public class KVPair<K, V> implements Iterable<KVPair<K, V>>{
    private final K key;
    private final V value;

    public KVPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new Iterator<KVPair<K, V>>() {
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public KVPair<K, V> next() {
                hasNext = false;
                return KVPair.this;
            }
        };
    }
}
