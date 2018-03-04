#coding=utf-8
from math import pi
#列表的表示和运算
a = [1,'33',45,2.3]
b = [1,2,3]
print a[0], a[1:]
print b + a
a.append(2)
print a

print [str(round(pi, i)) for i in range(1,6)]

matrix = [[1,2,3], [3,4,5]]
print [[row[i] for row in matrix] for i in range(3)]