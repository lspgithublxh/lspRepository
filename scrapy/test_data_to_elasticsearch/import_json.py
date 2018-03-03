#!/usr/bin/env python
# encoding: utf-8

"""
@version:
@author: lcx
@contact: chenxing.li@winicssec.com
@site: http://www.winicssec.com/
@software: PyCharm
@file: import_es.py
@time: 2017/10/14 17:26
"""

import sys

import json
import time

from elasticsearch import Elasticsearch
from elasticsearch import helpers
reload(sys)
sys.setdefaultencoding('utf-8')  # @UndefinedVariable



    #
class importEsData():
    size = 1000


    def __init__(self, ip, port):
        self.es = Elasticsearch(ip)

    # def __init__(self, ip, port):
    #     self.conn = pyes.ES('%s:%d' % (ip, port))
    #     print self.conn
    def import_data(self,list):
        helpers.bulk(self.es, list, request_timeout=100)

    def readFile(self):
        try:
            f = open("f2.json", "r")
            list=[]
            flag = 0
            savelist = []
            while True:
                line = f.readline()
                flag += 1

                if line == "":
                    # break
                    pass
                else:

                    print line[0:line.index('\r')]
                    # savelist.append(json.loads(r'{1}'.format(1,line)))
                if flag % self.size == 0:
                    # self.import_data(savelist)
                    print flag
                    #flag=0
                    savelist=[]
            if len(savelist)>0:
                # self.import_data(savelist)
                pass
        finally:
            print "end"
if __name__ == "__main__":
    tool= importEsData("192.168.1.152",9200)
    begin = time.time()
    tool.readFile()
    print("import data end!!!\n\t total consuming time:" + str(time.time() - begin) + "s")
    # s = '{  "_score": 1.0,   "_type": "base",   "_id": "AV4ZC9FP4_BdWcfVkrlH",   "_index": "vulnerability",   "_source": {    "product": [      {        "pro": [          {            "version": "2.5.5",             "type": null,             "name": " Inc. Mango Automation ",             "classify": null          },           {            "version": "2.6.0",             "type": null,             "name": " Inc. Mango Automation  ",             "classify": null          }        ],         "vendor": "Infinite Automation Systems"      }    ],     "uuid": "7c6c9818-2351-11e6-abef-000c29c66e3d",     "vulnerability": {      "updateTime": "2015-08-17",       "vulType": "跨站脚本",       "vulName": "Infinite Automation Mango Automation跨站脚本漏洞",       "cnvd": "CNVD-2015-07168",       "vulDescription": "** RESERVED ** This candidate has been reserved by an organization or individual that will use it when announcing a new security problem.  When the candidate has been publicized, the details for this candidate will be provided.",       "cpe": "Infinite_automation_systems Mango_automation:2.6.0  \nInfinite_automation_systems Mango_automation:2.5.5  \nInfinite_automation_systems Mango_automation:2.5.0\n",       "threadType": "远程",       "cnnvd": "CNNVD-201510-678",       "cve": "CVE-2015-6494",       "refWebsite": [        "https://ics-cert.us-cert.gov/advisories/ICSA-15-300-02"      ],       "releaseTime": "2015-08-17"    },     "is_ics": true,     "CVSS": {      "riskLevel": "低危",       "integImpact": 0.275,       "accessComplexity": 0.61,       "cvssBaseScore": 3.5,       "authentication": 0.704,       "availImpact": 0,       "confImpact": 0,       "accessVector": 1    },     "wve": null  }}'
    # s = r'{  "_score": 1.0,   "_type": "base",   "_id": "AV4ZC9FP4_BdWcfVkrlH", "product": [      {        "pro": [          {            "version": "2.5.5",             "type": null,             "name": " Inc. Mango Automation ",             "classify": null          },           {            "version": "2.6.0",             "type": null,             "name": " Inc. Mango Automation  ",             "classify": null          }        ],         "vendor": "Infinite Automation Systems"      }    ], "vulAdvisory": [         "\r\n\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t\t\t目前没有详细的解决方案，请到厂商的主页下载：\r",         "\r\n\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t\t\thttp://infiniteautomation.com",         "\r\n\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t"      ]}'

    # print json.loads(s)