# 首先完成一个树结构的抽象基类

import LinkList as ll


class Tree:

    class Position:

        def element(self):

            raise NotImplementedError("must be implemented by subclass")

        def __eq__(self, other):
            """return True if other does not represents the same location"""
            raise NotImplementedError("must be implemented by subclass")

        def __ne__(self, other):

            return not (self == other)

    def root(self):
        raise NotImplementedError("must be implemented by subclass")

    def parent(self, p):
        raise NotImplementedError("must be implemented by subclass")

    def num_children(self, p):
        raise NotImplementedError("must be implemented by subclass")

    def children(self, p):
        raise NotImplementedError("must be implemented by subclass")

    def __len__(self):
        raise NotImplementedError("must be implemented by subclass")

    def is_root(self, p):
        return self.root() == p

    def is_leaf(self, p):
        return self.num_children(p) == 0

    def is_empty(self):
        return len(self) == 0

    def depth(self, p):

        if self.is_root(p):
            return 0
        else:
            return 1 + self.depth(self.parent(p))

    def height(self, p=None):

        def _height(point):
            if self.is_leaf(point):
                return 0
            else:
                return 1 + max(_height(c) for c in self.children(point))

        if p is None:
            p = self.root()
        return _height(point=p)

    # 加入搜索算法

    # 先序遍历
    def _subtree_preorder(self, p):
        """依赖生成器递归输出所有子树节点的位置"""
        yield p  # 先序和后序最关键的区别
        for c in self.children(p):
            for other in self._subtree_preorder(c):
                yield other

    def preorder(self):
        """generate a preorder iteration of positions in the tree"""
        if not self.is_empty():
            for p in self._subtree_preorder(self.root()):
                yield p

    def positions(self):
        """generate an iteration of the tree's positions'"""
        return self.preorder()

    # 后序遍历
    def postorder(self):
        if not self.is_empty():
            for p in self._subtree_postorder(self.root()):
                yield p

    def _subtree_postorder(self, p):
        for c in self.children(p):
            for other in self._subtree_postorder(c):
                yield other
        yield p

    def breadthfirst(self):
        if not self.is_empty():
            fringe = ll.LinkedQueue()
            fringe.enqueue(self.root())
            while not fringe.is_empty():  # 只要这个条件不满足就会一直迭代，感觉到非同一般的优雅
                p = fringe.dequeue()
                yield p
                for c in self.children(p):
                    fringe.enqueue(c)

    # 二叉树


class BinaryTree(Tree):

    def left(self, p):
        """return a position representing p's left child"""
        raise NotImplementedError("must be implemented by subclass")

    def right(self, p):
        raise NotImplementedError("must be implemented by subclass")

    def sibling(self, p):
        """回到p兄弟节点的位置"""
        parent = self.parent(p)
        if parent is None:
            return None
        else:
            if p == self.left(parent):
                return self.right(parent)
            else:
                return self.left(parent)

    def children(self, p):
        if self.left(p) is not None:
            yield self.left(p)
        if self.right(p) is not None:
            yield self.right(p)

    # 中序遍历, 因为其特殊性，只适合用于二叉树
    def inorder(self):
        """generate an inorder iteration of position of the tree"""
        if not self.is_empty():
            for p in self._sub_tree_inorder(self.root()):
                yield p

    def _sub_tree_inorder(self, p):
        if self.left(p) is not None:
            for other in self._sub_tree_inorder(self.left(p)):
                yield other
        yield p
        if self.right(p) is not None:
            for other in self._sub_tree_inorder(self.right(p)):
                yield other

    def positions(self):
        return self.inorder()


# 用链式实现二叉树ADT结构

