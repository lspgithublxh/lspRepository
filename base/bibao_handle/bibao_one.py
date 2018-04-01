#coding=utf-8
import sys
print sys.path

def handle(self):
    print self.name
    pass

cc = {'name':'xiaoming', 'fun':handle}

class A:
    abc = 2
    def handle(self):
        self.id = 1
        print self.name
        pass
    pass

    @classmethod
    def handle2(self):
        self.a = 2
        b = 2


bb = A()
bb.name = 'lixiaoming'
bb.han = handle
bb.handle()
A.handle2()
print A.a
print A.abc
A.cmc = 3
print A.cmc