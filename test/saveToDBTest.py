#coding=utf-8
from pymongo import  MongoClient
import pprint

def lintoMongo():
    client = MongoClient('127.0.0.1',27017)
    print client.database_names()
    db = client.get_database('test')
    col2 = db.runoob
    col2.find_one()
    col = db.create_collection('book')
    doc = {'name':'c', 'price':34.34, 'author':'lishaoping'}
    res = col.insert_one(doc)
    id = res.inserted_id
    print col2.count()

def insetToMongo():
    client = MongoClient("127.0.0.1",27017)
    res = client['test']['book'].insert_many([{'name':'c', 'price':34.34, 'author':'lishaoping2'},
                                                     {'name': 'python', 'price': 24.34, 'author': 'lishaoping3'},
                                                     {'name': 'javascript', 'price': 14.34, 'author': 'lishaoping4'}])
    pprint.pprint(res.inserted_ids)

def insertToMongo(docs, col, db):
    client = MongoClient('127.0.0.1', 27017)
    client[db][col].insert_many(docs)


def queryMongo():
    client = MongoClient('127.0.0.1',27017)
    doc = client.get_database('test').book.find_one()
    print pprint.pprint(doc)
    print doc['author']
    db = client['local']
    doc = db['startup_log']
    for obj in doc.find():
        print obj['hostname']
    #查询单个字段
    ddd = client['test']['book'].find({"price": {"$gt":23}})
    for obj in ddd:
       print pprint.pprint(obj)
    #单个字段
    res = client['test']['book'].find({"price": {"$gt": 23}}).sort([('name',1)]).distinct('name')
    print res
    #总之集合操作非常丰富


if __name__ == '__main__':
    # lintoMongo()
    queryMongo()
    # insetToMongo()