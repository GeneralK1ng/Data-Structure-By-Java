package Tree.RBTree;


import List.Lists.FuckLinkedList;
import List.ListsImpl.DoublyLinkedList.DoublyListImpl;

/*红黑树的性质
* 1. 节点为红色或者黑色
* 2. NIL 节点（null子节点）为黑色
* 3. 红子节点的子节点为黑色
* 4. 从根节点到任意 NIL 节点的每条路径上的黑色节点数量相同
* */
public class RedBlackTree<K extends Comparable<K>, V> {
    private Node<K,V> root;
    private FuckLinkedList<Entry<K, V>> entryList = new DoublyListImpl<>();
    private static Integer size = 0;

    public RedBlackTree() {
        root = null;
    }

    public boolean isEmpty() {return size == 0;}

    public Integer getSize() {return size;}

    public V get(K key) {
        if (root == null) {
            throw new RuntimeException("Tree is empty");
        } else {
            Node<K, V> node = getNode(root, key);
            if (node != null) {
                return node.value;
            } else {
                throw new RuntimeException("Key not found");
            }
        }
    }

    /**
     * 从二叉搜索树中获取指定键值的节点
     * @param current 当前遍历的节点
     * @param key 要查找的键
     * @return 找到的节点，如果未找到则返回null
     */
    private Node<K, V> getNode (Node<K,V> current, K key) {
        // 当当前节点为空或者键值匹配时，返回当前节点
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.key);
        // 如果键值相等，返回当前节点
        if (cmp == 0) {
            return current;
        // 如果键值小于当前节点键值，递归在左子树中查找
        } else if (cmp < 0) {
            return getNode(current.left, key);
        // 如果键值大于当前节点键值，递归在右子树中查找
        } else {
            return getNode(current.right, key);
        }
    }

    /**
     * 获取给定键对应的值，如果不存在，则使用该键创建新节点，并返回新节点的值。
     *
     * @param key 用于查找或创建节点的键。
     * @return 找到或创建的节点的值。
     */
    public V getOrDefault (K key) {
        // 如果树为空，创建一个新节点作为根节点，并返回该节点的值
        if (root == null) {
            root = new Node<>(key);
            root.setColor(Color.BLACK); // 设置新节点颜色为黑色
            size++; // 更新树的大小
            return root.value;
        } else {
            // 在树中查找或创建节点，并返回该节点的值
            return getNodeOrProvide(root, key, new Node<>(key)).value;
        }
    }

    private Node<K, V> getNodeOrProvide(Node<K,V> node, K key, Node<K, V> provide) {
        assert node != null;

        if (key.equals(node.key)) {
            return node;
        }

        assert !key.equals(node.key);

        Node<K, V> result;
        if (compare(key, node.key)) {
            if (node.left == null) {
                result = node.left = provide;
                node.left.parent = node;
                maintainAfterInsert(node.left);
                size++;
            } else {
                result = getNodeOrProvide(node.left, key, provide);
            }
        } else {
            // key > node.key
            if (node.right == null) {
                result = node.right = provide;
                node.right.parent = node;
                maintainAfterInsert(node.right);
                size++;
            } else {
                result = getNodeOrProvide(node.right, key, provide);
            }
        }
        return result;
    }

    private boolean compare(K key1, K key2) {
        return key1.compareTo(key2) < 0;
    }

    public void clear() {
        if (root != null) {
            root.release();
            root = null;
        }
        size = 0;
    }

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public void insert(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
            root.setColor(Color.BLACK);
            size++;
        } else {
            insert(root, key, value, true);
        }
    }

    public boolean insertIfAbsent(K key, V value) {
        int sizeBeforeInsertion = size;
        if (root == null) {
            root = new Node<>(key, value);
            root.setColor(Color.BLACK);
            size++;
        } else {
            insert(root, key, value, false);
        }
        return size > sizeBeforeInsertion;
    }

    public V getOrInsert(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
            root.setColor(Color.BLACK);
            size++;
            return root.value;
        } else {
            Node<K, V> node = getNodeOrProvide(root, key, new Node<>(key, value));
            return node.value;
        }
    }



    private void insert(Node<K,V> node, K key, V value, boolean replace) {
        assert node != null;
        if (key.equals(node.key)) {
            if (replace) {
                node.value = value;
            }
            return;
        }

        assert !key.equals(node.key);

        if (compare(key, node.key)) {
            // key < node.key
            if (node.left == null) {
                Node<K, V> newNode = new Node<>(key, value);
                newNode.parent = node;
                node.left = newNode;
                maintainAfterInsert(newNode);
                size++;
            } else {
                insert(node.left, key, value, replace);
            }
        } else {
            // key > node.key
            if (node.right == null) {
                Node<K, V> newNode = new Node<>(key, value);
                newNode.parent = node;
                node.right = newNode;
                maintainAfterInsert(newNode);
                size++;
            } else {
                insert(node.right, key, value, replace);
            }
        }

    }

    //插入操作
    /*
     * 第一种情况： 原本树为空，插入第一个节点后无需修正
     * 第二种情况： 当前节点的父节点为黑色且为根节点，插入节点后无需修正
     * 第三种情况： 当节点 N 的父节点 P 是为根节点且为红色，需要将其染为黑色，此时性质也满足，插入节点后无需修正
     * 第四种情况： 当节点 N 的父节点 P 和叔节点 U 均为红色，此时 P 包含了一个红色子节点，此时违反红黑树性质，根据性质3可以确定
     *            N 的祖父节点 G 一定是黑色，此时只需要后续操作可以保证以 G 为根节点的子树在不违反性质4的情况下再递归维护祖父
     *           节点 G 以保证性质3即可
     *           需要：
     *               1. 将 P，U 染为黑色，将 G 染为红色（可以保证每条路径上的黑色节点个数不变）
     *               2. 递归维护 G 节点，因为不确定 G 的父节点状态，递归维护可以保证性质3的成立
     * 第五种情况： 当前节点 N 与父节点 P 的方向相反，（即 N 节点为右子节点且父节点为左子节点，或 N 节点为左子节点且父节点为右
     *            子节点。类似 AVL 树中 LR 和 RL 的情况）。根据性质 4，若 N 为新插入节点，U 则为 NIL 黑色节点，否则为普通
     *            黑色节点。该种情况无法直接进行维护，需要通过旋转操作将子树结构调整为 Case 6 的初始状态并进入 Case 6 进行后续维护。
     * 第六种情况： 当前节点 N 与父节点 P 的方向相同（即 N 节点为右子节点且父节点为右子节点，或 N 节点为左子节点且父节点为右子节点。
     *            类似 AVL 树中 LL 和 RR 的情况）。根据性质 4，若 N 为新插入节点，U 则为 NIL 黑色节点，否则为普通黑色节点。
     *            在这种情况下，若想在不改变结构的情况下使得子树满足性质 3，则需将 G 染成红色，将 P 染成黑色。但若这样维护的话则
     *            性质 4 被打破，且无法保证在 G 节点的父节点上性质 3 是否成立。而选择通过旋转改变子树结构后再进行重新染色即可同
     *            时满足性质 3 和 4。
     *            需要：
     *                1. 若 N 为左子节点则右旋祖父节点 G，否则左旋祖父节点 G.（该操作使得旋转过后 P - N 这条路径上的黑色节点
     *                   个数比 P - G - U 这条路径上少 1，暂时打破性质 4）。
     *                2. 重新染色，将 P 染黑，将 G 染红，同时满足了性质 3 和 4。
     * */

    private void maintainAfterInsert(Node<K, V> node) {
        assert node != null;

        if (node.isRoot()) {
            // 第一种情况， 当前节点是根节点，不用管
            assert node.isRed();
            return;
        }

        if (node.parent.isBlack()) {
            // 第二种情况，父节点是黑色的
            assert node.isRed();
            return;
        }

        if (node.parent.isRoot()) {
            // 第三种情况，父节点是红色， 改为黑色
            assert node.parent.isRed();
            node.parent.color = Color.BLACK;
            return;
        }

        if (node.hasUncle() && node.uncle().isRed()) {
            // 第四种情况
            assert node.parent.isRed();
            node.parent.color = Color.BLACK;
            node.uncle().color = Color.BLACK;
            node.grandParent().color = Color.RED;
            maintainAfterInsert(node.grandParent());
            return;
        }

        if (!node.hasUncle() || node.uncle().isBlack()) {
            // 第五种和第六种情况
            assert !node.isRoot();

            if (node.direction() != node.parent.direction()) {
                // 第五种情况
                Node<K, V> parent = node.parent;
                if (node.direction() == Direction.LEFT) {
                    rotateRight(node.parent);
                } else {
                    rotateLeft(node.parent);
                }
                node = parent;
            }


            // 第六种情况
            assert node.grandParent() != null;
            if (node.parent.direction() == Direction.LEFT) {
                rotateRight(node.grandParent());
            } else {
                rotateLeft(node.grandParent());
            }

            node.parent.color = Color.BLACK;
            node.sibling().color = Color.RED;
        }

    }


    /**
     * 将给定节点向左旋转
     * 当某个节点的右子树高度明显大于左子树时，通过将该节点向左旋转来调整树的结构
     *
     * @param node 需要进行左旋转的节点
     * WARNING：节点及其右子树不能为空。
     */
    public void rotateLeft (Node<K,V> node) {
        assert(node != null && node.right != null); // 确保节点及其右子树不为空

        Node<K,V> parent = node.parent; // 保存当前节点的父节点
        Direction direction = node.direction(); // 保存当前节点在父节点中的方向（左或右子节点，或根节点）

        Node<K,V> successor = node.right; // 成功者节点，即原节点的右子节点，将成为新的当前节点
        node.right = successor.left; // 将原节点的右子树挂到成功者节点的左部
        if (successor.left != null) {
            successor.left.parent = node; // 更新成功者节点原左子节点的父节点指针
        }
        successor.left = node; // 将原节点设置为成功者节点的左子节点
        node.parent = successor; // 更新原节点的父节点指针

        // 更新各节点的 parent 指针，以保持树的结构完整性
        maintainRelationship(node);
        maintainRelationship(successor);

        // 根据原节点在父节点中的位置，更新父节点指向，以保持树的平衡
        switch (direction) {
            case ROOT:
                this.root = successor; // 若原节点为根节点，则更新根节点
                break;
            case LEFT:
                parent.left = successor; // 若原节点为父节点的左子节点，则更新父节点的左指针
                break;
            case RIGHT:
                parent.right = successor; // 若原节点为父节点的右子节点，则更新父节点的右指针
                break;
        }
        successor.parent = parent; // 更新成功者节点的父节点指针
    }

    /**
     * 将给定节点向右旋转
     * 以维持树的平衡
     *
     * @param node 需要进行右旋转的节点，该节点不应为null且必须有左子节点。
     */
    public void rotateRight (Node<K,V> node) {
        assert(node != null && node.left != null);

        Node<K,V> parent = node.parent;
        Direction direction = node.direction();

        Node<K,V> predecessor = node.left;
        node.left = predecessor.right;
        if (predecessor.right != null) {
            predecessor.right.parent = node;
        }
        predecessor.right = node;

        node.parent = predecessor;

        //维护各节点指针
        maintainRelationship(node);
        maintainRelationship(predecessor);

        switch (direction) {
            case ROOT:
                this.root = predecessor;
                break;
            case LEFT:
                parent.left = predecessor;
                break;
            case RIGHT:
                parent.right = predecessor;
                break;
        }
        predecessor.parent = parent;
    }

    /**
     * 维护节点与其子节点之间的关系。
     * 为给定节点的左右子节点设置父节点指针。
     *
     * @param node 待维护关系的节点。
     */
    private void maintainRelationship(Node<K,V> node) {
        // 为左子节点设置父节点
        if (node.left != null) {
            node.left.parent = node;
        }
        // 为右子节点设置父节点
        if (node.right != null) {
            node.right.parent = node;
        }
    }



}
