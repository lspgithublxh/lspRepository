#coding=utf-8
import sys
import requests
import commonparser
import cnvd_search
from phantom_download import getContent2
from pymongo import MongoClient

client = MongoClient('192.168.1.152', 27017)
# col_cve = client['lsp_test']['cve_search']
col_cve = client['lsp_test']['cve_search2']
col_cve2 = client['lsp_test']['cve_search2']
col_cnvd = client['lsp_test']['cnvd_search3']


def search2(cve):
    headers = {
        'Host':'cve.scap.org.cn',
        'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0',
        'Accept':'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Language':'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
        'Accept-Encoding':'gzip, deflate',
        'Referer':'http://cve.scap.org.cn/index.html',
        'Cookie':'Hm_lvt_ca227db14814d01f2e44f01433e48552=1511341344; Hm_lpvt_ca227db14814d01f2e44f01433e48552=1511341388; _pk_id.1.4171=3016af841b8e096f.1511341344.1.1511341388.1511341344.; _pk_ses.1.4171=*',
        'Connection':'keep-alive',
        'Upgrade-Insecure-Requests':str(1)
    };
    # data = {'keyword': 'Infinite+Automation+Mango+Automation%E8%B7%A8%E7%AB%99%E8%84%9A%E6%9C%AC%E6%BC%8F%E6%B4%9E'}
    text = cnvd_search.download1('http://cve.scap.org.cn/{1}.html'.format(1, cve), None, headers)
    # print text
    map = commonparser.parseCve(text)
    return map


def addNewCVEFromCNNVD():
    limit = 100
    skip = 0
    while True:
        cur = col_cnvd.find({}).skip(skip).limit(limit)  # '$or':[{'status':{'$ne':1}},{'status':{'$exists':False}}]
        count = 0
        for re in cur:
            count += 1
            if re.has_key('cve_id'):
                if re['cve_id'] is None: continue
                print 'start download:', re['cve_id']
                search3(commonparser.justStrip(re['cve_id']))
        if count == 0: break
        skip += limit


def search3(cveid):
    ids = col_cve2.find({'cveid':cveid}, {'cveid':1}).distinct('cveid')
    if ids.__len__() > 0:
        print 'no need to insert', cveid
        return
    text = getContent2('http://cve.scap.org.cn/{1}.html'.format(1, cveid))
    # print text
    map = commonparser.parseCve(text, cveid)
    if map == -1 : return
    col_cve2.insert(map)


def justGetContent(cveid):
    # print 'http://cve.scap.org.cn/{1}.html'.format(1, cveid)
    text = getContent2('http://cve.scap.org.cn/{1}.html'.format(1, cveid))
    return commonparser.parseDescAndImportant(text, cveid)

def update_search(cveid):
    obj = col_cve.find({'cveid': cveid})
    print obj
    if obj.count() > 0:
        for cur in obj:
            if cur.has_key('tab1'):
                return cur['tab1']['data']['cpe']
            else:
                if cur.has_key('MITRE'):
                    return cur['MITRE']['cpe']
                elif cur.has_key('NVD'):
                    return cur['NVD']['cpe']
    else:
        text = getContent2('http://cve.scap.org.cn/{1}.html'.format(1, cveid))
        cpe = commonparser.getCPEFromCVE(text)
        return cpe
    return None



if __name__ == '__main__':
    # search2('CVE-2015-3934')
    # search3('CVE-2009-3739')
   # print update_search('CVE-2009-3739')
    addNewCVEFromCNNVD()