#coding=utf-8
from selenium import  webdriver
from selenium.webdriver.chrome.options import Options

import redis
from pymongo import MongoClient
from bs4 import BeautifulSoup
import threading

client = MongoClient('localhost', 27017)#10.252.62.125
col = client['detail']['detail']

chrome_options = Options()
chrome_options.add_argument('--no-sandbox')#解决DevToolsActivePort文件不存在的报错

chrome_options.add_argument('window-size=1920x3000') #指定浏览器分辨率
chrome_options.add_argument('--disable-gpu') #谷歌文档提到需要加上这个属性来规避bug
chrome_options.add_argument('--hide-scrollbars') #隐藏滚动条, 应对一些特殊页面
chrome_options.add_argument('blink-settings=imagesEnabled=false') #不加载图片, 提升速度
chrome_options.add_argument('--headless') #浏览器不提供可视化页面. linux下如果系统不支持可视化不加
def redisHandle():
    i = 0
    while i < 5:
        one = threading.Thread(target=threadHandle, args=())
        one.start()
        i += 1
    pass

def threadHandle():
    bro = webdriver.Chrome(executable_path="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe",
                           chrome_options=chrome_options)
    pool = redis.ConnectionPool(host='localhost', port=6379, decode_responses=True)
    r = redis.Redis(connection_pool=pool)
    url = 'https://bj.ke.com/ershoufang/{}.html'
    while True:
        print 'get id start:'
        id = r.rpop('detail')
        print 'get id:', id #or wait is ok , can't None
        if id is None:
            print 'get end, id is None'
            break
        rs = parseDetail(url.format(id), bro)
        if rs == -1: continue
        rs['id'] = id
        col.insert(rs)

