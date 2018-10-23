#coding=utf-8
import sys
from bs4 import BeautifulSoup
from fangyuanSel import getContent

def parseDetail(url):
    page = getContent(url)  # 'https://bj.ke.com/'
    bs = BeautifulSoup(page, 'html.parser')
    body = bs.body
    if body is None: return -1
    rs = {}
    title = {}
    titleDiv = body.find('div',{'class':'sellDetailHeader'})
    title_all = titleDiv.find('div',{'class':'title'})
    title_main = title_all.find('h1').text
    title_sub = title_all.find('div',{'class':'sub'}).text
    title['main'] = title_main
    title['sub'] = title_sub
    rs['title'] = title

    img = {}
    thumbnail = body.find('div',{'class':'thumbnail'})
    img_ul = thumbnail.find('ul',{'class':'smallpic'})
    img_lis = img_ul.find('li')
    for img_li in img_lis:
        url = img_li.attrs['data-src']
        img['url'] = url
    rs['img'] = img

    youbian = body.find('div',{'class':'content'})
    price_div = youbian.find('div',{'class':'price'})
    total = price_div.find('span',{'class':'total'})
    unit = price_div.find('span',{'class':'unit'})
    uprice = price_div.find('div',{'class':'unitPrice'}).text
    price_ = {}
    price_['total'] = total
    price_['unit'] = unit
    price_['uprice'] = uprice
    rs['price'] = price_

    houseInfo = youbian.find('div',{'class':'houseInfo'})
    room = houseInfo.find('div',{'class':'room'})
    ting = room.find('div',{'class':'mainInfo'})
    layer = room.find('div', {'class': 'subInfo'})
    type = houseInfo.find('div', {'class': 'type'})
    fangxiang = type.find('div', {'class': 'mainInfo'})
    loutype = type.find('div', {'class': 'subInfo'})
    area = houseInfo.find('div', {'class': 'area'})
    mianji = area.find('div', {'class': 'mainInfo'})
    buildtime = area.find('div', {'class': 'subInfo'})
    house_ = {}
    house_['ting'] = ting.text
    house_['layer'] = layer.text
    house_['fangxiang'] = fangxiang.text
    house_['loutype'] = loutype.text
    house_['mianji'] = mianji.text
    house_['buildtime'] = buildtime.text
    rs['house'] = house_


    aroundInfo = youbian.find('div', {'class': 'aroundInfo'})
    community = aroundInfo.find('div',{'class':'communityName'})
    nameInfo = community.find('a',{'class':'info'})
    a_href = nameInfo.attrs['href']
    ahref = ''
    if a_href is not None:
        ahref = a_href.split('/')[2]
    areaName = aroundInfo.find('div', {'class': 'areaName'})
    span_area = areaName.find('span', {'class': 'info'})
    a_ditie = areaName.find('a',{'class':'supplement'})
    quyu = {}
    quyu['communityName'] = nameInfo.text
    quyu['xiaoquId'] = ahref
    quyu['area'] = span_area.text
    quyu['ditie'] = a_ditie.text
    rs['quyu'] = quyu

    brokerInfo = youbian.find('div', {'class': 'brokerInfo clear'})
    agentInfo = brokerInfo.find('div', {'class': 'agentInfo'})
    head = agentInfo.find('img', {'class': 'head_photo'})
    head_href = head.attrs['src']
    broker_ = agentInfo.find('div',{'class':'brokerName'})
    brokerName = broker_.find('span',{'class':'name LOGCLICK'})
    brand = broker_.find('span',{'class':'brand_tag'})
    cyxinxika = broker_.find('span',{'class':'jjr_infocard'})
    xinxika = cyxinxika.attrs['data-pop-img']
    jjr_license = broker_.find('span', {'class': 'jjr_license'})
    zhengka = jjr_license.attrs['data-pop-img']
    agent_phone = brokerInfo.find('div', {'class': 'phone'})
    agPhone = agent_phone.find('div',{'class':'phone400'})
    agent_ = {}
    agent_['head'] = head_href
    agent_['brokerName'] = brokerName.text
    agent_['brand'] = brand.text
    agent_['xinxika'] = xinxika.text
    agent_['zhengka'] = zhengka.text
    agent_['phone'] = agPhone.text
    rs['agentInfo'] = agentInfo

    baseHouse = body.find('div', {'class': 'newwrap baseinform'})
    introContent = baseHouse.find('div', {'class': 'introContent'})
    baseInfo = introContent.find('div', {'class': 'base'})
    baseLis = baseInfo.find('ul').find_all('li')
    for baseli in baseLis:pass


if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
