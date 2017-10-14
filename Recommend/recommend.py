import sys
import math
from texttable import Texttable

#Y用户亲近距离
def getCosDict(user1, user2):
    sum_x = 0.0
    sum_y = 0.0
    sum_xy = 0.0
    for key1 in user1:
        for key2 in user2:
            if key1[0] == key2[0]:
                sum_x += key1[1] * key1[1]
                sum_y += key2[1] * key2[1]
                sum_xy += key1[1] * key2[1]
    if sum_xy == 0.0:
        return 0.0
    demo = math.sqrt(sum_x * sum_y)
    return  sum_xy / demo

def readFile(filename):
    contents = []
    f = open(filename,'r')
    contents = f.readlines()
    f.close()
    return contents


def getRatingInfo(ratings):
    rates = []
    for line in ratings:
        rate = line.split(",")
        rates.append([int(rate[0]), int(rate[1]), float(rate[2])])
    return rates


def getUserScoreDataStructure(rates):
    userDict = {}
    itemUser = {}
    for k in rates:
        user_rank = (k[1],k[2])
        if k[0] in userDict:
            userDict[k[0]].append(user_rank)
        else:
            userDict[k[0]] =[user_rank]
        if k[1] in itemUser:
            itemUser[k[1]].append(k[0])
        else:
            itemUser[k[1]] = [k[0]]
    return userDict,itemUser


def getNearestNeighbor(userId, userDIct, itemUser):
    neighbors = []
    #print(itemUser)
    for item in userDIct:
        if itemUser.keys().__contains__(item) == False:
            continue
        if item == 400:
            break
        print("item:{0},list:".format(item))#itemUser[item]
        for neighbor in itemUser[item]:
            if neighbor != userId and neighbor not in neighbors:
                neighbors.append(neighbor)
    neighbors_dict = []
    for neighbor in neighbors:
        dist = getCosDict(userDIct[userId],userDIct[neighbor])
        neighbors_dict.append([dist, neighbor])
    neighbors_dict.sort(reverse=True)
    return neighbors_dict


def recommendByUserFC(filename, userId, k = 5):
    #影-人-分list字符串
    contents = readFile(filename)
    #用户id-电影id-评分列表
    rates = getRatingInfo(contents)
    userDict, itemUser = getUserScoreDataStructure(rates)
    neighbors = getNearestNeighbor(userId, userDict,itemUser)[:5]

    #推荐字典
    recommend_dict = {}
    for neighbor in neighbors:
        neighbor_user_id = neighbor[1]
        movies = userDict[neighbor_user_id]
        for movie in movies:
            if movie[0] not in recommend_dict:
                recommend_dict[movie[0]] = neighbor[0] #喜欢者之间距离总和
            else:
                recommend_dict[movie[0]] += neighbor[0]
    recommend_list = []
    for key in recommend_dict:
        recommend_list.append([recommend_dict[key], key])
    #距离越小越靠前
    recommend_list.sort(reverse=True)
    user_movies = [ k[0] for k in userDict[userId]]
    return [k[1] for k in recommend_list], user_movies, itemUser, neighbors


def getUserDictAndItemUser(filename):
    # 影-人-分list字符串
    contents = readFile(filename)
    # 用户id-电影id-评分列表
    rates = getRatingInfo(contents)
    userDict, itemUser = getUserScoreDataStructure(rates)
    return userDict, itemUser

def recommendByUserFC2(userDict,itemUser, userId, k = 5):
    neighbors = getNearestNeighbor(userId, userDict,itemUser)[:5]
    #推荐字典
    recommend_dict = {}
    for neighbor in neighbors:
        neighbor_user_id = neighbor[1]
        movies = userDict[neighbor_user_id]
        for movie in movies:
            if movie[0] not in recommend_dict:
                recommend_dict[movie[0]] = neighbor[0] #喜欢者之间距离总和
            else:
                recommend_dict[movie[0]] += neighbor[0]
    recommend_list = []
    for key in recommend_dict:
        recommend_list.append([recommend_dict[key], key])
    #距离越小越靠前
    recommend_list.sort(reverse=True)
    user_movies = [ k[0] for k in userDict[userId]]
    return [k[1] for k in recommend_list], user_movies, itemUser, neighbors



def getMovieList(filename):
    #电影名|电影版本|...其他信息的list
    contentes = readFile(filename)
    movies_info = {}
    for movie in contentes:
        # print("movie:{0}".format(movie))
        singel_info = movie.split(",")
        movies_info[int(singel_info[0])] = singel_info[1:]
    return movies_info

def printRecommend(movies, recommend_list):
    table = Texttable()
    table.set_deco(Texttable.HEADER)
    table.set_cols_dtype(['t', 't', 't'])
    table.set_cols_align(['l', 'l', 'l'])
    rows = []
    rows.append(['movie name', 'type', 'userid'])
    i = 0
    for movie_id in recomend_list:
        rows.append([movies[movie_id][0], movies[movie_id][1], '{0}'.format(math.floor(i / 20) + 1)])
        i = i + 1
    table.add_rows(rows)
    print(table.draw())
    result = open('D:\\tool\ml-20m\\weRecommend3.csv', 'w+')
    result.write(table.draw())

if __name__ ==  '__main__':
    movies = getMovieList("D:\\tool\ml-20m\\movies.csv".encode("utf-8"))
    # recomend_list, user_movie,item_user, neighbors = recommendByUserFC("D:\\tool\\ml-20m\\mus.csv".encode("utf-8"),50,80)
    userDict, itemUser = getUserDictAndItemUser("D:\\tool\\ml-20m\\mus.csv")
    recomend_list1 = []
    for i in range(1,10):
        recomend_list, user_movie, item_user, neighbors = recommendByUserFC2(userDict, itemUser, i, 80)
        recomend_list1.extend(recomend_list[:20])
        print(recomend_list1)
    printRecommend(movies, recomend_list1)
    # recomend_list, user_movie, item_user, neighbors = recommendByUserFC2( userDict,itemUser,50, 80)
    # neighbors_id = [ i[1] for i in neighbors]

    #另一种打印
    # print('User:{0}, We Recommend:{1}'.format(50, recomend_list))