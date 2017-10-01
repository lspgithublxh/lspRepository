import  socket,threading,time

s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind(('127.0.0.1',9999))
s.listen(5)
print('Waiting for conncetion...')


def handleConncet(sock,addr):
    print('Accept new connection from {0}'.format(addr))
    sock.send(b'Hello')
    while True:
        data = sock.recv(1024)
        time.sleep(1)
        if data == 'exit' or not data:
            break
        sock.send(bytes('Hello, {0}'.format(data),encoding='utf-8'))
    sock.close()
    print('Connection from {0} closed...'.format(addr))


while True:
    print('wait to accept:')
    sock, addr = s.accept()
    print('access')
    t = threading.Thread(target=handleConncet, args=(sock, addr))
    t.start()
