#coding=utf-8
import sys
from selenium import  webdriver
from selenium.webdriver.chrome.options import Options
chrome_options = Options()
chrome_options.add_argument('--no-sandbox')#解决DevToolsActivePort文件不存在的报错

chrome_options.add_argument('window-size=1920x3000') #指定浏览器分辨率
chrome_options.add_argument('--disable-gpu') #谷歌文档提到需要加上这个属性来规避bug
chrome_options.add_argument('--hide-scrollbars') #隐藏滚动条, 应对一些特殊页面
chrome_options.add_argument('blink-settings=imagesEnabled=false') #不加载图片, 提升速度
chrome_options.add_argument('--headless') #浏览器不提供可视化页面. linux下如果系统不支持可视化不加
from lxml import etree
import pymysql
conn = pymysql.connect(host='localhost', port=3306, user='root', passwd='lsp', db='test', charset='utf8')
cursor = conn.cursor()
import redis


def getByPage(page):
    obj = etree.HTML(page)
    arr = obj.xpath('//div[@data-role="ershoufang"]/div[2]/a')
    rs = []
    for i in arr:
        print i.text, i.attrib['href']
        rs.append(i.attrib['href'])
    return rs

def getByXpath(url):
    bro = webdriver.Chrome(executable_path="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe",
                           chrome_options=chrome_options)
    bro.get(url)
    # aobj = bro.find_element_by_xpath('//div[@data-role="ershoufang"]/div[2]/a')
    # print aobj
    page = bro.page_source
    obj = etree.HTML(page)
    arr = obj.xpath('//div[@data-role="ershoufang"]/div[2]/a')
    for i in arr:
        print i.text, i.attrib['href']

def putRedis():
    r = None
    try:
        pool = redis.ConnectionPool(host='localhost', port=6379, decode_responses=True)
        r = redis.Redis(connection_pool=pool)  # host='localhost',port=6379, db=0
    except Exception as e:
        print e
    cursor.execute('SELECT DISTINCT id FROM beijing')
    rows = cursor.fetchall()
    for row in rows:
        id = row[0]
        r.lpush('detail', id)
        print id

if __name__ == '__main__':
    # getByXpath('https://bj.ke.com/ershoufang/haidian/')
    # arr = ['ss']
    # b = ['xx']
    # arr.extend(b)
    # print arr
    # arx = ['ss','ss','x','3']
    # print arx[1:2], arx[2:3]
    putRedis()