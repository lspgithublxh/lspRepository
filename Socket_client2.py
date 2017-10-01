import socket


#连接本地的客户端：
s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
print('Connection to ...')
s.connect(('127.0.0.1',9999))
print(s.recv(1024))
for data in ['Lishaoping','Lixiaohai','lida']:
    print('send to')
    s.send(bytes(data,encoding='utf-8'))
    print(s.recv(1024))
s.send(bytes('exit',encoding='utf-8'))
s.close()