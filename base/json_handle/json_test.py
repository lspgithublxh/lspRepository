#coding=utf-8
import json
a = ['a',1,'c']
b = {'名字':'李少平','age':26, 'address':'北京市朝阳区'}
# s = json.dump(b,encoding='utf-8')
# print s
c = [b]
print json.dumps(c, ensure_ascii=False)
r2 = json.dumps(b, encoding='utf-8')
print r2
r2 = json.dumps(b)
print r2
r2 = json.dumps(b, ensure_ascii=False) #输入如果有非ascll码编码的内容，将会使用unicode来编码
print r2
print '8' * 49
f = open('D:\\python2.log','r')
with f :
    # f.write(r2)
    while True:
        line = f.readline()
        print line,'--------'
        if line == '':
            break
        import sys
        reload(sys)
        sys.setdefaultencoding('utf-8')
        objx = json.loads(line)
        print objx['name']


print '9' * 40
obj2 = json.loads(r2)
print obj2
obj2 = json.loads(r2, encoding='utf-8')
acb = {'name':'我'}
print obj2['age']
print '\xe6\x88\x91', u'\u6211'
print 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'
print json.dumps(acb, ensure_ascii=False)
print json.dumps(acb)
c = json.dumps(acb)
c_zhuan = json.loads(c)
print json.dumps(c_zhuan, ensure_ascii=False)


class A():  #继承json
    name = 'lishaoping'  #相当于self.name
    id = 123  #相当于self.id
    def obj_2_json(self, obj):
        print self.name
        return {'name':obj.name, 'key':obj.id}
    def __init__(self, id, name):
        self.id = id
        self.name = name
    def getString(self, obj):
        print 'get String', obj.__dict__
        return obj.__dict__
    # def __repr__(self):
    #     return repr((self.id, self.name)) #tostring方法,影响实例的展示

a = A(1, 'name')
print a , 'this is ', isinstance(a.__dict__, dict)
# a_json = json.dumps(a)  #报错
a_json = json.dumps(a, default=a.getString)
print a_json
a_json = json.dumps(a, default=lambda o : o.__dict__)
print a_json,
a_json = json.dumps(a, default=a.obj_2_json,sort_keys=True, indent=4)
print a_json
a_json = json.dumps(a, default=lambda obj:{'id':obj.id, 'name':obj.name}, sort_keys=True)
print a_json
obj = json.loads(a_json, object_hook= lambda obj:A(obj['id'], obj['name']))
print obj
# a_json = json.dumps(a, default=lambda obj:a.__dict__)
# print a_json
