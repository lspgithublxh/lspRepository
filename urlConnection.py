from urllib import request
def getPage(url):
    respon = request.urlopen(url)
    page = respon.read()
    content = page.decode("utf-8")
    return content
print(getPage(r'http://python.org/'))