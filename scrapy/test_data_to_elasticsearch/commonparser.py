#coding=utf-8
from bs4 import  BeautifulSoup
from lxml import etree
import re
import sys
from phantom_download import getContent2

def parseCNNvd(content, cnnvd):
    bs = BeautifulSoup(content, 'html.parser')
    body = bs.body
    if body is None: return -1
    divs = body.find('div', {'class': 'fl w770'})
    secend = divs.find('div', {'class': 'detail_xq'})
    map = {}
    resmap = get_cnnvd_map()
    if secend is not None:
        map['bug_name'] = justStrip(secend.find('h2').text)
        lis = secend.find_all('li')
        for li in lis:
            if li is not None:
                val = li.text.strip()
                key = val[0:val.index('：')]
                key = justStrip(key)
                col = resmap.get(key.encode('UTF-8'))
                if col is not None:
                    val = val[val.index('：') + 1:]
                    map[col] = justStrip(val)
    divs = divs.find_all('div',{'class':'d_ldjj'})
    for div in divs:
        title = div.find('h2').text
        title = justStrip(title)
        key = resmap.get(title.encode('UTF-8'))
        if key is not  None:
            if key.__contains__('influnce_obj') or key.__contains__('patch') :
               map = getValFromLis(div.find_all('li'), key, map)
            else:
               map = getValFromLis(div.find_all('p'), key, map)
    if len(map) == 0:return -1
    return  map


def getValFromLis(lis, key, map):
    str = ''
    for p in lis:
        str += justStrip(p.text) + '\r\n'
    map[key] = str
    return map

def get_cnnvd_map():
    remap = {'CNNVD编号':'cnnvd','危害等级':'danger_level','CVE编号':'cve','漏洞类型':'bug_type',
            '发布时间':'publish_time','威胁类型':'threaten_type','更新时间':'update_time','厂':'firm','漏洞来源':'bug_source'
           ,'漏洞公告':'notice', '参考地址':'refer', '补丁':'patch', '受影响实体':'influnce_obj', '漏洞简介':'introduce'}
    return remap

def parseCNNvd_old(content, cnnvd):
    bs = BeautifulSoup(content, 'html.parser')
    body = bs.body
    # print body
    if body is None: return -1
    divs = body.find('div', {'class': 'fl w770'})
    infos = divs.find_all('div')
    bug_name = infos[1].find('h2').text
    infolis = infos[1].find_all('li')
    valueslist = []
    for item in infolis:
        val = item.text
        val = val[val.index('：') + 1:]
        if val is not None:
            val = val.replace('\n','').strip()
            val = re.sub('\s+','',val)
        valueslist.append(val)
    map = {'cnnvd':cnnvd, 'bug_name':bug_name, 'danger_level':valueslist[1], 'cve':valueslist[2],
           'bug_type':valueslist[3],'publish_time':valueslist[4],'threaten_type':valueslist[5],
           'update_time':valueslist[6],'firm':valueslist[7]}
    introduce = infos[3].find_all('p')
    intro_str = getItemAndStrip(introduce)
    # print 'res:', intro_str
    notice = infos[6].find_all('p')
    notice_str = getItemAndStrip(notice)
    # print 'res2:', notice_str
    refer = infos[9].find_all('p')
    refer_str = getItemAndStrip(refer)
    # print 'refer: ', refer_str
    influnce_obj = infos[12].find_all('ul')
    inf_str = getItemAndStrip(influnce_obj)
    # print 'inf_str: ', inf_str
    patch = infos[-1].text
    # print 'patch', justStrip(patch)
    map['introduce'] = intro_str
    map['notice'] = notice_str
    map['influnce_obj'] = inf_str
    map['refer'] = refer_str
    map['patch'] = justStrip(patch)
    return map


def getItemAndStrip(notice):
    notice_str = ''
    for item in notice:
        s = item.text
        if s is not None:
            s = re.sub('^\n*\t*', '', s)
            s = re.sub('\t*\n*$', '', s)
            s = s.strip()
            notice_str += s + '\n'
    return notice_str


