class Student:
    def __init__(self,name,age):
        self.name = name
        self.age = age
    def getNameAndAge(self):
        print("this one 's name :{0},{1}".format(self.name,self.age))
