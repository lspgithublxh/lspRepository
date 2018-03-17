#coding=utf-8
import sys

f = open('D:\\log2.log','r') #r只读 , w只写, a追加, r+读写    b二进制读写需要加
while True:
    line = f.readline()
    print line,
    if line is None or line == '':
        break
f.close()

print '-' * 40
f = open('D:\\log2.log','r')
while True:
    lines = f.readlines(10)  #高效读取文件
    print lines
    if len(lines) == 0:
        break
f.close()

print '*' * 40
f = open('D:\\log2.log','r')
for line in f:
    print line,

f = open('D:\\python.log','w')#会自动创建文件
print f.write(str(('a bc', 40))) #原样输出
f = open('D:\\python4.log','a')
print f.write('just test file handle')

f = open('D:\\log2.log','r')
f.seek(3) #文件指针移动
print f.readline()

#自动用完关闭文件：
with open('D:\\python.log','r') as f:
    while True:
        line = f.readline()
        print line,
        if line == '':
            break

