#coding=utf-8
import Queue
import thread , threading
q = Queue.Queue()
q.put('aa')
print q.get()

class Put(threading.Thread):
    def run(self):
        for i in range(1,10):
            print 'put'
            q.put(i)

class Get(threading.Thread):
    def run(self):
        for i in range(1, 10):
            # print 'get'
            print q.get(),

for i in range(1, 3):
    # t = thread.start_new_thread(put, ())
    tt = Put()
    tt.start()
import time
print 'sleep 2s'
time.sleep(2)

for i in range(1,3):
    # t = thread.start_new_thread(get, ('ss',))
    t = Get()
    t.start()