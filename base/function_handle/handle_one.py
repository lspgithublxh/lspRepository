#coding=utf-8

def abc(a, *p):
    print p

abc(3,4,5)

def igh(a = 3, **p):
    print a,  p

igh(2, name='xiaoming', id='123')
igh(name='xiaoming')

def shican(name, id):
    print name, id

shican(*['xiaoming', '123'])
shican(*('xiaoming', '123'))

def shican2(item, item2):
    print item, item2

shican2(**{'item':'xiaoming', 'item2':'123'})

a = shican2
a(**{'item':'xiaoming', 'item2':'123'})
del a
print shican2
shican2 = shican
shican2(*['xiaoming', '123'])

print filter(lambda x : x>2, [1,2,3,4])
print reduce(lambda x, y: x + y, [1,2,3,4])
print type(reduce(lambda x, y: x * y, [], 1))


#预扫描过程

def ff():
    print 'start'
    a = 1
    b = 1
    if a == b:
        for i in range(1, 20, 1):
            yield i
    print 'end'
    for i in range(1, 20, 1):
        yield i

gee = ff()
ccc = ff()
print type(gee)
for ix in gee:
    print ix
for ix in ccc:
    print ix
print dir()



