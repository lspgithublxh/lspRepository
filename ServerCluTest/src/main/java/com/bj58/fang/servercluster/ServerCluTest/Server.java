package com.bj58.fang.servercluster.ServerCluTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基本的测试逻辑：
 * 1.根据配置的iplist, 访问通，找出访问通的列表里的ip值最大的ip， 向它投票；超过一半，则自认为是中心，而开始去请求后端而 且向所有投票者广播回发--当前的中心server ip；；收到广播后开始向它发送请求：获取token.
 * 2.server的访问不了：重新启动投票。---同时可能认定发起者为有问题的server
 * 		--健康机制检测：比如server的内存-cpu消耗大了，开始准备重新选取---选取时也看cpu-mem消耗占用情况
 * 3.心跳机制：中心server轮询其他server-----意义不大。
 * 
 * @ClassName:Server
 * @Description:
 * @Author lishaoping
 * @Date 2019年3月6日
 * @Version V1.0
 * @Package com.bj58.fang.servercluster.ServerCluTest
 */
public class Server {

	static String ips = "10.8.9.59;10.8.18.214;10.8.12.185;10.8.9.222";
	static int serverCount = 0;
	static int port = 10456;
	static Server ser = new Server();
	static String maxIp = null;
	static String localIp = null;//多网卡问题，就不好弄
	static AtomicInteger voteCount = new AtomicInteger(0);
	static AtomicBoolean voteFinish = new AtomicBoolean(false);
	static List<String> voteIPList = new ArrayList<String>();
	static List<String> serverList = new ArrayList<String>();
	static String centerServer = null;
	
	public static void main(String[] args) {
//		getLocalIp();
		//首先
		run();
	}

