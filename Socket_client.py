import socket

s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.connect(('www.sina.com',80))

s.send(b'GET / HTTP/1.1\r\nHost:www.sina.com.cn\r\nConnection:close\r\n\r\n')

buffer = []
while True:
    d = s.recv(1024)
    if d:
        buffer.append(d)
    else:
        break
# data = ''.join(buffer)
data = r'{0}'.format(buffer)
print(r'{0}'.format(data))
data = r'{0}'.format(data);
s.close()

header, body = data.split('\\r\\n\\r\\n', 1)
print(header)

with open('sina.html','wb') as f:
    da = r'{0}'.format(body)
    da = bytes(da,encoding='UTF-8')
    f.write(da)