class LinkedBinaryTree(BinaryTree):

    class _Node:
        __slots__ = '_element', '_parent', '_left', '_right'

        def __init__(self, element, parent, left, right):
            self._element = element
            self._parent = parent
            self._left = left
            self._right = right

    class Position(BinaryTree.Position):

        def __init__(self, container, node):
            self._container = container
            self._node = node

        def elements(self):
            return self._node._element

        def __eq__(self, other):
            return isinstance(other, type(self)) and other._node is self._node

    def _validate(self, p):
        if not isinstance(p, self.Position):
            raise TypeError("Invalid position")
        if p._container is not self:
            raise ValueError("p does not belong to this container")
        if p._node._parent is p._node:
            raise ValueError("p is no longer valid")
        return p._node

    def _make_position(self, node):
        return self.Position(self, node) if node is not None else None

    def __init__(self):
        self._root = None
        self._size = 0

    def __len__(self):
        return self._size

    def root(self):
        self._make_position(self._root)

    def parent(self, p):
        node = self._validate(p)
        # 用class position来包装节点
        return self._make_position(node._parent)

    def left(self, p):
        node = self._validate(p)
        return self._make_position(node._left)

    def _right(self, p):
        node = self._validate(p)
        return self._make_position(node._right)

    def num_children(self, p):
        node = self._validate(p)
        count = 0
        if node._left is not None:
            count += 1
        if node._right is not None:
            count += 1
        return count

    # 之后就是非公开的二叉树更新方法了

    def _add_root(self, e):
        if self._root is not None:
            raise ValueError("Root already exists")
        self._size = 1
        self._root = self._Node(e)
        return self._make_position(self._root)

    def _add_left(self, p, e):
        """create a new left child node for Position P with e"""
        node = self._validate(p)
        if node._left is not None:
            raise ValueError("Left already exists")
        self._size += 1
        node._left = self._Node(e, node)
        return self._make_position(node._left)

    def _add_right(self, p, e):
        node = self._validate(p)
        if node._right is not None:
            raise ValueError("Right already exists")
        self._size += 1
        node._left = self._Node(e, node)
        return self._make_position(node._right)

    def _replace(self, p, e):
        """return the element at position P with e, and return the old element"""
        node = self._validate(p)
        old_value = node._element
        node._element = e
        return old_value

    def _delete(self, p):
        """delete the node at position p and replace it with its child"""
        node = self._validate(p)
        if self.num_children(p) == 2:
            raise ValueError("p has two children")
        child = node._left if node._left else node._right
        if child is not None:
            # 已经更改了child的父亲，后面要修正新父亲的儿子
            child._parent = node._parent
        if node is self._root:
            self._root = child
        else:
            parent = node._parent
            if node is parent._left:
                parent._left = child
            else:
                parent._right = child
        self._size -= 1
        node._parent = node  # 儿子没有了，父亲改为了自己，会被内存回收
        return node._element

    def _attach(self, p, t1, t2):
        """attach trees t1 and t2 as left and right subtrees of external a"""
        node = self._validate(p)
        if not self.is_leaf(p):
            raise ValueError("p is not a leaf")
        if not type(self) is type(t1) is type(t2):
            raise TypeError("Three trees must have the same type")
        self._size += len(t1) + len(t2)
        if not t1.is_empty():
            t1._root._parent = p
            node._left = t1._root
            t1._root = None
            t1._size = 0
        if not t2.is_empty():
            t2._root._parent = p
            node._right = t2._root
            t2._root = None
            t2._size = 0

# 欧拉遍历，实际上是一个将先序遍历和后序遍历结合起来的遍历方法,提供基类


class EulerTour:

    def __init__(self, tree):

        self._tree = tree

    def tree(self):
        return self._tree

    def execute(self):
        """perform the tour and return any result from post visit of root"""
        if len(self._tree) > 0:
            return self._tour(self._tree.root(), 0, [])

    def _hook_previsit(self, p, d, path):
        pass

    def _hook_postvisit(self, p, d, path, result):
        pass

    def _tour(self, p, d, path):
        """
        perfrom the tour and return any result from post visit of root
        :param p:  position of current node being visted
        :param d: depth of p in the tree
        :param path: list of indices of chldren on path from root to p
        :return:
        """
        self._hook_previsit(p, d, path)
        result = []
        path.append(0)
        for c in self._tree.children(p):
            result.append(self._tour(c, d + 1, path))
            path[-1] += 1  # increment index
        path.pop()
        answer = self._hook_postvisit(p, d, path, result)  # "post visit p"
        return answer

# 二叉树的欧拉遍历,这个很难，没看懂，跳


class BinaryEulerTour(EulerTour):

    def _tour(self, p, d, path):
        result = [None, None]  # update result with recursion
        self._hook_previsit(p, d, path)
        if self._tree.left(p) is not None:
            path.append(0)
            result[0] = self._tour(self._tree, d + 1, path)
            path.pop()
        self._hook_invisit(p, d, path)
        if self._tree.right(p) is not None:
            path.append(1)
            result[1] = self._tour(self._tree, d + 1, path)
            path.pop()
        answer = self._hook_postvisit(p, d, path, result)
        return answer

    def _hook_invisit(self, p, d, path):
        pass

# 中序遍历，解释树


class ExpressionTree(LinkedBinaryTree):

    def __init__(self, token, left=None, right=None):
        super().__init__()
        if not isinstance(token, self):
            raise TypeError("Token must be string")

        if left is not None:
            if token not in '+-*x/':
                raise ValueError("token must be vaild operator")
            self._attach(self.root(), left, right)

        def __str__(self):
            pieces = []
            self._parentesize_recur(self.root(), pieces)
            return ''.join(pieces)

        def _parentesize_recur(self, p, result):
            """append piecewise representation of p's subtree to resulting list"""
            if self.is_leaf(p):
                result.append(str(p.element))
            else:
                result.append("(")
                self._parentesize_recur(self.left(p), result)
                result.append(p.element())
                self._parentesize_recur(self.right(p), result)
                result.append(")")

        def evaluate(self):
            return self._evaluate_recur(self.root())

        def _evaluate_recur(self, p):
            if self.is_leaf(p):
                return float(p.element())
            else:
                op = p.element()
                left_val = self._evaluate_recur(self.left(p))
                right_val = self._evaluate_recur(self.right(p))
                if op == "+":
                    return left_val + right_val
                if op == "-":
                    return left_val - right_val
                if op == "/":
                    return left_val / right_val
                else:
                    return left_val * right_val
