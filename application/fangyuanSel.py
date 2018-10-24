#coding=utf-8
import sys
import json
import pymysql
import uuid
import re
from bs4 import BeautifulSoup
from selenium import  webdriver
bro = webdriver.Chrome(executable_path="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe")

conn = pymysql.connect(host='localhost', port=3306, user='root', passwd='lsp', db='test', charset='utf8')
cursor = conn.cursor()

def getContent(url):
   rs = {'ulr':None,'page':None}
   try:
       bro.get(url)
       print bro.current_url
       rs['url'] = bro.current_url
       rs['page'] = bro.page_source
       # driver.close()
       # print bro.page_source
   except Exception as e:
       print e.args
   return rs

def parseContent(content):
    bs = BeautifulSoup(content, 'html.parser')
    body = bs.body
    if body is None: return -1
    ul = body.find('ul', {'class': 'sellListContent'})
    if ul is None:
        return []
    lis = ul.find_all('li')
    rs = []
    for li in lis:
        item = {}
        a = li.find('a', {'class':'maidian-detail'})
        if a is not None:
            attrs = a.attrs
            try:
                detail = attrs['href']
                item['detail'] = detail
                img = a.find('img', {'class': 'lj-lazy'})
                img_url = img.attrs['data-original']
                item['img'] = img_url
            except:
                item['img'] = ''

        div_tit = li.find('div', {'class': 'info clear'})
        if div_tit is not None:
            try:
                sub_div = div_tit.find('div', {'class': 'title'})
                a_title = sub_div.find('a')
                title = a_title.text
                item['title'] = title
            except:
                item['title'] = ''

            try:
                address = div_tit.find('div', {'class': 'address'})
                a_address = address.find('a')
                t_address = a_address.text
                item['address'] = t_address
            except:
                item['address'] = ''
            try:
                flood = div_tit.find('div', {'class': 'flood'})
                div_flood = flood.find('div')
                t_flood = div_flood.text
                item['flood'] = t_flood
            except:
                item['flood'] = ''
            try:
                followInfo = div_tit.find('div', {'class': 'followInfo'})
                t_followInfo = followInfo.text
                item['followInfo'] = t_followInfo
            except:
                item['followInfo'] = ''

            try:
                tag = div_tit.find('div', {'class': 'tag'})
                span_subway = tag.find('span', {'class': 'subway'})
                span_isfree = tag.find('span', {'class': 'is_see_free'})
                span_isvr = tag.find('span', {'class': 'is_vr'})
                tag_text = ''
                if span_subway is not None:
                    tag_text += span_subway.text
                if span_isfree is not None:
                    tag_text += span_isfree.text
                if span_isvr is not None:
                    tag_text += span_isvr.text
                item['tag'] = tag_text
            except:
                item['tag'] = ''
            try:
                priceInfo = div_tit.find('div', {'class': 'priceInfo'})
                div_pricetotal = priceInfo.find('div', {'class': 'totalPrice'})
                div_unitprice = priceInfo.find('div', {'class': 'unitPrice'})
                price_text = div_pricetotal.text + '.' + div_unitprice.text
                item['priceInfo'] = price_text
            except:
                item['priceInfo'] = ''
        rs.append(item)
    return rs

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
    xxx = getContent(url)#'https://bj.ke.com/'
    page = xxx['page']
    bs = BeautifulSoup(page, 'html.parser')
    body = bs.body
    rs = []
    if body is None: return -1
    top = body.find('div',{'class':'position'})
    if top is None:return rs
    divs = top.find('div', {'data-role': 'ershoufang'})
    aa = divs.find_all('a')

    for a in aa:
        src = a.attrs['href']
        rs.append(src)
    return rs

def getPageBox(page):
    #page = getContent(url)  # 'https://bj.ke.com/'
    bs = BeautifulSoup(page, 'html.parser')
    body = bs.body
    if body is None: return -1
    top = body.find('div', {'class': 'page-box fr'})
    aarr = top.find_all('a')
    a = []
    for x in aarr:
        a.append(x)
    if a.__len__() < 2:
        return -1;
    else:
        b = a.pop(a.__len__() - 2)
        return int(b.text)


