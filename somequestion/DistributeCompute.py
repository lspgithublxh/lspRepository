import random,time
from multiprocessing.managers import BaseManager
# from multiprocessing import Queue
from queue import Queue

task = Queue()
result = Queue()


class QueueManager(BaseManager):
    pass


#注册为网络数据结构
QueueManager.register('get_task_queue',callable=lambda: task)
QueueManager.register('get_result_queue',callable=lambda:result)

#绑定一个网络数据结构的访问接口
manager = QueueManager(address=('',5000), authkey='abc'.encode('UTF-8'))

manager.start()

task = manager.get_task_queue()
result = manager.get_result_queue()

for i in random(5):
    task.put(random.randint(0,1000))
    print('put a value to server datamodel')

for i in random(5):
    rs = result.get(timeout=10)

manager.shutdown()