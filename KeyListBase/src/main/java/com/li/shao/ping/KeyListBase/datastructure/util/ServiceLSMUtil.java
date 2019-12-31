package com.li.shao.ping.KeyListBase.datastructure.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;
import com.li.shao.ping.KeyListBase.datastructure.geneutil.SimpleThreadPoolUtil;
import com.li.shao.ping.KeyListBase.datastructure.util.seria.SerializerUtil;

import avro.shaded.com.google.common.collect.Lists;
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
	private TreeMap<String, Entity> memstore = Maps.newTreeMap();
//	private Map<String, String> flushstore;
	private LinkedBlockingQueue<TreeMap<String, Entity>> tasks;
	private SerializerUtil serialUtil = new SerializerUtil(Entity.class, TreeMap.class);
	private SerializerUtil serialUtil2 = new SerializerUtil(String.class, TreeMap.class);

//	private int status;//0 正常,1正在刷磁盘
	private int maxTasks;
	private String filePath;
	
	private int maxMemStoreSize;
	private AtomicInteger fileCount = new AtomicInteger(0);
	private int maxFileCount;
	private long maxFileSize;
	private int maxVersionCount;
	private int maxBlockKeySize;
	
	 public static SimpleThreadPoolUtil pool2 = new SimpleThreadPoolUtil(100, 200, 30, 1000,
				(task) ->{task.run();return true;}) ;
	
	private ServiceLSMUtil(int maxTasks) {
		tasks = new LinkedBlockingQueue<TreeMap<String,Entity>>(maxTasks);
		initTasks();
	}
	//序列化
	
	private void initTasks() {
		//写入磁盘
		SimpleThreadPoolUtil.pool.addTask(()->{
			try {
				TreeMap<String, Entity> task = tasks.take();
				pool2.addTask(()->{
					//老数据刷到磁盘
					File newFile = new File(filePath + "/C0File" + currCallNo());
					try {
//						ByteArrayOutputStream bout = new ByteArrayOutputStream();
//						for(Entry<String,Entity> item : task.entrySet()) {
//							//序列化写入
//							String line = item.getKey() + "|" + item.getValue().val + "|" + item.getValue().status + "\r\n";
//							bout.write(line.getBytes());
//						}
//						ByteBuffer buffer = ByteBuffer.wrap(bout.toByteArray());
//						newFile.createNewFile();
//						FileOutputStream out = new FileOutputStream(newFile);
//						FileChannel channel = out.getChannel();
//						channel.write(buffer);
//						channel.force(true);
//						channel.close();
						long[] startEnd = serialUtil.serialize2(task, newFile);//开始写磁盘
						TreeMap<String, String> indexMap = Maps.newTreeMap();
						String firstKey = task.firstKey();
						String highKey = task.lastKey();
						indexMap.put(firstKey, highKey + " " + startEnd[0] + " " + startEnd[1]);
						long[] se = serialUtil2.serialize2(indexMap, newFile);
						newFile.renameTo(new File(filePath + "/C0" + currCallNo() + "_" + startEnd[0] + ":" + startEnd[1]));
						synchronized (fileCount) {
							File f = new File(filePath);
							File[] files = f.listFiles((it,name )->name.startsWith("C0"));
							if(files.length > maxFileCount) {
								fileCount.notify();
							}
							
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
				File f = new File(filePath);
				File[] files = f.listFiles((it,name )->name.startsWith("C0"));
				synchronized (fileCount) {
					if(files.length <= maxFileCount) {
						try {
							fileCount.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(files.length > maxFileCount) {
					//开始两两合并
					try {
						int res = files.length % 2;
						Map<String, Entity> totalMap = Maps.newHashMap();
						for(int i = 0; i + 1 < files.length; i += 2) {
							Map<String, Entity> mt = mergeFile(files[i], files[1]);
							totalMap = mergeToTotalMap(mt, totalMap);
						}
						//切分totalMap为多块；
						persistentTotalMap(totalMap);
					}catch (Exception e) {
						e.printStackTrace();
					}
//					Arrays.asList(files).stream().reduce(null, (fil1, file2) ->mergeTwoFile(fil1, file2));
				}//
				
			}
		});
	}

	private void persistentTotalMap(Map<String, Entity> totalMap) {
		//按行数合并--key的个数
		TreeMap<String, Entity> blockMap = Maps.newTreeMap();
		int i = 0;
		File file = new File(filePath + "/C1" + currCallNo());
		Map<String, String> indexMap = Maps.newTreeMap();
		try {
			file.createNewFile();
			for(Entry<String, Entity> entity : totalMap.entrySet()) {
				blockMap.put(entity.getKey(), entity.getValue());
				if(blockMap.size() > maxBlockKeySize) {
					//持久化到磁盘
					perstenseMap(blockMap, file, indexMap);
				}
			}
			if(blockMap.size() > 0) {
				perstenseMap(blockMap, file, indexMap);
			}
			//持久化索引
			long[] startEnd = serialUtil2.serialize2(indexMap, file);
			boolean rename = file.renameTo(new File(filePath + "/C1" + currCallNo() + "_" + startEnd[0] + ":" + startEnd[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void perstenseMap(TreeMap<String, Entity> blockMap, File file, Map<String, String> indexMap) {
		long[] startEnd = serialUtil.serialize2(blockMap, file);
		String lowKey = blockMap.firstKey();
		String highKey = blockMap.lastKey();
		indexMap.put(lowKey, highKey + " " + startEnd[0] + " " + startEnd[1]);
		blockMap.clear();
	}

	private Map<String, Entity> mergeToTotalMap(Map<String, Entity> mt, Map<String, Entity> totalMap) {
		if(mt.size() > totalMap.size()) {
			mt.putAll(totalMap);
			totalMap = mt;
		}else {
			totalMap.putAll(mt);
		}
		return totalMap;
	}

	/**
	 * gson序列化也占用很大空间；	
	 * @param file
	 * @param file2
	 * @return
	 */
	private Map<String, Entity> mergeFile(File file, File file2) {
		Map<String, Entity> map = null;
		Map<String, Entity> map1 = null;
		Map<String, Entity> map2 = null;
		if(file != null ) {
			map1 = serialUtil.deserialize(file, TreeMap.class);//HashMap<String, Entity> dataMap
			map = map1;
		}
		if(file2 != null) {
			map2 = serialUtil.deserialize(file2, TreeMap.class);
			map = map2;
		}
		if(file != null && file2 != null) {//合并--只处理键相等的；仅仅保留时间戳不同的n个版本以内的。
			int mapSize1 = map1.size();
			int mapSize2 = map2.size();
			if(mapSize1 > mapSize2) {
				map1.putAll(map2);
				map = map1;
			}else {
				map2.putAll(map1);
				map = map2;
			}
		}
		mergeKey(map);
		return map;
	}

	private void mergeKey(Map<String, Entity> map1) {
		String oldPrfix = null;
		List<String> keyList = Lists.newArrayList();
		Iterator<Entry<String, Entity>> iterator1 = map1.entrySet().iterator();
		for(;iterator1.hasNext();) {
			Entry<String, Entity> entry = iterator1.next();
			String key = entry.getKey();
			Entity val = entry.getValue();
			int len1 = key.lastIndexOf(":");
			String prefix = key.substring(len1 + 1);
			if(prefix.equals(oldPrfix)) {
				//查看新老key是否要根据操作类型合并
				if(val.status == 0) {//删除
					keyList.clear();
				}else if(val.status == 1) {//新增
					keyList.add(key);
				}
			}else {
				oldPrfix = prefix;
				keyList.clear();
				if(val.status != 0) {
					keyList.add(key);
				}
			}
			if(keyList.size() > maxVersionCount) {//删除多余老版本
				String removeKey = keyList.remove(0);
				map1.remove(removeKey);//删除老版本
			}
		}
		//合并完毕
	}

	private File mergeTwoFile(File fil1, File file2) {
		if(fil1 == null) {
			return file2;
		}
		if(fil1 != null && file2 != null) {
			long len1 = fil1.length();
			long len2 = file2.length();
			if(len1 > maxFileSize) {
				fil1.renameTo(new File(filePath + "/C1" + currCallNo()));
				return fil1;
			}
			if(len2 > maxFileSize) {
				file2.renameTo(new File(filePath + "/C1" + currCallNo()));
				return file2;
			}
			//开始合并两个文件：合并之后一块一块的写入新文件中；末尾添加上索引
			//一行一个key-value
			//一次写1024个keyvalue
			//读取时候，读取byte起止区间，byte[]转为一个map对象或者一个字符串对象-自己处理
			//合并两个map，转为byte[]写到文件，且
			//当文件的总大小超过某个值后，就要按行键分裂为两个目录-region, 各存一半的内容：每个目录有一个memstore进行分别的写入。:::所以不担心太多C1
			//
			
			
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
	static class Entity{
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
		
		//重命名：
		File file = new File("D:\\test\\b.txt");
		file.renameTo(new File("D:\\test\\bs.txt"));
		
	}
}
