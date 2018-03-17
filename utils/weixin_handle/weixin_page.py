#coding=utf-8
import itchat
#只需要alt+ enter就可以快速导入已经pip安装的模块
# itchat.login()
# itchat.send('你好','filehelper')
@itchat.msg_register(itchat.content.TEXT)
def print_content(msg):
    print(msg['Text'])

try:
    itchat.auto_login()
except Exception :
    pass

# itchat.auto_login(hotReload=True, enableCmdQR=True)#装pillow
# itchat.run()
import threading
threading._sleep(15)
itchat.send('测试消息发送','filehelper')