	private static void getLocalIp() {
		try {
			String os = System.getProperty("os.name");
			if(os.toLowerCase().indexOf("windows") > -1) {
				String ip = InetAddress.getLocalHost().getHostAddress();
				localIp = ip;
				System.out.println("ip:" + ip);
			}else {
				try {
					Enumeration<NetworkInterface>  en = NetworkInterface.getNetworkInterfaces();
					for(;en.hasMoreElements();) {
						NetworkInterface nk = en.nextElement();
						String name = nk.getName();
						if(!name.contains("docker") && !name.contains("lo")) {
							Enumeration<InetAddress> ids = nk.getInetAddresses();
							for(;ids.hasMoreElements();) {
								InetAddress id = ids.nextElement();
								if(!id.isLoopbackAddress()) {
									String adr = id.getHostAddress().toString();
									if(!adr.contains("::") && !adr.contains("0:0:") && !adr.contains("fe80")) {
										System.out.println("ip:" + adr);
										localIp = adr;//退出
									}
								}
							}
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}
				
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private static void run() {
		
		//1.计算最大ip
		String[] ipArr = ips.split(";");
		int max = 0;
		String mip = null;
		int count = 0;
		for(String ip : ipArr) {
			//ip能否ping tong
			boolean ok = pingIp(ip);
			if(!ok) {
				continue;
			}
			count++;
			serverList.add(ip);
			String[] ipParts = ip.split("\\.");
			int total = 0;
			for(int i = ipParts.length - 1; i >=0 ; i--) {
				int val = Integer.valueOf(ipParts[i]) *((int) Math.pow(10, i));
				total += val;
			}
			if(total > max) {
				max = total;
				mip = ip;
			}
		}
		serverCount = count;
		maxIp = mip;
		getLocalIp();
		//开始利用server进行
		new Thread(new Runnable() {
			
			public void run() {
				try {
					ServerSocket server = new ServerSocket(port);
					while(true) {
						Socket cl = server.accept();
						//判断：是投票来的，还是请求token
						ser.new ReadThread(cl).start();
//						ser.new WriteThread(cl).start();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
	}
	
	private static boolean pingIp(String ip) {
		try {
			InetAddress inetAdd = InetAddress.getByName(ip);
			boolean ok = inetAdd.isReachable(3000);
			System.out.println("ip isReachable:" + ip);
			return ok;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	class ReadThread extends Thread{
		
		Socket sock = null;
		
		Socket linkCenterServer = null;

		public ReadThread(Socket sock) {
			super();
			this.sock = sock;
		}
		
		Map<String, String> data = new HashMap<>();
		Object lockData = new Object();
		public String getFuture(String key) {
			String da = data.get(key);
			if(da == null) {
				synchronized (lockData) {
					da = data.get(key);
					if(da == null) {
						try {
							lockData.wait();
							da = data.get(key);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return da;
		}
		
		public String getDataNonSync(final Socket so) {
			final String key = System.currentTimeMillis() + "" + Math.random();
			new Thread(new Runnable() {
				public void run() {
					try {
						DataInputStream ins = new DataInputStream(so.getInputStream());
						String data = ins.readUTF();
						ReadThread.this.data.put(key, data);
						synchronized (lockData) {
							lockData.notifyAll();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			return key;
		}
		
		@Override
		public void run() {
			try {
				DataInputStream in = new DataInputStream(sock.getInputStream());
				while(true) {
					String data = in.readUTF();
					System.out.println("received data:" + data);
					if(data.startsWith("vote|")) {//vote|投票ip|自己ip|票数|其他ip
						String[] info = data.split("|");
						if(info[1].equals(maxIp)) {//和自己计算的一样 
							//向maxIp发送投票，或者自己累计
							if(maxIp.equals(localIp)) {
								voteIPList.add(info[2]);
								int num = voteCount.addAndGet(Integer.valueOf(info[3]));
//									int num = voteCount.incrementAndGet();
								if(num >= serverCount / 2) {
									//自己已经是，开始启动请求token程序---畅通
									
									//开始广播：自己是center
									//最后设置
									voteFinish.set(true);
								}else {
									//还不能自主决定---但是何时是个头？主动询问?超时询问？都结合？
									if(num >= serverCount / 3) {//开始主动询问剩下的？
										
									}
								}
								
							}else {
								//发送vote 给maxIp机器---自己不是，但是别人投票了。票数增加
								
							}
						}else {//专门再发送给maxIp---不累计，直接转发---自己不是，但是别人投票了。票数增加
							
						}
					}else if(data.startsWith("get|token|")) {
						//返回token---可以是一个独立程序
						String token = getToken();//返回
						String retInfo = "ret|token|" + token;
						pureWrite(sock, retInfo);//sync
					}else if(data.startsWith("ret|token|")){
						String[] infos = data.split("|");
						String token = infos[2];//对外提供的sock接口
						//发送，返回给客户端
					}else if(data.startsWith("cget|token|")) {
						//作为对外提供接口，选举出来之后则从本地、或者linkserver里取数据，；；没有选举出来就等待
						String token = "{\"token\":\"%s\"}";
						if(voteFinish.get()) {
							if(localIp.equals(centerServer)) {
								token = String.format(token, getToken());
							}else {
								//远程获取
								final Socket so = new Socket(centerServer, port);
								final String[] dats = new String[1];
								final Object lock = new Object();
								new Thread(new Runnable() {
									public void run() {
										try {
											DataInputStream ins = new DataInputStream(so.getInputStream());
											String data = ins.readUTF();
											dats[0] = data;
											synchronized (lock) {
												lock.notifyAll();
											}
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}).start();
								new WriteThread(so).setMes("get|token").start();
								synchronized (lock) {
									try {
										lock.wait();
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								token = String.format(token, getToken());
							}
							new WriteThread(sock).setMes("cret|token|ok|" + token).start();
						}else {
							//阻塞，异步--返回没准备好
							new WriteThread(sock).setMes("cret|token|wait").start();
						}
					}else if(data.startsWith("brocast|token|")) {//广播信息
						//
						String[] infos = data.split("|");
						centerServer = infos[2];
						voteFinish.set(true);
						break;
					}
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		public void pureRead() {
			
		}
	}
	
	private void pureWrite(Socket sock2, String data) {
		try {
			DataOutputStream out = new DataOutputStream(sock2.getOutputStream());
			out.writeUTF(data);
			out.flush();
//			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getToken() {
		return "this_is_token";
	}
	
	class WriteThread extends Thread{
		
		Socket sock = null;
		String mes = "";

		public WriteThread(Socket sock) {
			super();
			this.sock = sock;
		}
		
		public WriteThread setMes(String mes) {
			this.mes = mes;
			return this;
		}
		
		@Override
		public void run() {
			try {
				DataOutputStream out = new DataOutputStream(sock.getOutputStream());
				out.writeUTF(mes);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
