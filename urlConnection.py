from urllib import request
def getPage(url):
    respon = request.urlopen(url)
    page = respon.read()
    content = page.decode("utf-8")
    return content
# print(getPage(r'http://python.org/'))
print(getPage(r'http://www.cnblogs.com/fnng/p/3576154.html'))
# 用报头的方式访问,构造request，再访问，可以增加data参数，成为post方式
def getPage2(url,headers):
    reques = request.Request(url,headers=headers)
    page = request.urlopen(reques).read()
    contetn = page.decode("utf-8")
headers = {
     'User-Agent': r'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
                     r'Chrome/45.0.2454.85 Safari/537.36 115Browser/6.0.3',
     'Referer': r'http://www.lagou.com/zhaopin/Python/?labelWords=label',
     'Connection': 'keep-alive'
}
print(getPage2(r'http://www.cnblogs.com/fnng/p/3576154.html',headers))
