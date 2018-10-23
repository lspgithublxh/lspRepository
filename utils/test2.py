#coding=utf-8
# indate = raw_input()
# dict = {}
# len = len(indate) #
# for i in range(len):
#     if indate[i] not in dict:
#         dict[indate[i]] = 1
#     else:
#         dict[indate[i]] += 1
#
# dict = sorted(dict.items(), key=lambda x: x[1], reverse=True)
# for key in dict:
#     print key[0]

# def most_three(inputt_str):
#     count_dict = {}
#     for ch in inputt_str:
#         if ch not in count_dict:
#             count_dict[ch] = 0
#         count_dict[ch] += 1
#     letter_set = ''
#     for i in range(3):
#         max_v = max(count_dict.values())
#         for ch in count_dict:
#             if count_dict[ch] == max_v:
#                 letter_set += ch
#                 count_dict.pop(ch)
#     return letter_set

# if __name__ == '__main__':
#     most_three('sdfsfsdfsdfsdf')


# class Solution(object):
#   def count(self,string):
#     array=list(string)
#     string_dict={}
#     register_array=[]
#     for i in range(len(array)):
#       string_dict[array[i]]=string_dict[array[i]]+1
#     f=zip(string_dict.values(),string_dict.keys())
#     sorted(f)
#     return (f)
# a=Solution().count('asazzzsdde')
#
#
# def solution(array, target):
#     res = []
#     current = []
#     helper(array, current, res, target)
#     return len(res)
#
# global tem
# tem = -1
#
# def helper(array, current, res, target):
#     global tem
#     if len(current) == len(array) - 1:
#         tem = get_result(array, current)
#     if tem == target:
#         res.append(current)
#     return
#
#     current.append('+')
#     helper(array, current, res, target)
#     current.pop()
#     current.append('-')
#     helper(array, current, res, target)
#
#
# def get_result(array, ops):
#     size = len(array)
#     i = 0
#     j = 0
#     sum = array[0]
#     while i < size - 1:
#         if ops[j] == '+':
#             sum += array[i + 1]
#         else:
#             sum -= array[i + 1]
#
#
#         i += 1
#     j += 1
#     return sum
#
# if __name__ == '__main__':
#     print solution([1,1,1,1,1], 3)


# class Solution():
#     def findplan(self, arr, s):
#         ans = 0
#         self.dfs(arr, 0, s, ans, 0)
#         return ans
#
#     def dfs(self, arr, index, s, ans, pre):
#         if index >= len(arr):
#             return
#         if pre + arr[index] == s or pre - arr[index] == 0:
#             ans += 1
#             return
#         else:
#             self.dfs(arr, index + 1, s, pre + arr[index])
#             self.dfs(arr, index + 1, s, pre - arr[index])
#
#
# print Solution().findplan([1,1,1,1,1], 3)


# def assign(num_list, S):
#     if len(num_list) == 0:
#         return 0;
#
#
#     if len(num_list) == 1:
#         if num_list[0] == S:
#             return 1
#         else:
#             return 0
#         return assign(num_list[:-1], S + num_list[-1]) + assign(num_list[:-1], S - num_list[-1])
#
# print assign([1,1,1,1,1], 3)
#
# import numpy as np
#
# A = [1, 1, 1, 1, 1]
# S = 3
# all_sign = []
#
# def signall():
#
#     for m in range((np.shape(A)[0] - 1) ** 2):
#         sign = []
#         sign.append(1)
#         for i in [1, -1]:
#             sign.append(i)
#             for j in [1, -1]:
#                 sign.append(j)
#                 for k in [1, -1]:
#                     sign.append(k)
#                     for l in [1, -1]:
#                         sign.append(l)
#                 all_sign.append(sign)
#         return all_sign
#
#
# all_sum = all_sign * A
# count = 0
# for i in range(4 ** 2):
#     if all_sum[i] == 3:
#         count += 1
#
#     print(count)

#
# def distribute(nums):
#     if len(nums) == 1:
#         if nums[0] == s:
#             return 1
#         else:
#             return 0
#     else:
#         return distribute(nums[1:],s + nums[0]) + distribute(nums[1:], s - nums[0])
#
# while 1:
#     try:
#         nums = map(int, raw_input().split())
#         s = input
#         print distribute(nums, s)
#
#
#     except:pass
#     break

A = [1, 1, 1, 1, 1]
S = 3

def cnt_res(A, S, f):
    if f >= pow(2, len(A)):
        return 0


    sum = A[0]
    tem = f
    for i in range(len(A)):
        if tem % 2 == 0:
            sum += A[i]
        else:
            sum -= A[i]
        tem = tem / 2

        if S == sum:
            return 1 + cnt_res(A, S, f + 1)
        else:
            return cnt_res(A, S, f + 1)


print cnt_res(A, S,3)