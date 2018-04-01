#coding=utf-8
import matplotlib.pyplot as plt
import numpy as np
x = np.linspace(0, 5, 100)

y = 2 * np.sin(x) + 0.3 * x ** 2
plt.figure('model')
plt.plot(x, y)
plt.show()