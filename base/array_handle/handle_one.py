#coding=utf-8
def one():
    for i in range(1,10):
        print i


if __name__ == '__main__':
    one()
    a = [1,2,3,4]
    print a.__len__()
    dict = {'name':'hhh', 'age':23, 'hh':None}
    for (key, val) in enumerate(dict):
        print(str(key) + " " + str(val))
    print(dict)
    for obj in dict.items():
        k, v = obj
        print(k + str(v))
        if v is None:
            del dict[k]

    print(dict)
    print('*' * 30)





