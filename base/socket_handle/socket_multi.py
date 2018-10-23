#coding=utf-8
import socket, socketserver
class REQUH(socketserver.BaseRequestHandler):
    def handle(self):
        print 'sever start'
        conn = self.request
        print self.client_address
        while True:
            da = conn.recv(1024)
            print da.decode('utf-8')
            if da.decode('utf-8') == 'hehe':
                print 'close'
                conn.close()
                break
            conn.sendall('hahha')

def handle():
    s0 = socketserver.TCPServer(('127.0.0.1', 80), REQUH, True)
    s0.serve_forever()
def handle2():
    s00 = socketserver.TCPServer(('127.0.0.1', 11180), REQUH, True)
    s00.serve_forever()

import thread
thread.start_new_thread(handle, ())
thread.start_new_thread(handle2, ())
# s0.server_bind()
s1 = socket.socket()
s2 = socket.socket()
s3 = socket.socket()
s3.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s1.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s2.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s1.bind(('127.0.0.1', 12345))
s2.bind(('127.0.0.1', 12345))
s3.bind(('127.0.0.1', 12346))
s1.connect(('127.0.0.1', 80))
s2.connect(('127.0.0.1', 11180))
s2.send('hehe')

s3.connect(('127.0.0.1', 12345))
