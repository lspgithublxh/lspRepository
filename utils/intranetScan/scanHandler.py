#coding=utf-8
import os, sys,subprocess, re
reload(sys)
sys.setdefaultencoding('UTF-8')

s = os.system('ping -c 1 -w 1 10.252.24.248') #1次报文c，一次等待w
print s
# p = subprocess.Popen(['ping.exe', 'www.google.com'], stdin = subprocess.PIPE,stdout = subprocess.PIPE,stderr = subprocess.PIPE, shell=True)
# out = p.stdout.read()
# regex = re.compile("Minimum = (\d+)ms, Maximum = (\d+)ms, Average = (\d+)ms", re.IGNORECASE)
# print regex.findall(out)