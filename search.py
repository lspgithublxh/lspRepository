#0 其他python文件的引入,引入类的方式也可以
import util
# from util import Student
#1.控制台输出
print("hello world")
#2.算数运算
x = 4
y = 6
z = x + y
m = y % x
print(z)
print(m)
#3.字符串处理
#4.流程控制
# coding = utf-8
score = 100
if score > x * y * 10:
    print("优秀")
elif score > x * y * 5:
    print("好")
elif 1 > 0:
    print("正确")
#4.2 字符串拼接和循环       空格是必须的，代表内容体内操作，而不是主线流程操作,体外操作...体内和体外，层层嵌套
for i in range(0,4):
    print("Output : {0}, {1}".format(i,"number"))
print("out of for clause")
#5.函数定义和调用
def printHello():
    print("helo ")
def compute(x, y):
    return x + y
printHello()
print(compute(3,7))
# 6.类的定义和实例化和实例方法调用：
class User:
    x = 0
    y = 1
    def __init__(self, name):
        self.name = name
        print("实例化")
    def getUserName(self):
        return self.name
    def getInnerResult(self):
        return self.x + self.y #必须使用self，否则访问不到值，就没有值
user = User("lishaoping")
print(user.getUserName())
print(user.getInnerResult())

# 6.2 继承
print("-----------------------")
class SUBUser(User):
    def __init__(self,name):
        User.__init__(self,name)
    def subUserOutputInfo(self):
        print("yes, this is sub user:{0}".format(self))
        print("user name:{0}, {1}".format(self.name,self.y))
subUser = SUBUser("lixiaohai")
print(subUser.getInnerResult())
subUser.subUserOutputInfo()
print("-----------------------")

# 7.调用其他文件里的类
student = util.Student("lsp",25)
student.getNameAndAge()
print("----anything else===========")

