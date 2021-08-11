# 为了确保了解底层算法原理，还是要多用C语言实现
class arrayStack:

    def __init__(self):
        self._data = []

    def __len__(self):
        return len(self._data)

    def is_empty(self):
        return len(self._data) == 0

    def push(self, e):
        self._data.append(e)

    def top(self):
        if self.is_empty():
            raise IndexError("Data has no items")

        return self._data[-1]

    def pop(self):
        if self.is_empty:
            raise IndexError("Data has no items")
        self._data.pop()


# 可以利用栈的特性实现数据的逆置

def reverse_file(filename):
    S = arrayStack()
    original = open(filename)
    for line in original:
        S.push(line + "\n")
    original.close()

    outputs = open(filename, "w")
    while not S.is_empty():
        outputs.write(S.pop() + "\n")
    outputs.close()


def is_matched_html(raw):
    S = arrayStack()
    first_punctuate = raw.find("<")
    while first_punctuate != -1:
        second_punctuate = raw.find(">", first_punctuate + 1)
        if second_punctuate != -1:
            tag = raw[first_punctuate, second_punctuate]
            if not tag.startswith("/"):
                S.push(tag)
            else:
                if S.is_empty():
                    return False
                if tag[1:] != S.pop():
                    return False
        first_punctuate = raw.find("<", second_punctuate + 1)

    return S.is_empty()


class ArrayQueue:
    # size 指的是队列长度
    # len(self._data) 指的是容量
    # self._front 扮演的就是一个头部指针
    DEFAULT_CAPACITY = 10

    def __init__(self):
        self._data = [None] * ArrayQueue.DEFAULT_CAPACITY
        self._size = 0
        self._front = 0

    def __len__(self):
        return self._size

    def is_empty(self):
        return self._size == 0

    def _resize(self, cap):

        # 注意这里需要一个头尾相连的数列，因此要取模

        old = self._data
        self._data = [None] * cap
        walk = self._front
        for k in range(self._size):
            self._data[k] = old[walk]
            walk = (1 + walk) % len(self._data)
        # 最后还要记得把指针至零
        self._front = 0

    def first(self):
        """

        :return the first element at the front of the queue
        """

        if self.is_empty():
            raise IndexError("Queue has no items")
        return self._data[self._front]

    def dequeue(self):
        if self.is_empty():
            raise IndexError("Queue is empty")
        answer = self._data[self._front]
        self._data[self._front] = None
        self._front = (self._front + 1) % len(self._data)
        self._size -= 1

        # 动态缩减数列，降低数组存储消耗
        if 0 < self._size < len(self._data) // 4:
            self._resize(len(self._data) // 2)
        return answer

    def enqueue(self, e):
        if self._size == len(self._data):
            self._resize(2 * len(self._data))
        avail = ((self._front + self._size) % len(self._data))
        self._data[avail] = e
        self._size += 1
