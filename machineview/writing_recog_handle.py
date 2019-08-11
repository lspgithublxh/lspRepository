#coding=utf-8
#https://blog.csdn.net/gaohuazhao/article/details/72886450 单独说明
import sys
import struct
import pip._internal
from tensorflow import Session, device, constant, matmul,Variable, add, initialize_all_variables\
    , assign, placeholder, float32, multiply, random_normal, zeros, nn, reduce_mean, reduce_sum, \
    square, train

import tensorflow as tf

import numpy as np
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2' #使得cpu计算效率更高

def method1():
    with Session() as session:
        with device('/cpu:0'):#/gpu:0 /gpu:1
            mat1 = constant([[3,3]])
            mat2 = constant([[2],[2]])
            product = matmul(mat1, mat2)
            result = session.run(product)
            print(result)


def method2():
    state = Variable(0, name='counter')
    one = constant(1)
    val = add(state, one)
    update = assign(state, val)
    setup = initialize_all_variables()
    with Session() as session:
        session.run(setup)
        print(session.run(setup))

#动态输入并运算
def method3():
    input1 = placeholder(float32)
    input2 = placeholder(float32)
    output = multiply(input1, input2)
    with Session() as session:
        print(session.run(output, feed_dict={input1:[2], input2:[3]}))

class BPNeuralNetwork:
    def __init__(self):
        self.session = Session()
        self.input_layer = None
        self.label_layer = None
        self.loss = None
        self.optimizer = None
        self.input_n = 0
        self.hidden_n = 0
        self.hidden_size = []
        self.output_n = 0
        self.hidden_layers = []
        self.output_layer = None


    def close(self):
        self.session.close()

    def __del__(self):
        self.session.close()

    # def setup(self, ni, nh, no):
    #     self.input_n = ni
    #     self.hidden_n = len(nh)
    #     self.hidden_size = nh
    #     self.output_n = no
    #     self.input_layer = placeholder(float32, [None, 1])#input_n
    #     self.output_layer = placeholder(float32, [None, 1])#self.output_n
    #     in_size = self.input_n
    #     out_size = self.hidden_size[0]
    #     inputs = self.input_layer
    #     self.hidden_layers.append(make_layer(inputs, in_size, out_size, activate=tf.nn.relu))
    #     for i in range(self.hidden_n - 1):
    #         in_size = out_size
    #         out_size = self.hidden_size[i+1]
    #         inputs = self.hidden_layers[-1]
    #         self.hidden_layers.append(make_layer(inputs, in_size, out_size,activate= nn.relu))
    #     self.output_layer = make_layer(self.hidden_layers[-1], self.hidden_size[-1], self.output_n)
    def setup(self, ni, nh, no):
        # 设置参数个数
        self.input_n = ni
        self.hidden_n = len(nh)  # 隐藏层的数量
        self.hidden_size = nh  # 每个隐藏层中的单元格数
        self.output_n = no
        # 构建输入层
        self.input_layer = tf.placeholder(tf.float32, [None, self.input_n])
        # 构建标签层
        self.label_layer = tf.placeholder(tf.float32, [None, self.output_n])
        # 构建隐藏层
        in_size = self.input_n
        out_size = self.hidden_size[0]
        inputs = self.input_layer
        self.hidden_layers.append(make_layer(inputs, in_size, out_size, activate=tf.nn.relu))
        for i in range(self.hidden_n - 1):
            in_size = out_size
            out_size = self.hidden_size[i + 1]
            inputs = self.hidden_layers[-1]
            self.hidden_layers.append(make_layer(inputs, in_size, out_size, activate=tf.nn.relu))
        # 构建输出层
        self.output_layer = make_layer(self.hidden_layers[-1], self.hidden_size[-1], self.output_n)

    # def train(self, cases, labels, limit=100, learn_rate=0.05):
    #     self.input_layer = placeholder(float32, [None,2])
    #     self.label_layer = placeholder(float32, [None,1])
    #     self.layers.append(make_layer(self.input_layer, 2, 10,activate=nn.relu))
    #     self.layers.append(make_layer(self.layers[0], 10, 2, activate=None))
    #     #yinhanceng
    #     self.loss = reduce_mean(reduce_sum(square(self.label_layer - self.layers[1]), reduction_indices=[1]))
    #     #youhuaqi
    #     self.optimizer = train.GradientDescentOptimizer(learn_rate).minimize(self.loss)
    #     initer = initialize_all_variables()
    #     #train start
    #     self.session.run(initer)
    #     for i in range(limit):
    #         self.session.run(self.optimizer, feed_dict={self.input_layer:cases, self.label_layer:labels})
    def train(self, cases, labels, limit=100, learn_rate=0.05):
        self.loss = tf.reduce_mean(tf.reduce_sum(tf.square((self.label_layer - self.output_layer)), reduction_indices=[1]))
        self.optimizer = tf.train.GradientDescentOptimizer(learn_rate).minimize(self.loss)
        initer = tf.initialize_all_variables()
        #做训练
        self.session.run(initer)
        for i in range(limit):
            self.session.run(self.optimizer, feed_dict={self.input_layer: cases, self.label_layer: labels})

    def predict(self, case):
        return self.session.run(self.output_layer, feed_dict={self.input_layer:case})

    def test(self):
        x_data = np.array([[0, 0], [0, 1], [1, 0], [1, 1]])
        y_data = np.array([[0, 1, 1, 0]]).transpose()
        test_data = np.array([[0, 1]])
        self.setup(2, [10, 5], 1)
        self.train(x_data, y_data)
        print(self.predict(test_data))



def make_layer(inputs, in_size, out_size, activate=None):
    weights = Variable(random_normal([in_size, out_size]))
    basis = Variable(zeros([1, out_size]) + 0.1)
    result = matmul(inputs, weights) + basis
    if activate is None:
        return result
    else:
        return activate(result)


if __name__ == '__main__':
    pass
    # print(pip._internal.pep425tags.get_supported())
    # method1()
    # method3()
    nn = BPNeuralNetwork()
    nn.test()
    # p = placeholder(float32, [None, 1])
    # print(p)