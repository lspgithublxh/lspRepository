#coding=utf-8
from pymongo import MongoClient
import pprint
import pygal.maps.world

def groupbyCity():
    countrymap = {}
    with open('D:\\test\\scrapy\\test_map\\worldmap.txt', 'r') as f:
        while True:
            str = f.readline()
            if str == '': break
            countrymap[str[3:-1]] = str[:2]
    print countrymap
    client = MongoClient('192.168.1.152',27017)
    col = client['lsp_test']['shordan_better']
    # col._command()
    res = col.aggregate([{'$group':{'_id':{'country':'$city'}, 'number':{'$sum': 1}}}])
    count = 0
    obj = {}
    for row in res:
        country, num = row['_id']['country'], row['number']
        count = count + 1
        cy = countrymap.get(country)
        print cy , num
        # pprint.pprint(row)
        obj[cy] = num
    #构造世界地图
    showMap(obj)

def showMap(data):
    chart = pygal.maps.world.World()
    chart.title = 'shordan search result: distrubute condition'
    chart.add('In 2017',data)
    chart.render_to_file('shordan_result.svg')

if __name__ == '__main__':
    groupbyCity()