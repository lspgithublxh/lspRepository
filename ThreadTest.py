import time,threading,random
lock = threading.Lock()
local_school = threading.local()
bal = 0

def handle():
    name = threading.current_thread().getName()
    id = local_school.id
    print('name:{0},{1}'.format(name,id))

def loop(k):
    n = 0
    #time.sleep(random.random() * 3)
    global lock
    lock.acquire()
    try:
        global bal
        bal = bal + 1
        bal = bal + k
        name = threading.current_thread().getName()
        print('current thraed: {0},{1}'.format(name,bal))
    finally:
        lock.release()
    local_school.id = k
    handle()
if __name__ == '__main__':
    for i in range(5):
        t1 = threading.Thread(target=loop, name='threadlsp{0}'.format(i),args=(i,))
        t1.start()
        t1.join()
    print('current thraed: {0}'.format(threading.current_thread().getName()))