#coding=utf-8
from pyspark import SparkContext, SparkConf
import os
import re

os.environ['HADOOP_HOME'] = "D:\download\spark-2.3.0-bin-hadoop2.7\spark-2.3.0-bin-hadoop2.7\HADOOP_HOME"
sc = SparkContext("local", "Page Rank")

#文件中读取,构成RDD  PythonRDD
distFile = sc.textFile("README.txt")
distFile.persist().cache()
#文本长度
print distFile.map(lambda line : len(line)).reduce(lambda a, b : a + b)
#单词个数
print distFile.map(lambda line : len(re.split("\s+", line))).reduce(lambda a, b : a + b)
#分割统计, reduceBykey、sortByKey要求在(key,val)对上进行
print distFile.flatMap(lambda line : re.split("\s+", line)).map(lambda word : (word, 1)) \
        .reduceByKey(lambda count1 , count2: count1 + count2).map(lambda tu : (tu[1], tu[0])).sortByKey(ascending=False).collect()



