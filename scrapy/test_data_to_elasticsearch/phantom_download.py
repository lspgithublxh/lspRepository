#coding=utf-8
from selenium import webdriver
import  time

driver = webdriver.PhantomJS(executable_path='D:\\download\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs')

# driver2 = webdriver.Firefox(executable_path='D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe')
# driver2 = webdriver.Chrome('C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe')

def getContent(url):
    driver.get(url)
    data = driver.title
    # driver.close()
    print data


def getContent2(url):
    driver.get(url)
    # data = driver.title
    # print data
    text = driver.page_source
    # driver.close()
    return text

def getContentByPost(url, cveid):
    driver.get(url)
    cve = driver.find_element_by_id('qcvCnnvdid')
    cve.clear()
    cve.send_keys(cveid)
    search = driver.find_element_by_class_name('bd_b')
    search.click()
    # time.sleep(10)
    driver.refresh()
    # time.sleep(10)
    text = driver.page_source
    # driver.close()
    return text



def search_by_name(url, name):
    driver.get(url)
    nameobj = driver.find_element_by_id('qcvCname')
    nameobj.clear()
    nameobj.send_keys(name)
    search = driver.find_element_by_class_name('bd_b')
    search.click()
    driver.refresh()
    text = driver.page_source
    # driver.close()
    return text


# def getContentByFireFox():
#     print 'start'
#     driver2.get('http://www.cnvd.org.cn/flaw/list.htm?flag=true')
#     print driver2.page_source

if __name__ == '__main__':
    # getContent('http://www.baidu.com')
    # getContent2('http://cve.scap.org.cn/CVE-2015-3934.html')
    #'http://www.cnnvd.org.cn/web/vulnerability/queryLds.tag'
    print getContentByPost('http://www.cnnvd.org.cn/web/vulnerability/queryLds.tag', 'CVE-2011-3980')# CVE-2015-3934
    # getContentByFireFox()

    pass
