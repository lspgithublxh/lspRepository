#coding=utf-8
import time
def yieldTest(num):
    n, a, b = 0, 0, 1
    print 'start yield'
    while n < num:
        yield b
        a, b = b, a + b #先取值，再运算，再赋值
        n = n + 1
        print 'yield b ok, ',n

def yieldFile(path):
    size = 1024
    with open(path,'r') as f:

        while True:
            # block = f.read(size)
            block = f.readline(size)
            if block:
                yield block
            else:
                return



if __name__ == '__main__':
    ite = yieldTest(5) #并没有执行函数
    time.sleep(1)
    print ite
    time.sleep(1)
    print ite.next() #才开始执行函数--到yield为止
    print ite.next()
    print ite.next()
    print ite.next()
    print ite.next()
    block = yieldFile('D:\\test\\scrapy\\trace.log')
    for blo in block:
        # print blo.replace('\n',';')
        print blo