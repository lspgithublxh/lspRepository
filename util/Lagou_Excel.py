import  re
from urllib import request
from urllib import parse
import json,collections,xlsxwriter
import  bs4,datetime
import  requests

starttime = datetime.datetime.now()
# url = r'http://www.lagou.com/jobs/positionAjax.json?city=%E5%8C%97%E4%BA%AC'
# url = r'https://www.lagou.com/jobs/list_java?city=%E5%8C%97%E4%BA%AC&cl=false&fromSearch=true&labelWords=sug&suginput=java'
url = r'https://www.lagou.com/jobs/positionAjax.json?city=%E5%8C%97%E4%BA%AC&needAddtionalResult=false&isSchoolJob=0'
keyword = input('请输入您所需要查找的关键词 : ')

def getPage2(url,pn,keyword):
    headers = {
        'User-Agent':'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/45.0.2454.85 Safari/537.36 115Browser/6.0.3',
        'Host':'www.lagou.com',
        'Connection':'keep-alive',
        'Origin':'http://www.lagou.com',
       'Cookie':'JSESSIONID=ABAAABAAAGGABCB8EEC952C31AB3AA9A18BFA809495A8C7; user_trace_token=20171002112650-04238be5-9d8c-4769-9362-2f1c6cc7ae59; LGUID=20171002112653-88e1de05-a721-11e7-b81d-525400f775ce; index_location_city=%E5%8C%97%E4%BA%AC; SEARCH_ID=c0253b6c5f874e45990d399269e20297; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1506914804; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1506916934; _gat=1; _ga=GA1.2.556263261.1506914804; _gid=GA1.2.500162274.1506914804; LGSID=20171002112653-88e1dc0c-a721-11e7-b81d-525400f775ce; LGRID=20171002120313-9c734b18-a726-11e7-b821-525400f775ce; TG-TRACK-CODE=search_code',
        'Connection': 'keep-alive',
        'Content-Length': 38,
        'Content-Type': 'application/x-www-form-urlencoded;charset = UTF - 8',
        'Referer':'https://www.lagou.com/jobs/list_%E7%BB%8F%E7%90%86?city=%E5%8C%97%E4%BA%AC&cl=false&fromSearch=true&labelWords=&suginput=',
        'X-Requested-With':'XMLHttpRequest',
        'X-Anit-Forge-Code': 0,
       'X-Anit-Forge-Token': None,
        'Pragma':'no-cache',
        'Accept':'application/json, text/javascript, */*; q=0.01'
    }
    if pn == 1:
        boo = 'true'
    else:
        boo = 'false'
    data = parse.urlencode([
        ('first',boo),
        ('pn',pn),
        ('kd',keyword)
    ])
    req = request.Request(url,headers=headers)
    page = request.open(req,data=data.encode('utf-8')).read()
    # page = request.u
    page = page.decode('utf-8')
    return page


def getPage(url,pn,keyword):
    s = requests.Session()
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/45.0.2454.85 Safari/537.36 115Browser/6.0.3',
        'Host': 'www.lagou.com',
        'Connection': 'keep-alive',
        'Origin': 'http://www.lagou.com',
        'Cookie': 'JSESSIONID=ABAAABAAAGGABCB8EEC952C31AB3AA9A18BFA809495A8C7; user_trace_token=20171002112650-04238be5-9d8c-4769-9362-2f1c6cc7ae59; LGUID=20171002112653-88e1de05-a721-11e7-b81d-525400f775ce; index_location_city=%E5%8C%97%E4%BA%AC; SEARCH_ID=c0253b6c5f874e45990d399269e20297; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1506914804; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1506916934; _gat=1; _ga=GA1.2.556263261.1506914804; _gid=GA1.2.500162274.1506914804; LGSID=20171002112653-88e1dc0c-a721-11e7-b81d-525400f775ce; LGRID=20171002120313-9c734b18-a726-11e7-b821-525400f775ce; TG-TRACK-CODE=search_code',
        'Connection': 'keep-alive',
        'Content-Length': '38',
        'Content-Type': 'application/x-www-form-urlencoded;charset = UTF - 8',
        'Referer': 'https://www.lagou.com/jobs/list_%E7%BB%8F%E7%90%86?city=%E5%8C%97%E4%BA%AC&cl=false&fromSearch=true&labelWords=&suginput=',
        'X-Requested-With': 'XMLHttpRequest',
        'X-Anit-Forge-Code': '0',
        'X-Anit-Forge-Token': None,
        'Pragma': 'no-cache',
        'Accept': 'application/json, text/javascript, */*; q=0.01'
    }
    if pn == 1:
        boo = 'true'
    else:
        boo = 'false'
    data = {'first':boo,'pn':pn, 'kd':keyword}
    s.headers.update(headers)
    r = s.post(url,data=data)
    return r.text


