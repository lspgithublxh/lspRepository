#coding=utf-8
import socket
import threading
import time


def test():
    sk = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sk.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sk.bind(("127.0.0.1", 10227))
    sk.listen(3)
    conn, addr = sk.accept()
    conn.sendall(bytes('hello, client'))
    print str(conn.recv(1024))

def server():
    sk = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sk.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    sk.bind(("127.0.0.1", 10227))
    sk.listen(3)
    while True:
        conn, addr = sk.accept()
        print('accept a new connect:')
        print(conn.getsockname())#服务端本身自己的ip-port
        print(addr)#客户端的ip-port
        thread = threading.Thread(target=handle, args=(conn, addr))
        thread.setName('tokentask:' + str(time.time()))

        thread.start()


def handle(conn, addr):
    conn.sendall(bytes('token jiami'))

if __name__ == '__main__':
    server()