def parseDetail(url, bro):
    # xxx = getContent(url)  # 'https://bj.ke.com/'
    # page = xxx['page']
    page = None
    bs = None
    try:
        bro.get(url)
        page = bro.page_source
        bs = BeautifulSoup(page, 'html.parser')
    except:
        return -1
    body = bs.body
    if body is None: return -1
    rs = {}
    title = {}
    try:
        titleDiv = body.find('div',{'class':'sellDetailHeader'})
        title_all = titleDiv.find('div',{'class':'title'})
        title_main = title_all.find('h1').text
        title_sub = title_all.find('div',{'class':'sub'}).text
        title['main'] = title_main
        title['sub'] = title_sub
        rs['title'] = title
    except:
        print 'title'
        pass


    img = []
    thumbnail = body.find('div',{'class':'thumbnail'})
    try:
        img_ul = thumbnail.find('ul',{'class':'smallpic'})
        img_lis = img_ul.find_all('li')
        for img_li in img_lis:
            url = img_li.attrs['data-src']#data-
            img.append(url)
        rs['img'] = img
    except:
        print 'img'
        pass

    try:
        youbian = body.find('div', {'class': 'overview'})
        overview = youbian.find('div',{'class':'content'})
        price_div = overview.find('div',{'class':'price'})
        total = price_div.find('span',{'class':'total'})
        unit = price_div.find('span',{'class':'unit'})
        uprice = price_div.find('div',{'class':'unitPrice'})
        price_ = {}
        price_['total'] = total.text
        price_['unit'] = unit.text
        price_['uprice'] = uprice.text
        rs['price'] = price_
    except:
        print 'price'
        pass

    try:
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
    except:pass

    try:
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
    except:pass

    try:
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
    except:pass

    try:
        baseHouse = body.find('div', {'class': 'newwrap baseinform', 'id':'introduction'})
        introContent = baseHouse.find('div', {'class': 'introContent'})
        baseInfo = introContent.find('div', {'class': 'base'})
        baseLis = baseInfo.find('ul').find_all('li')
        baseinfolist = []
        for baseli in baseLis:
            baseI = baseli.find('span',{'class':'label'})
            baseitem = {'name':baseI.text, 'value':baseli.text}
            baseinfolist.append(baseitem)
        transaction  = introContent.find('div', {'class': 'transaction'})
        transclis = transaction.find('ul').find_all('li')
        tranarr = []
        for baseli in transclis:
            baseI = baseli.find('span', {'class': 'label'})
            baseitem = {'name': baseI.text, 'value': baseli.text}
            tranarr.append(baseitem)
        basetransbody = {}
        basetransbody['baseinfolist'] = baseinfolist
        basetransbody['transaction'] = tranarr
        rs['baseinfodetail'] = basetransbody

        tese = body.find('div',{'class':'introContent showbasemore'})
        tese_tags = tese.find('div',{'class':'tags clear'})
        tese_list = []
        tese_name = tese_tags.find('div',{'class':'name'})
        tese_content = tese_tags.find('div',{'class':'content'})
        tese_arr = tese_content.find_all('a')
        tese_namearr = ''
        for tes_a in tese_arr:
            tese_namearr += tes_a.text +','
        tese_list.append({'name':tese_name.text, 'content':tese_namearr})
        tese_other = tese.find('div',{'class':'baseattribute clear'})
        for ts_other in tese_other:
            ts_name = ts_other.find('div',{'class':'name'})
            ts_value = ts_other.find('div',{'class':'content'})
            itx_ts = {'name':ts_name.text, 'content':ts_value.text}
            tese_list.append(itx_ts)
        rs['tese'] = tese_list
    except:pass

    try:
        daikan = body.find('div', {'id': 'daikanContainer'})
        daikan_list = daikan.find('div', {'class': 'daikan_list'})
        daikan_item = daikan_list.find('div',{'class':'item clear'})
        daikan_img = daikan_item.find('span').find('img')
        daikanimg_url = daikan_img.attrs['src']
        daikan_div = daikan_item.find('div',{'class':'daikan_item_content'})\
            .find('div',{'class':'item_title'})
        daikanName = daikan_div.find('span',{'class':'itemAgentName'})
        daikanBrand = daikan_div.find('span', {'class': 'brand_tag'})
        daikjjr_infocard = daikan_div.find('div',{'class':'jjr_infocard'})
        daikancard = daikjjr_infocard.attrs['data-pop-img']
        daikanphone = daikan_div.find('span', {'class': 'phone'})
        daikan_desc = daikan_item.find('div', {'class': 'daikan_item_content'})\
            .find('div',{'class':'des'})
        daikan_record = daikan_item.find('div', {'class': 'daikan_item_content'}) \
            .find('div', {'class': 'daikan_item_record'})
        daikan_ = {}
        daikan_['name'] = daikanName.text
        daikan_['brand'] = daikanBrand.text
        daikan_['card'] = daikancard
        daikan_['phone'] = daikanphone.text
        daikan_['desc'] = daikan_desc.text
        daikan_['record'] = daikan_record.text
        rs['daikan'] = daikan_
    except:pass

    try:
        hxfj = body.find('div', {'class': 'agent-tips'})
        hxfj_name = hxfj.find('div', {'class': 'fl'})
        hxfj_nweihu = hxfj_name.find('img').attrs['src']
        hxfj_fr = hxfj.find('div', {'class': 'fr'})
        hxfj_whr = hxfj_fr.find('div', {'class': 'text'})
        hxfj_rname = hxfj_whr.find('span')
        hxfj_card = hxfj_fr.find('div',{'class':'jjr_infocard'})
        hxfj_cardurl = hxfj_card.attrs['data-pop-img']
        hxfj_p = hxfj_fr.find('p')
        hxfj_content = body.find('div', {'class': 'layout-wrapper'})
        hxfj_body = hxfj_content.find('div',{'class':'content'})
        hxfj_img = hxfj_body.find('div', {'class':'imgdiv'})
        hxfj_imgurl = hxfj_img.attrs['data-img']
        hxfj_desc = hxfj_body.find('div', {'class': 'des'})
        hxfj_infolist = hxfj_desc.find('div',{'id':'infoList'})
        hxfj_rows = hxfj_infolist.find_all('div',{'class':'row'})
        hxfj_rowarr = []
        for row in hxfj_rows:
            row_divs = row.find_all('div',{'class':'col'})
            rowstr = ''
            for row_div in row_divs:
                rowstr += row_div.text
            hxfj_rowarr.append(rowstr)
        hxfj_z = {}
        hxfj_z['nameurl'] = hxfj_nweihu
        hxfj_z['name'] = hxfj_rname.text
        hxfj_z['card'] = hxfj_cardurl
        hxfj_z['phone'] = hxfj_p.text
        hxfj_z['hxtu'] = hxfj_imgurl
        hxfj_z['desc'] = hxfj_rowarr
        rs['hxfj'] = hxfj_z
    except:
        print 'hxfj'

    try:
        housepic = body.find('div', {'class': 'housePic'})
        hpiclist = housepic.find('div', {'class': 'list'})
        hpicdivs = hpiclist.find_all('div')
        hpicarr = []
        for picdiv in hpicdivs:
            if picdiv.attrs.has_key('class') and picdiv.attrs['class'] == 'left_fix':continue
            picdivimg = picdiv.find('img')
            if picdivimg is not None:
                picurl = picdivimg.attrs['src']
                hpicmap = {}
                hpicmap['picurl'] = picurl
                hpicmap['text'] = picdiv.text
                hpicarr.append(hpicmap)
        rs['pics'] = hpicarr

        fy_record = body.find('div', {'id': 'record'})
        fy_list = fy_record.find('div', {'class': 'list'})
        fy_content = fy_record.find('div', {'class': 'content'})
        fy_rows = fy_content.find_all('div', {'class': 'row'})
        fy_data = []
        for fy_row in fy_rows:
            fy_time = fy_row.find('div',{'class':'item mytime'})
            fy_agent = fy_row.find('div',{'class':'agentName'})
            if fy_time is None or fy_agent is None:continue
            fy_agent_img = fy_agent.find('img',{'class':'head_photo'})
            fy_imgsrc = fy_agent_img.attrs['src']
            fy_name = fy_agent.find('span')
            fy_brand = fy_agent.find('div',{'class':'brand'})
            fy_card = fy_agent.find('div',{'class':'jjr_infocard'})
            fy_cardimg = fy_card.attrs['data-pop-img']
            fy_phone = fy_row.find('div', {'class': 'phone'})
            fy_times = fy_row.find('div',{'class':'item mytotal'})
            fy_daik = {}
            fy_daik['time'] = fy_time.text
            fy_daik['img'] = fy_imgsrc.text
            fy_daik['name'] = fy_name.text
            fy_daik['brand'] = fy_brand.text
            fy_daik['card'] = fy_cardimg
            fy_daik['phone'] = fy_phone.text
            fy_daik['times'] = fy_times.text
            fy_data.append(fy_daik)
        fy_panel = fy_record.find('div', {'class': 'panel'})
        fy_last7 = fy_panel.find('div', {'class': 'panel-title'})
        fy_lastcount = fy_panel.find('div', {'class': 'count'})
        fy_totalcount = fy_panel.find('div', {'class': 'totalCount'})
        record = {}
        record['data'] = fy_data
        record['last7'] = fy_last7.text
        record['count'] = fy_lastcount.text
        record['totalcount'] = fy_totalcount.text
        rs['fy_record'] = record
    except:pass

    try:
        #gundong dao dibu
        # bro.execute_script("window.scrollTo(0, document.body.scrollHeight)")

        bro.execute_script("window.scrollBy(0, 700)")
        bro.execute_script("window.scrollBy(0, 800)")
        bro.execute_script("window.scrollBy(0, 900)")
        bro.execute_script("window.scrollBy(0, 1000)")
        bro.execute_script("window.scrollBy(0, 1200)")
        bro.execute_script("window.scrollBy(0, 1300)")
        bro.execute_script("window.scrollBy(0, 1400)")

        # bro.execute_script("window.scrollBy(0, 1500)")
        # bro.execute_script("window.scrollBy(0, 1600)")
        # bro.execute_script("window.scrollBy(0, 1700)")
        # bro.implicitly_wait(10)
        # page = bro.page_source
        # bs = BeautifulSoup(page, 'html.parser')
        # body = bs.body
        # time.sleep(3)
        # target = bro.find_element_by_xpath("//div[@id='resblockCardContainer']")#.send_keys('\ue015')
        # target = bro.find_element_by_id('resblockCardContainer')#.send_keys('\ue015')#.click()
        # webdriver.ActionChains(webdriver).move_to_element(target).perform()#key_down()#send_keys('\ue015')
        #http://chromedriver.storage.googleapis.com/index.html
        #https://npm.taobao.org/mirrors/chromedriver/2.42/

        #小区完整路径：https://bj.ke.com/xiaoqu/1111027376686/

        # time.sleep(0.1)
        xiaoqu = body.find('div', {'id': 'resblockCardContainer'})
        xq_card = xiaoqu.find('div', {'class': 'xiaoquCard'})
        xq_content = xq_card.find('div', {'class': 'xiaoqu_content'})
        xq_items = xq_content.find('div', {'class': 'xiaoqu_main fl'})
        xq_infos = xq_items.find_all('div', {'class': 'xiaoqu_info'})
        xq_data = []
        for xq_info in xq_infos:
            xq_label = xq_info.find('label')
            xq_spans = xq_info.find_all('span')
            xq_str = ''
            for xq_span in xq_spans:
                xq_str += xq_span.text + ','
            xq_infomap = {}
            xq_infomap['label'] = xq_label.text
            xq_infomap['content'] = xq_str
            xq_data.append(xq_infomap)
        xq_ = {}
        xq_['data'] = xq_data
        rs['xq_info'] = xq_
    except Exception as e:
        print 'xq_info'
        print e

    try:
        xq_cj = body.find('div', {'id': 'dealPrice'})
        xq_records = xq_cj.find('div', {'id': 'bizcircleDeal'})
        xq_rows = xq_records.find_all('div', {'class': 'row'})
        xq_cjarr = []
        for xq_row in xq_rows:
            cj_house = xq_row.find('div',{'class':'house'})
            cj_fy = cj_house.find('a')
            cj_fyhref = cj_fy.attrs['href']
            cj_desc = cj_house.find('div',{'class':'desc'})
            cj_frame = cj_desc.find('div',{'class':'frame'})
            cj_floor = cj_desc.find('div',{'class':'floor'})
            cj_name = cj_desc.find('a')
            house_ = {}
            house_['href'] = cj_fyhref
            house_['frame'] = cj_frame.text
            house_['floor'] = cj_floor.text
            house_['name'] = cj_name.text
            cj_area = xq_row.find('div',{'class':'area'})
            cj_date = xq_row.find('div',{'class':'date'})
            cj_price = xq_row.find('div',{'class':'price'})
            cj_unitPrice = xq_row.find('div',{'class':'unitPrice'})
            cj_from = xq_row.find('div',{'class':'from'})
            cj_body = {}
            cj_body['house'] = house_
            cj_body['area'] = cj_area.text
            cj_body['date'] = cj_date.text
            cj_body['price'] = cj_price.text
            cj_body['unitPrice'] = cj_unitPrice.text
            cj_body['from'] = cj_from.text
            xq_cjarr.append(cj_body)
        rs['xq_cjrecord'] = xq_cjarr
    except:
        print 'cjrecord'
    return rs

if __name__ == '__main__':
    redisHandle()
    pass