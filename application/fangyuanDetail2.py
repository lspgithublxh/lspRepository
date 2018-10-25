#coding=utf-8
import threading
from fangyuanSel import bro

count = {'count':10}
event = threading.Event()
lock = threading.Lock()

def threadinghandle(url, handle):
    print url
    #panduan
    lock.acquire()
    bro.switch_to_window(handle)
    bro.get(url)
    count['count'] -= 1
    if count['count'] == 0:
        event.set()
        count['count'] = 10
    lock.release()

def begainGet():
    ids = ['101103481530','101102880959','101102880981','101102881021']
    i = 1
    amount = ids.__len__()
    c = 10
    if amount < 10:
        c = amount

    while i < c:
        bro.execute_script('window.open("https://www.baidu.com")')
        i +=1
    allhandles = bro.window_handles
    url = 'https://bj.ke.com/ershoufang/{}.html'
    j = 0
    while j < amount:
        turn = ids[j:j+c]
        k = 0
        for handle in allhandles:
            one = threading.Thread(target=threadinghandle, args=(url.format(turn[k]),handle))
            one.start()
            k += 1
        event.wait()
        for handle in allhandles:
            bro.switch_to_window(handle)
            print bro.title
        j += c
    if j < amount:#不用线程, 一个一个取
        le = ids[j:]
        for ll in le:
            bro.get(url.format(ll))
            print bro.title

if __name__ == '__main__':
    # begainGet()
    pass