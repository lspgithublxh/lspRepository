#coding=utf-8
import sys
import utils.util1 as util1
from first.first_module import *
from first import *
print first_module.a()
a()
first_module.b()
print util1.hello()
print sys.path

import mypython
mypython.hello()

x = 0
def f1():
    x= 1
    def f2():
        x = 2
        def f3():
            global x
            x = 3
        f3()
        print x
    f2()
    print x
f1()
print x