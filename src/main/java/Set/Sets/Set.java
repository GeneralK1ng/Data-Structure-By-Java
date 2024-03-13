package Set.Sets;

public interface Set<E> {
    boolean add(E e);

    boolean remove(E e);

    boolean contains(E e);

    boolean isEmpty();

    int size();

    void clear();
}
