#coding=utf-8
#至少4种方式执行周期任务
import sched
import time
from threading import Timer
import  threading
from apscheduler.schedulers.blocking import BlockingScheduler
from apscheduler.schedulers.background import BackgroundScheduler
import datetime
import time


def func(info1, info2):
    print 'now is ', time.time(), ',and input info is :', info1, info2
    print status

def func2(info1, info2, thread):
    print 'now is ', time.time(), ',and input info is :', info1, info2
    print status
    m = 1 / 0


status = 0

def old_schedul():
    schedule = sched.scheduler(time.time, time.sleep)  # 第一个参数是时间戳函数，第二个是阻塞函数
    schedule.enter(3, 0, func, ('test1', time.time()))  # 第一个延迟时间， 第二个有限级别
    schedule.run()  # 会陷入阻塞，
    # 定时器,
    print
    res = Timer(5, func2, ('test3', time.time())).start()
    res = Timer(4, func2, ('test2', time.time())).start()  # 异步执行即任务是同步执行的
    status = 1
    time.sleep(6)
    status = 5
    print status

def task1():
    print 'hello world'

def task2():
    print 'hello boy'

#基本的周期任务
def quartz():
    scheduler = BlockingScheduler()
    scheduler.add_job(task1, 'cron', second='2/3', hour='*', minute='*') #*立即开始,0也是立即开始
    try:
        scheduler.start()
    except (KeyboardInterrupt,SystemExit) as ex:
        print 'Exception occurence: ' , ex
        scheduler.shutdown()

#注解方式

def quartz2():
    scheduler = BackgroundScheduler()
    scheduler.add_job(task1, 'cron', second='0',hour='*', minute='1/5')
    try:
        print 'task start now:', time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
        scheduler.start()
    except (KeyboardInterrupt, SystemExit) as ex:
        print 'exception , restart:', ex
        scheduler.shutdown()
    print 'else task:'


def quartz3(func, argg):
    scheduler = BlockingScheduler()
    print 'canshu budui', func
    scheduler.add_job(func, 'cron',kwargs={'argg':argg}, second='0', hour='*', minute='1/5')
    while True:
        try:
            print 'task start now:', time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
            scheduler.start()
        except (KeyboardInterrupt, SystemExit) as ex:
            print 'exception , restart:', ex
            scheduler.shutdown()

def worker(argg):
    print 'this is a worker', argg

def timer():
    threading.Thread(target=quartz3, args=(worker,'transfer'), name='thread1').start()

def nonblockingQuartz():
    scheduler = BackgroundScheduler()
    scheduler.add_job(task2, 'cron', second='2/3',hour='*')
    scheduler.start()

if __name__ == '__main__':
    # nonblockingQuartz()
    # print 'main start'
    # quartz()
    # print 'main end'
    # quartz3()
    print 'main start'
    timer()
    print 'main end'