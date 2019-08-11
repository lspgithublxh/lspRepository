#coding=utf-8
import SimpleHTTPServer
import re
from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
from base.design_model import request_handle

map = request_handle.urlMap

def getParam(ano_url, r_url):
    s = ano_url
    # s = '/{city}/sss.html'
    p = re.compile('^.+?(?:\{(.+?)\}.+?)?.*$')
    m = p.match(s)
    rsList = []
    if m is not None:
        tupl = m.groups()
        for i, t in enumerate(tupl):
            # print i, t
            rsList.append([t, ''])
            s = s.replace('{' + t + '}', r'(\S+?)')
        # print s, rsMap
        s = s.replace('.', '\.')
        p2 = re.compile(s)
        m2 = p2.match(r_url)#'/bj/sss.html'
        if m2 is not None:
            tup2 = m2.groups()
            for i, t in enumerate(tup2):
                rsList[i][1] = t
            # print rsList
            rs = {}
            for l in rsList:
                rs[l[0]] = l[1]
            return rs
        return None
    return None

def route(path):
    for key in map.iterkeys():
        rs = getParam(key, path)
        if rs is not None:
           print  map.__getitem__(key)(**rs)


#servlet
class HttpHandle(BaseHTTPRequestHandler):

    def do_GET(self):
        print self.path
        route(self.path)
        self.send_response(200)
        self.send_header('Content-type','text/html')
        self.end_headers()
        self.wfile.write('Hello world!')
        return

    def do_POST(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/json')
        self.end_headers()
        data = {'v': 'vvvv'}
        self.wfile.write(bytes(json.dumps(data, ensure_ascii=False, ), encoding='utf-8'))

try:
    print 'server start at 8000'
    server = HTTPServer(('', 8000), HttpHandle)
    server.serve_forever()
except KeyboardInterrupt:
    server.socket.close()