def findCount(page):
    bs = BeautifulSoup(page, 'html.parser')
    body = bs.body
    if body is None: return -1
    try:
        top = body.find('div', {'class': 'resultDes clear'})
        hv = top.find('h2', {'class': 'total fl'})
        rs = hv.find('span').text
        return int(rs.strip())
    except:
        return 0

def mainMethod():
    p = re.compile('.*?/(\d*)\.html')
    #u'hf',u'wuhu', u'bf', u'huainan', u'mas', u'huaibei', u'tl', u'aq', u'tc', u'huangshan', u'mingguang', u'fy', u'suzhou', u'la', u'hq', u'bozhou', u'chizhou', u'xuancheng', u'cz', u'ng', u'tianchang', u'bj',
    #u'cq', u'fz'
    # citys = [ u'xm', u'pt', u'sm', u'yongan', u'quanzhou', u'shishi', u'na', u'longhai', u'np', u'nd', u'ly', u'zhangzhou', u'jo', u'shaowu', u'fa', u'fd', u'fuqing', u'lianj', u'gz', u'shaoguan', u'sz', u'zh', u'st', u'fs', u'sd', u'jiangmen', u'taishan', u'ep', u'zhanjiang', u'lianjiang', u'leizhou', u'wuchuan', u'mm', u'gaozhou', u'huazhou', u'zq', u'hui', u'meizhou', u'xingning', u'sw', u'heyuan', u'yangjiang', u'yangchun', u'yingde', u'lianzhou', u'dg', u'zs', u'chaozhou', u'jieyang', u'pn', u'qy', u'lechang', u'lufeng', u'luoding', u'kp', u'sjz', u'zhaoxian', u'xl', u'ts', u'zhunhua', u'qa', u'hd', u'wa', u'xt', u'baoding', u'dingzhou', u'cangzhou', u'rq', u'lf', u'bazhou', u'sanhe', u'hs', u'chengde', u'qhd', u'zjk', u'xan', u'ag', u'botou', u'huanghua', u'hj', u'gbd', u'jizhou', u'nangong', u'wj', u'shenzhou', u'zhuozhou', u'cc', u'yushu', u'dehui', u'jl', u'cy', u'huadian', u'sp', u'gzl', u'liaoyuan', u'tonghua', u'bs', u'songyuan', u'bc', u'yb', u'hunchun', u'jiaohe', u'longjing', u'linjiang', u'yj', u'dunhua', u'sy', u'xinmin', u'dl', u'pld', u'wfd', u'zhuanghe', u'as', u'haicheng', u'fushun', u'bx', u'dd', u'jinzhou', u'yk', u'gaizhou', u'dsq', u'liaoyang', u'pj', u'tieling', u'kaiyuan', u'chaoyang', u'hld', u'bp', u'dengta', u'lingyuan', u'xingcheng', u'donggang', u'fch', u'hhht', u'baotou', u'wuhai', u'cf', u'tongliao', u'eeds', u'hlbr', u'wlcb', u'xlht', u'alsm', u'mzl', u'xlglm', u'xining', u'haidong', u'haixi', u'dlh', u'grm', u'ty', u'qx', u'gujiao', u'dt', u'yq', u'changzhi', u'jc', u'yuncheng', u'lin', u'xinzhou', u'linfen', u'lvliang', u'jz', u'fenyang', u'houma', u'huozhou', u'lucheng', u'yongji', u'tj', u'lasa', u'rkz', u'changdu', u'nq', u'km', u'an', u'qj', u'yx', u'baoshan', u'zt', u'lj', u'pr', u'lincang', u'chuxiong', u'ky', u'honghe', u'ws', u'xsbn', u'jinghong', u'dali', u'dh', u'rl', u'nujiang', u'diqing', u'gejiu', u'xw', u'hz', u'nb', u'wz', u'ra', u'jx', u'huzhou', u'sx', u'jh', u'lanxi', u'yw', u'dongyang', u'yongkang', u'quzhou', u'zhoushan', u'taizhou', u'wl', u'lh', u'lishui', u'lq', u'shengzhou', u'cixi', u'haining', u'jd', u'jiashan', u'ph', u'tongxiang', u'yr', u'zhuji'];
    citys = [u'hf',u'wuhu', u'bf', u'huainan', u'mas', u'huaibei', u'tl', u'aq', u'tc', u'huangshan', u'mingguang', u'fy', u'suzhou', u'la', u'hq', u'bozhou', u'chizhou', u'xuancheng', u'cz', u'ng', u'tianchang']
    for city in citys:
        firsturl = 'https://{}.ke.com'.format(city)
        subcitys = parseGetSubCity(firsturl + '/ershoufang')
        print subcitys
        for sub in subcitys:
            visurl = ''
            if sub is not None and sub.__contains__('ke.com'):
                visurl = sub + 'pg{}/'
            else:
                visurl = firsturl + sub + 'pg{}/'
            boxget = False
            i = 100
            c = 1  # 已经all ready1364
            while c < i:
                factUrl = visurl.format(c)
                print factUrl
                xxx = getContent(factUrl)
                page = xxx['page']
                currUrl = xxx['url']
                if currUrl is None:
                    break
                if currUrl is not None and currUrl.endswith('ershoufang/'):
                    break
                tocount = findCount(page)
                if tocount == 0:
                    break
                # if boxget == False:
                #     i = getPageBox(page)
                #     print 'page:',i
                #     boxget = True
                #     if i == -1:break

                rs = parseContent(page)
                if rs.__len__() == 0:break
                sql = '''insert into fangyuan5(id,priceInfo,followInfo,flood,address,title,detail,img,city) values'''
                for item in rs:
                    print json.dumps(item, ensure_ascii=False)
                    if item.has_key('priceInfo'):  # print item['priceInfo']
                        id = item['detail']
                        om = p.match(id)
                        id = om.group(1)
                        sql += '''('{}','{}','{}','{}','{}','{}','{}','{}','{}'),'''.format(id, item['priceInfo'],
                                                                                       item['followInfo'],
                                                                                       item['flood'], item['address'],
                                                                                       item['title'], item['detail'],
                                                                                       item['img'], city)  # uuid.uuid1()
                if sql.endswith(','):
                    sql = sql[0:-1]
                    print sql
                    try:
                        exers = cursor.execute(sql)
                        conn.commit()
                    except Exception as e:
                        conn.rollback()
                        print e
                c += 1

