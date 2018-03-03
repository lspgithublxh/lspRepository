#coding=UTF-8
import time
import datetime

print time.time()
print 'localtime : ', time.localtime()
print time.gmtime()
print time.gmtime(time.time())
print time.strptime('2017-09-12', '%Y-%m-%d')
print '-----------'
print time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
print time.strptime('2017-11-13 23:23:23', '%Y-%m-%d %H:%M:%S')
print datetime.date.today()    #是日期类型
print datetime.date.today() - datetime.timedelta(hours=9)
print datetime.datetime.now()
print datetime.datetime.today()
print datetime.datetime.now().time()
#非常关键，下面这个代码将格林时间转换为北京时间，或者说互相转换
print datetime.datetime.strptime('2017-11-13 23:23:23', '%Y-%m-%d %H:%M:%S') - datetime.timedelta(hours=9)
print '----'
str = '2017-11-13 09:17:50 GMT'
print str[:-4]
time2 = datetime.datetime.strptime(str[:-4], '%Y-%m-%d %H:%M:%S') - datetime.timedelta(hours=8)
print time2
time1 = time.time()
time.sleep(2.343)
time2 = time.time()
print time2 - time1 > 2
