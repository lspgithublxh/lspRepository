import copy

obj1 = {'name':'lishaoping', 'age':23}
obj2 = obj1.copy()
obj2['name'] = 'lixiaohai'

print obj1, obj2


def globalVari():
     co = abc.copy()
     co['name'] = 'lixiaohai'
     print co

def extend(map1, map2):
    for key in map2.iterkeys():
        if key != None:
            map1[key] = map2[key]



if __name__ == '__main__':
    global abc
    abc = {'name':'lishaoping', 'age':23}
    print abc
    globalVari()
    obj = {'abc':'s'}
    rmi = {'address':'Beijingshi haidianqu'}
    rmi.iterkeys()
    extend(obj, abc)
    print obj
    extend(obj, rmi)
    print obj
