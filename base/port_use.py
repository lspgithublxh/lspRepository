# coding=utf-8
# encoding=utf8
import socket, platform, time, thread
print platform.platform()
print platform.version()
print platform.architecture()
print platform.machine()
print platform.processor()
print platform.uname()

print '----------------'
print platform.python_build()
print platform.python_compiler()
print platform.python_revision()
print platform.python_version()

socket.setdefaulttimeout(3)
def socket_connect(ip, port):
    soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    result = soc.connect_ex((ip, port))
    if result == 0:
        lock.acquire()
        print '端口已经占用:',ip,':',port
        lock.release()


def ip_scan(ip):
    try:
        time1 = time.time()
        for i in range(64400):
            thread.start_new_thread(socket_connect, (ip, int(i)))
        time2 = time.time()
        print '扫描耗时：',time2 - time1
    except Exception :
        print 'exception while ip_scan'


if __name__ == '__main__':
    import sys
    print sys.argv
    reload(sys)
    sys.setdefaultencoding('utf8')
    print sys.getdefaultencoding()

    ip = raw_input('input ip that you want to scan:')
    lock = thread.allocate_lock()
    ip_scan(ip)