#coding=utf-8
import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt

fig = plt.figure('Bar chart & Pie chart')

# 在整张图上加入一个子图，121的意思是在一个1行2列的子图中的第一张
ar = [48, 45, 120]
ax = fig.add_subplot(121)
# speed_map = {
#     'dog': (ar[0], '#7199cf'),
#     'cat': (ar[1], '#4fc4aa'),
#     'cheetah': (ar[2], '#e1a7a2')
# }

speed_map ={'8.6':(1532,'#7199cf'), '8.7':(2877,'#4fc4aa'), '8.2':(7,'#4ee4aa'), '8.4':(4,'#3399cf'), '8.5':(229,'#7559cf')}
animals = speed_map.keys()

# 奔跑速度
speeds = [x[0] for x in speed_map.values()]
labels = ['{}\n{} version'.format(animal, speed) for animal, speed in zip(animals, speeds)]
# 对应颜色
colors = [x[1] for x in speed_map.values()]
# 画饼状图，并指定标签和对应颜色
ax.pie(speeds, labels=labels, colors=colors)
plt.show()