def justStrip(s):
    if s is not None:
        s = re.sub('^\n*\t*', '', s)
        s = re.sub('\t*\n*$', '', s)
        s = s.strip()
    return s


def getValu(cvtable):
    return None if cvtable is None else justStrip(cvtable.text)


#先cve,cnnvd,cnvd的解析
def parseCve(content, cveid):
    bs = BeautifulSoup(content, 'html.parser')
    #可以过滤掉脚本
    body = bs.body
    if body is None: return -1
    tabs = body.find('ul', {'class': 'ui-tabs-nav'})
    if tabs is None:return -1
    lis = tabs.find_all('li')
    tabname = [None] * lis.__len__()
    i = 0
    for tab in lis:
        print tab.find('a').string
        tabname[i] = tab.find('a').string
        i = i + 1
    nvds = body.find_all('div',{'class':'ui-tabs-panel'})
    # print body
    nvd = A()
    table = nvds[0].find('table',{'id':'cvss'})
    nvd.cvss = getValu(table)
    table = nvds[0].find('table', {'id': 'cwe'})
    nvd.cwe = getValu(table)
    table = nvds[0].find('table', {'id': 'cpe'})
    nvd.cpe = None
    if table is not None:
        trs = table.find_all('tr')
        s = ''
        for tr in trs:
            s += justStrip(tr.text) + '\r\n'
        nvd.cpe = justRemovett(s)
    table = nvds[0].find('table', {'id': 'oval'})
    nvd.oval = getValu(table)
    table = nvds[0].find('table', {'id': 'officialref'})
    nvd.referer = getValu(table)
    table = nvds[0].find('table', {'id': 'reference'})
    nvd.otherRefer = getValu(table)
    # tables = nvds[0].find_all('table')
    # h2s = nvds[0].find_all('h2')
    # nvd = A()
    # indexh = 0
    # for h2 in h2s:
    #     cont = h2.text
    #     if cont.__contains__('CVSS'):
    #         nvd.cvss = tables[indexh].text
    #     elif cont.__contains__('CWE'):
    #         nvd.cwe = tables[indexh].text
    #     indexh += 1


    # nvd.cpe = tables[1].text
    # nvd.oval = tables[2].text
    # nvd.referer = tables[3].text
    # nvd.otherRefer = tables[4].text
    packetStorm = None
    print nvd.__dict__
    # trs = nvds[1].find('div',{'id':'info_psF132479'}).find('table').find_all('tr')
    # if nvds.__len__() > 1:
    #     packet = []
    #     trs = nvds[1].find('div').find('table').find_all('tr')
    #     for tr in trs:
    #         tdtext = tr.find('td').text
    #         tdtext = tdtext[tdtext.index(':') + 1:]
    #         packet.append(tdtext)
    #     codes = nvds[1].find('div').find('div', {'id': 'code_ps'}).text
    #     packetStorm = {'bugName': packet[0], 'bugTime': packet[1], 'fileAuthor': packet[2], 'fileTag': packet[3],
    #                    'sysTag': packet[4], 'cveid': re.sub('\s+', '', packet[5].replace('\n', '')), 'bugDetail': packet[7],
    #                    'codes': codes}
    #     print packetStorm
    securityFocus = None
    # if nvds.__len__() > 2:
    #     tablesObj = nvds[2].find_all('table')
    #     trs = tablesObj[0].find_all('tr')
    #     bugName = trs[0].text
    #     bugAuthor = trs[4].text
    #     info3 = []
    #     for i in range(1,4):
    #         tds = trs[i].find_all('td')
    #         t0 = tds[0].text
    #         t1 = tds[1].text
    #         info3.append(t0[t0.index(':') + 1:])
    #         info3.append(t1[t1.index(':') + 1:])
    #     securityFocus = {'bugName':bugName, 'bugType':info3[0], 'bugtraqID':info3[1], 'faroverflow':info3[2],
    #                      'localoverflow':info3[3], 'publishTime':info3[4], 'updateTime':info3[5], 'bugAuthoer':bugAuthor}
    #     print securityFocus
    all = {'cveid': cveid, tabname[0]: nvd.__dict__}
    for c in range(1,i):
        tn = tabname[c]
        if tn == 'CNNVD':
            cnnvd = parseCNNVD(nvds[c])
            all[tn] = cnnvd
        else:
            if c < nvds.__len__():#SecurityFocus是特殊的
                all[tn] = nvds[c].text

    return all
    # ehtml = etree.HTML(nvds[1].__str__)
    # result = ehtml.xpath('//div[@id="info_psF132479"]')
    # print result


