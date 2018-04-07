#coding=utf-8
import cv2
cap = cv2.VideoCapture(0)
i=0
while(1):
    ret, frame = cap.read()
    img_gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    img_gb = cv2.GaussianBlur(img_gray, (5, 5), 0)
    edges = cv2.Canny(img_gb, 100 , 200)
    cv2.imshow("capture", edges)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
cap.release()
cv2.destroyAllWindows()