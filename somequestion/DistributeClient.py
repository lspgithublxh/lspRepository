import time,sys,queue,random
from multiprocessing.managers import BaseManager


class QueueManager(BaseManager):
    pass


QueueManager.register('get_task_queue')
QueueManager.register('get_result_queue')


m = QueueManager(address=('127.0.0.1',5000), authkey='abc'.encode('UTF-8'))
m.connect()

task = m.get_task_queue()
result = m.get_result_queue()

for i in range(5):
   try:
       n = task.get(timeout=1)
       print('task get is :{0}'.format(n))
       time.sleep(1)
       result.put(random.random())
   except queue.Queue.empty():
       print("empty")

