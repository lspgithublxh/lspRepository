#coding=utf-8
import requests

global urlMap
urlMap = {}

def get(**dit):
    url = dit['url']
    print 'start proxy'
    def no2_level(fun):
        print 'start no2 proxy'
        def wrapper(**dic):
            print 'aop before', dic
            rs = fun(**dic)
            print 'aop after'
        urlMap[url] = wrapper  # 可以先存在式判断
        return wrapper
    return  no2_level


def filter(method):
    print 'filter'


@get(url=r"/{city}/list.shtml")
def list(city):
    print city, 'list'


@get(url=r"/{city}/detail.shtml")
def detail(city):
    print city, 'detail'

@get(url=r"/{city}/home.shtml")
def home(city):
    print city, 'home'


