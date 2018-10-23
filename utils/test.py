#coding=utf-8
import collections

class Solution():

  def findnumbers(self, arr):
    if len(arr)<3:
      return False
    numbers = collections.Counter(arr) #
    times = []
    for item, value in enumerate(numbers.iteritems()): #
      times.append(value)
    times = set(times)
    times = sorted(times)[::-1]
    ans = []
    for i in range(3):
      tmp_number = times[i]
      tmp_list = []
      for item,value in enumerate(numbers.iteritems()): #
        if value==tmp_number:
          tmp_list.append(item)
      tmp_list.sort()
      ans+=tmp_list
    return ans

if __name__ == '__main__':
    s = Solution().findnumbers('asazzzsdde')
    print s
    rs = 'wbmain://jump/house/shareChart?params={"slogan":"\u6211\u89c9\u5f9758\u4e0a\u8fd9\u5957\u623f\u5f88\u4e0d\u9519\uff01","fullPath":"1,8","location":"\u5317\u4eac\u00b7\u671d\u9633\u00b7\u5927\u5c71\u5b50","title":"\u6574\u79df \u4e2a\u4eba\u51fa\u79df15\u53f7\u7ebf\u5d14\u5404\u5e84\u5730\u94c1\u65c1\u9ad8\u6863\u697c\u623f\uff0c\u72ec\u7acb\u53a8\u536b\uff0c\u623f\u79df\u6708\u4ed8\uff01","huxing":"1\u5ba40\u53851\u536b","area":"28\u33a1","price":"1200","priceUnit":"\u5143\/\u6708","tips":"\u623f\u6e90\u6765\u81ea58\u540c\u57ce","qrCodeUrl":"https:\/\/houserentapp.58.com\/wechat\/Api_get_detail_qrcode?infoId=33551762570059&listName=zufang&localName=bj&fullPath=1,8&signature=170c4dfb052a73ca4bd5f990a829522b","houseImg":"https:\/\/pic5.58cdn.com.cn\/anjuke_58\/38a8ba0090b8ee2e7e7498184ea5bf5d?w=750&h=562&crop=1"}'
    ws = {"slogan":"\u6211\u89c9\u5f9758\u4e0a\u8fd9\u5957\u623f\u5f88\u4e0d\u9519\uff01","fullPath":"1,8","location":"\u5317\u4eac\u00b7\u671d\u9633\u00b7\u5927\u5c71\u5b50","title":"\u6574\u79df \u4e2a\u4eba\u51fa\u79df15\u53f7\u7ebf\u5d14\u5404\u5e84\u5730\u94c1\u65c1\u9ad8\u6863\u697c\u623f\uff0c\u72ec\u7acb\u53a8\u536b\uff0c\u623f\u79df\u6708\u4ed8\uff01"}
    for item in ws.iteritems():
      print u'{}'.format(item[1])
      print
    print u'\u6211\u89c9\u5f9758\u4e0a\u8fd9\u5957\u623f\u5f88\u4e0d\u9519\uff01'
    print u'\u5317\u4eac\u00b7\u671d\u9633\u00b7\u5927\u5c71\u5b50'
    print u'\u6574\u79df \u4e2a\u4eba\u51fa\u79df15\u53f7\u7ebf\u5d14\u5404\u5e84\u5730\u94c1\u65c1\u9ad8\u6863\u697c\u623f\uff0c\u72ec\u7acb\u53a8\u536b\uff0c\u623f\u79df\u6708\u4ed8\uff01'
    print unicode(rs)
    print '\u6211\u89c9\u5f9758\u4e0a\u8fd9\u5957\u623f\u5f88\u4e0d\u9519\uff01'.decode('unicode_escape')
    print rs.decode('unicode_escape')
