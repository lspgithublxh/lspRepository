#coding=utf-8
from pyspark import SparkContext
import os
import sys
os.environ['SPARK_HOME'] = "D:\download\spark-2.3.0-bin-hadoop2.7\spark-2.3.0-bin-hadoop2.7"
os.environ['JAVA_HOME'] = "D:\Program Files\Java\jdk1.8.0_151"
sys.path.append("D:\download\spark-2.3.0-bin-hadoop2.7\spark-2.3.0-bin-hadoop2.7\python")
os.environ['HADOOP_HOME'] = "D:\download\spark-2.3.0-bin-hadoop2.7\spark-2.3.0-bin-hadoop2.7\HADOOP_HOME"

sc = SparkContext("local", "Simple App")
from numpy import array
from pyspark.mllib.clustering import BisectingKMeans
data = array([0.0,0.0, 1.0,1.0, 9.0,8.0, 8.0,9.0]).reshape(4, 2)
bskm = BisectingKMeans()
model = bskm.train(sc.parallelize(data, 2), k=4)
p = array([0.0, 0.0])
print model.predict(p)
print model.k
print model.computeCost(p)