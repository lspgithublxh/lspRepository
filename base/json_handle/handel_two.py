#coding=utf-8
import json
class A:
    def __init__(self):
        self.name = None
        self.age = None

#无值
a = A()
print(json.dumps(a.__dict__, ensure_ascii=False))
#加值
a.age = 20
a.name = '小明'

str = json.dumps(a.__dict__, ensure_ascii=False)
print(str)
print(str.__class__.__name__)
data = json.loads(str, encoding='utf-8')
print data.__class__.__name__
b = A()
print json.dumps(b.__dict__, ensure_ascii=False)
b.__dict__ = a.__dict__
print json.dumps(b.__dict__, ensure_ascii=False)
print b.name, b.age

