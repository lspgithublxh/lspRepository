#coding=utf-8
import threading, time, thread, threadpool
print threading._active
#函数式处理
def thread_handle(name ,param):
    print name, param
    time.sleep(1)

one = thread.start_new_thread(thread_handle, ('thread name 1', 2, ))


#当前活跃的线程
print threading.enumerate()
threadLock = threading.Lock()
class ThreadMy(threading.Thread):
    def run(self):
        threadLock.acquire()
        time.sleep(1)
        print 'start a thread'
        threadLock.release()

t = ThreadMy()
t.start()


def hello(a,b, c,d):
    print a, b, threading.currentThread()

#线程池
pool = threadpool.ThreadPool(2)
tu = {'c':1, 'd':2}
requests = threadpool.makeRequests(hello, [((3,4),tu), ((3,4),tu),((3,4),tu),((3,4),tu)])#tuple和dict两种参数传递
[pool.putRequest(req) for req in requests]
pool.wait()

#---------------------
count = {'count':10}
event = threading.Event()
lock = threading.Lock()
def hello():
    time.sleep(2)
    print threading.currentThread().getName()
    lock.acquire()
    count['count'] -=1
    if count['count'] == 0:
        event.set()
        count['count'] = 10
    lock.release()


def handle():
    i = 0
    print 'start'
    while i < 10:
        one = threading.Thread(target=hello, args=())
        one.setDaemon(False)
        one.start()
        print '>>', i
        i+=1
    event.wait()
    print 'end'

def hellx():
    time.sleep(2)
    print threading.currentThread().getName()

def ziyou():
   i = 0
   while i < 10:
       one = threading.Thread(target=hellx, args=())
       one.start()
       i+=1

if __name__ == '__main__':
    # handle()
    print 'start'
    ziyou()
    print 'end'