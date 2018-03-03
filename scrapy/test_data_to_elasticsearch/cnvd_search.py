#coding=utf-8
import sys
import requests
import commonparser
from phantom_download import getContent2
from pymongo import MongoClient
from util_compute import computeScore
import time

client = MongoClient('192.168.1.152', 27017)
# col_cnvd = client['lsp_test']['cnvd_search']
col_cnvd = client['lsp_test']['cnvd_search3']


def download1(url,data, header):
    try:
        print url
        status = 0
        res = requests.get(url, headers=header, timeout=30) # ,verify=False 不加验证可能会报警告
        # res = requests.post(url, data=data, headers = header)
        status = 1
        print res.headers
        print res.encoding
        res.encoding = 'UTF-8'
    except (requests.exceptions.ConnectionError, requests.exceptions.ReadTimeout,requests.exceptions.ConnectTimeout,requests.exceptions.ChunkedEncodingError, Exception) ,ex:
        # 特殊错误可以考虑等待几秒
        print 'exception occured', ex
        return '<html></html>'; # <body></body>
    # res.encoding = 'utf-8'
    return res.text

def get_download(url):
    try:
        print url
        status = 0
        res = requests.get(url, timeout=30) # ,verify=False 不加验证可能会报警告
        # res = requests.post(url, data=data, headers = header)
        status = 1
        print res.headers
        print res.encoding
        res.encoding = 'UTF-8'
    except (requests.exceptions.ConnectionError, requests.exceptions.ReadTimeout,requests.exceptions.ConnectTimeout,requests.exceptions.ChunkedEncodingError, Exception) ,ex:
        # 特殊错误可以考虑等待几秒
        print 'exception occured', ex
        return '<html></html>'; # <body></body>
    # res.encoding = 'utf-8'
    return res.text

def download2(url,data, header):
    try:
        print url
        status = 0
        # res = requests.get(url, headers=header, timeout=30) # ,verify=False 不加验证可能会报警告
        res = requests.post(url, data=data, headers = header)
        status = 1
        print res.encoding
    except (requests.exceptions.ConnectionError, requests.exceptions.ReadTimeout,requests.exceptions.ConnectTimeout,requests.exceptions.ChunkedEncodingError, Exception) ,ex:
        # 特殊错误可以考虑等待几秒
        print 'exception occured', ex
        return '<html></html>'; # <body></body>
    # res.encoding = 'utf-8'
    return res.text

def search():
    headers = {
        # "authority": "www.shodan.io",
        # "method": "GET",
        # "path": "/search?query=" + condition,
        # "scheme": "https",
        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Accept-Encoding': 'gzip, deflate',
        'Accept-Language': 'zh-CN,zh;q=0.8',
        'Cache-Control': 'max-age=0',
        'Cookie': '__jsluid=c6eb1db12bd6e780794c348c737d5186',
        'Referer': 'http://ics.cnvd.org.cn/',
        'Upgrade-Insecure-Requests': str(1),
        'Host': 'ics.cnvd.org.cn',
        'Origin':'http://ics.cnvd.org.cn',
        'Content-Type':'',
        'Content-Length':str(98),
        'Connection': 'keep-alive',
        # 'If-None-Match': '1dc08276b844a4a4b818c976282e876e5cec3645-gzip',
        'User-Agent': 'Mozilla/5.0(Windows NT 10.0;WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
    };
    data = {'keyword':'Infinite+Automation+Mango+Automation%E8%B7%A8%E7%AB%99%E8%84%9A%E6%9C%AC%E6%BC%8F%E6%B4%9E'}
    text = download2('http://ics.cnvd.org.cn/', data, headers)
    print text
    status = commonparser.parseCnvd(text)
    return status


