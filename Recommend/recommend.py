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
        rate = line.split("\t")
        rates.append([int(rate[0]), int(rate[1]), int(rate[2])])
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
    for item in userDIct:
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
    contents = readFile(filename)
    #用户-电影-评分列表
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


def getMovieList(filename):
    contentes = readFile(filename)
    movies_info = {}
    for movie in contentes:
        singel_info = movie.split("|")
        movies_info[int(singel_info[0])] = singel_info[1:]
    return movies_info


if __name__ ==  '__main__':
    movies = getMovieList("u.item")
    recomend_list, user_movie,item_user, neighbors = recommendByUserFC("u.data",50,80)
    neighbors_id = [ i[1] for i in neighbors]
    table = Texttable()
    table.set_deco(Texttable.HEADER)
    table.set_cols_dtype(['t','t','t'])
    table.set_cols_align(['l','l','l'])
    rows = []
    rows.append(['movie name','release'])
    for movie_id in recomend_list[:20]:
        rows.append([movies[movie_id][0], movies[movie_id][1]])
    table.add_rows(rows)
    print(table.draw())
    #另一种打印
    print('User:{0}, We Recommend:{1}'.format(50, recomend_list))