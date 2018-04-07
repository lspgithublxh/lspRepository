#coding=utf-8
import struct, socket, binascii
print struct.pack("B", 1)


rawsocket = socket.socket(socket.AF_INET, socket.SOCK_RAW, socket.IPPROTO_RAW)
# pkt = rawsocket.recvfrom(2048)
