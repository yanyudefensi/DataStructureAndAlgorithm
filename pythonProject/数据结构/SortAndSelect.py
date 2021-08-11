from LinkList import LinkedQueue
import math
import random

# 基于list的归并排序
def merge(S1, S2, S):
    """Merge two sorted lists into propererly sized list S"""
    i = j = 0
    while i + j < len(S):
        if j == len(S2) or (i < len(S1) and S1[i] < S2[j]):
            S[i+j] = S1[i] # copy ith element of S1 as next item of S
            i += 1
        else:
            S[i+j] = S2[j] # copy jth element of S2 as next item of S
            j += 1

def merge_sort(S):
    n = len(S)
    if n < 2: return
    mid = n // 2
    S1 = S[0:mid]
    S2 = S[mid:n]
    merge_sort(S1)
    merge_sort(S2)
    merge(S1, S2, S)

# 基于链表队列的归并排序
def merge_link(S1, S2, S):
    while not S1.is_empty() and not S2.is_empty():
        if S1.first() < S2.first():
            S.enqueue(S1.dequeue())
        while not S1.is_empty(): # move remaining elements of S1 to S
            S.enqueue(S1.dequeue())
        while not S2.is_empty(): # move remaining elements of S2 to S
            S.enqueue(S2.dequeue())

def merge_sort_link(S):
    n = len(S)
    if n < 2: return
    S1 = LinkedQueue()
    S2 = LinkedQueue()
    while len(S1) < n // 2:
        S1.enqueue(S.dequeue())
    while not S.is_empty():
        S2.enqueue(S.dequeue())
    merge_sort_link(S1)
    merge_sort_link(S2)
    merge_link(S1, S2, S)

# 非递归的归并排序算法实现,自底向上
def merge_fast(src, result, start, inc):
    """merge src[start:start+inc] and src[start+inc:start+2inc] into result"""
    end1 = start + inc
    end2 = min(start + 2 * inc, len(src))
    x, y, z = start, start + inc, start
    while x < end1 and y < end2:
        if src(x) < src(y):
            result[z] = src[x]
            x += 1
        else:
            result[z] = src[y]
            y += 1
        z += 1
    if x < end1:
        result[z:end2] = src[x:end1]
    elif y < end2:
        result[z:end2] = src[y:end2]

def merge_sort_fast(S):
    n = len(S)
    logn = math.ceil(math.log(n, 2))
    src, dest = S, [None] * n
    for i in (2**k for k in range(logn)):
        for j in range(0, n, 2*i):
            merge(src, dest, j, i)
        src, dest = dest, src
    if S not in src:
        S[:n] = src[:n]

# 队列的序列S的快速排序实现
def quick_sort(S):
    n = len(S)
    if n < 2: return
    p = S.first()
    L = LinkedQueue()
    E = LinkedQueue()
    G = LinkedQueue()
    while not S.is_empty():
        if S.first() < p:
            L.enqueue(S.dequeue())
        elif S.first() > p:
            G.enqueue(S.dequeue())
        else:
            E.enqueue(S.dequeue())
    quick_sort(L)
    quick_sort(G)

    while not L.is_empty():
        S.enqueue(L.dequeue())
    while not E.is_empty():
        S.enqueue(L.dequeue())
    while not G.is_empty():
        S.enqueue(L.dequeue())

# 对Python列表S的就地快速排序
def inplace_quick_sort(S, a, b):
    if a >= b: return
    pivot = S[b]
    left = a
    right = b - 1
    while left <= right:
        while left <= right and S[left] < pivot:
            left += 1
        while left <= right and S[right] > pivot:
            right -= 1
        if left <= right:
            S[left], S[right] = S[right], S[left]
            left, right = left + 1, right - 1

    S[left], S[b] = S[b], S[left]

    inplace_quick_sort(S, a, left-1)
    inplace_quick_sort(S, left+1, b)

#快速随机选择
def quick_select(S, k):
    if len(S) == 1: return S[0]
    pivot = random.choice(S)
    L = [x for x in S if x < pivot]
    E = [x for x in S if x == pivot]
    G = [x for x in S if x > pivot]
    if k <= len(L):
        return quick_select(L, k)
    elif k <= len(L) + len(E):
        return pivot
    else:
        j = k - len(L) - len(E)
        return quick_select(G, j)




