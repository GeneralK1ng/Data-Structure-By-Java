package Tree.TreesImpl.RBTreesImpl;

public class Node<K extends Comparable<K>, V> {
    Color color = Color.RED;
    K key;
    V value;
    Node<K, V> parent = null;
    Node<K, V> left = null;
    Node<K, V> right = null;

    public Node(K key) {
        this.key = key;
    }

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isRed() {
        return this.color == Color.RED;
    }

    public boolean isBlack() {
        return this.color == Color.BLACK;
    }

    /**
     * 获取当前节点是父节点的那个方向的子节点
     *
     *
     * @return Direction 返回的方向枚举，可能的值为 LEFT、RIGHT 或 ROOT
     *         如果当前对象有父节点，并且是父节点的左子节点，则返回 LEFT；
     *         如果当前对象有父节点，并且是父节点的右子节点，则返回 RIGHT；
     *         如果当前对象没有父节点，则返回 ROOT。
     */
    public Direction direction() {
        // 判断当前节点是否有父节点
        if (this.parent != null) {
            // 如果有父节点，判断当前节点是在父节点的左侧还是右侧
            if (this == this.parent.left) {
                return Direction.LEFT;
            } else {
                return Direction.RIGHT;
            }
        } else {
            // 如果没有父节点，返回 ROOT
            return Direction.ROOT;
        }
    }

    /**
     * 获取当前节点的兄弟节点
     * 该方法假设当前节点不是根节点
     *
     * @return Node<K, V> 返回当前节点的兄弟节点。如果当前节点存在，则返回其兄弟节点；否则，返回null。
     */
    public Node<K, V> sibling() {
        assert !this.isRoot(); // 断言操作，确保当前节点不是根节点

        if (this.direction() == Direction.LEFT) {
            return this.parent.right; // 当前节点是父节点的左子节点时，返回右兄弟节点
        } else {
            return this.parent.left; // 当前节点是父节点的右子节点时，返回左兄弟节点
        }
    }

    /**
     * 判断当前对象是否有兄弟节点。
     *
     * 该方法不接受任何参数。
     *
     * @return boolean - 如果当前对象不是根节点且存在兄弟节点，则返回true；否则返回false
     */
    public boolean hasSibling() {
        // 判断当前节点是否为根节点且是否有兄弟节点
        return !this.isRoot() && this.sibling() != null;
    }

    /**
     * 获取当前节点的叔节点（父节点的兄弟节点）
     * 此方法假设当前节点的父节点不为null
     *
     * @return 返回叔节点，类型为Node<K, V>
     */
    public Node<K, V> uncle() {
        // 确保当前节点的父节点存在
        assert this.parent != null;
        // 返回父节点的兄弟节点，即叔节点
        return this.parent.sibling();
    }

    /**
     * 判断当前节点是否有叔节点
     *
     * @return boolean - 如果当前节点有叔节点，则返回true；否则返回false
     */
    public boolean hasUncle() {
        return !this.isRoot() && this.parent.hasSibling();
    }

    /**
     * 获取当前节点的祖父节点
     *
     * @return Node<K, V> 返回祖父节点
     */
    public Node<K, V> grandParent() {
        assert this.parent != null;
        return this.parent.parent;
    }

    /**
     * 判断当前节点是否有祖父节点
     *
     * @return boolean - 如果当前节点有祖父节点，则返回true；否则返回false
     */
    public boolean hasGrandParent() {
        return !this.isRoot() && this.parent.parent != null;
    }

    /**
     * 释放当前节点
     *
     * 该方法将当前节点的父节点、左子节点和右子节点置为null
     */
    public void release() {
        this.parent = null;
        if (this.left != null) {
            this.left.release();
        }
        if (this.right != null) {
            this.right.release();
        }
    }

    /**
     * 获取当前节点的 Entry 对象
     *
     * @return Entry<K, V> 返回当前节点的 Entry 对象
     */
    public Entry<K, V> entry() {
        return new Entry<>(key, value);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getParent() {
        return parent;
    }

    public void setParent(Node<K, V> parent) {
        this.parent = parent;
    }

    public Node<K, V> getLeft() {
        return left;
    }

    public void setLeft(Node<K, V> left) {
        this.left = left;
    }

    public Node<K, V> getRight() {
        return right;
    }

    public void setRight(Node<K, V> right) {
        this.right = right;
    }


}
