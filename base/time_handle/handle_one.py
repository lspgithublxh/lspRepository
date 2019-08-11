#coding=utf-8
import time
from datetime import  date
print date.today()  #yyyy-mm-dd
print date.today().strftime('%m-%d-%y')
print time.strftime('%Y-%m-%d %H:%M:%S')
print time.strptime('2018-03-17 13:16:37','%Y-%m-%d %H:%M:%S')
print time.time()
print int(round(time.time() * 1000)) #毫秒级别时间戳
print int(round(time.time()))#秒级时间戳
print int(time.time())#秒级时间戳
import re

