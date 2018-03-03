#coding=utf-8
from phantom_download import getContent2
from commonparser import get_cnvd_of_page
from cnvd_search import justGetCNVDFirst, update_else_keyvalue
import sys
from cnnvd_search import addNewCNNVDFromCnvd
from cve_search import addNewCVEFromCNNVD

def start():
    url = 'http://www.cnvd.org.cn/flaw/list.htm?flag=true'
    #1.cnvd新增到cnvd_search3
    # justGetCNVDFirst()
    # update_else_keyvalue()
    #2.cnnvd新增到cnnvd_search3
    # addNewCNNVDFromCnvd()
    #3.cve新增到cve_search2
    addNewCVEFromCNNVD()
    #4.独立任务，生成json,插入到vul4中
    #5.独立任务，直接加索引



def new_start():
    pass



if __name__ == '__main__':
    reload(sys)
    sys.setdefaultencoding('UTF-8')
    start()