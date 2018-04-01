#coding=utf-8

class TemplateA():
    """this is a document"""
    name = 'xiaoming'
    li = ['a','b']
    def __init__(self):
        self.age = 10
        self.lis = [1,2,3]
        self.__xxx = 1

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

def outer_function():
    print 'outer_function'

a = TemplateA()
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
    def getName(self):
        print 'ssss'
        TemplateA().getName()

    @staticmethod
    def jingtai():
        TemplateA.jingtai()

print TemplateA.name
print Templete_B().name




print isinstance(a, TemplateA)
print issubclass(Templete_B, TemplateA)
Templete_B.jingtai()

print isinstance(Templete_B(), Templete_B)
print isinstance('ss', ''.__class__)
print issubclass(Templete_B, TemplateA)