#coding=utf-8
from pymongo import  MongoClient

def lintoMongo():
    client = MongoClient('127.0.0.1',27017)
    print client.database_names()
    db = client.get_database('test')
    col2 = db.runoob
    col2.find_one()
    col = db.create_collection('book')
    doc = {'name':'java', 'price':34.34, 'author':'lishaoping'}
    res = col.insert_one(doc)
    id = res.inserted_id
    print col2.count()

if __name__ == '__main__':
    lintoMongo()
