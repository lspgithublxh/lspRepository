#coding=utf-8
from BaseHTTPServer import HTTPServer, BaseHTTPRequestHandler
import re
import urllib
import os

#定义http请求的handler, 类似继承定义一个自己的servlet
class httpHandle(BaseHTTPRequestHandler):
    print 'start '
    def do_GET(self):
        temp_str = """"""
        qrImg = '<img src="http://chart.apis.google.com/chart?chs=300*300&cht=qr&choe=UTF-8&chl=http://blog.csdn.net/testcs_dn&qr=Show\+QR" /></br/>' + urllib.unquote('http://blog.csdn.net/testcs_dn&qr=Show\+QR')

        self.protocol_version = 'HTTP/1.1'
        self.send_response(200)
        self.send_header('Welcome','Contect')
        self.end_headers()
        self.wfile.write(temp_str + qrImg)


def start_server(port):
    server = HTTPServer(('', 8999), httpHandle)
    server.serve_forever()#一直监听



if __name__ == '__main__':
    print urllib.unquote('http://blog.csdn.net/testcs_dn&qr=Show\+QR')
    os.chdir('static') #工作目录重新指定..当前目录的static目录下
    start_server(8000)