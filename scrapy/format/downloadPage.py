# -*- coding: utf-8 -*-
import  requests
import socket
import contentParser
import sys
from pymongo import  MongoClient
import time


def download2(url, header):
    try:
        print url
        status = 0
        res = requests.get(url, headers=header, timeout=30) # ,verify=False 不加验证可能会报警告
        status = 1
        print res.encoding

    except (requests.exceptions.ConnectionError, requests.exceptions.ReadTimeout,requests.exceptions.ConnectTimeout,requests.exceptions.ChunkedEncodingError, Exception) ,ex:
        # 特殊错误可以考虑等待几秒
        print 'exception occured', ex
        return '<html></html>'; # <body></body>
    # res.encoding = 'utf-8'
    return res.text


def shordan_query(port, condition, client):
    headers = {
        "authority": "www.shodan.io",
        "method": "GET",
        "path": "/search?query=" + condition,
        "scheme": "https",
        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        #  'Accept-Encoding':'gzip, deflate, sdch, br',
        'Accept-Language': 'zh-CN,zh;q=0.8',
        'cache-control': 'max-age=0',
        'cookie': '__cfduid=d02b5725456e0a298df5b6bc7a511dd921505977126; polito="d776973cc56a3a64b963d1247b26f8165a0bac735a080d79e449853f25e6b2a1!"; _gat=1; session=e86912a4efb5f6c8df64e1e40913efc5553a86b0gAJVQDQ5YjBjZjAwODg0YTMyNmNlYWQzNzU4OTQyMTRhYmVlY2NmNjVjMjFkOTgxNmU0MGYyYmUxODRlOWEyNzdlMGJxAS4=; cf_use_ob=0; _ga=GA1.2.810306969.1505977142; _gid=GA1.2.690659948.1510535573',
        'referer': 'https://www.shodan.io/explore/category/industrial-control-systems',
        'Upgrade-Insecure-Requests': str(1),
        'User-Agent': 'Mozilla/5.0(Windows NT 10.0;WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
        };
    text = download2('https://www.shodan.io/search?query=' + condition, headers)

    status = contentParser.parserShordan(text, client, port)
    return status

def fofa_query(pro, condition, client):
    headers = {
        # "authority": "www.shodan.io",
        # "method": "GET",
        # "path": "/search?query=" + condition,
        # "scheme": "https",
        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Accept-Encoding':'gzip, deflate, sdch, br',
        'Accept-Language': 'zh-CN,zh;q=0.8',
        'cache-control': 'max-age=0',
        'cookie': 'Hm_lvt_9490413c5eebdadf757c2be2c816aedf=1511177340,1511231119,1511231163,1511239154; Hm_lpvt_9490413c5eebdadf757c2be2c816aedf=1511239164; _fofapro_ars_session=f992fe1ba9f82e9814669e6aa0365d16',
        # 'referer': 'https://www.shodan.io/explore/category/industrial-control-systems',
        'Upgrade-Insecure-Requests': str(1),
        'Host':'fofa.so',
        'Connection':'keep-alive',
        'If-None-Match':'W/"561394f8a0b6c956fab6148856ae219b"',
        'User-Agent': 'Mozilla/5.0(Windows NT 10.0;WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
    };
    text = download2('https://fofa.so/result?q=protocol%3D{pro}&page={page}&qbase64=YXBwPSJXaW5kb3dzNyI%3D'.format(page=condition, pro=pro), headers)
    # print text
    status = contentParser.parseFofa(text, client, pro)
    return status


def zoomeye_query(port, condition, client):
    headers = {
        # "authority": "www.shodan.io",
        # "method": "GET",
        # "path": "/search?query=" + condition,
        # "scheme": "https",
        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        # 'Accept-Encoding': 'gzip, deflate, sdch, br',
        'Accept-Language': 'zh-CN,zh;q=0.8',
        # 'cache-control': 'max-age=0',
        'Cookie': '__jsluid=5345eb733686c8eb3797020407928089; Hm_lvt_e58da53564b1ec3fb2539178e6db042e=1505383398; Hm_lvt_3c8266fabffc08ed4774a252adcb9263=1510571725,1511246749; Hm_lpvt_3c8266fabffc08ed4774a252adcb9263=1511250824; __jsl_clearance=1511255985.7|0|nrz1rR3G9Sl0k3WfsZE1pHUkoN8%3D',
        'Referer': 'https://www.zoomeye.org/searchResult?q=port%3A102',
        'Upgrade-Insecure-Requests': str(1),
        'Host': 'www.zoomeye.org',
        'Connection': 'keep-alive',
        # 'If-None-Match': '1dc08276b844a4a4b818c976282e876e5cec3645-gzip',
        'User-Agent': 'Mozilla/5.0(Windows NT 10.0;WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
    };
    text = download2('https://www.zoomeye.org/searchResult?q=port%3A{port}'.format(port=102), headers)
    print text
    status = contentParser.parsezoomeye(text, client, port)
    return status

