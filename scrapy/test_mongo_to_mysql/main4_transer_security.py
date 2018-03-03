#coding=utf-8
from pymongo import MongoClient
import  pymysql

conn = pymysql.connect(host='180.76.136.243', port=3306, user='root', password='Isas.1@3456', db='isas',
                           charset='utf8')
# conn = pymysql.connect(host='192.168.1.152', port=3306, user='root', password='Isas.123456', db='isas',
#                            charset='utf8')
cur = conn.cursor()

client = MongoClient('192.168.1.152', 27017)
col = client['lsp_test']['shordan_better']


def substrIps(ips):
    ips2 = []
    for ip in ips:
        if ip == None or ip == '':continue
        if not ipCheck(ip):continue
        ip1 = ip[0:ip.rindex('.')] + '.0/24'
        if ips2.__contains__(ip1):continue
        ips2.append(ip1)
    return ips2

def getReallyIps(ips):
    ips2 = []
    for ip in ips:
        ips2.append(ip['ip'])
    return ips2

def getOne(sql):
    cur.execute(sql)
    resset = cur.fetchone()
    return resset


def gerRestIps(ips, countryId):
    common = getAll('select ip_segment from isas_ip_warehouse where ip_segment in ({ip}) and location_country_id = {id}'.format(ip=ips.__str__().replace("u'","'")[1:-1], id=countryId))
    if common.__len__() == 0:return ips
    c = []
    for tu in common:
        c.append(tu[0])
    #更新mongo数据库:正则的方式更新会更新更多,但是破坏了查找
    ips = list(set(ips).difference(set(c)))
    return ips


def getAll(sql):
    cur.execute(sql)
    resset = cur.fetchall()
    return resset


def getFromMongo():
    id = getOne('select max(id) from isas_ip_warehouse ')
    id = id[0] + 1
    print 1, ',', id
    pageSize = 100
    skip = 0
    counter = {'id':id}
    schedual_id = getOne('select if (max(id) is null, 20000, max(id)) from isas_task_scheduler where id >= 20000 and id < 40000 and location_country_id = 3')
    if schedual_id[0] >= 39999:
        return
    c = {'id': schedual_id[0]}
    # ips = col.find({}, {'ip': 1}).distinct('ip')  # 去重
    # objArr = []
    # idObjArr = []
    while True:
        ips_cur = col.find({'status':{'$ne':1}}, {'ip': 1}).skip(skip).limit(pageSize)
        ips = []
        for ip_c in ips_cur:
            ips.append(ip_c['ip'])
        if ips.__len__() == 0: return
        update_ips = ips
        ips = substrIps(ips)
        if ips.__len__() == 0: break
        # skip = skip + pageSize
        insertIPWare(ips, 3, counter, c)
        #正则ip来更新，更新的更多,但是对多个ip不适用,不安全做法
        col.update_many({'ip': {'$in': update_ips}}, {'$set': {'status': 1}})


def insertIPWare(ips, countryId, counter, c):
    print 'before filtering on mysql:',ips
    ips = gerRestIps(ips, countryId)
    ipsArr = []
    idArr = []
    for ip in ips:
        ipsArr.append((counter['id'], ip, 255, countryId,0,0,'2017-06-22 11:44:36'))
        idArr.append(counter['id'])
        counter['id'] = counter['id'] + 1
    print 'after filtering on mysql, :', ipsArr
    if ipsArr.__len__() > 0:
        insertIntoIpWareNow(ipsArr, idArr, c)
        # objArr[0:len(objArr)] = []
        # idObjArr[0:len(idObjArr)] = []
    # try:
      # cur.executemany('insert into isas_ip_warehouse(id, ip_segment, ip_num, location_country_id, location_province_id, location_city_id, update_time) values(%s,%s,%s,%s,%s,%s,%s)', ipsArr)
      # conn.commit()
      # insertIntoSchedual(idArr, c)
        # except Exception as e:
        #     print e
        # finally:
        #     pass
            # cur.close()
            # conn.close()


def insertIntoIpWareNow(ipsArr, idArr, c):
    cur.executemany('insert into isas_ip_warehouse(id, ip_segment, ip_num, location_country_id, location_province_id, location_city_id, update_time) values(%s,%s,%s,%s,%s,%s,%s)', ipsArr)
    conn.commit()
    insertIntoSchedual(idArr, c)


def insertIntoSchedual(ipsArr, counter):
    start = 0
    step = 20
    items = []
    print 'schedual:', ipsArr
    while (start + step) <= ipsArr.__len__():
        items.append((counter['id'], ipsArr[start], ipsArr[start + step - 1],0,3, 0, 0, '2017-06-22 11:44:36', None))
        start = start + step
        counter['id'] = counter['id'] + 1
    if start < ipsArr.__len__():
        items.append((counter['id'], ipsArr[start], ipsArr[ipsArr.__len__() - 1], 0, 3, 0, 0, '2017-06-22 11:44:36', None))
    print items
    cur.executemany('insert into isas_task_scheduler(id, ips_start_id, ips_end_id, scheduler_ip, location_country_id, location_province_id, location_city_id, last_scan_time, cur_update_time) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)', items)
    conn.commit()

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
    # test()
    # if not False:print 'sss'
    # print col.find({}, {'ip': 1}).distinct('ip').__len__()
    # print col.find({}, {'ip': 1}).count()
    # for cu in col.find({'status':{'$ne':1}}, {'ip': 1}).skip(10).limit(10):
    #     print cu['ip']

    # col.insert_one({'status':1, 'ip':'12.12.12.12'})
    # col.update({'ip':'198.85.222.6'},{'$set':{'status':1}})
    # col.update_many({'ip':{'$in':['75.151.25.141','152.26.72.54']}}, {'$set':{'status':1}})
    #  col.update_many({'city':{'$exists':False}, 'os':{'$exists':True}}, {'$set':{'status':None}})
    col.update_many({}, {'$set': {'status': None}})
    # getFromMongo()
    # insertIntoSchedual()
    # ips = col.find({}, {'ip': 1}).distinct('ip')
    # print ips.__len__()
    # for ip in ips:
    #     print ip
    # res = col.find({'city':{'$exists':False}, 'os':{'$exists':True}})
    # for i in res:
    #     print i['ip']
    pass