#coding=utf-8
#lamda表达式，定义就执行有三要素：形参、执行体、实参
d1 = {'name':'a', 'age':22, 'other':None}
d2 = {'name':'b', 'age':24, 'score':23}
a = (lambda a, b: (lambda a_copy : a_copy.update(b) or a_copy)(a.copy()))(d1, d2)
print(a)
print(dict(d1, **d2))
d2.update(d1)#最快的方法
#d2.update(d1)
print(d2)
# print(d1)
