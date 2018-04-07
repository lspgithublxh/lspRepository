#coding=utf-8
import cv2
faceCascade = cv2.CascadeClassifier(r'../haarcascade_frontalface_default.xml')
image = cv2.imread(r"../face1.jpg")

gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
faces = faceCascade.detectMultiScale(

gray,

scaleFactor=1.1,

minNeighbors=5,

minSize=(30, 30)

# flags = cv2.CV_HAAR_SCALE_IMAGE

)

for (x, y, w, h) in faces:

    cv2.rectangle(image, (x, y), (x+w, y+h), (0, 255, 0), 2)

cv2.imshow("Faces found" ,image)

cv2.waitKey(0)