def search2(cnvd_id):
    ids = col_cnvd.find({'cnvd': cnvd_id}, {'cnvd': 1}).distinct('cnvd')
    if ids.__len__() > 0:
        print 'no need to insert', cnvd_id
        return
    headers = {
        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Encoding': 'gzip, deflate',
        'Accept-Language': 'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
        'Cache-Control': 'max-age=0',
        'Cookie': '__jsluid=890a93a4872357f01aba2ea943846e46; bdshare_firstime=1511324181935; __jsl_clearance=1511843164.451|0|jNg%2FZM1j6TcLO4rAwNUQte55Qfk%3D',
        'Referer': 'http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd_id),
        'Upgrade-Insecure-Requests': str(1),
        'Host': 'www.cnvd.org.cn',
        'Connection': 'keep-alive',
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0'
    };
    # data = {'keyword': 'Infinite+Automation+Mango+Automation%E8%B7%A8%E7%AB%99%E8%84%9A%E6%9C%AC%E6%BC%8F%E6%B4%9E'}
    text = download1('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd_id), None, headers)
    print text
    if text.find('body') < 0:
        print 'script just'
        return -1
    map = commonparser.parseCnvd(text, cnvd_id)
    if map == -1:
        print 'this cnvd no exists'
        return
    col_cnvd.insert(map)
    return map

def search_cnvd_search2_db(tup):
    cnvd_id = tup[0]
    name = tup[1]
    ids = col_cnvd.find({'cnvd_id': cnvd_id}, {'cnvd_id': 1}).distinct('cnvd_id')
    if ids.__len__() > 0:
        print 'no need to insert', cnvd_id
        return
    headers = just_get_headers2()
    # data = {'keyword': 'Infinite+Automation+Mango+Automation%E8%B7%A8%E7%AB%99%E8%84%9A%E6%9C%AC%E6%BC%8F%E6%B4%9E'}
    text = download1('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd_id), None, headers)
    # print text
    if text.find('body') < 0:
        print 'script just'
        return -1
    map = commonparser.parseCnvd(text, cnvd_id)
    if map == -1:
        print 'this cnvd no exists', cnvd_id
        return
    map['bug_name'] = name
    col_cnvd.insert(map)
    return map

def just_get_headers2():
    #firefox
    headers = {
        'Host':'www.cnvd.org.cn',
        'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0',
        'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Language':'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
        'Accept-Encoding':'gzip, deflate',
        'Referer':'http://www.cnvd.org.cn/flaw/show/CNVD-2017-35027',
        'Cookie':' __jsluid=890a93a4872357f01aba2ea943846e46; bdshare_firstime=1511324181935; __jsl_clearance=1512012993.654|0|e4tJxWmZ0C7NmiP7WWHhY26G%2Fik%3D; JSESSIONID=3E2BCEFEDAA2F489A4C415DC725B5B1B',
        'Connection':'keep-alive',
        'Upgrade-Insecure-Requests':'1',
        'Cache-Control':'max-age=0'
    };
    #chrome
    return headers

def search_text():
    # headers = {
    #     'Host':'www.cnvd.org.cn',
    #     'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0',
    #     'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
    #     'Accept-Language':'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
    #     'Accept-Encoding':'gzip, deflate',
    #     'Referer':'http://www.cnvd.org.cn/flaw/list.htm?flag=true',
    #     'Cookie':'__jsluid=890a93a4872357f01aba2ea943846e46; bdshare_firstime=1511324181935; JSESSIONID=513DD3F59B794192F1BBD3BB9E6C5C4E; __jsl_clearance=1511753313.198|0|LF%2FmjEw3KdeMz%2BlVbbHA6s%2Bwebc%3D',
    #     'Connection':'keep-alive',
    #     'Upgrade-Insecure-Requests':'1',
    #     'Cache-Control':'max-age=0'
    # };
    #查询首页没有问题
    headers = {
        'Host': 'www.cnvd.org.cn',
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Language': 'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
        'Accept-Encoding': 'gzip, deflate',
        'Referer': 'http://www.cnvd.org.cn/flaw/list.htm?flag=true',
        'Cookie': '890a93a4872357f01aba2ea943846e46; bdshare_firstime=1511324181935; __jsl_clearance=1512005730.056|0|aLeZVaHEtELO1XVs14Vz09D%2BqK0%3D; JSESSIONID=1063F2F809A8CA95D6311033E34F3201',
        'Connection': 'keep-alive',
        'Upgrade-Insecure-Requests': '1',
        'Cache-Control': 'max-age=0'
    }
    # data = {'keyword': 'Infinite+Automation+Mango+Automation%E8%B7%A8%E7%AB%99%E8%84%9A%E6%9C%AC%E6%BC%8F%E6%B4%9E'}
    text = download1('http://www.cnvd.org.cn/flaw/list.htm?flag=true', None, headers)
    print text
    commonparser.get_cnvd_of_page(text)
    return text

def search_text_post():
    headers = {
        'Host': 'www.cnvd.org.cn',
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Language': 'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
        'Accept-Encoding': 'gzip, deflate',
        'Referer': 'http://www.cnvd.org.cn/flaw/list.htm?flag=true',
        'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Length': '104',
        'Cookie': '__jsluid=890a93a4872357f01aba2ea943846e46; bdshare_firstime=1511324181935; __jsl_clearance=1512002752.139|0|zcYau%2FBleL39OJPNWa%2FXp4inVE4%3D; JSESSIONID=F1744AB6EF390CCD4B362BEFC834855E',
        'Connection': 'keep-alive',
        'Upgrade-Insecure-Requests': '1',
        'Cache-Control': 'max-age=0'
    }
    # data = {'number': '%E8%AF%B7%E8%BE%93%E5%85%A5%E7%B2%BE%E7%A1%AE%E7%BC%96%E5%8F%B7',
    #         'startDate':'','endDate':'', 'field':'', 'order':'', 'flag':'true','max':'20','offset':'40'}
    data = {'number': '%E8%AF%B7%E8%BE%93%E5%85%A5%E7%B2%BE%E7%A1%AE%E7%BC%96%E5%8F%B7',
             'field': '', 'order': '','startDate': '', 'endDate': ''} #'startDate': '', 'endDate': '', 'field': '', 'order': '',,'flag': 'true', 'max': '20', 'offset': '40'
    text = download2('http://www.cnvd.org.cn/flaw/list.htm?flag=true', data, headers)
    print text
    return text




def search3(cnvd):
    ids = col_cnvd.find({'cnvd_id': cnvd}, {'cnvd_id': 1}).distinct('cnvd_id')
    if ids.__len__() > 0:
        print 'no need to insert', cnvd
        return
    text = getContent2('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd))
    print text
    map = commonparser.parseCnvd(text)
    if map == -1:
        print 'this cnvd no exists'
        return
    col_cnvd.insert(map)
    return map

def justGetCNVDFirst():
    # text = getContent2('http://www.cnvd.org.cn/flaw/list.htm?flag=true')
    offset = 0
    while True:
        text = getContent2('http://ics.cnvd.org.cn/?max=20&offset={1}'.format(1, offset))
        list = commonparser.get_cnvd_of_page(text)
        if list == -1:return
        for it in list:
            # search_cnvd_search2_db(it)
            just_insert_cnvd(it)
        print '--success  end--', offset
        offset += 20
        time.sleep(3)

def update_else_keyvalue():
    all = col_cnvd.find({'$or':[{'patch':{'$exists':False}},{'patch':None}]})
    for it in all:
        cnvd = it['cnvd_id']
        print cnvd
        headers = just_get_headers2()
        text = download1('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd), None, headers)
        if text.find('body') < 0:
            print 'script just'
            time.sleep(30)
            continue
        map = commonparser.parseCnvd(text, cnvd)
        if map == -1:
            print 'this cnvd no exists', cnvd
            continue
        commonparser.extend(it, map)
        col_cnvd.update_many({'cnvd_id':cnvd},{'$set':it})
        time.sleep(3)

def just_insert_cnvd(tup):
    cnvd_id = tup[0]
    name = tup[1]
    ids = col_cnvd.find({'cnvd_id': cnvd_id}, {'cnvd_id': 1}).distinct('cnvd_id')
    if ids.__len__() > 0:
        print 'no need to insert', cnvd_id
        return
    print 'insert one', cnvd_id
    col_cnvd.insert({'cnvd_id':cnvd_id, 'bug_name':name, 'threaten_level':tup[2], 'publish_time':tup[-2], 'click_times':tup[-1]})

def searchCPEFromCNVD(cnvd):
    obj = col_cnvd.find({'cnvd_id': cnvd})
    if obj.count() > 0:
        for ite in obj:
            if ite.has_key('influnce_pro'):
                return ite['influnce_pro']
    else:
        # text = getContent2('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd))
        headers = getHeaders(cnvd)
        # data = {'keyword': 'Infinite+Automation+Mango+Automation%E8%B7%A8%E7%AB%99%E8%84%9A%E6%9C%AC%E6%BC%8F%E6%B4%9E'}
        text = download1('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd), None, headers)
        res = commonparser.getCPEFromCNVD(text)
        return res


def udpateScore(cnvd):
    obj = col_cnvd.find({'cnvd_id': cnvd})
    score_map = {}
    if obj.count() > 0:
        for ite in obj:
            if ite.has_key('bad_level'):
                level = ite['bad_level']
                if level.__contains__('('):
                    score_str = level[level.index('(') + 1:level.rindex(")")]
                    score, score_map = computeScore(score_str)
                    riskLevel = level[0:1]
                    score_map['riskLevel'] = riskLevel + '危'
                    # print riskLevel + '危'
                    return score, score_map
                else:
                    riskLevel = level[0:1]
                    score_map['riskLevel'] = riskLevel + '危'
                    return None, score_map
    else:
        # text = getContent2('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd))
        headers = getHeaders(cnvd)
        # data = {'keyword': 'Infinite+Automation+Mango+Automation%E8%B7%A8%E7%AB%99%E8%84%9A%E6%9C%AC%E6%BC%8F%E6%B4%9E'}
        text = download1('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd), None, headers)
        res = commonparser.getBadLevelFromCNVD(text)
        if res is None:return None, {}
        if res.__contains__('('):
            score_str = res[res.index('(') + 1:res.rindex(")")]
            score, score_map = computeScore(score_str)
            riskLevel = res[0:1]
            score_map['riskLevel'] = riskLevel + '危'
            return score, score_map
        else:
            riskLevel = res[0:1]
            score_map['riskLevel'] = riskLevel + '危'
            return None, score_map


def getHeaders(cnvd):
    headers = {
        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Encoding': 'gzip, deflate',
        'Accept-Language': 'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
        'Cache-Control': 'max-age=0',
        'Cookie': '__jsluid=890a93a4872357f01aba2ea943846e46; bdshare_firstime=1511324181935; JSESSIONID=1AFD398A5D3B0C4664B73D18D18D8B05; __jsl_clearance=1511770789.176|0|rk%2FP2zLXkFi9ygif71efOQ5hc6M%3D',
        'Referer': 'http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd),
        'Upgrade-Insecure-Requests': str(1),
        'Host': 'www.cnvd.org.cn',
        'Connection': 'keep-alive',
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0'
    };
    return headers


def getProductFromCnvd(cnvd):
    obj = col_cnvd.find({'cnvd_id': cnvd})
    score_map = {}
    if obj.count() > 0:
        for ite in obj:
            if ite.has_key('influnce_pro'):
                val = ite['influnce_pro']
                prolist = commonparser.getProArrFromText(val)
                return prolist
    else:
        headers = getHeaders(cnvd)
        text = download1('http://www.cnvd.org.cn/flaw/show/{1}'.format(1, cnvd), None, headers)
        str = commonparser.getCPEFromCNVD(text)
        prolist = commonparser.getProArrFromText(str)
        return prolist


if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    # search()
    m = 'xcxd(dfsf)'
    print m[m.index('(') + 1:m.rindex(")")]
    arr = ['CNVD-2016-01894','CNVD-2015-07168']
    # search2(arr[1]) #
    # search3('CNVD-2017-33935')
    # print 'xxfsdf'.find('sxd')
    # search_text_post()
    # search_text()
    justGetCNVDFirst()
    update_else_keyvalue()
    # print (2,1)[1]
    # print udpateScore('CNVD-2012-7834')