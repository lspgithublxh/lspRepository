#coding=utf-8

class TemplateA():
    """this is a document"""
    name = 'xiaoming'
    li = ['a','b']
    def __init__(self):
        self.age = 10
        self.lis = [1,2,3]
        # self.__xxx = 1

    def getName(self):
        return self.name;

    def getLi(self):
        print self.getName()
        self.__private()

    def abc(self):
        print 'abc'

    @staticmethod
    def jingtai():
        print 'static method'

    __abc = abc

    def __private(self): #私有化方法
        print 'print private'

    def obj_2_json(self, obj):
        dict = obj.__dict__
        dict['sister'] = 'sister'
        del dict['son']
        return dict

def outer_function():
    print 'outer_function'

import json
a = TemplateA()
del a.age
c = [a]
print  json.dumps(a, ensure_ascii=False, default=lambda obj: obj.__dict__)
print  json.dumps(c, ensure_ascii=False, default=lambda obj: obj.__dict__)


a.son = 'son'
print a.son

print json.dumps(a, ensure_ascii=False, default=lambda obj: obj.__dict__)
print json.dumps(a, ensure_ascii=False, default=a.obj_2_json)
b = TemplateA()
b.a = 'abc'
b.outer = outer_function
b.abc()
print a.name
print b.name, b.a
b.outer()
print '_' * 40
print b.__class__
print '-' * 40
print a.li
a.li.append('c')
a.name = 'xiaoguang'
print b.name, b.li
a.li = ['scd']
print a.li, b.li
print a.name
a.lis.append(22)
print a.lis, b.lis
a.xx = 3
print a.xx


#继承
class Templete_B(TemplateA):
    """this a document"""
    attri = 'b'
    attri2 = 'c'
    name = 'templateB'
    def __init__(self):
        TemplateA().getName()
        self.li
        self.name
        self.getName()
        print('B __init__ method execute')

    def getName(self):
        print 'ssss'
        TemplateA().getName()

    @staticmethod
    def jingtai():
        TemplateA.jingtai()

    def __new__(cls, *args, **kwargs):
        #这个方法先于__init__()方法执行
        print('__new__ method execute')

    def __call__(self, *args, **kwargs):
        #这个方法更是优先执行
        print('__call__ method execute')

print TemplateA.name
print Templete_B().name




print isinstance(a, TemplateA)
print issubclass(Templete_B, TemplateA)
Templete_B.jingtai()

print isinstance(Templete_B(), Templete_B)
print isinstance('ss', ''.__class__)
print issubclass(Templete_B, TemplateA)

print a.__class__.__name__
print a.__module__