#coding=utf-8
import sys
#字符串格式化
print '%s---%s' % ('abc', 'def')
print '{}---{}'.format('def',234)

#字符串拼接,不用+=
items = ['ccc','sss','eee']
print items
print ''.join(items)

#多行构成的字符串：
print """abc
        def , def"""
print """abc\
        def, def"""
print('abcddd'
        'adefef')

#编码
print 'abd中文'
print sys.getdefaultencoding()
# reload(sys)
# sys.setdefaultencoding('utf-8')
print u'adb中文'
print ur'abd中文'
print u'中文'.encode('utf-8')
#print str(u'中文') #str()执行时会调用默认编码来编码

#数字
print 1/7.0
print 1/7
print str(1/7)
print repr(1/7)

#正则re
import re
ma = re.match('^1[3-9]\d{9}$', '15652587687', re.IGNORECASE)
p = re.compile('^1[3-9]\d{9}$')
print ma.group()
res = p.match('15652587687')
print res.group()