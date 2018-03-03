#coding=utf-8 中断线程测试成功
#中断线程两种方式：一是用ctypes + inspect直接杀死子线程，自己没事.但是有时不会立即杀死，
# 第二种方式就是用timeout:线程的join(超时),仅仅退出，没有中断子线程，
#第三种方式是用thread.Event():阻塞，直到被设置了set()但没有超时退出。。是一个对象锁，可以控制多个线程--执行完毕再执行主线程，而且没有顺序。
import threading
import inspect
import ctypes
import time

def handle():
    print 'hello'

# threading.currentThread()
# threading.Event()但是子线程可以让主线程继续执行

def async_raise(tid, exctpye):
    tid = ctypes.c_long(tid)
    if not inspect.isclass(exctpye):
        exctpye = type(exctpye)
    res = ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, ctypes.py_object(exctpye))
    print 'res value:=-----------' , res
    if res == 0:
        raise  ValueError('invalid thread id')
    elif res != 1:
        ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, None)
        raise SystemError('PyThreadState_SetAsyncExc failed')

def stop_thread(thread):
    async_raise(thread.ident, SystemExit)

class Task(threading.Thread):
    def run(self):
        print 'thread start '
        while True:
            print 'sleep:'
            time.sleep(1)
        print 'thread end'

class Task2(threading.Thread):
    def __init__(self, ip):
        threading.Thread.__init__(self)
        print 'ip:' + ip

    def run(self):
        i = 10
        while i > 0:
            print 'run sub thread'
            time.sleep(1)
            i = i - 1

class Task3(threading.Thread):
    def __init__(self, event):
        threading.Thread.__init__(self)
        self.event = event
        print event

    def run(self):
        print 'task3 is woking:'
        time.sleep(3)
        print 'task3 is ok'
        self.event.set()

def test():
    t = Task()
    t.start()
    print 'subthread start working'
    time.sleep(3)
    stop_thread(t)
    # 开新线程
    t = Task2('12.12.12.12')
    t.start()
    t.join(3)  # 阻塞到运行完毕或者超时退出
    # 开新线程
    event = threading.Event()
    t3 = Task3(event)
    t3.start()
    event.wait()
    print 'main stop subthread'

def hello(ii):
    time.sleep(2)
    print 'great ', ii

if __name__ == '__main__':
    t = threading.Thread(target=hello,args=('world',),name='thread1')
    t.start()
    print 'main'