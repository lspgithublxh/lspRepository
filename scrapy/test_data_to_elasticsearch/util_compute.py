#coding=utf-8
import sys
import requests
from  bs4 import BeautifulSoup
from phantom_download import getContent2

scoremap = {'Au:M': ('0.45', 'authentication'), 'AC:H': ('0.35', 'accessComplexity'), 'I:C': ('0.66', 'integImpact'), 'AV:N': ('1', 'accessVector'),
            'A:N': ('0', 'availImpact'), 'AV:L': ('0.395', 'accessVector'), 'C:P': ('0.275', 'confImpact'), 'A:C': ('0.66', 'availImpact'), 'Au:N': ('0.704', 'authentication'), 'I:N': ('0', 'integImpact'),
            'I:P': ('0.275', 'integImpact'), 'C:N': ('0', 'confImpact'), 'AC:M': ('0.61', 'accessComplexity'),
            'A:P': ('0.275', 'availImpact'), 'C:C': ('0.66', 'confImpact'), 'Au:S': ('0.56', 'authentication'), 'AV:A': ('0.646', 'accessVector'), 'AC:L': ('0.71', 'accessComplexity')}


def justTest():
    map = {}
    with open('score.txt', 'r') as f:
        while True:
            line = f.readline().replace('\n','')
            if line is None or line == '': break
            strArr = line.split('\t')
            map[strArr[0]] = (strArr[1], strArr[2])
    # print map
    return map
    pass


def computeScore(str):
    arr = str.split('/')
    map = {}
    for i in arr:
        tu = scoremap.get(i)
        map[tu[1]] = tu[0]
    score = None
    try:
        impact = 10.41 * (1 - (1 - float(map['confImpact'])) * (1 - float(map['integImpact'])) * (1 - float(map['availImpact'])))
        exploitability = 20 * float(map['accessVector']) * float(map['accessComplexity']) * float(map['authentication'])
        f = 1.176
        if impact <= 0:
            f = 0
        score = (0.6 * impact + 0.4 * exploitability - 1.5) * f
    except Exception as e:
        print e
    return score, map


def download2(url,data, header):
    try:
        print url
        status = 0
        # res = requests.get(url, headers=header, timeout=30) # ,verify=False 不加验证可能会报警告
        res = requests.post(url, data=data, headers = header)
        status = 1
        print res.encoding
    except (requests.exceptions.ConnectionError, requests.exceptions.ReadTimeout,requests.exceptions.ConnectTimeout,requests.exceptions.ChunkedEncodingError, Exception) ,ex:
        # 特殊错误可以考虑等待几秒
        print 'exception occured', ex
        return '<html></html>'; # <body></body>
    # res.encoding = 'utf-8'
    return res.text

def getCNNVDByCVE(cveid):
    headers = {
        'Host': 'www.cnnvd.org.cn',
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Language': 'zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2',
        'Accept-Encoding': 'gzip, deflate',
        'Referer': 'http://www.cnnvd.org.cn/web/xxk/ldxqById.tag?CNNVD=CNNVD-201202-036',
        'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Length': '77',
        'Cookie': 'SESSION=ad07c46a-73e0-4df7-bb42-4b07a2d95f06; topcookie=a1',
        'Connection': 'keep-alive',
        'Upgrade-Insecure-Requests': '1',
        'Cache-Control': 'max-age=0'
    }
    data = {
        'CSRFToken':'',
        'cvHazardRating':'',
        'cvVultype':'',
        'cpvendor':'',
        'cvCnnvdUpdatedateXq':'',
        'isArea':'',
        'qcvCname':'',
        'qcvCnnvdid':cveid,
        'qstartdate':'',
        'qstartdateXq':'',
        'cvUsedStyle':'',
        'qenddate':'',
        'hotLd':'',
        'relLdKey':''
    }
    text = download2('http://www.cnnvd.org.cn/web/vulnerability/queryLds.tag',data, headers)
    cnnvd_id = parseCNNVDFromText(text)
    print cnnvd_id

def getCNNVDByCVE2(cveid):
    pass

def parseCNNVDFromText(text):
    bs = BeautifulSoup(text, 'html.parser')
    # 可以过滤掉脚本
    body = bs.body
    if body is None: return -1
    print body
    obj = body.find('div', {'class': 'list_list'})
    if obj is None:return -1
    cnnvd = obj.find('li').find('p').find('a')
    cnnvd_id = cnnvd.text.strip()
    return cnnvd_id


if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    justTest()
    str = 'AV:N/AC:L/Au:N/C:C/I:C/A:C'
    print computeScore(str)
    # print float('23.23')
    getCNNVDByCVE('CVE-2011-3980')