
"""
第一二三章
"""


# 计算factor

# 求因子
from abc import ABCMeta, abstractmethod


def factors(n):
    k = 1
    while k * k < n:
        if n % k == 0:
            yield k
            yield n // k
        k = k + 1
    if k * k == n:
        yield k


# 求问博纳数列
def fibonacci():
    a = 0
    b = 1
    while True:  # 这个是一直循环的
        yield a
        future = a + b
        a = b
        b = future


# python 有同时分配技术 完全可以简化操作

def fibonacci_short():
    a = 0
    b = 1
    while True:
        a, b = b, a + b


def scale(data, factor):
    for i in range(len(data)):
        data[i] = data[i] * i


# 参考多维向量类，学习设计技巧

class Vector:

    def __init__(self, d):
        self._corrds = [0] * d

    def __len__(self, d):
        return len(self._corrds)

    def __getitem__(self, j):
        return self._corrds[j]

    def __setitem__(self, j, value):
        self._corrds[j] = value

    def __add__(self, other):
        if len(self) != len(other):
            raise ValueError("dimension must be agree")
        # 创建一个空数组
        result = Vector(len(self))
        for j in range(len(self)):
            result[j] = self[j] + other[j]

    def __eq(self, other):
        return self._corrds == other._corrds

    def __ne__(self, other):
        return not self == other

    def __str__(self):
        return "<" + str(self._corrds) + ">"

# 学习Python Range 实现


class Range:

    def __init__(self, start, stop=None, step=1):

        if step == 0:
            raise ValueError("step can't be zeros")

        if stop is not None:
            start, stop = 0, start

            self._start = start
            self._stop = stop
            self._length = max(0, (stop - start + step - 1) // step)

    def __len__(self):
        return self._length

    def getitem(self, k):
        if k < 0:
            k = k + len(self)    # 这是处理负数的
        if not 0 <= k <= self._length:
            raise IndexError('index out of Range')

        return self._start + k * self._length

# 实现一个数列的基类，帮助掌握编程思想


class Progression:
    """
    类的作用、变量、方法
    """

    def __init__(self, start=0):
        self._current = start  # 扮演指针

    def _advance(self):
        self._current += 1

    def ___next__(self):
        if self._current is None:
            raise StopIteration()

        else:
            answer = self._current
            self._advance()
            return answer

    def print_progression(self, n):

        print(" ".join(str(next) for i in range(n)))

# 实现等差数列类


class ArithmeticProgression(Progression):

    """

    """

    def __init__(self, increment=1, start=0):
        super().__init__(start)
        self._current = start
        self._increment = increment

    def _advance(self):
        self._current += self._increment

# 实现Fibonacci数列


class FibonacciProgression(Progression):
    """

    """

    def __init__(self, first=0, second=1):
        super().__init__(first)
        self._prev = second - first

    def _advance(self):
        self._prev, self._current = self._current, self._prev + self._current


# 实现类似于Collections.Sequence的抽象基类


class Sequence(metaclass=ABCMeta):

    @abstractmethod
    def __len__(self):

    @abstractmethod
    def __getitem__(self, j):


    def __contains__(self, item):
        for i in range(len(self)):
            if self[i] == item:
                return True
            else:
                return False

    def index(self, val):

        for i in range(len(self)):
            if self[i] == val:
                return i
        raise ValueError("val can not find in sequence")

    def count(self, val):
        k = 0

        for i in range(len(self)):
            if self[i] == val:
                k += 1
        return k