def start():
    ports = [18246, 4911, 102, 502, 44818, 30718, 2455, 47808, 161, 5002, 65000, 81, 5006, 61697, 4410, 1962, 8888,
             5007, 8000, 33333, 3000, 50123, 48899, 80, 5005, 443, 1911, 5060, 1200, 6000, 51960, 6004, 1025, 8080,
             5003, 5094, 23, 2000, 6002, 2404, 8001, 3003, 60000, 5000, 789, 7001, 9999, 5004, 9600, 20547, 10001,
             55555, 7000, 4840, 4000, 1201, 4800, 20000, 18245, 60001, 20004, 5001]
    # ports = [18246,4911,102,502,44818,30718,2455,47808,161,5002,65000,81,5006,61697,4410,1962,8888,5007,8000,33333,3000,50123,48899,80,5005,443,1911,5060,1200,6000,51960,6004]
    # ports = [1025,8080,5003,5094,23,2000,6002,2404,8001,3003,60000,5000,789,7001,9999,5004,9600,20547,10001,55555,7000,4840,4000,1201,4800,20000,18245,60001,20004,5001]

    client = MongoClient('192.168.1.152', 27017)
    while True:
        try:
            for port in ports:
                print 'next port:', port
                time.sleep(60)
                page = 1
                while True:
                    condition = 'port%3A{1}'.format(1, port) + '&page={1}'.format(1, page)
                    status = shordan_query(port, condition, client)
                    if status == -1:
                        break
                    page = page + 1
        except requests.exceptions.SSLError:
            pass


def start_fofa():
    proto = ['s7', 'codesys', 'gesrtp', 'omron', 'modbus', 'fox', 'ethernetip', 'dnp3', 'bacnet', 'melsecq', 'hart',
             'pcworx', 'redlion', 'iec', 'vertx', 'moxa', 'lantronix']

    client = MongoClient('192.168.1.152', 27017)
    while True:
        try:
            for pro in proto:
                print 'next protocol:', pro
                time.sleep(5)
                combineCondition(pro, client)
        except requests.exceptions.SSLError:
            pass


def combineCondition(pro, client):
    # 可以联合的查询
    countrys = ['US','BF','CN','CA','BO','BR','CO','ML','SS','SD','IT','NL','GB','ES','FR','RU','JP','KP','SE','AT','IN']
    years = []
    ports = [18246, 4911, 102, 502, 44818, 30718, 2455, 47808, 161, 5002, 65000, 81, 5006, 61697, 4410, 1962, 8888,
            5007, 8000, 33333, 3000, 50123, 48899, 80, 5005, 443, 1911, 5060, 1200, 6000, 51960, 6004, 1025, 8080,
            5003, 5094, 23, 2000, 6002, 2404, 8001, 3003, 60000, 5000, 789, 7001, 9999, 5004, 9600, 20547, 10001,
            55555, 7000, 4840, 4000, 1201, 4800, 20000, 18245, 60001, 20004, 5001]
    servers = []
    os = []
    # 随机字符串
    randoms = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
              'v', 'w', 'x', 'y', 'z']
    # 关键词
    keywords = []
    query_fofa_start(pro, '', client)
    combine_map = {'port':ports, 'country':countrys, 'title':randoms}
    for ky in combine_map.keys():
        for item in combine_map[ky]:
            query_fofa_start(pro, '&{item}%3D{value}'.format(item=ky, value=item), client)
    for port in ports:
        for country in countrys:
            query_fofa_start(pro,'&port%3D{port}&country%3D{country}'.format(port=port, country=country), client)

    pass

def query_fofa_start(pro, condition, client):
    page = 1
    while True:
        status = fofa_query(pro, '{page}{con}'.format(page=page, con=condition), client)
        if status == -1:
            break
        page = page + 1


def start_zoomeye():
    ports = [18246, 4911, 102, 502, 44818, 30718, 2455, 47808, 161, 5002, 65000, 81, 5006, 61697, 4410, 1962, 8888,
             5007, 8000, 33333, 3000, 50123, 48899, 80, 5005, 443, 1911, 5060, 1200, 6000, 51960, 6004, 1025, 8080,
             5003, 5094, 23, 2000, 6002, 2404, 8001, 3003, 60000, 5000, 789, 7001, 9999, 5004, 9600, 20547, 10001,
             55555, 7000, 4840, 4000, 1201, 4800, 20000, 18245, 60001, 20004, 5001]

    client = MongoClient('192.168.1.152', 27017)
    while True:
        try:
            for port in ports:
                print 'next port:', port
                time.sleep(5)
                zoomeye_query(port,None, client)
        except requests.exceptions.SSLError:
            pass

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8') # 解决报错 asclli
    # start()
    # proto = ['s7','codesys', 'gesrtp', 'omron', 'modbus', 'fox', 'ethernetip', 'dnp3', 'bacnet', 'melsecq', 'hart', 'pcworx', 'redlion','iec', 'vertx', 'moxa', 'lantronix']
    # client = MongoClient('192.168.1.152', 27017)
    # col = client['lsp_test']['fofa_better']
    # a = [u'175.191.68.63', u'101.104.45.55', u'54.214.233.111', u'185.159.80.119', u'185.159.80.118', u'185.159.80.115', u'185.159.80.113', u'185.159.80.104', u'87.230.43.110', u'185.159.80.102']
    # its =col.find({'ip': {'$in': a}, 'badge':'http'}).distinct('ip')
    # print its
    # start_fofa()
    start_zoomeye()




