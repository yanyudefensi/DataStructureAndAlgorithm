import ctypes


class DynamicArray:

    def init(self):
        self._n = 0
        self._capacity = 1
        self._A = self._make_array(self._capacity)

    def __len__(self):
        return self._n

    def __getitem__(self, i):
        if not 0 <= i < self._n:
            raise IndexError("invalid index")
        return self._A[k]

    def append(self, obj):

        if self._n == self._capacity:
            self._resize(2 * self._capacity)
        self._A[self._n] = obj

    def insert(self, k, value):

        if self._n < self._capacity:
            self._resize(2 * self._capacity)
        for j in range(self._n, k, -1):
            self._A[j] = self._A[j - 1]
        self._A[k] = value
        self._n += 1

    def remove(self, value):
        for k in range(self._n):
            if self._A[k] == value:
                for j in range(k, self._n - 1):
                    self._A[j] = self._A[j + 1]
                self._A[self._n - 1] = None
                self._n -= 1
                return
        raise ValueError("Not found value")

    def _risize(self, c):
        B = self._make_array(c)
        for k in range(self._n):
            B[k] = self._A[k]
            self._A = B
            self._capacity = c

    def _make_array(self, c):
        return (c * ctypes.py_object)()

# 设计凯撒密码


class CasearCiper:

    def __init__(self, shift):
        encoder = [None] * 26
        decoder = [None] * 26
        for k in range(26):
            encoder[k] = chr((k + shift) % 26 + ord("A"))
            decoder[k] = chr((k - shift) % 26 + ord("A"))
        self._forward = "".join(encoder)
        self._backward = "".join(decoder)

    def encrypt(self, message):
        return self._transform(message, self._forward)

    def decrypt(self, secret):
        return self._transform(secret, self._backward)

    def _transform(self, message, code):
        msg = list(message)
        for k in range(len(msg)):
            if msg[k].isupper():
                j = ord(msg[k]) - ord('A')
                msg[k] = code[j]
        return "".join(msg)


def InsertionSort(A: list):
    for i in range(1, len(A)):
        item = A[i]
        j = i

        while j > 0 and A[j - 1] > item:
            A[j] = A[j - 1]
            j = j - 1

        A[j] = item

    return print(A)


if __name__ == "__main__":
    print(chr(ord("A")))
