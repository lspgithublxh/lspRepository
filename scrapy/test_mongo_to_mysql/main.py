#coding=utf-8
#
# 

import pymysql
from pymongo import MongoClient
import pprint

conn = pymysql.connect(host='192.168.1.152', port=3306, user='root', password='Isas.123456', db='isas',
                           charset='utf8')
cur = conn.cursor()

client = MongoClient('192.168.1.152', 27017)
col = client['lsp_test']['shordan_better']

def getAll(sql):
    cur.execute(sql)
    resset = cur.fetchall()
    return resset

def getOne(sql):
    cur.execute(sql)
    resset = cur.fetchone()
    return resset

def insertMany(sql):
    pass

def getCountries():
    return  getAll('select distinct id, region_name_en from isas_region_mapping where parent_id = 2')
    # map = {}
    # for item in resset:
    #    map[item[1]] = item[0]
    # return map

def substrIps(ips):
    ips2 = []
    for ip in ips:
        if ip == None or ip == '':continue
        ip[0:ip.rindex('.')] + '.0/24'
        ips2.append(ip)
    return ips2

def getReallyIps(ips):
    ips2 = []
    for ip in ips:
        ips2.append(ip['ip'])
    return ips2

def getIpInCountries(set):
    pageSize = 100
    for item in set:
        skip = 0
        id = getOne('select max(id) from isas_ip_warehouse where location_country_id ={1}'.format(1, item[0]))
        print 1, item, ',', id
        while True:
            ips = col.find({'city':{'$regex':'.*?' + item[1] + '.*?'}}, {'ip':1}).skip(skip).limit(pageSize)
            if ips.count() == 0: break
            ips = getReallyIps(ips)
            ips = substrIps(ips)
            if ips.__len__() == 0:break
            skip = skip + pageSize
            ip_has_sql = 'select ip_segment from isas_ip_warehouse where ip_segment in ({1})'.format(1, ips.__str__().replace("u'","'")[1:-1])
            print ip_has_sql
            ips_insert = getAll(ip_has_sql)
            if ips_insert.__len__() == 0:continue
            insert_sql = insertSql(ips_insert, item[0], id)
            print 3, insert_sql
            with open("D:\\test\\scrapy\\inset.log", 'a') as f:
                f.write(insert_sql)
                f.close
            insertMany(ips_insert, item[1], id)

def insertMany(ips, countryId, idstart):
    ipsArr = []
    for ip in ips:
        ipsArr.append((idstart, ip, 'null', countryId,0,0,'null'))
    if ipsArr.__len__() > 0:
        cur.executemany('insert into isas_ip_lsp(id, ip_segment, ip_num, location_country_id, location_province_id, location_city_id, update_time) values(%s,%s,%s,%s,%s,%s,%s)', ipsArr)

def insertSql(ips, countryId, idstart):
    sql = 'insert into isas_ip_warehouse(id, ip_segment, ip_num, location_country_id, location_province_id, location_city_id, update_time) values '
    for ip in ips:
        idstart = idstart + 1
        sql = sql + '(' + idstart + ',' + ip + 'null,'+ countryId +',0,0,null),'
    sql = sql[0:-1]

if __name__ == '__main__':
    # print getCountries()
    # print 'select ip from isas_ip_warehouse where ip_segment in ({1})'.format(1, [1,2,3,5].__str__()[1:-1])
    # print [].__len__()
    set = getCountries()
    getIpInCountries(set)
