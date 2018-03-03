#coding=utf-8
from downloadPage import start as start_spider
import sys
from timer_spider import timer_

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')  # 解决报错 asclli
    #
    timer_()
    #
    start_spider()