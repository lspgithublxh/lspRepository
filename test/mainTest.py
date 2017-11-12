# coding=UTF-8
import beautifulsoup4Test
import requestsTest
import saveToDBTest
import sys

if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8') # 解决报错 asclli
    print 'hello boy'
    headers = {
        "authority": "www.shodan.io",
        "method": "GET",
        "path": "/search?query=port%3A102",
        "scheme": "https",
        "Accept": 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
      #  'Accept-Encoding':'gzip, deflate, sdch, br',
        'Accept-Language':'zh-CN,zh;q=0.8',
        'cache-control':'max-age=0',
        'cookie':'__cfduid=dd7096f06fdc5ad7b5f81ef9706c673351510477684; __guid=257598605.3644327904132300300.1510477677988.1885; polito="b91d5dce8ae4ca991d6009cc56a1d4f75a080dc45a080d79e449853f25e6b2a1!"; session=4af887580ef211b0719c4b44c9fb112d2c2cf19bgAJVQDQ1OWU1MTNhYjFiM2JlZmQ1ZDhjNDM4YWFhMTFmYWE2Y2JlYWU3ZmRjZjM2MWIwNWQ0ZTY2NDA2NmYzOTMwODZxAS4=; monitor_count=9; _ga=GA1.2.1287974922.1510477679; _gid=GA1.2.1788027332.1510477679',
        'referer':'https://www.shodan.io/explore/category/industrial-control-systems',
        'Upgrade-Insecure-Requests': str(1),
        'User-Agent': 'Mozilla/5.0(Windows NT 10.0;WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'
    };
    text = requestsTest.download('https://www.shodan.io/search?query=port%3A102',headers)
    print text