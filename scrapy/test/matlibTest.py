#coding=utf-8
import matplotlib.pyplot as plt
import matplotlib.dates as mdates
import numpy as np
import datetime

def drawSin():
    t = np.arange(0.0, 2.0, 0.01)
    s = np.sin(2 * np.pi * t)
    fig, ax = plt.subplots()
    ax.plot(t, s)
    ax.set(xlabel='time(s)', ylabel='voltage(v)', title='About as simple as it gets, folks')
    ax.grid()
    fig.savefig('test.png')
    # fig.autofmt_xdate()
    plt.show()

#文本倾斜
def drawTime():
    fig, ax = plt.subplots()
    t = ['abc','def','nfg']
    s = [3,4,5]
    ax.plot(t, s)
    ax.set(xlabel='t', ylabel='v', title='show t-v')
    ax.grid()
    for label in ax.get_xticklabels():
        label.set_rotation(30)
        label.set_horizontalalignment('right')
    plt.show()

def getLogData():
    t = []
    s = []
    count = 0
    with open('D:\\test\\scrapy\\trace.log', 'r') as f:
       while True:
           str = f.readline()
           if str == '':break
           if str == '\n':
               continue
           if str == None:break
           arr = str.split(',')
           if arr[0] == '':continue
           # print arr[1][0:-1]
           s.append(int(arr[0]))
           t.append(datetime.datetime.strptime(arr[1][0:-1], '%Y-%m-%d %H:%M:%S'))
           # if count % 20 == 0:
           #     t.append(arr[1][0:-1])
           # else:
           #     t.append(arr[1][0:-1])
           count = count + 1
    draw_only(t, s)
    # draw_bar(t, s)

def draw_only(t, s):

    fig, ax = plt.subplots()

    ax.set(xlabel='datetime', ylabel='num', title='show time-num trend')
    # ax.xaxis_date()
    ax.xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d %H:%M:%S'))
    # ax.plot(t, s)
    ax.plot_date(t,s,'-')
    ax.grid()
    for label in ax.get_xticklabels():
        label.set_rotation(30)
        label.set_horizontalalignment('right')
    plt.ylim(-100, 100)
    fig.savefig('shordan.png')
    plt.show()

    #试图画第二张图

#柱状图横坐标是一般的，不是日期。没有日期类型
def draw_bar(t, s):
    plt.bar(t, s ,facecolor='#9999ff', edgecolor='white')
    plt.show()


if __name__ == '__main__':
    # drawSin()
    # drawTime()
    getLogData()