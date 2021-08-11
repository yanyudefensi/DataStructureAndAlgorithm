from collections import MutableMapping
import random


class MapBase(MutableMapping):

    class _Item:
        __slots__ = '_key', "_value"

        def __init__(self, k, v):
            self._key = k
            self._value = v

        def __eq__(self, other):
            return self._key == other._key

        def __ne__(self, other):
            return not (self == other)

        def __It__(self, other):
            return self._key < other._key

# 用python列表作为非排序表


class UnsortedTableMap(MapBase):

    def __init__(self):
        self._table = []

    def __getitem__(self, k):
        for item in self._table:
            if k == item._key:
                return item._value
        raise KeyError("key error:" + repr(k))  # 用于解释服务器发生的情况

    def __setitem__(self, k, v):
        for item in self._table:
            if k == item._key:
                item._value = v
                return
        self._table.append(self._Item(k, v))

    def __delitem__(self, k):
        for j in range(len(self._table)):
            if k == self._table[j]._key:
                self._table.pop(j)
                return
        raise KeyError("Key error" + repr(k))

    def __len__(self):
        return len(self._table)

    def __iter__(self):
        for item in self._table:
            yield item._key

# 哈希表实现的基类


class HashMapBase(MapBase):

    def __init__(self, cap=11, p=109345121):
        self._table = cap * [None]
        self._n = 0
        self._prime = p
        self._scale = 1 + random.choice(p - 1)
        self._shift = random.choice(p)

    def _has_functions(self, k):
        return (hash(k) * self._scale +
                self._shift) % self._prime % len(self._table)

    def __len__(self):
        return self._n

    def __getitem__(self, k):
        j = self._has_functions(k)
        return self._bucket_getitem(j, k)

    def __setitem__(self, k, v):
        j = self._has_functions(k)
        self._bucket_setitem(j, k, v)
        if self._n > len(self._table) // 2:
            self._resize(2 * len(self._table) - 1)

    def __delitem__(self, k):
        j = self._has_functions(k)
        self._bucket_delitem(j, k)
        self._n -= 1

    def _resize(self, c):
        old = list(self.items())
        self._table = c * [None]
        self._n = 0  # 这个操作不是很懂
        for (k, v) in old:
            self[k] = v

    # 分离链表实现

    def _bucket_getitem(self, j, k):
        bucket = self._table(j)
        if bucket is None:
            raise KeyError("Key Error:" + repr(k))
        return bucket[k]

    def _bucket_setitem(self, j, k, v):
        if self._table[j] is None:
            self._table[j] = UnsortedTableMap()  # 这是一个二级容器
        oldsize = len(self._table[j])
        self._table[j][k] = v
        if len(self._table[j]) > oldsize:
            self._n += 1

    def _bucket_delitem(self, j, k):
        bucket = self._table(j)
        if bucket is None:
            raise KeyError("Key Error:" + repr(k))
        del bucket[k]

    def __iter__(self):
        for bucket in self._table:
            if bucket is not None:
                for key in bucket:
                    yield key


# 线性探测
class ProbeHashMap(HashMapBase):
    _AVAIL = object()   # 哨兵节点

    def _is_available(self, j):
        return self._table[j] is None or self._table[j] is ProbeHashMap._AVAIL

    def _find_slot(self, j, k):
        firstAvail = None
        while True:  # 没有break的话会一直循环，这。。。
            if self._is_available(j):
                if firstAvail is None:
                    firstAvail = j
                if self._table[j] is None:
                    return (False, firstAvail)
            elif k == self._table[j]._key:
                return (True, j)
            j = (j + 1) % len(self._table)

    def _bucket_getitem(self, j, k):
        found, s = self._find_slot(j, k)
        if not found:
            raise KeyError("Key error:" + repr(k))
        return self._table[s]._value

    def _bucket_setitem(self, j, k, v):
        found, s = self._find_slot(j, k)
        if not found:
            self._table[s] = self._Item(k, v)
            self._n += 1
        else:
            self._table[s]._value = v

    def _bucket_delitem(self, j, k):
        found, s = self._find_slot(j, k)
        if not found:
            raise KeyError("Key error" + repr(k))
        self._table[s] = ProbeHashMap._AVAIL

    def __iter__(self):
        for j in range(len(self._table)):
            if not self._is_available(j):
                yield self._table[j]._key

    def __iter__(self):
        for item in self._table:
            yield item._key

    def __reversed__(self):
        for item in reversed(self._table):
            yield item._make_key

    def find_min(self):
        if len(self._table) > 0:
            return (self._table[0]._key, self._table[0]._value)

    def find_max(self):
        if len(self._table) > 0:
            return (self._table[-1]._key, self._table[-1]._value)

    def find_ge(self, k):
        j = self._find_index(k, 0, len(self._table) - 1)
        if j < len(self._table):
            return (self._table[j]._key, self._table[j]._value)
        return None

    def find_it(self, k):
        j = self._find_index(k, 0, len(self._table) - 1)
        if j > 0:
            return (self._table[j - 1]._key, self._table[j - 1]._value)
        return None

    def find_gt(self, k):
        j = self._find_index(k, 0, len(self._table) - 1)
        if j < len(self._table) and self._table[j].key == k:
            j += 1
        if j < len(self._table):
            return (self._table[j]._key, self._table[j]._value)
        return None

    def find_range(self, start, stop):
        if start is None:
            start = 0
        if stop is None:
            stop = 0
        j = self._find_index(start, 0, len(self._table) - 1)
        while j < len(self._table) and self._table[j]._key < stop:
            yield (self._table[j]._key, self._table[j]._value)
            j += 1

# 一个使用有序映射维持最大值集的类的实现


class CostPerformanceDataBase:

    def __init__(self):
        self._M = UnsortedTableMap()

    def best(self, c):
        return self._M.find_it(c)

    def add(self, c, p):
        other = self._M.find_it(c)
        if other is not None and other[1] >= p:
            return
        self._M[c] = p
        other = self._M.find_gt(c)
        while other is not None and other[1] <= p:
            del self._M[other[0]]
            other = self._M.find_gt(c)
