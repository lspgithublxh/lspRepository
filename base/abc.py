#coding=utf-8
import string
from inner import innerTest, first
print innerTest.a()
print first.first_module.a()
str = raw_input('请输入一个字符串:\n')
stat = {'letter':0, 'space':0, 'digit':0, 'others':0}
for c in str:
    if c.isalpha():
        stat['letter'] += 1
    elif c.isspace():
        stat['space'] += 1
    elif c.isdigit():
        stat['digit'] += 1
    else:
        stat['others'] += 1
print stat

import  sys
print sys.path

def abc():
    print 'hello'

stat['hello'] = abc
stat['hello']()

print 1,3
print (1,3)
a = 1,3
b = (1,3)
print a
print b

def abc():
    a = 1

def mcd():
    ccc = 1
