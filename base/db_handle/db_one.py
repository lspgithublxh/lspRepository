#coding=utf-8
import pymysql

conn = pymysql.connect(host='localhost', port=3306, user='root', passwd='lsp', db='test', charset='utf8')
cursor = conn.cursor()
effect_row = cursor.execute("select * from abc")
print effect_row
rows = cursor.fetchall()
print rows
conn.commit()
cursor.close()
conn.close()