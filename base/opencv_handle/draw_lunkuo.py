#coding=utf-8
# import numpy as np

# import matplotlib.pyplot as plt

# from skimage import measure,draw

import cv2

img = cv2.imread("1.jpg")
h, w = img.shape[:2]
cv2.imshow("Origin", img)

#滤波
blured = cv2.blur(img, (5,5))
cv2.imshow("Blur", blured)

#灰度图
gray = cv2.cvtColor(blured, cv2.COLOR_BGR2GRAY)
cv2.imshow("gray", gray)
#开闭运算
kernel = cv2.getStructuringElement(cv2.MORPH_RECT,(50,50))
opened = cv2.morphologyEx(gray, cv2.MORPH_OPEN, kernel)
closed = cv2.morphologyEx(opened, cv2.MORPH_CLOSE, kernel)
cv2.imshow("closed", closed)

#轮廓提取, 二值图
ret , binary = cv2.threshold(closed, 250,255,cv2.THRESH_BINARY)
cv2.imshow("binary", binary)
_, contours, hierarchy = cv2.findContours(binary, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

cv2.drawContours(img, contours, -1, (0,0,255), 3)
cv2.imshow("result", img)
cv2.waitKey(0)
cv2.destroyAllWindows()