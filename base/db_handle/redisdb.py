#coding=utf-8
#https://github.com/ServiceStack/redis-windows/blob/master/downloads/redis64-3.0.501.zip
#http://download.redis.io/releases/
#https://github.com/MicrosoftArchive/redis/releases
import sys
import redis



def baseTest():
    r = None
    try:
        pool = redis.ConnectionPool(host='localhost', port=6379, decode_responses=True)
        r = redis.Redis(connection_pool=pool)  # host='localhost',port=6379, db=0
    except Exception as e:
        print e
    # r.pubsub()
    ids = []
    for id in ids:
        print 'start'
        r.lpush('detail', id)
        print '---'
        print 'put'
    # i = ids.__len__()
    # j = 0
    # while j < i:
    #     j += 1
    #     print 'xxx'
    #     print r.rpop('detail')

if __name__ == '__main__':
    baseTest()
    pass
