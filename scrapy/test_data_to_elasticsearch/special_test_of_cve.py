#coding=utf-8
import sys
import cve_search
import re

list = ['centos 6.5'
'AIX 6.1'
'Windows SERVER 2012'
'REDHAD 7.2 裁剪版'
'Windows SERVER 2008 R2']

def justStrip(s):
    if s is not None:
        s = re.sub('^\n*\t*', '', s)
        s = re.sub('\t*\n*$', '', s)
        s = s.strip()
    return s

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    with open('cvedetail.txt', 'a') as f1:
        with open('cveid.txt','r') as f2:
           while True:
               cve = f2.readline()
               if cve is None or cve == '':break
               print cve
               cve = cve.replace('\r\n', '').replace('\r', '').replace('\n', '')
               zhong, level = cve_search.justGetContent(cve)
               if zhong is not None:
                   zhong = justStrip(zhong)
                   zhong = zhong.replace('\r\n', ';;;').replace('\r', ';;;').replace('\n', ';;;')
                   zhong = re.sub('(\r\n)+', ';;;', zhong)
                   zhong = re.sub('(\n)+', ';;;', zhong)
                   zhong = re.sub('(\r)+', ';;;', zhong)
               print zhong, level
               f1.write(cve + '@_-_@' + level + '@_-_@' + zhong + '@_-_@' + 'http://cve.scap.org.cn/{1}.html'.format(1,cve) + '\r\n')
               f1.flush()