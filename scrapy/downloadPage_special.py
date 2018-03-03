import downloadPage
import re
from pymongo import  MongoClient
import requests

def getCondition(text):
    str = ''
    for i in text:
        if i == ' ':
            str = str + '+'
        elif i == ':':
            str = str + '%3A'
        elif i == '"':
            str = str + '%22'
        elif i == ',':
            str = str + '%2C'
        else:
            str = str + i
    return str


if __name__ == '__main__':
    condition = ['port:1911,4911 product:Niagara','port:18245,18246 product:"general electric"','port:5094 hart-ip','port:1962 PLC','port:5006,5007 product:mitsubishi','port:9600 response code','port:789 product:"Red Lion Controls"','port:2455 operating system','port:2404 asdu address','port:20547 PLC','port:20000 source address'];
    pRe = re.compile('port\:([\d,]*)?(\s|$)')
    client = MongoClient('192.168.1.152', 27017)
    while True:
        try:
            for con in condition:
                port = pRe.match(con).group(1)
                cond = getCondition(con)
                page = 1
                while True:
                    status = downloadPage.shordan_query(port, cond, client)
                    if status == -1:
                        break
                    page = page + 1
        except requests.exceptions.SSLError:
            pass