def read_id(page):
    tag = 'positionId'
    page_json = json.loads(page)
    page_json = page_json['content']['hrInfoMap']
    company_list = []
    # for i in range(15):
    #     company_list.append(page_json[i].get(tag))
    for i in page_json:
        company_list.append(i)
    return company_list


def get_content(company_id):
    fin_url = r'http://www.lagou.com/jobs/{0}.html'.format(company_id)
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) '
         'Chrome/45.0.2454.85 Safari/537.36 115Browser/6.0.3',
         'Host': 'www.lagou.com',
        'Connection': 'keep-alive',
        'Origin': 'http://www.lagou.com'
    }
    req = request.Request(fin_url,headers=headers)
    page = request.urlopen(req).read()
    content = page.decode('utf-8')
    return content


def get_result(content):
    # print(content)
    soup = bs4.BeautifulSoup(content,'html.parser')
    job_description = soup.select('dd[class="job_bt"]')
    job_description = str(job_description[0])
    rule = re.compile(r'<[^>]+>')
    result = rule.sub('',job_description)
    return  result


def search_skills(result):
    rule = re.compile(r'[a-zA-Z]+')
    skill_list = rule.findall(result)
    return skill_list


def count_skill(skill_list):
    for i in range(len(skill_list)):
        skill_list[i] = skill_list[i].lower()
    count_dict = collections.Counter(skill_list).most_common(80)
    return  count_dict


def save_excel(count_dict, file_name):
    book = xlsxwriter.Workbook(r'D:\test\{0}.xls'.format(file_name))
    tmp = book.add_worksheet()
    row_num = len(count_dict)
    for i in range(1, row_num):
        if i == 1:
            tag_pos = 'A{0}'.format(i)
            tmp.write_row(tag_pos,['关键词','频次'])
        else:
            con_pos = 'A{0}'.format(i)
            k_v = list(count_dict[i - 2])
            tmp.write_row(con_pos,k_v)
    chart1 = book.add_chart({'type':'area'})
    chart1.add_series({
        'name':'=Sheet1!$B$1',
        'categories':'=Sheet1!$A$2:$A$80',
        'values':'=Sheet1!$B$2:$B$80'
    })
    chart1.set_title({'name':'关键词排名'})
    chart1.set_x_axis({'name':'关键词'})
    chart1.set_y_axis({'name':'频次/次'})
    tmp.insert_chart('C2',chart1, {'x_offset':25,'y_offset':10})

def read_max_page(page):
    page_json = json.loads(page)
    print(page_json)
    max_page_num = page_json['content']['pageSize'] #解析json表达式
    if max_page_num > 30:
        max_page_num = 30
    return max_page_num


if __name__ == '__main__':
    max_pn = read_max_page(getPage(url,1, keyword))
    fin_skill_list = []
    for pn in range(1, max_pn):
        print('--------------正在抓取----------------')
        page = getPage(url,pn,keyword)
        company_list = read_id(page)
        for company_id in company_list:
            content = get_content(company_id)
            result = get_result(content)
            skill_list = search_skills(result)
            fin_skill_list.extend(skill_list)
    print('----------统计关键字----------')
    count_dict = count_skill(fin_skill_list)
    print(count_dict)
    file_name = input('请输入要保存的文件名')
    save_excel(count_dict, file_name)
    print('已经保存到文件')
    endtime = datetime.datetime.now()
    time = (endtime - starttime).seconds
    print('总共耗时：{0}'.format(time))