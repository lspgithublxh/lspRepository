#coding=utf-8
import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
# 生成同时包含名称和速度的标签
fig = plt.figure('Bar chart & Pie chart')

# 在整张图上加入一个子图，121的意思是在一个1行2列的子图中的第一张
ax = fig.add_subplot(121)
speed_map = {
    'dog': (48, '#7199cf'),
    'cat': (45, '#4fc4aa'),
    'cheetah': (120, '#e1a7a2')
}
animals = speed_map.keys()

# 奔跑速度
speeds = [x[0] for x in speed_map.values()]
labels = ['{}\n{} km/h'.format(animal, speed) for animal, speed in zip(animals, speeds)]
# 对应颜色
colors = [x[1] for x in speed_map.values()]
# 画饼状图，并指定标签和对应颜色
ax.pie(speeds, labels=labels, colors=colors)
plt.show()
