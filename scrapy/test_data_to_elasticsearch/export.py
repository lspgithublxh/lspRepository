from pymongo import MongoClient
import json

client = MongoClient('192.168.1.152', 27017)

col3 = client['lsp_test']['vul3']
def export_db():
    cur = col3.find({})
    f = None
    a = 0
    for re in cur:
        # t = str(re).replace('u\'','\'')
        # print re['_source']['vulnerability']['vulName']
        text = json.dumps(re, ensure_ascii=False,indent=2)
        print text.replace('\n','')

        # f.write(t)
        # f.write('\r')
        a = a + 1
        print a


if __name__ == '__main__':
    export_db()
