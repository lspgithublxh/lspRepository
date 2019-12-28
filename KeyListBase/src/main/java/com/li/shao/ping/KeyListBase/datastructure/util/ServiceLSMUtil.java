package com.li.shao.ping.KeyListBase.datastructure.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * B+树：随机写：磁盘随机写(块的不连续)
 * LSM树：顺序写：磁盘顺序写
 * 二叉树缺点：两个二叉树合并
 *       核心算法：C0内存保存最近写入的(k,v)全部是有序的。(像memstore)更新可以先更新这个内存里的有序k-v结构。
 *        C1-Ck
 *  put的过程： 先写WAL记录操作, 再写C0(追加或者覆盖或者删除), C0达到一定大小开始关闭写入：创建一个新的C0,老的直接写入磁盘
 *  独立线程：监控磁盘上上C0的个数，超过额定则两两合并为一个C1 ; 同样C1也额定之后合并为C2,C3,C4: 8-4-2-1
 *独立线程算法2： 监控磁盘上上C0的总的个数，超过额定则合并为一个，且不大于某个大小，为C1；剩下的再轮询看个数。定期另外的线程：major合并；将全部C1C0合并为一个文件-同样不超过额定C2；C2有线程定期过期删除
 * @author lishaoping
 * @date 2019年12月24日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util
 */
@Slf4j
public class ServiceLSMUtil {

	//rowkey:colflm:col:timestamp 冒号的ascll比数字大比字母小
	//val
	private Map<String, Entity> memstore = Maps.newTreeMap();
//	private Map<String, String> flushstore;
	private LinkedBlockingQueue<Map<String, Entity>> tasks;
//	private int status;//0 正常,1正在刷磁盘
	private int maxTasks;
	private String filePath;
	
	private int maxMemStoreSize;
	private AtomicInteger fileCount = new AtomicInteger(0);
	private int maxFileCount;
	private long maxFileSize;
	
	 public static SimpleThreadPoolUtil pool2 = new SimpleThreadPoolUtil(100, 200, 30, 1000,
				(task) ->{task.run();return true;}) ;
	
	private ServiceLSMUtil(int maxTasks) {
		tasks = new LinkedBlockingQueue<Map<String,Entity>>(maxTasks);
		initTasks();
	}
	//序列化
	
	private void initTasks() {
		//写入磁盘
		SimpleThreadPoolUtil.pool.addTask(()->{
			try {
				Map<String, Entity> task = tasks.take();
				pool2.addTask(()->{
					//老数据刷到磁盘
					File newFile = new File(filePath + "/C0File" + currCallNo());
					try {
						ByteArrayOutputStream bout = new ByteArrayOutputStream();
						for(Entry<String,Entity> item : task.entrySet()) {
							//序列化写入
							String line = item.getKey() + "|" + item.getValue().val + "|" + item.getValue().status + "\r\n";
							bout.write(line.getBytes());
						}
						ByteBuffer buffer = ByteBuffer.wrap(bout.toByteArray());
						newFile.createNewFile();
						FileOutputStream out = new FileOutputStream(newFile);
						FileChannel channel = out.getChannel();
						channel.write(buffer);
						channel.force(true);
						channel.close();
						fileCount.incrementAndGet();
						synchronized (fileCount) {
							fileCount.notify();
						}
					} catch (Exception e) {
						e.printStackTrace();
						newFile.delete();
					}
					
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		});
		//监听磁盘个数，而合并磁盘中文件个数
		SimpleThreadPoolUtil.pool.addTask(()->{
			for(;;) {
				if(fileCount.get() > maxFileCount) {
					//开始两两合并
					File f = new File(filePath);
					File[] files = f.listFiles((it,name )->name.startsWith("C0"));
					Arrays.asList(files).stream().reduce(null, (fil1, file2) ->mergeTwoFile(fil1, file2));
				}//
				synchronized (fileCount) {
					try {
						fileCount.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private File mergeTwoFile(File fil1, File file2) {
		if(fil1 == null) {
			return file2;
		}
		if(fil1 != null && file2 != null) {
			long len1 = fil1.length();
			long len2 = file2.length();
			if(len1 > maxFileSize) {
				fil1.renameTo(new File("C1" + currCallNo()));
			}
		}
		return null;
	}
	public synchronized void putVal(String key, String val) {
		String oldVal = memstore.put(key, new Entity().setVal(val).setStatus((short)1)).getVal();
		if(memstore.size() > maxMemStoreSize) {
			//开始新建memstore,异步序列化到磁盘
			try {
				boolean suc = tasks.offer(memstore, 1000, TimeUnit.MILLISECONDS);
				log.info("offer tasks:" + suc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			memstore = Maps.newTreeMap();
		}
	}
	
	private String currCallNo() {
		long now = System.currentTimeMillis();
		int num = increNum();
		return now + "a" + num;
	}
	
	private AtomicInteger no = new AtomicInteger(0);
	
	private int increNum() {
		int incre = no.incrementAndGet();
		no.compareAndSet(100000, 0);
		return incre;
	}
	
	@Data
	@Accessors(chain = true)
	class Entity{
		String val;
		short status;//0删除,1新增
	}
	
	public static void main(String[] args) {
		Map<String, String> memstore = Maps.newTreeMap();
		memstore.put("s", "v");
		memstore.put("s2", "v");
		memstore.put("s3", "v");
		memstore.put("s4", "v");
		memstore.entrySet().forEach(item ->{
			System.out.println(item);//批量的刷到文件里
		});
		
	}
}
