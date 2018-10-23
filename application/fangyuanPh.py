import sys
from selenium import webdriver
import time

wd = webdriver.PhantomJS(executable_path='D:\\download\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs')

def getContent(url):
    wd.get(url)
    data = wd.title
    # driver.close()
    print wd.page_source

if __name__ == '__main__':
    # getContent('http://www.baidu.com')
    # getContent2('http://cve.scap.org.cn/CVE-2015-3934.html')
    #'http://www.cnnvd.org.cn/web/vulnerability/queryLds.tag'
    t1 = time.time()
    print getContent('https://bj.ke.com/ershoufang/')# CVE-2015-3934
    # getContentByFireFox()
    t2 = time.time()
    print t2 - t1
    pass