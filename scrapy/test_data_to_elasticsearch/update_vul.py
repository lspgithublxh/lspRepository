#coding=utf-8
import sys
from pymongo import MongoClient
import cve_search
import  cnnvd_search
import  cnvd_search
import re as regex
import pprint
import json
from elasticsearch import helpers
from elasticsearch import Elasticsearch

client = MongoClient('192.168.1.152', 27017)
col2 = client['lsp_test']['vul']
col3 = client['lsp_test']['vul3']
col4 = client['lsp_test']['vul4']

def queryAndUpdateCPE():
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit)
        count = 0
        for re in cur:
            vul = re['_source']['vulnerability']
            cve = vul['cve']
            cpe = None
            if vul.has_key('cpe'):
                if (vul['cpe'] is None) or (not vul['cpe'].__contains__('暂不可用')) or (not vul['cpe'].__contains__('暂无')):
                    print 'not need to update:', re['_id']
                    continue
                else:
                    print '需要更新cpe，暂不可用'
                    cpe = None
            print 'start sync : ' , re['_id']
            if cve is not None:
                print 'sync by cve'
                cpe = cve_search.update_search(cve)
                if (cpe is not None) and cpe.__contains__('暂不可用'):
                    print 'cve暂不可用'
                    cpe = None
            if cpe is None and vul['cnnvd'] is not None:
                print 'sync by cnnvd'
                cpe = cnnvd_search.searchFromCnnvd(vul['cnnvd'])
            if cpe is None and vul['cnvd'] is not None:
                print 'sync by cnvd'
                cpe = cnvd_search.searchCPEFromCNVD(vul['cnvd'])
            re['_source']['vulnerability']['cpe'] = cpe
            col3.update_many({'_id':re['_id']},{'$set':re})
            count += 1
        print skip
        # if count == 0:break

        skip = skip + limit
    pass


def queryAndUpdateCPE():
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit)
        count = 0
        for re in cur:
            vul = re['_source']['vulnerability']
            cpe = None
            if vul.has_key('cpe'):
                if vul['cpe'] is not None and vul['cpe'].__contains__('暂无'):
                    print vul['cpe'], re['_id']
                    vul['cpe'] = None
                    col3.update_many({'_id':re['_id']}, {'$set':re})
        skip = skip + limit

def queryAndUpdateBaseScore():
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit)
        count = 0
        for re in cur:
            cvss = re['_source']['CVSS']
            if cvss is None:
                print 'start udpate'
                cvss = {}
                score, score_map = cnvd_search.udpateScore(re['_source']['vulnerability']['cnvd'])
                cvss['cvssBaseScore'] = score
                extend(cvss, score_map)
                re['_source']['CVSS'] = cvss
                col3.update_many({'_id':re['_id']}, {'$set':re})
            else:
                print 'not need to update'
        skip = skip + limit
        print skip

def queryAndUpdateCNNVD():
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit)
        count = 0
        for re in cur:
            vuobj = re['_source']['vulnerability']
            if vuobj['cve'] is not None and vuobj['cnnvd'] is None:
                print 'start udpate'
                cnnvd = cnnvd_search.searchByCVE(vuobj['cve'])
                re['_source']['vulnerability']['cnnvd'] = cnnvd
                col3.update_many({'_id': re['_id']}, {'$set': re})
            else:
                print 'not need to update'
        skip = skip + limit
        print skip


def queryAndUpdateProduct():
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit)
        count = 0
        for re in cur:
            count += 1
            pro = re['_source']['product']
            boo = pro is None or len(pro) == 0
            if not boo:
                if not pro[0].has_key('pro'):
                    print 'old data, which is not format'
                    boo = True
            if boo:
                vuobj = re['_source']['vulnerability']
                cnvd = vuobj['cnvd']
                product = cnvd_search.getProductFromCnvd(cnvd)
                if product.__len__() == 0:
                    cnnvd = vuobj['cnnvd']
                    product = cnnvd_search.getProductFromCnnvd(cnnvd)
                re['_source']['product'] = product
                print 'productList is ', product, ' and id is ', re['_id']
                col3.update_many({'_id':re['_id']},{'$set':re})
            else:
                print 'no productlist need to update', re['_id']
        skip = skip + limit
        if count == 0:break

