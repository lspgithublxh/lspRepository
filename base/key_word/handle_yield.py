#coding=utf-8

#生成器;
generator = ( i *  i for i in range(100, 1, -10))

for c in generator:
    print c

print '-' * 40

for c in generator:
    print c


def abc():
    for i in range(1,10,1):
        print '开始迭代'
        if(i == 2):
            print '偶数'
            yield i * i
        elif i == 3:
            print '奇数'
            yield i * i * i

print 'other' * 40
ge = abc()#并没有任何执行, 函数有预扫描过程，里面有yield则都有返回，且是迭代器.且函数里不能有return
#只有迭代器返回了值才会执行for里的内容，否则直接重复。但是迭代器可以一直执行：从代码开始位置-结束为止，执行到退出取值循环。
ge2 = abc()
print ge
print ge2

print '-' * 40
for c in ge:
    print '输出值{}'.format(c)

for c in ge2:
    print '输出值---{}'.format(c)

print 'next:' , '-' * 40
ge3 = abc()
print ge3.next()


#计算组合
