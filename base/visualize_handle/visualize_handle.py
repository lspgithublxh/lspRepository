#coding=utf-8
_foo = 3
foo = 3

class Test:
    _foo = 3 #都是静态的
    __foo = 3#都是静态的
    aaa = 2#都是静态的
    def __init__(self):
        self._abc = 3
        self.__ok = 4
        self.imc = 3
        print self

    def __what__(self):
        print self

    def getFoo(self):
        print self._foo
        print self.__foo
        print self._abc

class B(Test):
    def getA(self):
        self.imc

a = Test()
a.__what__()
a.getFoo()
a.aaa
print """sss\
dsfsfsf"""

print u'adb\u0020sdfs'
print '\u0020abd',u'\u6211'
# print '{}'.format(u'我')#u'我'
print '我'.__class__, '-' * 40
print '我'.decode('utf-8').__class__
print '我'.decode('utf-8').encode('utf-8').__class__
print 'a'[0:1]
print {'我':'好'}
print '\xe6\x88\x91', u'\u6211'
print u'ss'.__class__
print isinstance('sss',u'utf-8'.__class__)
print type('ss')

#MEID:A0000069848BC3
#IMEI1:861498039727957
#IMEI2:861498039877950
#http://houserent.m.58.com/home/Api_recommend_ershoufang?n_city=1&lon=116.4081980&lat=39.9046670&platform=android&page=0&imei=1212&jsoncallback=jsonp3
