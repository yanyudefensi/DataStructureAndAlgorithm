"""
第四章
"""
import os


def factorial(n):
    if n == 1:
        return 1
    else:
        return n * factorial(n - 1)


# 绘制一个标尺函数，设计出这个的真的鬼才

def draw_line(tick_length, tick_label=""):
    line = "-" * tick_length

    if tick_label:
        line += " " + tick_label
    print(line)


def draw_interval(center_length):

    if center_length > 0:
        draw_interval(center_length - 1)
        draw_line(center_length)
        draw_interval(center_length - 1)


def draw_ruler(num_inches, major_length):
    draw_line(major_length, "0")
    for j in range(1, 1 + num_inches):
        draw_interval(major_length - 1)
        draw_line(major_length, str(j))


# 二分查找
def binary_search(data, target, low, high):
    if low > high:
        return False
    mid = low + high // 2
    if target == data[mid]:
        return mid
    elif target > data[mid]:
        return binary_search(data, target, mid + 1, high)
    else:
        return binary_search(data, target, low, mid - 1)

# 报告一个文件系统磁盘使用情况的递归函数


def disk_usage(path):

    total = os.path.getsize(path)

    if os.listdir(path):
        for filname in os.listdir(path):
            childpath = os.path.join(path, filname)
            total += disk_usage(childpath)

    print('{0:<7'.format(total), path)
    return total

# 一个好的数列


def good_fibonacci(n):

    if n <= 1:
        return n, 0
    else:
        a, b = good_fibonacci(n - 1)
    return a + b, a

# 使用线性递归计算序列的和


def linear_sum(S, n):
    if n == 0:
        return 0
    return linear_sum(n - 1) + S(n)


def reverse(S, start, stop):
    if stop - start > 1:
        S[start], S[stop] = S[stop], S[start]
        return reverse(S, start + 1, stop - 1)


def power(x, n):
    if n == 0:
        return 1
    else:
        partial = power(x, n // 2)
        result = partial * partial
        if n // 2 == 1:
            result *= result * n
        return result
# 利用递归思想求一个序列元素和


def sequence_sum(S, start, stop):
    if start > stop:
        return 0
    if stop - start == 1:
        return S[start]
    mid = (stop - start) // 2
    return sequence_sum(S, start, mid), sequence_sum(S, mid, stop)


if __name__ == "__main__":

    draw_ruler(3, 3)
