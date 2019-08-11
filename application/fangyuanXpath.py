#coding=utf-8
import sys
from bs4 import BeautifulSoup
from fangyuanSel import getContent


def parseGetCity():
    page = getContent('https://www.ke.com/city/')
    bs = BeautifulSoup(page, 'html.parser')
    body = bs.body
    if body is None: return -1
    ul = body.find('ul', {'class': 'city_list_ul'})
    lis = ul.find_all('li')
    rs = []
    for li in lis:
        cilist = li.find('div',{'class':'city_list'})
        if cilist is None:continue
        ulcity = cilist.find('ul')
        if ulcity is not None:
            cityli = ulcity.find_all('li')
            for city in cityli:
                a = city.find('a')
                src = a.attrs['href']
                href = src[2:src.index('.')]
                rs.append(href)
    return rs

def parseGetSubCity(url):
    page = getContent(url)#'https://bj.ke.com/'
    bs = BeautifulSoup(page, 'html.parser')
    body = bs.body
    if body is None: return -1
    top = body.find('div',{'class':'position'})
    divs = top.find('div', {'data-role': 'ershoufang'})
    aa = divs.find_all('a')
    rs = []
    for a in aa:
        src = a.attrs['href']
        rs.append(src)


    return rs

def getPageBox(url):
    page = getContent(url)  # 'https://bj.ke.com/'
    bs = BeautifulSoup(page, 'html.parser')
    body = bs.body
    if body is None: return -1
    top = body.find('div', {'class': 'page-box fr'})
    aarr = top.find_all('a')
    a = []
    for x in aarr:
        a.append(x)
    b = a.pop(a.__len__() - 2)
    return b.text

if __name__ == '__main__':
    print parseGetCity()
    # print parseGetSubCity('https://bj.ke.com/ershoufang')
    # getPageBox('https://bj.ke.com/ershoufang/pg2000')
    #不用page的方式，而是看url有没有被重定向的方式---如果重定向了，那么就说明到底了