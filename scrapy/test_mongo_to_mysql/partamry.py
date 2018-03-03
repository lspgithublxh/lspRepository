#coding=utf-8
import pymysql
import os
import re
import time

conn = pymysql.connect(host='192.168.1.73', port=3306, user='root', password='Isas.1@3456', db='isas',
                           charset='utf8')
cur = conn.cursor()



def readFromFile():
    files = os.listdir('./')
    ip_region = {}
    region_spe = []
    mapping_id = getOne('select max(id) from isas_region_mapping ')
    mapping_id = mapping_id[0] + 1
    for file in files:
        if file.endswith('.txt'):
            fileName = file[0:file.rindex('.')].lstrip()
            region_id = getOne("select id from isas_region_mapping where region_name_cn='{1}'".format(1, fileName))
            if region_id == None:
                region_spe.append((mapping_id, fileName, fileName, 1, 0, None, '2017-06-23 15:11:53', '2017-06-23 15:11:53')) #'2017-06-23 15:11:53'
                region_id = mapping_id
                mapping_id = mapping_id + 1
            else:
                region_id = region_id[0]
            ips = []
            with open(file) as f:
                while True:
                    line = f.readline()
                    if line == None or line.strip() == '':break
                    line = line.strip()
                    if ips.__contains__(line):continue
                    print line
                    if not ipCheck(line):continue
                    ips.append(line)
            ip_insert = substrIps(ips)
            ip_region[region_id] = ip_insert
    if region_spe.__len__() > 0:
        print region_spe
        cur.executemany("insert into isas_region_mapping(id,region_name_cn,region_name_en, region_level, is_parent, parent_id, create_time, update_time) values(%s,%s,%s,%s,%s,%s,%s,%s)", region_spe) # str_to_date(%s,'%%Y-%%m-%%d %%H:%%M:%%S')
        conn.commit()
    return ip_region


def substrIps(ips):
    ips2 = []
    for ip in ips:
        ip = ip[0:ip.rindex('.')] + '.0/24'
        if ips2.__contains__(ip):continue
        ips2.append(ip)
    return ips2

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


def main__handle():
    id = getOne('select max(id) from isas_ip_warehouse ')
    id = id[0] + 1
    pageSize = 100
    skip = 0
    counter = {'id': id}
    schedual_id = getOne(
        'select if (max(id) is null, 20000, max(id)) from isas_task_scheduler where id >= 20000 and id < 40000')
    if schedual_id[0] >= 39999:
        print 'exception: isas_task_scheduler is full, no space to save. id :20000~40000'
        return
    c = {'id': schedual_id[0] + 1}
    ip_regions = readFromFile()
    for key in ip_regions:
        ips = ip_regions[key]
        while True:
            ipsPage = ips[skip:(skip + pageSize)]
            if ipsPage.__len__() == 0: break
            skip = skip + pageSize
            insertIPWare(ipsPage, key, counter, c)

def insertIPWare(ips, countryId, counter, c):
    # 数据库去重
    # ips = gerRestIps(ips, countryId)
    ipsArr = []
    idArr = []
    for ip in ips:
        ipsArr.append((counter['id'], ip, 255, countryId,0,0,'2017-06-22 11:44:36'))
        idArr.append(counter['id'])
        counter['id'] = counter['id'] + 1
    print 'ipsArr:' , ipsArr
    if ipsArr.__len__() > 0:
        try:
          cur.executemany('insert into isas_ip_warehouse(id, ip_segment, ip_num, location_country_id, location_province_id, location_city_id, update_time) values(%s,%s,%s,%s,%s,%s,%s)', ipsArr)
          conn.commit()

          insertIntoSchedual(idArr, c, countryId)
        except Exception as e:
            print e
        finally:
            pass


def insertIntoSchedual(ipsArr, counter, countryId):
    start = 0
    step = 20
    items = []
    print 'schedual:', ipsArr
    while (start + step) <= ipsArr.__len__():
        items.append((counter['id'], ipsArr[start], ipsArr[start + step - 1],0,countryId, 0, 0, '2017-06-22 11:44:36', None))
        start = start + step
        counter['id'] = counter['id'] + 1
    if start < ipsArr.__len__():
        items.append((counter['id'], ipsArr[start], ipsArr[ipsArr.__len__() - 1], 0, countryId, 0, 0, '2017-06-22 11:44:36', None))
    print items
    cur.executemany('insert into isas_task_scheduler(id, ips_start_id, ips_end_id, scheduler_ip, location_country_id, location_province_id, location_city_id, last_scan_time, cur_update_time) values(%s,%s,%s,%s,%s,%s,%s,%s,%s)', items)
    conn.commit()

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
    ips = list(set(ips).difference(set(c)))
    return ips

def getAll(sql):
    cur.execute(sql)
    resset = cur.fetchall()
    return resset

if __name__ == '__main__':
    # ips = ['3', '98.189.150.206/24','98.189.150.207/24']
    # common = getAll('select ip_segment from isas_ip_warehouse where id <1')
    # print common.__len__() == 0
    # c = []
    # for tu in common:
    #     c.append(tu[0])
    # print c
    # ips = list(set(ips).difference(set(c)))
    # print ips
    main__handle()
    pass
    # pass
    # ips = ['2','3','4']
    # common = ['3']
    # print list(set(ips).difference(set(common)))
    # print '3,3,4,4{name}, fdsfs{ip}d'.format(name=4,ip='3')
    # pass
    # print readFromFile()
    # print '343434'.split('.')
    # print ipCheck('1-0.23.23.23')
    # print [2,3,4,5][0:36]
    # file = 'abc.txt'
    # print  file[0:file.rindex('.')]
    # id = getOne("select id from isas_region_mapping where region_name_cn='{1}'".format(1, '阿富汗'))
    # print id[0]
