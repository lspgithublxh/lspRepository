#coding=utf-8
from selenium import webdriver
from bs4 import  BeautifulSoup




# ul = content.find('ul', class_='house-list-wrap')
# for li_div in ul.find_all('li'):
#     div = li_div.find('div', class_='pic')
#     print div.find('a').find('img')
# ershoufanglist = content.select('ul[class="house-list-wrap"]')
# divs = ershoufanglist
# print len(divs)

def getHtml():
    bro = webdriver.Chrome(executable_path="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe")
    bro.get('http://bj.58.com/ershoufang/pn2/?PGTID=0d300000-0000-0c00-a946-b152b56caca9&ClickID=1')

    html = bro.page_source
    content = BeautifulSoup(html, 'html.parser')
    ul = content.find(name='ul', attrs={'class': 'house-list-wrap'})
    for li_div in ul.find_all('li'):
        div = li_div.find('div', class_='pic')
        pic_src = div.find('a').find('img').attrs['src']
        detail_page_url = div.find('a').attrs['href']
        # 文本信息
        info = li_div.find('div', class_='list-info')
        into_text = info.find('h2').find('a').text
        #
        house_info = li_div.find_all('p', class_='baseinfo')
        base_info2 = house_info[1]
        print detail_page_url, into_text, house_info[0].text.replace('\n', '-')#.replace('\r\n', '-').replace('\r', '-')
        spans = base_info2.find_all('span')
        aArr = spans[0].find_all('a')
        xiaoquLink = aArr[0].attrs['href']
        address = None
        if ( len(spans) > 1 ):
            address = spans[1].text
        print spans[0].text.replace('\n', '-'),xiaoquLink.replace('\n', '-'), address
        #
        jjrinfo = li_div.find('div', class_='jjrinfo')
        print jjrinfo.text.replace('\n', '-')
        price = li_div.find('div', class_='price')
        print price.text.replace('\n', '-')
        rs_info = {'price': price.text.replace('\n', '-'), 'jjinfo':jjrinfo.text.replace('\n', '-'),
                   'xiaoqu':spans[0].text.replace('\n', '-'), 'xiaoquLink':xiaoquLink.replace('\n', '-'),
                   'address':address, 'detail_page_url':detail_page_url}

if __name__ == '__main__':
    print 'sdfds\r\nsdfs'.replace('\r\n', '')
    getHtml()