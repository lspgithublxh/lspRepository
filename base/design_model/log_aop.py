#coding=utf-8
import math
#代理模式：函数的代理函数
def proxy_somefunction(fun, **kwargs):
    print 'fun is : ' , fun
    print 'kwargs is :', kwargs
    def handler(**params):
        print 'handle before'
        val = fun(kwargs['di'], params['zhi']) #此处参数尚未丢弃，是名称空间中的
        print 'handle after'
        return val
    return handler

print math.pow(3,4)

pow_proxy = proxy_somefunction(fun=math.pow, di=3)
value = pow_proxy(zhi=3)
print value

print '-'* 40
#函数的代理函数，定义的切面函数可以指定切哪些函数。。。。
#必须知道注解的执行过程----如何处理注解和下面的函数的。。。注解函数就是wapper

#
def anotation_fun(fun):
    print 'just excute anotation_fun'
    def wrapper(*kwargs):
        print 'run every time before'
        fun(*kwargs)#传引用，好kwargs[0], kwargs[1]
        # fun(kwargs)#传值,不好
        print 'run every time after'
    return wrapper

@anotation_fun
def need_anotation(id, name):
    print id,':',name

need_anotation('id--1', 'name---xiaoming')
need_anotation('id--2', 'name---xiaohong')


#不在目标类上注解，自动切面----即在运行时实际上已经换成了代理类。
print '-' * 40

def anotation_again(fun):
    print 'anotation again'
    def wrapper(*kwargs):
        print 'again---before'
        fun(*kwargs)
        print 'again----after'
    return wrapper

@anotation_again
@anotation_fun
def need_again_anotation(id, name):
    print '----target function', id, ':',name

print '---------' * 5
need_again_anotation('123', 'xiaoming')
need_again_anotation('124','xiaohong')

