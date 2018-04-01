#coding=utf-8
import numpy as np, matplotlib
import numpy.random as random

a = np.array([3,4])
np.linalg.norm(a)
b = np.array([
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
])
c = np.array([1, 0, 1])

print np.dot(b,c)

print np.linalg.det(b)        		# 求矩阵的行列式值，0
print np.linalg.matrix_rank(b)

print random.choice(a, 7)
print random.shuffle(a)
print a
print random.permutation(a)
print a