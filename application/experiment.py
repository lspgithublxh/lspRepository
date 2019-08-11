#coding=utf-8
import sys
import pymysql
import redis
from bs4 import BeautifulSoup
from selenium import  webdriver
from selenium.webdriver.chrome.options import Options
from lxml import etree

chrome_options = Options()
chrome_options.add_argument('--no-sandbox')#解决DevToolsActivePort文件不存在的报错

chrome_options.add_argument('window-size=1920x3000') #指定浏览器分辨率
chrome_options.add_argument('--disable-gpu') #谷歌文档提到需要加上这个属性来规避bug
chrome_options.add_argument('--hide-scrollbars') #隐藏滚动条, 应对一些特殊页面
chrome_options.add_argument('blink-settings=imagesEnabled=false') #不加载图片, 提升速度
chrome_options.add_argument('--headless') #浏览器不提供可视化页面. linux下如果系统不支持可视化不加

conn = pymysql.connect(host='localhost', port=3306, user='root', passwd='lsp', db='test', charset='utf8')
cursor = conn.cursor()
pool = redis.ConnectionPool(host='localhost', port=6379, decode_responses=True)
r = redis.Redis(connection_pool=pool)


def shua_beijing():
    cursor.execute('select distinct id from beijing')
    all = cursor.fetchall()
    arr = []
    set1 = set()
    set2 = set()
    for i in all:
        # arr.append(i[0])
        set1.add(i[0])
    arr2 = []
    handle = open('D:\\id.txt', 'r')
    while True:
        line = handle.readline()  # readline
        if line is None or line == '':
            break
        # arr2.append(int(line))
        set2.add(line.strip('\n'))
    print set2.__len__()
    print set1.__len__()
    set3 = set1 - set2
    print set3.__len__()
    # handle = open('D:\\cccc.txt','a')
    for i in set3:
        # handle.write(i + '\r')
        r.lpush('detail', i)
    pass

def shuaquanguo():
    cursor.execute('select distinct city from quanguo')
    all = cursor.fetchall()
    set1 = set()
    set2 = set()
    for i in all:
        set1.add(i[0])
    citys = [ u'xm', u'pt', u'sm', u'yongan', u'quanzhou', u'shishi', u'na', u'longhai', u'np', u'nd', u'ly', u'zhangzhou', u'jo', u'shaowu', u'fa', u'fd', u'fuqing', u'lianj', u'gz', u'shaoguan', u'sz', u'zh', u'st', u'fs', u'sd', u'jiangmen', u'taishan', u'ep', u'zhanjiang', u'lianjiang', u'leizhou', u'wuchuan', u'mm', u'gaozhou', u'huazhou', u'zq', u'hui', u'meizhou', u'xingning', u'sw', u'heyuan', u'yangjiang', u'yangchun', u'yingde', u'lianzhou', u'dg', u'zs', u'chaozhou', u'jieyang', u'pn', u'qy', u'lechang', u'lufeng', u'luoding', u'kp', u'sjz', u'zhaoxian', u'xl', u'ts', u'zhunhua', u'qa', u'hd', u'wa', u'xt', u'baoding', u'dingzhou', u'cangzhou', u'rq', u'lf', u'bazhou', u'sanhe', u'hs', u'chengde', u'qhd', u'zjk', u'xan', u'ag', u'botou', u'huanghua', u'hj', u'gbd', u'jizhou', u'nangong', u'wj', u'shenzhou', u'zhuozhou', u'cc', u'yushu', u'dehui', u'jl', u'cy', u'huadian', u'sp', u'gzl', u'liaoyuan', u'tonghua', u'bs', u'songyuan', u'bc', u'yb', u'hunchun', u'jiaohe', u'longjing', u'linjiang', u'yj', u'dunhua', u'sy', u'xinmin', u'dl', u'pld', u'wfd', u'zhuanghe', u'as', u'haicheng', u'fushun', u'bx', u'dd', u'jinzhou', u'yk', u'gaizhou', u'dsq', u'liaoyang', u'pj', u'tieling', u'kaiyuan', u'chaoyang', u'hld', u'bp', u'dengta', u'lingyuan', u'xingcheng', u'donggang', u'fch', u'hhht', u'baotou', u'wuhai', u'cf', u'tongliao', u'eeds', u'hlbr', u'wlcb', u'xlht', u'alsm', u'mzl', u'xlglm', u'xining', u'haidong', u'haixi', u'dlh', u'grm', u'ty', u'qx', u'gujiao', u'dt', u'yq', u'changzhi', u'jc', u'yuncheng', u'lin', u'xinzhou', u'linfen', u'lvliang', u'jz', u'fenyang', u'houma', u'huozhou', u'lucheng', u'yongji', u'tj', u'lasa', u'rkz', u'changdu', u'nq', u'km', u'an', u'qj', u'yx', u'baoshan', u'zt', u'lj', u'pr', u'lincang', u'chuxiong', u'ky', u'honghe', u'ws', u'xsbn', u'jinghong', u'dali', u'dh', u'rl', u'nujiang', u'diqing', u'gejiu', u'xw', u'hz', u'nb', u'wz', u'ra', u'jx', u'huzhou', u'sx', u'jh', u'lanxi', u'yw', u'dongyang', u'yongkang', u'quzhou', u'zhoushan', u'taizhou', u'wl', u'lh', u'lishui', u'lq', u'shengzhou', u'cixi', u'haining', u'jd', u'jiashan', u'ph', u'tongxiang', u'yr', u'zhuji'];
    set3 = set(citys)
    set4 = set3 - set1
    for i in set4:
        r.lpush('quanguo',i)

def getAllcity():
    page = None
    try:
        bro = webdriver.Chrome(executable_path="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe",
                               chrome_options=chrome_options)
        bro.get("https://www.ke.com/city/")
        page = bro.page_source
    except Exception as e:
        print e
    # arr = bro.find_element_by_xpath("//div[@class='city_list']//ul/li[@class='CLICKDATA']/a")
    res = []
    entity = etree.HTML(page)
    arr = entity.xpath("//div[@class='city_list']//ul/li[@class='CLICKDATA']/a//@href")
    for i in arr:
        if i.__contains__('.fang.') == False and i.__contains__('i.ke.com') == False:
            print i
            res.append(i)
    return res

if __name__ == '__main__':
    # shua_beijing()
    # shuaquanguo()
    # getAllcity()
    city = '//bj.ke.com'[2:].split('.')[0]
    print city