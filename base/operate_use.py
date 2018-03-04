#coding=utf-8
a,b = 0,1
while b < 100:
    print b,
    a,b = b, a + b
print 'sss',a
for a in range(1,100,1):
    print a
    if a == 56:break
else:
    print 'no print'

#print raw_input('please input a number:')
#函数定义
def gg(li):
    li = ['ccc']
    li.append('a')
    print 'abc'
    print li
print gg
cc = [1,2,3,4]
gg(cc)
print cc

a = {}
a['c'] = 'abc'
print a['c']
a['c'] =  gg
print a['c']

#函数定义
def ab(kind, *key, **keyword):
    print kind
    print key
    print keyword

ab('abc','eeee','ddd', ac = 'ad', de = 'af')
print ','.join(('a','b','c'))
print '-'* 30
ab(*[1,2,3,4], **{'key1':2,'key2':3})