def updateVendor():
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit)
        count = 0
        for re in cur:
            count += 1
            pro = re['_source']['product']
            boo = pro is None or len(pro) == 0
            if not boo:
                for px in pro:
                    vendor = px['vendor']
                    if vendor == 'unknown_all':
                        prolist = px['pro']
                        name = prolist[0]['name']
                        if name.__contains__(' '):
                            vendor = name[0:name.index(' ')]
                        else:
                            vendor = name
                        px['vendor'] = vendor
                        print 'update one again,', re['_id']
                        col3.update_many({'_id':re['_id']}, {'$set':re})

        skip = skip + limit
        if count == 0: break


def justRemovett(str):
    if str is not None and str.__contains__('\t'):
        cpe = regex.sub('\t+', '', str)
        cpe = regex.sub('(\r\n)+', '\r\n', cpe)
        return cpe
    return str


def queryCPEFromDB():
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit)
        count = 0
        for re in cur:
                count += 1
                vul = re['_source']['vulnerability']
                cpe = vul['cpe']
                print 'start ', re['_id'], cpe
                if cpe is not None and cpe.__contains__('\t'):
                    cpe = regex.sub('\t+', '', cpe)
                    cpe = regex.sub('(\r\n)+', '\r\n', cpe)
                    print cpe, re['_id']
                    vul['cpe'] = cpe
                    col3.update_many({'_id':re['_id']}, {'$set': re})
        skip = skip + limit


def import_data(self,list):
    helpers.bulk(self.es, list, request_timeout=100)


def extend(map1, map2):
    for key in map2.iterkeys():
        if key != None:
            map1[key] = map2[key]

def copyToAnothor():
    cur = col3.find({})
    for re in cur:
        print re
        col4.insert(re)

def test():
    cur = col3.find({})
    for re in cur:
        cvss = re['_source']['CVSS']
        if cvss is None:
            print re['_id']

def export_db():
    cur = col3.find({})
    f = None
    a = 0
    with open('f2.json','a') as f:
        for re in cur:
            # t = str(re).replace('u\'','\'')
            text = json.dumps(re, ensure_ascii=False, indent=2)
            f.write(text.replace('\n',''))
            f.write('\r')
            a = a + 1
            print a
    f.close()

def updatecvss():
    cur = col3.find({'_id': {
        '$in': ['AV4ZC9ie4_BdWcfVkrlt', 'AV4ZDLp84_BdWcfVkr2a', 'AV4ZDBW04_BdWcfVkrqa', 'AV4ZDQE84_BdWcfVkr7p',
                'AV4ZDLyV4_BdWcfVkr2j', 'AV4ZDBB24_BdWcfVkrqA', 'AV4ZDBWH4_BdWcfVkrqZ', 'AV4ZDB2c4_BdWcfVkrq-',
                'AV4ZC9FP4_BdWcfVkrlH', 'AV4ZC9gk4_BdWcfVkrlr']}})
    for re in cur:
        s = re['_source']['CVSS']
        if s is not None:
            s['integImpact'] = float(s['integImpact'])
            col3.update_many({'_id': re['_id']}, {'$set': re})

def getDataFromFile():
    cur = col3.find({})
    # cur = col3.find({'_id':{'$in':['AV4ZC9ie4_BdWcfVkrlt','AV4ZDLp84_BdWcfVkr2a','AV4ZDBW04_BdWcfVkrqa','AV4ZDQE84_BdWcfVkr7p','AV4ZDLyV4_BdWcfVkr2j','AV4ZDBB24_BdWcfVkrqA','AV4ZDBWH4_BdWcfVkrqZ','AV4ZDB2c4_BdWcfVkrq-']}})
    f = None
    a = 0
    list = []
    es = Elasticsearch([{'host': "192.168.1.152", "port": 9200, 'timeout': 15000}])

    for re in cur:
        # t = str(re).replace('u\'','\'')
        
        if a % 100 == 0:
            try:
                helpers.bulk(es, list, request_timeout=100)
            except Exception as e:
                print e
            list = []
        list.append(re)
        a = a + 1
        print a
    try:
        helpers.bulk(es, list, request_timeout=100)
    except Exception as e:
        print e


def justExport():
    pass

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    # queryAndUpdateCNNVD()
    # queryAndUpdateCPE()
    # copyToAnothor()
    # queryAndUpdateBaseScore()
    # test()
    # export_db()
    #加索引到es,未查看是否已经存在,也无状态位
    getDataFromFile()
    # updatecvss()
    # queryAndUpdateProduct()
    # updateVendor()
    # queryCPEFromDB()