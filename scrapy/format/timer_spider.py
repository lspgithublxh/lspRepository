#coding=utf-8
import  threading
from apscheduler.schedulers.blocking import BlockingScheduler
from apscheduler.schedulers.background import BackgroundScheduler
import time
from main_transfer_format import getFromMongo

def quartz3():
    scheduler = BlockingScheduler()
    print 'canshu budui', getFromMongo
    scheduler.add_job(getFromMongo, 'cron', second='0', hour='1/12', minute='1')
    while True:
        try:
            print 'task start now:', time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
            scheduler.start()
        except (KeyboardInterrupt, SystemExit) as ex:
            print 'exception , restart:', ex
            scheduler.shutdown()


def timer_():
    threading.Thread(target=quartz3, args=(), name='thread1').start()