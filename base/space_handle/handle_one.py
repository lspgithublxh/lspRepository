#coding=utf-8
global a
b = 100

def a():
    print '1'
    a = 2
    print a
    print b
    c = 4
    inner = 0
    def inner2():
        print c
    inner2()

def do():
    global  a
    a()
    a = 1


if __name__ == '__main__':
    a()
    print a
    do()