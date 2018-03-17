#coding=utf-8
import requests
import json

#微信公众号开发者获取token
def get_token():
    rs = requests.get(url='https://api.weixin.qq.com/cgi-bin/token',
                 params={"grant_type": "client_credential",
                         "appid": "wx323b4b38ecaa684b",
                         "secret": "f6451a73783d37d0057bdb34c48c0e59"}).json()
    if rs is not None and rs.get('access_token'):
        access_token = rs.get('access_token')
    else:
        access_token = None
    return  access_token


def sendMsg(touser, msg):
    access_token = get_token()
    print access_token
    body = {
        'touser':touser,
        'msgtype':'text',
        'text':{
            "content":msg
        }
    }
    rs = requests.post(url='https://api.weixin.qq.com/cgi-bin/message/custom/send',
                  params={
                      'access_token':access_token
                  },
                  data=bytes(json.dumps(body,ensure_ascii=False))
                  ).json()
    print(rs)

if __name__ == '__main__':
    sendMsg('ok81y0m8M1qhv7vkDdWVZG4n380A','hello, my name is lishaoping')