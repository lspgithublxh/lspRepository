# -*- coding: utf-8 -*-
from bs4 import  BeautifulSoup
import time as time_module
import re
import datetime
import downloadPage
from util import ipCheck


ipRe = re.compile('.*?(/)?((\d+\.){3}\d+)')


def parserShordan(content, client, port):
    bs = BeautifulSoup(content, 'html.parser')
    body = bs.body
    if body is None:return -1
    resarr = body.find_all('div',{'class':'search-result'})
    if resarr.__len__() == 0: return -1
    arr = {}
    url = ''
    col = client['lsp_test']['shordan_better']
    for item in resarr:
        ip = item.find('div', {'class':'ip'}).find('a')['href']
        detail_item = {}
        # 详细信息请求逻辑--暂时不用，
        address = ip
        ip = ipRe.match(ip)
        if ip is not None:
            ip = ip.group(2)
        son = item.find('div',{'class':'search-result-summary'})
        if ip is None:
            ip = son.find('span')
            if ip is not None:
                ip = ip.string
        badge = None
        os = None
        city = None
        time = None
        pre = None
        try:
            os = son.find('a', {'class': 'os'})
            if os is not None:
                os = os.string
                time = os.next_element.next.next.string
            city = son.find('a', {'class': 'city'})
            if city is not None:
                city = city.string
            pre = item.find('pre')
            if pre is not None:
                pre = pre.string
            badge = son.find('span', {'class': 'badge'})
            if badge is not None :
                badge = badge.string
            else:
                # badge = 'ics'
                continue
            if badge != 'ics': continue
        except AttributeError:
            pass;
        if time != None :
            time = time[9:]
            time = '{1}'.format(1, time)
            try:
                time = datetime.datetime.strptime(time[:-4], '%Y-%m-%d %H:%M:%S') + datetime.timedelta(hours=8)
            except ValueError:
                pass
        obj = {'ip':ip, 'os':os, 'city':city, 'badge':badge, 'time':time, 'pre':pre, 'port':port}
                # 开始解析
        extend(obj, detail_item)
        arr[ip] = obj

    #过滤
    saveToMongo(col, arr, port)
    return 1

def saveToMongo(col, arr, port):
    res = col.find({'ip': {'$in': arr.keys()}, 'port':port}).distinct('ip')
    print  res
    print arr.keys() #为空大多数是因为不是ics
    documents = []
    for key in arr.keys():
        if res.__contains__(key):
            col.update({'port':port, 'ip':key}, {'$set':arr.get(key)}) #全部更新，因为都有可能发生改变
            print 'update ' #以后可以同时存储，如果有意义
            continue # 以后可以换成更新
        print arr.get(key), key
        documents.append(arr.get(key))
    #插入数据库
    if documents.__len__() == 0:return 0
    saveToFile(documents.__len__())
    col.insert_many(documents)

def saveToMongo_fofa(col, arr, protocol):
    keys = arr.keys()
    print  protocol
    # a = [u'175.191.68.63', u'101.104.45.55', u'54.214.233.111', u'185.159.80.119', u'185.159.80.118', u'185.159.80.115', u'185.159.80.113', u'185.159.80.104', u'87.230.43.110', u'185.159.80.102']
    res2 = col.find({'ip': {'$in': keys}}).distinct('ip') # , 'badge': protocol增加这个，导致查出来为空，奇怪
    # print 'not need  insert :', res2, 'total to insert:', arr.keys() #为空大多数是因为不是ics
    print 'need to insert: ', (arr.keys().__len__() - res2.__len__())
    documents = []
    for key in arr.keys():
        if res2.__contains__(key):
            # col.update(arr.get(key), {'$set':{'badge':protocol, 'ip':key}}) #全部更新，因为都有可能发生改变
            col.update({'ip':key}, {'$set': arr.get(key)})  #
            print 'update ' #以后可以同时存储，如果有意义
            continue # 以后可以换成更新
        print arr.get(key)
        documents.append(arr.get(key))
    #插入数据库
    if documents.__len__() == 0:return 0
    saveToFile(documents.__len__())
    col.insert_many(documents)

def parseShordan_detail(content):
    bs = BeautifulSoup(content, 'html.parser')
    body = bs.body
    obj = {}
    if body is None: return obj
    table = body.find('table', {'class':'table'})
    if table is not None:
        trArr = table.find_all('tr')
        for tr in trArr:
            td = tr.find('td')
            th = tr.find('th')
            obj[td.string] = th.string
    return obj

def extend(map1, map2):
    for key in map2.iterkeys():
        if key != None:
            map1[key] = map2[key]

def saveToFile(content):
    with open('trace.log', 'a') as f:
        f.write('{1}'.format(1,content) + ',' + time_module.strftime('%Y-%m-%d %H:%M:%S', time_module.localtime()) + '\n')
        f.close

def parseFofa(content, client, pro):
    bs = BeautifulSoup(content, 'html.parser')
    body = bs.body
    if body is None: return -1
    resarr = body.find_all('div', {'class': 'list_mod'})
    if resarr.__len__() == 0: return -1
    arr = {}
    col = client['lsp_test']['fofa_more']
    for item in resarr:
        ul = item.find('ul', {'class':'list_sx1'})
        ip = None
        time = None
        country = None
        city = None
        if ul is not None:
            ipItem = ul.find_all('li')
            if ipItem is not None:
                ip = ipItem[0].find('a').string.strip()
                if not ipCheck(ip):continue
                # print ip.next_element.next.next
                time = ipItem[1].text.strip()
                # print time
                country = ipItem[2].find('a').string
                city = ipItem[2].find('a').next_element.next.next.string
            # print country , city
        pre = item.find('div',{'class':'auto-wrap'})
        if pre is not None:
            pre = pre.text.strip().replace('\r\n','').replace("'",'')
            # print pre
        proto_port = item.find('div',{'class':'span'}).find_all('a')
        port_ = proto_port[0].string
        proto = proto_port[1].string
        # print proto, port
        if ip is None:continue
        obj = {'ip': ip, 'os': None, 'city': city, 'country':country, 'badge': proto, 'time': time, 'pre': pre, 'port': port_}
        arr[ip] = obj
        # print obj
    saveToMongo_fofa(col, arr, pro)


def parsezoomeye(content, client, pro):
    pass

