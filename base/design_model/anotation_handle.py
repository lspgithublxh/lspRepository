#coding=utf-8

def fun(cc):
    print 'haha'

def handle_outer(**handle):
    print 'outer handle', handle

def handle(f):
    print 'start '

    def wraper(**handle):
        f(**handle)

    print 'end'
    f = wraper
    return handle_outer

@handle
def target(name, id):
    print name, id

@fun
def ta2():
    print 'test'

# target(name='xiaoming', id=2)
target(name='xiaoming', id=2)


#含有参数的注解

def handle_x(**dic):
    print '----------', dic
    def wraper(f):
        print 'checkout',f
        def relly_wrapper(name):
            print 'really_wrapper', name
            f(name)
        return relly_wrapper
    return wraper


@handle_x(name='xiaoming', id=123)
def tar3(name):
    print name

# tar3('mingming')

