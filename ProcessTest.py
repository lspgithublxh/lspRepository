import os,time,random
from multiprocessing import Process, Pool,Queue
#进程之间的同步和通信
def write(q):
    for i in ['A','B','C','D','E']:
        q.put(i)
        time.sleep(random.random() * 3)
        print('put value: {0}'.format(i))

def read(q):
    while True:
        value = q.get(True)
        print('get value is {0}'.format(value))

if __name__ == '__main__':
    q = Queue()
    p1 = Process(target=write,args=(q,))
    p2 = Process(target=read,args=(q,))
    p1.start()
    p2.start()
    p1.join()
    p2.terminate()