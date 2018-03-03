# -*- coding: utf-8 -*-
import  requests
import socket
import contentParser
import sys
from pymongo import  MongoClient
import time

def download(url):
    header = {
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Accept-Encoding': 'gzip, deflate, sdch',
        'Accept-Language': 'zh-CN,zh;q=0.8',
        'Connection': 'keep-alive',
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.235'
    };
    try:
        print ("start downloadpage url:{1}".format(1,url))
        response = requests.get(url, headers=header, timeout=80)
        response.encoding = "UTF-8"
        print ("finish downloadpage url:{1}".format(1,url))
    except socket.timeout:
        print ("timeout!!url:{1}".format(url))
    return response.text



def download2(url, header):
    try:
        status = 0
        res = requests.get(url, headers=header, timeout=30) # ,verify=False 不加验证可能会报警告
        status = 1
        print res.text
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
    # print text

    # client['lsp_test']['shordan'].insert_one({'ip':'23.232.32.232'})
    # res = client['lsp_test']['shordan'].find({'ip':{'$in':['80.14.179.127', '23.23.23.23']}}).distinct('ip')
    # print res
    status = contentParser.parserShordan(text, client, port)
    return status


if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8') # 解决报错 asclli
    # print 'hello boy'
    # ports = [161,5006,4410,8080,23,7001]#5006,4410,8080,23,7001,102,443,502,789,81,
    ports = [18246,4911,102,502,44818,30718,2455,47808,161,5002,65000,81,5006,61697,4410,1962,8888,5007,8000,33333,3000,50123,48899,80,5005,443,1911,5060,1200,6000,51960,6004]
    # ports = [1025,8080,5003,5094,23,2000,6002,2404,8001,3003,60000,5000,789,7001,9999,5004,9600,20547,10001,55555,7000,4840,4000,1201,4800,20000,18245,60001,20004,5001]
    # ports = [2455, 20000, 18245, 60001, 20004, 5001, 48899, 80, 5005, 443, 1911, 5060, 1200, 6000, 51960, 6004];

    client = MongoClient('192.168.1.152', 27017)
    while True:
        try:
            for port in ports:
                print 'next port:', port
                page = 1
                while True:
                    condition = 'port%3A{1}'.format(1, port) + '&page={1}'.format(1, page)
                    status = shordan_query(port, condition, client)
                    if status == -1:
                        break
                    page = page + 1
        except requests.exceptions.SSLError:pass



