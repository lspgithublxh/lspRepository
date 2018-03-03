import pymysql
import pprint

def connect_to_mysql():
    con = pymysql.connect(host='192.168.1.152', user='root', password='Isas.123456',db='isas',port=3306, charset='utf8')
    cur = con.cursor()
    cur.execute('select count(1) from b_city')
    data = cur.fetchall()
    print data
    pprint.pprint(data)




if __name__ == '__main__':
    connect_to_mysql()
