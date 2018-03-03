#coding=utf-8
from datetime import datetime
from elasticsearch import Elasticsearch
import cnvd_search
import cve_search
import cnnvd_search
from pymongo import MongoClient
import time
import sys

client = MongoClient('192.168.1.152', 27017)
col = client['lsp_test']['cnvd_search']
col2 = client['lsp_test']['vul2']
col3 = client['lsp_test']['vul3']

def getRestIps(ids):
    common = col.find({'cnvd_id':{'$in':ids}},{'cnvd_id':1}).distinct('cnvd_id')
    if common.__len__() == 0:return ids
    c = [tu[0] for tu in common]
    ids = list(set(ids).difference(set(c)))
    return ids


def queryEs():
    es = Elasticsearch([{'host': "192.168.1.152", "port": 9200, 'timeout': 15000}])
    # res = es.search(index='vulnerability', doc_type='base', body={"query": {"match_all": {}}, 'size': 10000})
    res = es.get(id='AV4ZC9FP4_BdWcfVkrlH',index='vulnerability', doc_type='base')
    # print es.count(index='vulnerability', doc_type='base')#id='AV4ZC9FP4_BdWcfVkrlH',
    print es.exists(id='AV4ZC9FP4_BdWcfVkrlHx',index='vulnerability', doc_type='base')
    print res

def insertEs():
    es = Elasticsearch([{'host': "192.168.1.152", "port": 9200, 'timeout': 15000}])
    # res = es.search(index='vulnerability', doc_type='base', body={"query": {"match_all": {}}, 'size': 10000})
    es.create(index='lsp_test', doc_type='lsp_type',id=1, body={'product':None, 'uuid':'xdfsdx', 'vulnerability':{'a':'b'}})
    pass


def testEs():
    es = Elasticsearch([{'host': "192.168.1.152", "port": 9200, 'timeout': 15000}])
    # vulnerability
    # res = es.get(index='vulnerability', id='AV4ZC9FP4_BdWcfVkrlH', doc_type='base')
    res = es.search(index='vulnerability', doc_type='base', body={"query": {"match_all": {}}, 'size':10000})
    c = 0
    list = []
    for item in res['hits']['hits']:
        vul = item['_source']['vulnerability']
        print item['_id'], vul['cnvd'], vul['cnnvd'], vul['cve'], vul['vulName'], item['_source']['product']
        c = c + 1
        print c
        list.append(item)
        if c == 100:
            col3.insert_many(list)
            c = 0
            list = []
    col3.insert_many(list)


def getFromMongo():
    limit = 100
    skip = 0
    while True:
        cur = col2.find({'$or':[{'status':{'$ne':1}},{'status':{'$exists':False}}]}).skip(skip).limit(limit)
        count = 0
        for re in cur:
            vul = re['_source']['vulnerability']
            print re['_id'], vul['cnvd'], vul['cnnvd'], vul['cve'], vul['vulName'], re['_source']['product']
            count = count + 1
            insert_cnvd(vul, re['_id'])
        if count == 0:break
        skip = skip + limit



def work():
    es = Elasticsearch([{'host': "192.168.1.152", "port": 9200, 'timeout': 15000}])
    # vulnerability
    # res = es.get(index='vulnerability', id='AV4ZC9FP4_BdWcfVkrlH', doc_type='base')
    res = es.search(index='vulnerability', doc_type='base', body={"query": {"match_all": {}}, 'size':10000})
    resList = []
    idList = []
    count = 0
    for item in res['hits']['hits']:
        vul = item['_source']['vulnerability']
        # print item['_id'], vul['cnvd'],vul['cnnvd'],vul['cve'],vul['vulName'], item['_source']['product']
        insert_cnvd(vul, item['_id'])
        # if count == 100:
        #     count = 0
        #     ids = getRestIps(idList)
        #     print 'should to insert:', ids
        #     print resList
        #     col.insert_many(resList)
        #     resList = []
        #     idList = []


def insert_cnvd(vul, id):
    obj = cnvd_search.search2(vul['cnvd'])
    time.sleep(2)
    ids = col.find({'cnvd_id': obj.cnvd_id}, {'cnvd_id': 1}).distinct('cnvd_id')
    if ids.__len__() > 0: return
    col.insert_one(obj.__dict__)
    col2.update({'_id':id},{'$set':{'status': 1}})


def updateTask():
    ids = col.find({'status': 1}, {'cnvd_id': 1}).distinct('cnvd_id')
    map = {}
    for bo in col2.find({}):
        box = bo['_source']['vulnerability']
        map[box['cnvd']] = bo['_id']
    for id in ids:
        col2.update({'_id': map[id]}, {'$set': {'status': 1}})


def justQuery():
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit)  # '$or':[{'status':{'$ne':1}},{'status':{'$exists':False}}]
        count = 0
        for re in cur:
            vul = re['_source']['vulnerability']
            print vul['cnvd'], vul['cnnvd'], vul['cve']
            count += 1
        if count == 0:break
        skip = skip + limit


def query_cve_cnvd_cnnvd(fun, param):
    limit = 100
    skip = 0
    while True:
        cur = col3.find({}).skip(skip).limit(limit) # '$or':[{'status':{'$ne':1}},{'status':{'$exists':False}}]
        count = 0
        for re in cur:
            vul = re['_source']['vulnerability']
            print re['_id'], vul['cnvd'], vul['cnnvd'], vul['cve'], vul['vulName'], re['_source']['product']
            count = count + 1
            if vul[param] != '' and (vul[param] is not None):
                result = fun(vul[param])
                if result == -1:
                    time.sleep(30)
                    return
            else:
                print '{1} is null'.format(1, param)
                if 'cve' == param:
                    pass
                # try:
                #     fun(vul['cve'])
                # except Exception as e:
        if count == 0: break
        skip = skip + limit

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    # insertEs()
    # testEs()
    # work()
    # getFromMongo()
    # updateTask()
    # query_cve_cnvd_cnnvd(cve_search.search3, 'cve')
    # query_cve_cnvd_cnnvd(cnvd_search.search2, 'cnvd')
    # query_cve_cnvd_cnnvd(cnnvd_search.search3, 'cnnvd')
    # justQuery()
    # print 'xxx'
    queryEs()