def parseDescFromEnglishPage(cveid):
    zhong = ''
    text = getContent2('http://cve.mitre.org/cgi-bin/cvename.cgi?name={1}'.format(1, cveid))
    bs = BeautifulSoup(text, 'html.parser')
    body = bs.body
    if body is None: return zhong
    resarr = body.find('div', {'id': 'GeneratedTable'})
    if resarr is not None:
        trs = resarr.find_all('tr')
        if trs.__len__() > 3:
            return justStrip(trs[3].text)
    return zhong
    pass


def parseDescAndImportant(text, cveid):
    level = ''
    zhong = ''
    bs = BeautifulSoup(text, 'html.parser')
    body = bs.body
    if body is None: return zhong,level
    resarr = body.find('div', {'class': 'summary'})
    if resarr is None:
        entry = body.find('div', {'class':'entry'})
        if entry is not None:
            obj = entry.find('h1')
            if obj is not None:
                zhong = parseDescFromEnglishPage(cveid)
        return zhong,level
    p = resarr.find_all('p')
    # print p[2].text
    zhong = p[2].text
    if zhong.__contains__('译文') or zhong.__contains__('Google 翻译'):
        yuan = p[0].text
        zhong = yuan[yuan.index(']') + 1:]
    else:
        for i in range(2,p.__len__()):
            zhong += p[i].text
    # print zhong.encode('UTF-8').__contains__('译文')
    ccc = body.find('div', {'id': 'nvd'})
    if ccc is None:return zhong,level
    tr = ccc.find('table').find('tr')
    if tr is None:return zhong,level
    tds = tr.find_all('td')
    if tds.__len__() < 3:return zhong,level
    level = tds[2]
    level = level.text
    if level is None: level = ''
    return zhong,level

    pass

def parseCNNVD(pannel):
    tables = pannel.find_all('table')
    # print 'tables: ', tables
    trs = tables[0].find_all('tr')
    bugName = trs[0].text
    bugName = bugName[bugName.index(':') + 1:]
    attackPath = trs[3].text
    attackPath = attackPath[attackPath.index(':') + 1:]
    info3 = []
    for i in range(1,3):
        tds = trs[i].find_all('td')
        t0 = tds[0].text
        t1 = tds[1].text
        info3.append(t0[t0.index(':') + 1:])
        info3.append(t1[t1.index(':') + 1:])
    detail = trs[5].text
    cnnvdMap = {'bug_name':bugName, 'import_level':info3[0], 'bug_type':info3[1], 'publish_time':info3[2],
                'update_time':info3[3], 'attack_path':attackPath,'detail':detail, 'patch':tables[1].text}
    return cnnvdMap


