import sys

def get_download(url):
    try:
        print url
        status = 0
        res = requests.get(url, timeout=30) # ,verify=False 不加验证可能会报警告
        # res = requests.post(url, data=data, headers = header)
        status = 1
        print res.headers
        print res.encoding
        res.encoding = 'UTF-8'
    except (requests.exceptions.ConnectionError, requests.exceptions.ReadTimeout,requests.exceptions.ConnectTimeout,requests.exceptions.ChunkedEncodingError, Exception) ,ex:
        # 特殊错误可以考虑等待几秒
        print 'exception occured', ex
        return '<html></html>'; # <body></body>
    # res.encoding = 'utf-8'
    return res.text


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


if __name__ == '__main__':
    import re
    #item_id=PC_city_click&click_name=国内城市&click_url=//xz.ke.com&click_location=2
    p = re.compile('.*?//(\S+)?\..+')
    m = p.match('item_id=PC_city_click&click_name=国内城市&click_url=//xz.ke.com&click_location=2')
    print m.group(1)
    #//ul[@class='city_list_ul']/li/div[@class='city_list']//ul/li/a//@href   提取数据的另一种方式xpath