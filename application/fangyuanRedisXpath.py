#coding=utf-8
import sys
from selenium import  webdriver
from selenium.webdriver.chrome.options import Options

import redis
from pymongo import MongoClient
from bs4 import BeautifulSoup
import threading
from lxml import etree

client = MongoClient('localhost', 27017)#10.252.62.125
col = client['detail']['quanguo2']

chrome_options = Options()
chrome_options.add_argument('--no-sandbox')#解决DevToolsActivePort文件不存在的报错

chrome_options.add_argument('window-size=1920x3000') #指定浏览器分辨率
chrome_options.add_argument('--disable-gpu') #谷歌文档提到需要加上这个属性来规避bug
chrome_options.add_argument('--hide-scrollbars') #隐藏滚动条, 应对一些特殊页面
chrome_options.add_argument('blink-settings=imagesEnabled=false') #不加载图片, 提升速度
chrome_options.add_argument('--headless') #浏览器不提供可视化页面. linux下如果系统不支持可视化不加
def redisHandle():
    i = 0
    while i < 1:
        one = threading.Thread(target=threadHandle2, args=())
        one.start()
        i += 1
    pass


def threadHandle2():
    bro = webdriver.Chrome(executable_path="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe",
                           chrome_options=chrome_options)
    pool = redis.ConnectionPool(host='localhost', port=6379, decode_responses=True)
    r = redis.Redis(connection_pool=pool)
    url = 'https://{}.ke.com/ershoufang/{}.html'
    while True:
        print 'get url start:'
        ctid = r.rpop('ctid')
        two = ctid.split(',')
        print 'get id:', two[0] #or wait is ok , can't None
        if two[1] is None:
            print 'get end, url is None'
            break
        rs = parseDetail(url.format(two[1],two[0]), bro)
        if rs == -1: continue
        if rs == -2: continue
        rs['city'] = two[1]
        rs['id'] = two[0]
        col.insert(rs)


def parseDetail(url, bro):
    page = None
    bs = None
    try:
        bro.get(url)
        page = bro.page_source
        bs = BeautifulSoup(page, 'html.parser')
    except:
        return -2
    body = bs.body
    if body is None: return -1
    rs = {}
    title = {}
    pageObj = None
    try:
        pageObj = etree.HTML(page)
        # x = pageObj.xpath("//div[@class='aroundInfo']/div[@class='communityName']")
        # m = x[0].xpath("//a[@class='info ']/@href")
        # print m[0]

        arr = pageObj.xpath("//div[@class='sellDetailHeader']//div[@class='title']/h1//@title")
        arr2 = pageObj.xpath("//div[@class='sellDetailHeader']//div[@class='title']/div//@title")
        title['main'] = arr[0]
        title['sub'] = arr2[0]
        rs['title'] = title
        # for i in arr:
        #     print i.text, i.attrib['href']
    except Exception as e:
        print e

    try:
        arr = pageObj.xpath("//div[@class='houseInfo']/div[@class='room']/div[@class='mainInfo']")
        arr2 = pageObj.xpath("//div[@class='houseInfo']/div[@class='room']/div[@class='subInfo']")
        arr3 = pageObj.xpath("//div[@class='houseInfo']/div[@class='type']/div[@class='mainInfo']")
        arr4 = pageObj.xpath("//div[@class='houseInfo']/div[@class='type']/div[@class='subInfo']")
        arr5 = pageObj.xpath("//div[@class='houseInfo']/div[@class='area']/div[@class='mainInfo']")
        arr6 = pageObj.xpath("//div[@class='houseInfo']/div[@class='area']/div[@class='subInfo']")
        house_ = {}
        house_['ting'] = arr[0].text
        house_['layer'] = arr2[0].text
        house_['fangxiang'] = arr3[0].text
        house_['loutype'] = arr4[0].text
        house_['mianji'] = arr5[0].text
        house_['buildtime'] = arr6[0].text
        rs['house'] = house_
    except:pass
    try:
        arr0 = pageObj.xpath("//div[@class='aroundInfo']/div[@class='communityName']/a/text()']")
        arr = pageObj.xpath("//div[@class='aroundInfo']/div[@class='communityName']//a[@class='info ']/@href']")
        a_href = arr[0]
        ahref = ''
        if a_href is not None:
            ahref = a_href.split('/')[2]
        span_area = pageObj.xpath("//div[@class='aroundInfo']/div[@class='areaName']/span[@class='info']")[0]
        a_ditie = pageObj.xpath("//div[@class='aroundInfo']/div[@class='areaName']/a[@class='supplement']")[0]
        quyu = {}
        quyu['communityName'] = arr0[0]
        quyu['xiaoquId'] = ahref
        quyu['area'] = span_area.text
        quyu['ditie'] = a_ditie.text
        rs['quyu'] = quyu
    except:pass


if __name__ == '__main__':
    bro = webdriver.Chrome(executable_path="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe",
                           chrome_options=chrome_options)
    url = 'https://bj.ke.com/ershoufang/101103481530.html?fb_expo_id=111843257925292033'
    parseDetail(url, bro)