def parseCnvd(content, cnvd):
    bs = BeautifulSoup(content, 'html.parser')
    body = bs.body
    if body is None: return -1
    resarr = body.find('table', {'class': 'gg_detail'})
    if resarr is None:return -1
    trs = resarr.find_all('tr')
    colmap = get_cnvd_map()
    map = {}
    map['cnvd_id'] = cnvd
    for tr in trs:
        tds = tr.find_all('td')
        key = tds[0].text.strip()
        key = colmap.get(key.encode('UTF-8'))
        if key is not None:
            map[key] = tds[1].text.strip()

    # map = A()
    # map.cnvd_id = trs[0].find_all('td')[1].text.strip()
    # map.cnvd_id = cnvd
    # map.publish_time = trs[1].find_all('td')[1].text.strip()
    # map.bad_level = trs[2].find_all('td')[1].text.strip()
    # map.influnce_pro = trs[3].find_all('td')[1].text.strip()
    # map.cve_id = trs[4].find_all('td')[1].text.strip()
    # map.bug_desc = trs[5].find_all('td')[1].text.strip()
    # map.refer = trs[6].find_all('td')[1].text.strip()
    # map.solution = trs[7].find_all('td')[1].text.strip()
    # map.finder = trs[8].find_all('td')[1].text.strip()
    # map.patch = trs[9].find_all('td')[1].text.strip()
    # map.validate = trs[10].find_all('td')[1].text.strip()
    # map.report_time = trs[11].find_all('td')[1].text.strip()
    # map.receive_time = trs[12].find_all('td')[1].text.strip()
    # map.update_time = trs[13].find_all('td')[1].text.strip()
    # for i in map.__dict__:
    #     print i, map.__dict__[i]
    return map
    # list = []
    # for tr in trs:
    #     tds = tr.find_all('td')
    #     # if tds.__len__() > 1:
    #         # list.append(tds[1].text.strip())
    #     print tds[0].text.strip(), tds[1].text.strip()
    # print list
    # ''.
    pass


def get_cnvd_of_page(text):
    bs = BeautifulSoup(text, 'html.parser')
    body = bs.body
    if body is None: return -1
    resarr = body.find('div', {'class': 'list'})
    if resarr is None: return -1
    tbody = resarr.find('tbody')
    trs = tbody.find_all('tr')
    list = []
    for tr in trs:
        tds = tr.find_all('td')
        a = tds[0].find('a')
        href = a['href']
        cnvd = href[href.rindex('/') + 1:]
        name = justStrip(a['title'])
        print cnvd, '----', name
        threaten_level = tds[1].text
        publish_time = tds[-1].text
        click_times = tds[2].text
        list.append((cnvd, name, justStrip(threaten_level), justStrip(publish_time), justStrip(click_times)))
    return list


def get_cnvd_map():
    map = {'发布时间':'publish_time','危害级别':'bad_level', '影响产品':'influnce_pro', ' BUGTRAQ ID':' bugtraq_id','CVE ID':'cve_id',
           '漏洞描述':'bug_desc', '参考链接':'refer', '漏洞解决方案':'solution', '厂商补丁':'patch', '验证信息':'validate',
           '报送时间':'report_time', '收录时间':'receive_time', '更新时间':'update_time', '漏洞附件':'attach'}
    return map


def extend(map1, map2):
    for key in map2.iterkeys():
        if key != None:
            map1[key] = map2[key]

def getCPEFromCVE(text):
    bs = BeautifulSoup(text, 'html.parser')
    body = bs.body
    if body is None: return None
    res = body.find('table', {'id': 'cve'})
    if res is None: return None
    return justRemovett(justStrip(res.text))


def getCPEFromCNNVD(text):
    bs = BeautifulSoup(text, 'html.parser')
    body = bs.body
    if body is None:return None
    res = body.find('div',{'class':'vulnerability_list'})
    if res is None:return None
    return justRemovett(justStrip(res.text))


def justRemovett(str):
    if str is not None and str.__contains__('\t'):
        cpe = re.sub('\t+', '', str)
        cpe = re.sub('(\r\n)+', '\r\n', cpe)
        return cpe
    return str

def getCNNVDFromText(text):
    bs = BeautifulSoup(text, 'html.parser')
    body = bs.body
    if body is None: return None
    res = body.find('div', {'class': 'list_list'})
    if res is None:return None
    div = res.find('div',{'class':'fl'})
    if div is None:return None
    s = div.find('p').find('a').text
    return justStrip(s)

def getCPEFromCNVD(text):
    bs = BeautifulSoup(text, 'html.parser')
    body = bs.body
    if body is None: return None
    tables = body.find('table', {'class': 'gg_detail'})
    if tables is None: return None
    trs = tables.find_all('tr')
    if trs is not None:
        for tr in trs:
            tds = trs.find_all('td')
            if tds is not None and tds[0].strip().encode('UTF-8').__contains__('影响产品'):
                return justRemovett(justStrip(tds[1]))
    return None

