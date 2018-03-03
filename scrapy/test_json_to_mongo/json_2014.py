#coding=utf-8
from pymongo import MongoClient
import json
import os

client = MongoClient('192.168.1.152', 27017)
col = client['lsp_test']['shordan_better']

def parseJson():
    jsonArr = []
    ips = []
    ip_insert = []
    ip_map = {}
    obj_union = []
    files = os.listdir('./')
    count = 0
    count2 = 0
    for file in files:
        if file.endswith('.json'):
            print 'parse file:', file
            with open(file) as f: # './iec-104.json'
                while True:
                    line = f.readline()
                    if line == '' or line == None:break
                    jo = json.loads(line)
                    ip = jo['ip_str']
                    if not ipCheck(ip):continue
                    if ips.__contains__(ip): continue
                    ips.append(ip)
                    ip_insert.append(ip)
                    # json_insert = {'ip':ip, 'port':jo['port'], 'os':jo['os'],'time':jo['timestamp']}
                    ip_map[ip] = {'ip':ip, 'port':jo['port'], 'os':jo['os'],'time':jo['timestamp']}
                    # jsonArr.append(json_insert)
                    if ip_insert.__len__() == 100:
                        ipArr = getResIps(ip_insert, ip_map)
                        count = count + ipArr.__len__()
                        obj_union.extend(ipArr)
                        if obj_union.__len__() > 50:
                            print obj_union
                            count2 = count2 + obj_union.__len__()
                            col.insert_many(obj_union)
                            obj_union = []

                        # jsonArr = []
                        ip_insert = []
    if obj_union.__len__() > 0:
        count2 = count2 + obj_union.__len__()
        col.insert_many(obj_union)
    print count, count2


def getResIps(ips, ip_map):
    ipres = col.find({'ip': {'$in': ips}}, {'ip': 1}).distinct('ip')
    objs = []
    for ip in ipres:
        objs.append(ip_map[ip])
    return objs

def ipCheck(ip):
    parts = ip.split('.')
    if parts.__len__() != 4:
        return False
    try:
        for part in parts:
            if int(part) < 0 or int(part) > 255:
                return False
    except Exception as e:
        print e
        return False
    if parts[0].startswith('0'): return False
    return True


if __name__ == '__main__':
    parseJson()
    # s = set()
    # s.add('ip')
    # s.add(2)
    # s.add('2.23')
    # s.add(2)
    # s.add('ip')
    # m = list(s)
    # print s
    # print m
    # res = col.find({'ip': {'$in': ['24.106.125.94', '47.68.28.64', '217.92.110.235']}}, {'ip': 1}).distinct('ip')
    # for i in res:
    #     print i
    # a = ['2','3','5']
    # b = ['4','6','8']
    # s = list(set(a).union(set(b)))
    # print a
    # print b
    # print s
    # a.extend(b)
    # print a
    # # a.append(b)
    # # print a
    # a[len(a):len(b)] = b
    # print a
