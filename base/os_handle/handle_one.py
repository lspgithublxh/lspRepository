#coding=utf-8
import os
import win32serviceutil
print os.getcwd()

#运行cmd命令
import sys
reload(sys)
print sys.getdefaultencoding()
sys.setdefaultencoding('utf-8')
# print os.system('ipconfig')

#服务操作

if __name__ == '__main__':
    import sys
    import win32serviceutil

