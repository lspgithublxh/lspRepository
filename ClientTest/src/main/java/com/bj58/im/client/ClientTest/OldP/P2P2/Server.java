package com.bj58.im.client.ClientTest.OldP.P2P2;

import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;  
import java.util.List;  
  
/** 
 * 外网端服务，穿透中继
 * 问题：可能全部在本机上是不行的。 
 * 可能复用端口需要将原来的连接关闭---服务器这边主动关闭。。。毕竟只有一个端口--给一个socket
 * 方法2：客户端都建立serversocket转为被动监听
 *  
 * @author ln 
 * 
 */  
public class Server {  
  
    public static List<ServerThread> connections = new ArrayList<ServerThread>();  
  
    public static void main(String[] args) {  
        try {  
            // 1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口 
        	System.setProperty("java.net.preferIPv4Stack" , "true");
            ServerSocket serverSocket = new ServerSocket(9999);  
            Socket socket = null;  
            // 记录客户端的数量  
            int count = 0;  
            System.out.println("***服务器即将启动，等待客户端的连接***");  
            // 循环监听等待客户端的连接  
            while (true) {  
                // 调用accept()方法开始监听，等待客户端的连接  
                socket = serverSocket.accept();  
                System.out.println(socket.getLocalPort());//就是监听端口
                System.out.println(socket.getReuseAddress());
                // 创建一个新的线程  
                ServerThread serverThread = new ServerThread(socket);  
                // 启动线程  
                serverThread.start();  
  
                connections.add(serverThread);  
  
                count++;// 统计客户端的数量  
                System.out.println("客户端的数量：" + count);  
            }  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  
