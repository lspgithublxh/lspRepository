#coding=utf-8
import socket
sk = socket.socket()
sk.connect(('127.0.0.1', 10227))
ret = str(sk.recv(1024))
sk.sendall(bytes('hello, server'))
print ret