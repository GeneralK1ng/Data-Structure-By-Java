package Tree.TreesImpl.RBTreesImpl;


import List.Lists.FuckLinkedList;
import List.ListsImpl.DoublyLinkedList.DoublyListImpl;
import Stack.StacksImpl.ListNodeStackImpl;
import Tree.Trees.RBTree;


/**
 * 红黑树的实现类
 * 性质：
 * 1. 节点为红色或者黑色
 * 2. NIL 节点（null子节点）为黑色
 * 3. 红子节点的子节点为黑色
 * 4. 从根节点到任意 NIL 节点的每条路径上的黑色节点数量相同
 * @param <K> - 键的类型
 * @param <V> - 值的类型
 */
public class RedBlackTree<K extends Comparable<K>, V> implements RBTree<K, V> {
    /**
     * 键值对存储的根节点。这是一个指向存储结构顶端的引用，用于快速访问和管理存储的数据。
     */
    private Node<K,V> root;

    /**
     * 链表用于存储所有的键值对入口。这种数据结构允许快速的插入和删除操作，
     * 对于需要频繁修改集合的操作来说非常高效。
     */
    private FuckLinkedList<Entry<K, V>> entryList = new DoublyListImpl<>();

    /**
     * 集合的大小计数器。这是一个静态变量，用于跟踪集合中元素的数量，
     * 便于快速访问而不必遍历整个集合。初始值为0。
     */
    private static Integer size = 0;

    /**
     * 初始化红黑树
     */
    public RedBlackTree() {
        root = null;
    }
    /**
     * 检查树是否为空。
     *
     * @return boolean - 如果树中没有元素，则返回true；否则返回false
     */
    @Override
    public boolean isEmpty() {return size == 0;}

    /**
     * 获取红黑树节点数量
     * @return 整型，表示树中节点的数量
     */
    @Override
    public Integer getSize() {return size;}

    /**
     * 获取给定键对应的值。
     * @param key 要查找的键，类型为K。
     * @return 如果找到对应键的节点，则返回该节点的值，类型为V。如果树为空或键不存在，则抛出运行时异常。
     */
    @Override
    public V get(K key) {
        if (root == null) { // 检查树是否为空
            throw new RuntimeException("Tree is empty");
        } else {
            Node<K, V> node = getNode(root, key); // 在树中查找指定键的节点
            if (node != null) {
                return node.value; // 找到则返回节点的值
            } else {
                throw new RuntimeException("Key not found"); // 未找到键则抛出异常
            }
        }
    }

    @Override
    public Integer getLevel(K key) {
        // 递归获取节点的深度
        return getLevel(root, key);
    }
    private Integer getLevel(Node<K, V> node, K key) {
        if (node == null) {
            return 0;
        }
        if (node.key.equals(key)) {
            return 1;
        } else {
            Integer leftLevel = getLevel(node.getLeft(), key);
            Integer rightLevel = getLevel(node.getRight(), key);
            return Math.max(leftLevel, rightLevel) + 1;
        }
    }

    /**
     * 获取链表的有序遍历结果。
     * 该方法首先会执行中序遍历，然后返回遍历结果形成的链表。
     *
     * @return FuckLinkedList<Entry<K, V>> 返回中序遍历后形成的链表，其中Entry<K, V>表示键值对。
     */
    @Override
    public FuckLinkedList<Entry<K, V>> getList() {
        inorderTraversal(); // 执行中序遍历
        return entryList; // 返回遍历结果形成的链表
    }

