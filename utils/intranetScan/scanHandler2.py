#coding=utf-8
import os
import re
import time
import sys
import subprocess

lifeline = re.compile(r"^.*?TTL\=.+?")
report = ("No response", "Partial Response", "Alive")
lis = []
print time.ctime()

for host in range(1, 255):
    ip = "10.252.24." + str(host) #
    # pingaling = subprocess.Popen(["ping ", "-q", "-c 2", "-r", ip], shell=False, stdin=subprocess.PIPE,
    #                              stdout=subprocess.PIPE) ["ping", '-n 1 ' ,'-w 1', "-a", ip]
    pingaling = subprocess.Popen("ping {} -n 1 -w 1000 ".format(ip), shell=False, stdin=subprocess.PIPE,
                                 stdout=subprocess.PIPE)
    # print "Testing ", ip,
    status = 0
    while 1:
        pingaling.stdout.flush()
        line = pingaling.stdout.readline()
        try:
            line = line.decode('GB2312').encode('utf-8')
        except UnicodeDecodeError as e:
            try:
                line = line.decode('GBK').encode('utf-8')
            except UnicodeDecodeError as e:
                line = line.decode('utf-8')
        if not line: break
        print line
        if line.__contains__('TTL'):
            print ip
            lis.append(ip)
            break
        elif line.__contains__('请求超时'):
            break
        # igot = re.findall(lifeline, line)
        # print line
        # if igot.__len__() > 0 and status == 0:
        #     print ip
        #     status = 1

print time.ctime()

print lis