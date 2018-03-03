#coding=utf-8
import sys
import requests
import commonparser
import cnvd_search

from phantom_download import getContent2, getContentByPost
from pymongo import MongoClient
import re

client = MongoClient('192.168.1.152', 27017)
col_cnnvd = client['lsp_test']['cnnvd_search']
col_cnnvd2 = client['lsp_test']['cnnvd_search2']
col_cnnvd3 = client['lsp_test']['cnnvd_search3']

col_cnvd = client['lsp_test']['cnvd_search3']

def search2(cnnvd):
    headers = {
        'Host':'www.cnnvd.org.cn',
        'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0',
        'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Language':'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
        'Accept-Encoding':'gzip, deflate',
        'Cookie':'SESSION=0ba33c55-6b28-4a28-943b-31baad4e4964',
        'Connection':'keep-alive',
        'Upgrade-Insecure-Requests':str(1),
        'Cache-Control':'max-age=0'
    };
    # data = {'keyword': 'Infinite+Automation+Mango+Automation%E8%B7%A8%E7%AB%99%E8%84%9A%E6%9C%AC%E6%BC%8F%E6%B4%9E'}
    text = cnvd_search.download1('http://www.cnnvd.org.cn/web/xxk/ldxqById.tag?CNNVD={1}'.format(1, cnnvd), None, headers)
    # print text
    map = commonparser.parseCNNvd(text, cnnvd)
    print map
    return map


def addNewCNNVDFromCnvd():
    limit = 100
    skip = 0
    while True:
        cur = col_cnvd.find({}).skip(skip).limit(limit)  # '$or':[{'status':{'$ne':1}},{'status':{'$exists':False}}]
        count = 0
        for re in cur:
            count += 1
            if re.has_key('cve_id'):
                print re['cve_id']
                if re['cve_id'] is None:continue
                cnnvd = searchByCVE(commonparser.justStrip(re['cve_id']))
                if cnnvd is None:continue
                search3(commonparser.justStrip(cnnvd))
        if count == 0:break
        skip += limit

def search3(cnnvd):
    ids = col_cnnvd3.find({'cnnvd': cnnvd}, {'cnnvd': 1}).distinct('cnnvd')
    if ids.__len__() > 0:
        print 'no need to insert', cnnvd
        return
    text = getContent2('http://www.cnnvd.org.cn/web/xxk/ldxqById.tag?CNNVD={1}'.format(1, cnnvd))
    map = commonparser.parseCNNvd(text, cnnvd)
    if map == -1:
        print 'no found content requested'
        return
    col_cnnvd3.insert(map)
    print map
    return map


def searchFromCnnvd(cnnvd):
    if cnnvd is None:return None
    upobj = col_cnnvd.find({'cnnvd': cnnvd})
    if upobj.count() > 0:
        for it in upobj:
           if it.has_key('influnce_obj'):
                return it['influnce_obj']
    else:
        text = getContent2('http://www.cnnvd.org.cn/web/xxk/ldxqById.tag?CNNVD={1}'.format(1, cnnvd))
        if text is not None:
            return commonparser.getCPEFromCNNVD(text)
    return text


def getProductFromCnnvd(cnnvd):
    text = searchFromCnnvd(cnnvd)
    # prolist = []
    # vendor = ''
    map = {}
    product = []
    if text is None:return product
    pre = re.compile('\n')
    pre4 = re.compile('(.*?)(?:\:)?((?:v)?\d+.*)')
    pre2 = re.compile('(.+?)\s+(.+?)(?:\:)?((?:v)?\d+.*)')
    groups = pre.split(text)
    for group in groups:
        if group != '':
            groups2 = pre2.match(group)
            if groups2 is None:
                p2 = re.compile('\w+')
                ma = p2.match(group)
                if ma is not None:
                    ma4 = pre4.match(group)
                    if ma4 is not None:
                        name = ma4.group(1).strip()
                        version = ma4.group(2).strip()
                        if map.has_key('unknown_vendor'):
                            map['unknown_vendor'].append({'name': name, 'version': version, 'type': None, 'classify': None})
                        else:
                            pr2list = [{'name': name, 'version': version, 'type': None, 'classify': None}]
                            map['unknown_vendor'] = pr2list
                    else:
                        if map.has_key('unknown_all'):
                            map['unknown_all'].append({'name': group, 'version': None, 'type': None, 'classify': None})
                        else:
                            pr2list = [{'name': group, 'version': None, 'type': None, 'classify': None}]
                            map['unknown_all'] = pr2list
                continue
            vendor = groups2.group(1).strip()
            name = groups2.group(2).strip()
            version = groups2.group(3).strip()
            if map.has_key(vendor):
                map[vendor].append({'name': name, 'version': version, 'type': None, 'classify': None})
            else:
                prlist = [{'name': name, 'version': version, 'type': None, 'classify': None}]
                map[vendor] = prlist
    for key in map.keys():
        product.append({'pro':map.get(key), 'vendor':key})
    return product


def searchByCVE(cve):
    upobj = col_cnnvd.find({'cve': cve})
    if upobj.count() > 0:
        for it in upobj:
           if it.has_key('cnnvd'):
               return it['cnnvd']
    else:
        text = getContentByPost('http://www.cnnvd.org.cn/web/vulnerability/queryLds.tag', cve)
        if text is not None:
            return commonparser.getCNNVDFromText(text)
        return None
        pass

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    # search3('CNNVD-201110-017')#CNNVD-201110-017
    # print searchByCVE('CVE-2015-6494')
    # str = 'Infinite_automation_systems Mango_automation:2.6.0  \nInfinite_automation_systems Mango_automation:2.5.5  \nInfinite_automation_systems Mango_automation:2.5.0\n'
    str = 'sdf\sdfs\r\n\n\r\n\dfs\n\nsdf'
    pre = re.compile('\r\n')
    groups = pre.split(str)
    pre2 = re.compile('(.*?)(?:\:)?((?:v)?\d+.*)')
    for group in groups:
        if group != '':
            print group
            groups2 = pre2.match(group)
            if groups2 is None:continue
            print groups2.group(1)
            print groups2.group(2)
    print 'end'
    p2 = re.compile('\w+')
    # ma = p2.match('  ')
    # print ma.group()
    s = 'Jerome_schneider Ameos_dragndropupload:2.0.2'
    pre = re.compile('\n')
    print pre.split(s)
    pre2 = re.compile('(.+?)\s+(.+?)(?:\:)?((?:v)?\d+.*)')
    groups2 = pre2.match(s)
    print groups2.group(1)
    print groups2.group(2)
    print groups2.group(3)
    print {}.has_key('ad')
    print {'ad':[]}['ad']