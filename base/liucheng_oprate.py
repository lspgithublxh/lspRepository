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
for i, a in enumerate(range(1,100, 1)):
    print i ,a
print enumerate(range(1,10, 1))

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

#lambda表达式定义小函数：
print '*' * 40
print lambda nn,mm:nn +mm
f = lambda uu,oo:uu + oo
f(*[1,2])
f(1,3)
print '-' * 40
def lam(n):
    return lambda x , n : x + n
f1 = lam(23)
f1(3,4)

print map(lambda a:a*a, [1,2,3,4])

#全局变量
print '23', '-' * 40
print i
# global i
cmc = [1,2]

def getGlobal():
    # global i
    i = 2
    print i
    cmc = [2,3,4]
    cmc.append(1)
    global dd
    dd = [2,3]

getGlobal()

print i
print cmc, dd