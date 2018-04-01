#coding=utf-8
import qrcode
import matplotlib.pyplot as plt
img=qrcode.make("please input some data here")
img.save("D:/Some.png")

plt.figure('erweima')
little_dog_img = plt.imread('D:/Some.png')
plt.imshow(little_dog_img)


plt.show()
