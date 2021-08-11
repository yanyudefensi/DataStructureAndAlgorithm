

# 单项链表实现栈
class LinkedStack:

    # 轻量级节点类

    class _Node:
        __slots__ = '_element', '_next'

        def __init__(self, element, next):  # 初始化一个新节点
            self._element = element
            self._next = next

    # 栈堆
    def __init__(self):
        self._head = None
        self._size = 0

    def __len__(self):
        return self._size

    def is_empty(self):
        return self._size == 0

    def push(self, e):
        self._head = self._Node(e, self._head)  # self._head 变成了一个指向实例化Node的指针
        self._size += 1

    def top(self):

        if self.is_empty():
            raise IndexError("stack is empty")
        return self._head._element

    def pop(self):

        if self.is_empty():
            raise IndexError("stack si empty")
        answer = self._head._element
        self._head = self._head.next
        self._size -= 1
        return answer

# 单项列表实现队列


class LinkQueue:

    class _Node:
        __slots__ = '_element', '_next'

        def __init__(self, element, next):  # 初始化一个新节点
            self._element = element  #
            self._next = next

    def __init__(self):

        self._head = None
        self._tail = None
        self._size = 0

    def __len__(self):

        return self._size

    def is_empty(self):

        return self._size == 0

    def first(self):

        if self.is_empty():
            raise IndexError("Queue is empty")
        answer = self._head._element

    def dequeue(self):

        if self.is_empty():
            raise IndexError("Queue is empty")
        answer = self._head._element
        self._head = self._head._next
        self._size -= 1
        # 注意这里还要加一个判分
        if self.is_empty():
            self._tail = None
        return answer

    def enqueue(self, e):
        newest = self._Node(e, None)
        if self.is_empty():
            self._head = newest
        else:
            self._tail_next = newest

        self._tail = newest
        self._size += 1


# 循环列表实现队列

class CircularQueue:

    class _Node:
        __slots__ = '_element', '_next'

        def __init__(self, element, next):  # 初始化一个新节点
            self._element = element  #
            self._next = next

    def __init__(self):

        self._tail = None
        self._size = 0

    def __len__(self):

        return self._size

    def is_empty(self):

        return self._size == 0

    def first(self):

        if self.is_empty():
            raise IndexError("CircularQueue is empty")
        head = self._tail._next
        return head._element

    def dequeue(self):

        if self.is_empty():
            raise IndexError("CircularQueue is empty")

        oldhead = self._tail._next  # 头部元素
        if self._size == 1:
            self._tail = None
        else:
            self._tail._next = oldhead._next
        self._size -= 1
        return oldhead._element

    def enqueue(self, e):
        newest = self._Node(e, None)
        if self.is_empty():
            newest._next = newest
        else:
            newest._next = self._tail._next
        self._tail._next = newest
        self._size += 1

   # 可以将删除队首的操作和将该元素插入到尾部的操作合并处理，即头部变尾部
    def rotate(self):

        if self._size > 0:
            self._tail = self._tail._next


class _DoublyLinkedBase(LinkQueue):

    class _Node:
        __slots__ = '_element', '_next', '_prev'

        def __init__(self, element, prev, next):
            self._element = element
            self._prev = prev
            self._next = next

    def __init__(self):
        self._header = self._Node(None, None, None)
        self._trailer = self._Node(None, None, None)
        self._header._next = self._trailer
        self._trailer._prev = self._header
        self._size = 0

    def __len__(self):
        return self._size

    def is_empty(self):
        return self._size == 0

    def _insert_between(self, e, predecessor, successor):
        newest = self._Node(e, predecessor, successor)
        predecessor._next = newest
        successor._prev = newest
        self._size += 1
        return newest

    def _delete_between(self, node):

        predecessor = node._prev
        successor = node._next
        predecessor._next = successor
        successor._prev = predecessor
        self._size -= 1
        # 改变了引用节点，还需要删除掉节点本身
        element = node._element
        node._element = node._next = node._prev = None
        return element

# 继承双向链基类而实现的链式双端队列类


class LinkedQueue(_DoublyLinkedBase):

    def first(self):
        if self.is_empty():
            raise IndexError("LinkedDeque is empty")
        return self._header._next._element

    def last(self):
        if self.is_empty():
            raise IndexError("LinkedDeque is empty")
        return self._trailer._prev._element

    def insert_first(self, e):
        self._insert_between(self, e, self._header, self._header._next)

    def insert_last(self, e):
        self._insert_between(self, e, self._trailer._prev, self._trailer)

    def delete_first(self, e):
        if self.is_empty():
            raise IndexError("LinkedDeque is empty")

        return self._delete_between(self, self._header._next)

    def delete_last(self, e):
        if self.is_empty():
            raise IndexError("LinkedDeque is empty")

        return self._delete_between(self, self._trailer._prev)

# 使用双向链表实现一个包含位置信息的抽象数据类型