    /**
     * 获取给定键对应的值，如果不存在，则使用该键创建新节点，并返回新节点的值。
     *
     * @param key 用于查找或创建节点的键。
     * @return 找到或创建的节点的值。
     */
    @Override
    public V getOrDefault(K key) {
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

    /**
     * 获取给定键对应的节点，如果该节点不存在，则提供一个新节点并插入。
     *
     * @param node 当前比较的节点
     * @param key 需要查找或插入的键
     * @param provide 当查找的键不存在时，提供用来插入的新节点
     * @return 找到的或插入的节点
     */
    private Node<K, V> getNodeOrProvide(Node<K,V> node, K key, Node<K, V> provide) {
        assert node != null;  // 确保传入的当前节点不为null

        // 如果键相等，则返回当前节点
        if (key.equals(node.key)) {
            return node;
        }

        assert !key.equals(node.key);  // 确保传入的键不等于当前节点的键

        Node<K, V> result;
        // 比较键的大小，决定向左子树还是右子树搜索
        if (compare(key, node.key) < 0) {
            // 向左子树搜索
            if (node.left == null) {
                // 如果左子节点为空，将提供的节点插入左子树
                result = node.left = provide;
                node.left.parent = node;
                maintainAfterInsert(node.left);  // 保持树的性质
                size++;  // 更新树的大小
            } else {
                // 如果左子节点不为空，递归向左子树搜索
                result = getNodeOrProvide(node.left, key, provide);
            }
        } else {
            // 向右子树搜索
            if (node.right == null) {
                // 如果右子节点为空，将提供的节点插入右子树
                result = node.right = provide;
                node.right.parent = node;
                maintainAfterInsert(node.right);  // 保持树的性质
                size++;  // 更新树的大小
            } else {
                // 如果右子节点不为空，递归向右子树搜索
                result = getNodeOrProvide(node.right, key, provide);
            }
        }
        return result;  // 返回找到的或插入的节点
    }


    /**
     * 比较两个键的大小。
     *
     * @param key1 第一个键，类型为K，不能为空。
     * @param key2 第二个键，类型为K，不能为空。
     * @return 返回比较的结果，如果key1小于key2，返回负数；如果相等，返回0；如果大于，返回正数。
     *         该结果用于确定键的顺序或匹配性。
     */
    private int compare(K key1, K key2) {
        return key1.compareTo(key2);
    }


    /**
     * 清空树结构，释放根节点资源，并将大小设置为0。
     * 该方法不接受参数，也不返回任何值。
     */
    @Override
    public void clear() {
        // 如果根节点不为空，则释放根节点资源，并将根节点置为null
        if (root != null) {
            root.release();
            root = null;
        }
        // 将大小设置为0
        size = 0;
    }


    /**
     * 检查树中是否包含指定的键。
     *
     * @param key 要查找的键。
     * @return 如果树中存在指定的键，则返回true；否则返回false。
     */
    @Override
    public boolean contains(K key) {
        // 通过递归搜索树，查找是否存在与给定键相匹配的节点。
        return getNode(root, key) != null;
    }


    /**
     * 将给定的键值对插入到红黑树中。
     * @param key 要插入的键
     * @param value 与键对应的值
     */
    @Override
    public void insert(K key, V value) {
        // 如果树为空，创建一个新的节点作为根节点，并将其颜色设置为黑色
        if (root == null) {
            root = new Node<>(key, value);
            root.setColor(Color.BLACK);
            size++;
        } else {
            // 否则，递归地将键值对插入到树的适当位置
            insert(root, key, value, true);
        }
    }

    /**
     * 如果给定的键不存在于树中，则插入该键值对。
     *
     * @param key  要插入的键
     * @param value 与键对应的值
     * @return 如果插入了新的键值对，则返回true；如果键已存在，不进行插入，返回false。
     */
    @Override
    public boolean insertIfAbsent(K key, V value) {
        // 记录插入前树的大小
        int sizeBeforeInsertion = size;
        // 如果树为空，插入新的节点作为根节点
        if (root == null) {
            root = new Node<>(key, value);
            root.setColor(Color.BLACK); // 设置新节点颜色为黑色
            size++; // 更新树的大小
        } else {
            // 否则，在树中正常插入新的键值对
            insert(root, key, value, false);
        }
        // 判断是否成功插入新的键值对
        return size > sizeBeforeInsertion;
    }

    /**
     * 获取给定键对应的值，如果不存在，则插入此键值对并返回该值。
     * @param key 要查找或插入的键。
     * @param value 与键对应的值，如果键不存在则插入此值。
     * @return 返回键对应的值，如果插入新键值对，则返回新值。
     */
    @Override
    public V getOrInsert(K key, V value) {
        // 如果树为空，即没有元素，则创建并返回一个新的节点
        if (root == null) {
            root = new Node<>(key, value);
            root.setColor(Color.BLACK); // 设置新节点颜色为黑色
            size++; // 树的大小加一
            return root.value;
        } else {
            // 获取已有节点或提供一个新节点（如果键不存在）
            Node<K, V> node = getNodeOrProvide(root, key, new Node<>(key, value));
            return node.value; // 返回找到或插入的节点的值
        }
    }

    /**
     * 从红黑树中移除指定的键和其对应的值。
     *
     * @param key 要移除的键。
     * @return 如果成功移除了键，则返回 true；如果树中没有此键，则返回 false。
     */
    @Override
    public boolean remove(K key) {

        // 当树为空时，直接返回 false，表示无法移除任何键
        if (root == null) {
            return false;
        }

        // 调用递归函数 remove，尝试从根节点开始移除指定的键
        return remove(root, key);
    }


    /**
     * 从集合中获取给定键对应的值并移除该键值对。
     *
     * @param key 用于查找和移除的键。
     * @return 如果找到且成功移除，则返回对应的值；如果未找到，则抛出运行时异常。
     * @throws RuntimeException 如果给定的键无效（即不存在于集合中）。
     */
    @Override
    public V getAndRemove(K key) {
        V result = get(key); // 尝试获取给定键对应的值

        if (root == null) {
            throw new RuntimeException("Invalid key"); // 如果树为空，表示未找到键，抛出异常
        } else {
            // 尝试移除键值对
            if (remove(root, key)) {
                return result; // 移除成功，返回对应的值
            } else {
                throw new RuntimeException("Invalid key"); // 移除失败（即键不存在），抛出异常
            }
        }
    }

    /**
     * 从红黑树中移除指定键的节点
     *
     * @param node 根节点
     * @param key 要移除的键
     * @return 如果成功移除节点，返回true；否则返回false
     */
    private boolean remove(Node<K,V> node, K key) {
        assert node != null;
        if (key != node.key) {
            if (compare(key, node.key) < 0) {
                //Node<K, V> _left = node.left;
                if (node.left != null && remove(node.left, key)) {
                    maintainRelationship(node);
                    return true;
                } else {
                    return false;
                }
            } else if (compare(key, node.key) > 0){
                //Node<K, V> _right = node.right;
                if (node.right != null && remove(node.right, key)) {
                    maintainRelationship(node);
                    return true;
                } else {
                    return false;
                }
            }
        }

        assert key.equals(node.key);

        if (size == 1) {
            this.clear();
            return true;
        }

        if (node.left != null && node.right != null) {
            Node<K, V> successor = node.right;
            Node<K, V> parent = node;
            while (successor.left != null) {
                parent = successor;
                successor = parent.left;
            }

            swapNode(node, successor);
            maintainRelationship(parent);
        }

        if (node.isLeaf()) {
            assert node.parent != null;

            if (node.isBlack()) {
                maintainAfterRemove(node);
            }
            if (node.direction() == Direction.LEFT) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

        } else {
            assert node.left == null || node.right == null;

            Node<K, V> parent = node.parent;
            Node<K, V> repacement = node.left != null ? node.left : node.right;

            switch (node.direction()) {
                case ROOT:
                    root = repacement;
                    break;
                case LEFT:
                    parent.left = repacement;
                    break;
                case RIGHT:
                    parent.right = repacement;
                    break;
            }

            if (node.isBlack()) {
                if (repacement.isRed()) {
                    repacement.color = Color.BLACK;
                } else {
                    maintainAfterRemove(repacement);
                }
            }
        }
        size--;
        return true;
    }

    /**
     * 交换两个节点的键值对。
     * @param lhs 第一个节点，左操作数
     * @param rhs 第二个节点，右操作数
     */
    private void swapNode(Node<K, V> lhs, Node<K, V> rhs) {
        // 临时保存lhs节点的键和值
        K tempKey = lhs.key;
        V tempValue = lhs.value;

        // 将rhs节点的键和值赋给lhs节点
        lhs.key = rhs.key;
        lhs.value = rhs.value;

        // 将之前保存的lhs节点的键和值赋给rhs节点
        rhs.key = tempKey;
        rhs.value = tempValue;
    }

    /**
     * 在删除操作后维护红黑树的性质。
     *
     * @param node 被删除节点的子节点，用于维持红黑树的性质
     */
    private void maintainAfterRemove(Node<K, V> node) {
        // 根节点无需维护
        if (node.isRoot()) {
            return;
        }
        // 断言：当前节点是黑色且有兄弟节点
        assert node.isBlack() && node.hasSibling();

        // 当前节点相对于父节点的方向
        Direction direction = node.direction();

        // 获取兄弟节点
        Node<K, V> sibling = node.sibling();

        if (sibling.isRed()) {
            Node<K, V> parent = node.parent;
            assert parent != null && parent.isBlack();
            assert sibling.left != null && sibling.left.isBlack();
            assert sibling.right != null && sibling.right.isBlack();

            rotateSameDirection(node.parent, direction);
            sibling.color = Color.BLACK;
            parent.color = Color.RED;

            sibling = node.sibling();
        }

        Node<K, V> closeNephew = direction == Direction.LEFT ? sibling.left : sibling.right;
        Node<K, V> distantNephew = direction == Direction.LEFT ? sibling.right : sibling.left;

        boolean closeNephewIsBlack = closeNephew == null || closeNephew.isBlack();
        boolean distantNephewIsBlack = distantNephew == null || distantNephew.isBlack();

        assert sibling.isBlack();

        if (closeNephewIsBlack && distantNephewIsBlack) {
            if (node.parent.isRed()) {
                sibling.color = Color.RED;
                node.parent.color = Color.BLACK;
            } else {
                sibling.color = Color.RED;
                maintainAfterRemove(node.parent);
            }
        } else {
            if (closeNephew != null && closeNephew.isRed()) {
                rotateOppositeDirection(sibling, direction);
                closeNephew.color = Color.BLACK;
                sibling.color = Color.RED;
                sibling = node.sibling();

                closeNephew = direction == Direction.LEFT ? sibling.left : sibling.right;
                distantNephew = direction == Direction.LEFT ? sibling.right : sibling.left;
            }

            assert closeNephew == null || closeNephew.isBlack();
            if (distantNephew != null) {
                assert distantNephew.isRed();
            }

            rotateSameDirection(node.parent, direction);

            sibling.color = node.parent.color;
            node.parent.color = Color.BLACK;

            if (distantNephew != null) {
                distantNephew.color = Color.BLACK;
            }
        }
    }

    /**
     * 根据指定方向对节点进行旋转。
     * 该函数用于当需要对某个节点进行左旋转或右旋转时的调用，旋转操作的具体方向由参数 direction 指定。
     *
     * @param node 需要进行旋转的节点，类型为 Node<K, V>，是红黑树中的一个节点。
     * @param direction 旋转的方向，由 Direction 枚举类型指定。不能为 Direction.ROOT，因为根节点不需要旋转。
     *                  Direction.LEFT 表示左旋转，Direction.RIGHT 表示右旋转。
     * 注意：该方法不返回任何值，其作用是修改节点的指向，以保持红黑树的性质。
     */
    private void rotateSameDirection(Node<K, V> node, Direction direction) {
        assert direction != Direction.ROOT; // 确保方向不为根节点方向

        // 根据方向执行相应的旋转操作
        if (direction == Direction.LEFT) {
            rotateLeft(node);
        } else {
            rotateRight(node);
        }
    }

    /**
     * 根据给定的方向，对指定节点进行反向旋转。
     * 该操作主要用于平衡红黑树，在插入或删除操作后，可能需要对树进行调整，以保持其平衡。
     *
     * @param node 需要进行旋转操作的节点。
     * @param direction 方向，指定是向左还是向右旋转。注意，方向不能为ROOT，因为根节点不需要旋转。
     */
    private void rotateOppositeDirection(Node<K, V> node, Direction direction) {
        assert direction != Direction.ROOT; // 确保方向不是ROOT，因为根节点不需要旋转。

        // 根据输入的方向执行反向的旋转操作。
        if (direction == Direction.LEFT) {
            rotateRight(node);
        } else {
            rotateLeft(node);
        }
    }


    /**
     * 将键值对插入到二叉搜索树中。
     * 如果树中已存在与给定键相等的节点，并且replace为true，则用新的值替换原有的值。
     * @param node 当前正在检查的节点
     * @param key 需要插入的键
     * @param value 需要插入的值
     * @param replace 如果为true，且遇到相等的键，则替换原有值
     */
    private void insert(Node<K,V> node, K key, V value, boolean replace) {
        assert node != null;
        // 如果key与当前节点的key相等，根据replace标志决定是否替换值
        if (key.equals(node.key)) {
            if (replace) {
                node.value = value;
            }
            return;
        }

        assert !key.equals(node.key);

        // 根据key与当前节点key的比较结果，决定向左子树还是右子树插入
        if (compare(key, node.key) < 0) {
            // key小于node.key，向左子树插入
            if (node.left == null) {
                Node<K, V> newNode = new Node<>(key, value);
                newNode.parent = node;
                node.left = newNode;
                maintainAfterInsert(newNode); // 保持树的性质
                size++; // 更新树的大小
            } else {
                insert(node.left, key, value, replace);
            }
        } else {
            // key大于node.key，向右子树插入
            if (node.right == null) {
                Node<K, V> newNode = new Node<>(key, value);
                newNode.parent = node;
                node.right = newNode;
                maintainAfterInsert(newNode); // 保持树的性质
                size++; // 更新树的大小
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

    /**
     * 在插入节点后，维护红黑树的性质
     * @param node 需要维护的节点
     */
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
    private void rotateLeft (Node<K,V> node) {
        assert(node != null && node.right != null); // 确保节点及其右子树不为空

        Node<K,V> parent = node.parent; // 保存当前节点的父节点
        Direction direction = node.direction(); // 保存当前节点在父节点中的方向（左或右子节点，或根节点）

        Node<K,V> successor = node.right; // 成功者节点，即原节点的右子节点，将成为新的当前节点
        node.right = successor.left; // 将原节点的右子树挂到成功者节点的左部
        successor.left = node;


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
    private void rotateRight (Node<K,V> node) {
        assert(node != null && node.left != null);

        Node<K,V> parent = node.parent;
        Direction direction = node.direction();

        Node<K,V> predecessor = node.left;
        node.left = predecessor.right;
        predecessor.right = node;


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
    private static void maintainRelationship(Node node) {
        // 为左子节点设置父节点
        if (node.left != null) {
            node.left.parent = node;
        }
        // 为右子节点设置父节点
        if (node.right != null) {
            node.right.parent = node;
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
     * 中序遍历二叉树并把节点值存储到双向链表中。
     * 该方法不接受参数，也不返回任何值。
     * 遍历顺序为：先遍历左子树，然后访问当前节点，最后遍历右子树。
     * 在遍历过程中，使用栈来辅助实现。
     */
    private void inorderTraversal() {
        if (root == null) {
            return;
        }

        entryList = new DoublyListImpl<>();

        // 使用栈来辅助进行中序遍历
        // 鼠鼠的栈
        Stack.Stacks.Stack<Node<K, V>> stack = new ListNodeStackImpl<>();

        Node<K,V> current = root;

        // 当前节点不为空或者栈不为空时继续遍历
        while (current != null || !stack.empty()) {
            // 遍历当前节点的左子树，将所有左节点压入栈中
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            // 当前节点为空时，栈顶元素即为刚刚遍历完的左子树的最右节点
            if (!stack.empty()) {
                current = stack.peek();
                stack.pop();
                // 将节点值添加到链表中
                entryList.append(current.entry());
                // 遍历右子树
                current = current.right;
            }

        }
    }

}