def getBadLevelFromCNVD(text):
    bs = BeautifulSoup(text, 'html.parser')
    body = bs.body
    if body is None: return None
    tables = body.find('table', {'class': 'gg_detail'})
    if tables is None: return None
    tds = tables.find_all('td', {'class':'denle'})
    if tds is not None:
        return justStrip(tds[0].text)
    return None


def getProArrFromText(text):
    groups = re.split('(\r\n\t*)*\t*', text)
    p = re.compile('(.*?)(?:\:)?((?:v)?\d+.*)')
    pre2 = re.compile('(.+?)\s+(.+?)(?:\:)?((?:v)?\d+.*)')
    # prolist = []
    product = []
    map = {}
    # for ind in range(0, groups.__len__(),2):
    for group in groups:
        s = group
        if s.__contains__(','):
            arr = re.split(',', s)
            om = p.match(arr[1])
            vendor = arr[0]
            if om is None:
                p2 = re.compile('\w+')
                ma = p2.match(s)
                if ma is not None:
                    if map.has_key(vendor):
                        map[vendor].append({'name': arr[1], 'version': None, 'type': None, 'classify': None})
                    else:
                        pr2list = [{'name': arr[1], 'version': None, 'type': None, 'classify': None}]
                        map[vendor] = pr2list
                continue
            prod = om.group(1)
            version = om.group(2)
            if map.has_key(vendor):
                map[vendor].append({'name': prod, 'version': version, 'type': None, 'classify': None})
            else:
                prlist = [{'name': prod, 'version': version, 'type': None, 'classify': None}]
                map[vendor] = prlist
        else:
            om = pre2.match(s)
            if om is None:
                p2 = re.compile('\w+')
                ma = p2.match(group)
                if ma is not None:
                    ma4 = p.match(group)
                    if ma4 is not None:
                        name = ma4.group(1).strip()
                        version = ma4.group(2).strip()
                        # vendor = name if not name.__contains__(' ') else
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
            vendor = om.group(1).strip()
            name = om.group(2).strip()
            version = om.group(3).strip()
            if map.has_key(vendor):
                map[vendor].append({'name': name, 'version': version, 'type': None, 'classify': None})
            else:
                prlist = [{'name': name, 'version': version, 'type': None, 'classify': None}]
                map[vendor] = prlist
    for key in map.keys():
        product.append({'pro':map.get(key), 'vendor':key})
    return product

class A:
    pass

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    a = A()
    a.name = 'haha'
    a.age = 42
    for i in a.__dict__:
        print i, a.__dict__[i]
    print 'sdfs:sdfsd'['sdfs:sdfsd'.index(':') + 1:]
    print re.sub('\s+','','     yyyy')
    print re.sub('\n+', '', '     \r\n\n\nhahahaha')
    cc = [None] * 10
    print cc[0]
    xx = 0
    for i in range(1, 3):
        print i
        cc[xx] = i
        xx = xx + 1
    print cc
    for i in range(1,1):
        print 'xxx', i
    print 'end'
    print {'kae':''}.get('sx')
    map = {u'中文':'中文'}
    print map
    a = '中文'
    c = a.decode('UTF-8')
    print c
    d = u'中文'
    e = d.encode('UTF-8')
    print e
    print 'dfs中文dfs'.__contains__('中文')
    getProArrFromText('Infinite Automation Systems, Inc. Mango Automation 2.5.5\r\n\t\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t\t\t\tInfinite Automation Systems, Inc. Mango Automation  2.6.0')
    p = re.compile('(.*?)(?:\:)?((?:v)?\d+\..*)')
    om = p.match('Infinite Automation Systems, Inc. Mango Automation 2.5.5')
    print om.group(1),'xxxx', om.group(2)
    # print 'size1',{}.keys().count()
    print 'size2', len({})