class PositionalList(_DoublyLinkedBase):

    # 实现位置记录
    class Position:

        def __init__(self, container, node):
            self._container = container
            self._node = node

        def element(self):
            return self._node._element

        def __eq__(self, other):
            """return true if other is a Position representing the same location"""
            return isinstance(other, type(self)) and other._node is self._node

        def __ne__(self, other):
            """return true if other does not represent the same location"""
            return not (self == other)

    def _validate(self, p):
        """return position's node, or raise apporpriate error if invalid"""
        if not isinstance(p, self.position):
            raise TypeError("P msut be proper Position type")
        if p._container is not self._container:
            raise ValueError("p does not belong to this container")
        if p._node._next is None:
            raise ValueError("p is no longer valid")

        return p._node

    def _make_position(self, node):
        if node is self._header or node is self._trailer:
            return None
        else:
            return self.Position(self, node)

    def first(self):
        return self._make_position(self._header._next)

    def last(self):
        return self._make_position(self._trailer._prev)

    def before(self, p):
        """return the position before the position p"""
        node = self._validate(p)
        return self._make_position(node._prev)

    def after(self, p):

        node = self._validate(p)
        return self._make_position(node._next)

    def __iter__(self):
        """generate an iterator of elements in the list"""
        curosr = self.first()
        while curosr is not None:
            yield curosr.element()
            curosr = self.after(curosr)

    def _insert_between(self, e, predecessor, successor):
        node = super()._insert_between(e, predecessor, successor)
        return self._make_position(node)

    def add_first(self, e):
        return self._insert_between(e, self._header, self._header._next)

    def add_last(self, e):
        return self._insert_between(e, self._trailer._prev, self._trailer)

    def add_before(self, p, e):
        original = self._validate(p)
        return self._insert_between(e, original._prev, original)

    def add_after(self, p, e):
        original = self._validate(p)
        return self._insert_between(e, original, original._next)

    def delete(self, p):
        original = self._validate(p)
        return self._delete_between(original)

    def replace(self, p, e):
        original = self._validate(p)
        old_value = original._element
        original._element = e
        return old_value

    # 这个插入排序也不是很懂，需要研究
    def insertion_sort(self, L):
        if len(L) > 1:
            marker = L.first()
            while marker != L.last():
                pivot = L.after(marker)
                value = pivot.element()
                if value > marker.element():
                    marker = pivot
                else:
                    walk = marker
                    while walk != L.first() and L.before(walk).element() > value:
                        walk = L.before(walk)
                    L.delete(pivot)
                    L.add_before(walk, value)

# 类的嵌套非常复杂，要使用统一建模图例管理


class FavoritesList:

    class _Item:
        __slots__ = "_value", "_count"

        def __init__(self, value):
            self._value = value
            self._count = 0

    def __init__(self):
        self._data = PositionalList()

    def __len__(self):
        return self._data._size

    def is_empty(self):
        return self._data._size == 0

    def access(self, e):
        p = self._find_position(e)
        if p is None:
            p = self._data.add_last(self._Item(e))  # 这一个类是用来计数的，便于做启发式分析
        p.element()._count += 1
        self._move_up(p)

    def remove(self, e):
        p = self._find_position(e)
        if p is not None:
            self._data.delete(p)

    def top(self, k):
        """generate sequence of top k elements in terms of access count"""
        if not 1 <= k <= len(self):
            raise ValueError("Illegal value for k")
        walk = self._data.first()
        for j in range(k):
            item = walk.element()
            yield item._value
            walk = self._data.after(walk)

    def _move_up(self, p):
        """Move item in p position earlier in the last based on access account"""
        if p != self._data.first():
            cnt = p.element()._count
            walk = self._data.before(p)
            if cnt > walk.element()._count:
                while(walk != self._data.first and cnt > self._data.before(walk).element()._count):
                    walk = self._data.before(walk)    # 检测出前两个元素都比p值小，游标前移
                self._data.add_before(
                    walk, self._data.delete(p))  # delete and reinsert

    def _find_position(self, e):
        walk = self._data.first()
        while walk is not None and walk.element()._value != e:
            walk = self._data.after(walk)

# 启发式动态列表，降低搜索量


class FavoritesListMTF(FavoritesList):

    def _move_up(self, p):
        """move to front"""
        if p != self._data.first():
            self._data.add_first(self._data.delete(p))

    # 这种排序方法复杂度为nk，因为没有使用标准的排序方法
    def top(self, k):
        if not 1 <= k <= len(self):
            raise ValueError("Illegal value for k")

        # 要根据点击量重排序
        temp = PositionalList()
        for item in self._data:
            temp.add_last(item)

        for j in range(k):
            # 找到最高的count
            highPos = temp.first()
            walk = temp.after(highPos)
            while walk is not None:
                if walk.element()._count > highPos.element().count:
                    highPos = walk
                walk = temp.after(walk)
            yield highPos.element()._value
            temp.delete(highPos)
