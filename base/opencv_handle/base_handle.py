#coding=utf-8
#显示图片
import cv2 as cv
img = cv.imread('1.jpg')
cv.namedWindow("Image")
cv.imshow("Image", img)
cv.waitKey(0)
cv.destroyAllWindows()