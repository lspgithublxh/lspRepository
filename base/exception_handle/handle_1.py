#coding=utf-8
import __builtin__

try:
    raise Exception('jibenxinxi', 'abc')
except Exception as e:
    print e
else:
    print 'not occurence exception'
finally:
    print 'this finally'

try:
    raise Exception , e
except Exception, e:
    print e


print Exception.__class__
print __builtin__.Exception.__class__
print dir(__builtin__)


class MyError(Exception):
    def __init__(self):
        print 'hahah'

    def __init__(self,value):
        self.value = value

    def __str__(self):
        return repr(self.value)

try:
    raise MyError('so error')
except MyError as e:
    print 'this error is :', e.value

