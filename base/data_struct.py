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

del a[0]
print a
#元组
yuan = 1,2,3,4
print yuan[0]
zu = (a,b)
print zu
del a[1]
print zu
empty = ()
print empty
simple = 1,
print simple

element1, element2 = zu
print element1,element2

#集合
collect = set()
print collect
collect.add(1)
print collect
col2 = set(a)
col2.add(2)
print col2
print col2.union(collect)
initialSet = set()
print isinstance(initialSet,set)
a_set = {i for i in 'avvvseesxxxdccdddess' if i not in 'abc'}
print a_set

#字典
a_dict = {'abc':1, 'bcd':1}
print a_dict
print a_dict['abc']
dict_key = 'abc'
print a_dict[dict_key]
print 'a' in ['a','b']
for c in a_dict.iteritems():
    print c

b_dict = {x:x**2 for x in range(1,10,1)}
print b_dict

print '*' * 30
for index, item in enumerate(a):
    print index, item

for a_, b_ in zip(a, b):
    print a_, b_

print dir()


print '*' * 40
cmc = list()
cmc.append('a')
it = cmc.__iter__()
print it.__next__()
print
