from email.mime.text import MIMEText
#构造邮件
msg = MIMEText('hello, send by Python','plain','UTF-8')

#发送邮件
import smtplib
clientA = r'471126124lxh@sina.com'
clientB = '1913796030@qq.com'
server = smtplib.SMTP('smtp.sina.com',25)
server.set_debuglevel(1)
server.login(r'471126124lxh',r'1qaz2wsx3edc')
server.sendmail(clientA,[clientB],msg.as_string())
server.quit()

print('--------------------')

server2 = smtplib.SMTP('smtp.qq.com',25)
server2.set_debuglevel(1)
server2.login(clientB,'1234567890lsp')
server2.sendmail(clientB,[clientB],msg.as_string())
server2.quit()