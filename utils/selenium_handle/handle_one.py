#coding=utf-8
from  selenium import webdriver

bro = webdriver.Chrome(executable_path="C:\Program Files (x86)\Google\Chrome\Application\chromedriver.exe")
bro.get("http://www.baidu.com")

#base handle
#事件模拟和数据获取
input = bro.find_element_by_id('kw')
input.send_keys('java')
button = bro.find_element_by_id('su')
button.click()
print bro.page_source


print bro.page_source