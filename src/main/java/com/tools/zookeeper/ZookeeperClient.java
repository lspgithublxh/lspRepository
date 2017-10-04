package com.tools.zookeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

public class ZookeeperClient {
	private static final int SESSION_TIMEOUT=30000;
	
	public ZooKeeper zk;
	// ���� Watcher ʵ��
	private CountDownLatch connectedLatch = new CountDownLatch(1);

    public  Watcher wh = new Watcher(){
    	 /**
	   	  * ͳ�������Լ�Ҳʹ�ù�
	   	  */
       public void process(org.apache.zookeeper.WatchedEvent event) {
    	   System.out.println(event.toString());
    	   if (event.getState() == KeeperState.SyncConnected) {  
               connectedLatch.countDown();  
           }  
       }

     }; 
     
     public static void waitUntilConnected(ZooKeeper zooKeeper, CountDownLatch connectedLatch) {  
         if (States.CONNECTING == zooKeeper.getState()) {  
             try {  
                 connectedLatch.await();  
             } catch (InterruptedException e) {  
                 throw new IllegalStateException(e);  
             }  
         }  
     }  
     
     private void createZKInstance() throws IOException {
    	 System.out.println("create a client ...");
          zk=new ZooKeeper("localhost:2181",ZookeeperClient.SESSION_TIMEOUT,this.wh);
          System.out.println("start wait...");
          waitUntilConnected(zk, connectedLatch);
     }    
     
     
     public void connect() throws KeeperException, InterruptedException, UnsupportedEncodingException {
    	 System.out.println("/n1. ���� ZooKeeper �ڵ� (znode �� zoo2, ���ݣ� myData2 ��Ȩ�ޣ� OPEN_ACL_UNSAFE ���ڵ����ͣ� Persistent");
    	 //1.�����ڵ�
    	 zk.create("/zoo2","lishaoping".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    	 getData();
         //2.�޸Ľڵ�
         zk.setData("/zoo2", "lixiaohai".getBytes(), -1);
         getData();
         //3.ɾ���ڵ�
         zk.delete("/zoo2", -1);
         Stat stat = zk.exists("/zoo2", false);
         System.out.println("stat:" + stat);
         
     }

     private void close() throws InterruptedException {
    	 zk.close();
     }
     

	private void getData() throws KeeperException, InterruptedException, UnsupportedEncodingException {
		byte[] res = zk.getData("/zoo2", false, null);
         String str = new String(res, "UTF-8");
         System.out.println("get from zookeeper" + str);
	}
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZookeeperClient client = new ZookeeperClient();
		client.createZKInstance();
		
		client.connect();
		client.close();
	}
     
}
