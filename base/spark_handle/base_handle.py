#coding=utf-8
from pyspark import SparkContext, SparkConf
import os

os.environ['HADOOP_HOME'] = "D:\download\spark-2.3.0-bin-hadoop2.7\spark-2.3.0-bin-hadoop2.7\HADOOP_HOME"

sc = SparkContext("local", "Page Rank")
data = [1,2,3,4,5]
#创建分布式数据集RDD
distData = sc.parallelize(data)
#求和
print distData.reduce(lambda  a, b: a + b)