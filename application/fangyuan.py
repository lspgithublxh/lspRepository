import sys
import requests
from lxml import etree

html = etree.HTML('')
html.xpath('')
def getContent(url):
    try:
        content = requests.get(url, timeout=30)
        print content.content
        print '--------------'
        # print content.text
    except Exception as e:
        print e

def parse(content):
    pass

if __name__ == '__main__':

    reload(sys)
    sys.setdefaultencoding('UTF-8')
    print 'this word'
    print sys.argv
    print getContent('https://bj.ke.com/ershoufang/')
