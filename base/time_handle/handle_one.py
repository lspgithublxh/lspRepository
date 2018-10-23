#coding=utf-8
import time
from datetime import  date
print date.today()  #yyyy-mm-dd
print date.today().strftime('%m-%d-%y')
print time.strftime('%Y-%m-%d %H:%M:%S')
print time.strptime('2018-03-17 13:16:37','%Y-%m-%d %H:%M:%S')

import re

