#coding=utf-8
import json
a = ['a',1,'c']
b = {'name':'李少平','age':26, 'address':'北京市朝阳区'}
# s = json.dump(b,encoding='utf-8')
# print s

r2 = json.dumps(b, encoding='utf-8')
print r2
r2 = json.dumps(b)
print r2
r2 = json.dumps(b, ensure_ascii=False) #输入如果有非ascll码编码的内容，将会使用unicode来编码
print r2
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



obj2 = json.loads(r2)
print obj2
acb = {'name':'李少平'}
print acb