def buchong():
    p = re.compile('.*?/(\d*)\.html')
    i = 100
    c = 1  # 已经all ready1364
    city = 'lf'
    visurl = 'https://lf.ke.com/ershoufang/xianghe/pg{}'#https://lf.ke.com/ershoufang/xianghe/
    while c < i:
        factUrl = visurl.format(c)
        print factUrl
        xxx = getContent(factUrl)
        page = xxx['page']
        currUrl = xxx['url']
        if currUrl is None:
            break
        if currUrl is not None and currUrl.endswith('ershoufang/'):
            break
        tocount = findCount(page)
        if tocount == 0:
            break
        # if boxget == False:
        #     i = getPageBox(page)
        #     print 'page:',i
        #     boxget = True
        #     if i == -1:break

        rs = parseContent(page)
        sql = '''insert into fangyuan3(id,priceInfo,followInfo,flood,address,title,detail,img,city) values'''
        for item in rs:
            print json.dumps(item, ensure_ascii=False)
            if item.has_key('priceInfo'):  # print item['priceInfo']
                id = item['detail']
                om = p.match(id)
                id = om.group(1)
                sql += '''('{}','{}','{}','{}','{}','{}','{}','{}','{}'),'''.format(id, item['priceInfo'],
                                                                                    item['followInfo'],
                                                                                    item['flood'], item['address'],
                                                                                    item['title'], item['detail'],
                                                                                    item['img'], city)  # uuid.uuid1()
        if sql.endswith(','):
            sql = sql[0:-1]
            print sql
            try:
                exers = cursor.execute(sql)
                conn.commit()
            except Exception as e:
                conn.rollback()
                print e
        c += 1


if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    # buchong()
    mainMethod()
