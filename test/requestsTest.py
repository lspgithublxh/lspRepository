#coding=utf-8
import  requests
import sys
import beautifulsoup4Test

def test():
    # res = requests.get('''https://mbd.baidu.com/newspage/data/landingsuper?context=%7B"nid"%3A"news_2762606785137875577"%7D&n_type=0&p_from=1''')
    res = requests.get('''https://www.shodan.io/search?query=port%3A102''')
    res.encoding = 'utf-8'
    print  res.text
    print res.content
    # print (res.text.decode('ISO-8859-1').encode('utf-8'))
    # print res.text.decode('ISO-8859-1')
    # print res.text.decode('ISO-8859-1').encode('utf-8')
    # print res.text.decode('gbk').encode('utf-8')
    # print res.text.decode('gbk')
    # print res.text.decode('gbk').encode('utf-8').decode('utf-8')
    print (res.headers)
    print(res.encoding)
    print res.cookies
    beautifulsoup4Test.test(res.text)

def download(url, header):
    res = requests.get(url, headers=header)
    print res.encoding
    # res.encoding = 'utf-8'
    return res.text

def download2(url, data, header):
    res = requests.get(url,data, headers=header)
    # res.encoding = 'utf-8'
    return res.text


def headers():
    head = {
        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
        'Accept-Encoding':'gzip, deflate, sdch, br',
        'Accept-Language':'zh-CN,zh;q=0.8',
        'Connection':'keep-alive',
        'Host': 'www.baidu.com',
        'Upgrade-Insecure-Requests': str(1),
        'User-Agent': 'Mozilla/5.0(Windows NT 10.0;WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
    }
    res = requests.get('http://www.baidu.com',headers=head)
    # res = requests.get('https://mbd.baidu.com/newspage/data/landingsuper?context=%7B"nid"%3A"news_2762606785137875577"%7D&n_type=0&p_from=1', headers=head)
    res.encoding = 'utf-8'
    print res.text
    beautifulsoup4Test.test(res.text)



def test2():
    print sys.getdefaultencoding()
    # reload(sys)
    # sys.setdefaultencoding('UTF-8') # 解决报错 asclli
    print isinstance(u"李少平", unicode)
    print "李少平".__class__
    print (u"李少平".encode('utf-8'))
    print "李少平".decode('utf-8').encode('utf-8')
    print "李少平".decode('utf-8').encode('gb2312')
    print "李少平".decode('utf-8').encode('gbk')
    # print "李少平".decode('utf-8').encode('ISO-8859-1')  报错
    print '李少平'.decode('utf-8').encode('gbk').decode('ISO-8859-1')
    # print u'李少平'.encode('ISO-8859-1')报错latin
    print "李少平".decode('utf-8')
    print isinstance("李少平".decode('utf-8'), unicode)
    print "李少".decode('ISO-8859-1')
    print '\x41'
    print '\x41'.decode('ISO-8859-1')
    print '\xc4' #asclli码下没有这个编码,超出了128的范围
    print '\xc4'.decode('ISO-8859-1')
    print '\xc4'.decode('ISO-8859-1').encode('utf-8')
    # print '\xc4'.decode('ISO-8859-1').encode('gbk')
    # print '\xc4'.decode('ISO-8859-1').encode('gb2312')
    # print '\xc4'.decode('ISO-8859-1').encode('')
    print 'hello world'
    print '\xe4\xb8\xad\xe6\x96\x87'.decode('ISO-8859-1').encode('utf-8')
    print '\xe4\xb8\xad\xe6\x96\x87'
    print '\xe4\xb8\xad\xe6\x96\x87\x41dfdf' #默认当做了utf-8的编码
    print '\xd6\xd0\xce\xc4\x41dfcd'.decode('GBK')

if __name__ == '__main__':
     # test2()
     test()
     # headers()