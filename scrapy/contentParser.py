# -*- coding: utf-8 -*-
from bs4 import  BeautifulSoup
import time as time_module
import re
import datetime
import downloadPage

def parserHtmlContent(html):
    bs = BeautifulSoup(html, "html.parser")
    body = bs.body
    data = body.find("p", {'class': 'article_txt'})
    # print ("title say: {1}".format(1, u'中国人'))
    title = [];
    print(data.string)
    data2 = body.find_all('p',{'class':'article_txt'})
    index = 1
    for da in data2:
        # print("title say:{1}".format(1, da))
        print (da.string)
        item = []
        item.append(index)
        item.append(da.string.decode('utf-8').encode('GB2312'))
        item.append(time_module.strftime('%Y-%m-%d %H:%M:%S', time_module.localtime(time_module.time())))
        index = index + 1
        title.append(item)

    print("zdgders ".replace('dg',''))
    #保存到csv
    return title


ipRe = re.compile('.*?(/)?((\d+\.){3}\d+)')
headers = {
    "authority": "www.shodan.io",
    "method": "GET",
    "path": "/search?query=",
    "scheme": "https",
    "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
    #  'Accept-Encoding':'gzip, deflate, sdch, br',
    'Accept-Language': 'zh-CN,zh;q=0.8',
    'cache-control': 'max-age=0',
    'cookie': '__cfduid=d02b5725456e0a298df5b6bc7a511dd921505977126; polito="e177fb7fb8cbf0add92825280e7d935e5a051e9b59857cdae449852cdc70a107!"; session=45cac7e3cd49b6b5ccf1baa2b7a27b6c07b906ddgAJVQGMxNTgyZmEwZDBmOTNmZGVjYTlkZTQ4MDNjNTBiZWM0YjY3ZjQ2NGQ4YjE4NGYwNWUzZjRjYjA1MjAyY2U2MDVxAS4=; _ga=GA1.2.810306969.1505977142; _gid=GA1.2.690659948.1510535573; _gat=1',
    'referer': 'https://www.shodan.io/explore/category/industrial-control-systems',
    'Upgrade-Insecure-Requests': str(1),
    'User-Agent': 'Mozilla/5.0(Windows NT 10.0;WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
};

def parserShordan(content, client, port):
    bs = BeautifulSoup(content, 'html.parser')
    body = bs.body
    if body is None:return -1
    resarr = body.find_all('div',{'class':'search-result'})
    if resarr.__len__() == 0: return -1
    arr = {}
    # arr = []
    url = ''
    col = client['lsp_test']['shordan_better']
    for item in resarr:
        # ip = item.find('div',{'class':'ip'}).find('a').string
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
        #  print ip, os, city, badge, time[9:]
        if time != None :
            time = time[9:]
            time = '{1}'.format(1, time)
            try:
                time = datetime.datetime.strptime(time[:-4], '%Y-%m-%d %H:%M:%S') + datetime.timedelta(hours=8)
            except ValueError:
                pass
        obj = {'ip':ip, 'os':os, 'city':city, 'badge':badge, 'time':time, 'pre':pre, 'port':port}
        if city == 'China':
            if address is not None:
                if address.startswith('/host'):
                    url = 'https://www.shodan.io/' + address
                elif address.startswith('http'):
                    url = address
                if url.startswith('http'):
                    time_module.sleep(3)  # 延迟一下，否则请求不下来
                    detail = downloadPage.download2(url, headers)
                    detail_item = parseShordan_detail(detail)
                # 开始解析
        extend(obj, detail_item)
        # arr.append(obj)
        arr[ip] = obj

    #过滤
    res = col.find({'ip': {'$in': arr.keys()}, 'port':port}).distinct('ip')
    print  res
    print arr.keys() #为空大多数是因为不是ics
    documents = []
    for key in arr.keys():
        if res.__contains__(key):
            col.update(arr.get(key), {'$set':{'port':port, 'ip':key}}) #全部更新，因为都有可能发生改变
            print 'update ' #以后可以同时存储，如果有意义
            continue # 以后可以换成更新
        print arr.get(key), key
        documents.append(arr.get(key))
    #插入数据库
    if documents.__len__() == 0:return 0
    saveToFile(documents.__len__())
    col.insert_many(documents)
    return 1


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
    with open('D:\\test\\scrapy\\trace.log', 'a') as f:
        f.write('{1}'.format(1,content) + ',' + time_module.strftime('%Y-%m-%d %H:%M:%S', time_module.localtime()) + '\n')
        f.close

