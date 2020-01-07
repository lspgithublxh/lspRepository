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
import java.util.SortedMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
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
 * 
 * 
 * 方法重复：用继承/模板模式
 * ;;如果不用模式，就会一堆重复冗余代码。
 * 
 * -- 单并发版本：开发测试完毕;2020/1/7
 * -- 代价太大，所以没有并发版本的TreeMap
 * @author lishaoping
 * @date 2019年12月24日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util
 */
@Slf4j
public class ServiceLSMUtilConcurrent {

	//rowkey:colflm:col:timestamp 冒号的ascll比数字大比字母小
	//val
	private TreeMap<String, Entity> memstore = Maps.newTreeMap();
//	private Map<String, String> flushstore;
	private LinkedBlockingQueue<TreeMap<String, Entity>> tasks;
	private SerializerUtil serialUtil = new SerializerUtil(Entity.class, TreeMap.class);
	private SerializerUtil serialUtil2 = new SerializerUtil(String.class, TreeMap.class);
	private SimpleThreadPoolUtil tpool1 = new SimpleThreadPoolUtil(20, 200, 10, 1000,
			(task) ->{task.run();log.info("rejection thread execute");;return true;}) ;
	

//	private int status;//0 正常,1正在刷磁盘
	private int maxTasks;
	private String filePath;
	
	private int maxMemStoreSize;
	private AtomicInteger fileCount = new AtomicInteger(0);
	private int maxFileCount;
	private long maxFileSize;
	private int maxVersionCount;
	private int maxBlockKeySize;
	private String defaultTimeStamp = "0000000000000";
	private String defaultTsBigger = "9999999999999";

	
	 public static SimpleThreadPoolUtil pool2 = new SimpleThreadPoolUtil(100, 200, 30, 1000,
				(task) ->{task.run();return true;}) ;
	
	private ServiceLSMUtilConcurrent(int maxTasks, String filePath, int maxMemStoreSize,
			int maxFileCount, long maxFileSize, int maxVersionCount, int maxBlockKeySize) {
		tasks = new LinkedBlockingQueue<TreeMap<String,Entity>>(maxTasks);
		this.maxTasks = maxTasks;
		this.filePath = filePath;
		this.maxMemStoreSize = maxMemStoreSize;
		this.maxFileCount = maxFileCount;
		this.maxFileSize = maxFileSize;
		this.maxVersionCount = maxVersionCount;
		this.maxBlockKeySize = maxBlockKeySize;
		initTasks();
	}
	//序列化
	
	private void initTasks() {
		//写入磁盘
		log.info("initTasks");
		SimpleThreadPoolUtil.pool.addTask(()->{
			while(true) {
				try {
					TreeMap<String, Entity> task = tasks.take();
					log.info("task take, size:" + task.size());
					pool2.addTask(()->{
						String firstKey = task.firstKey();
						String highKey = task.lastKey();
						//老数据刷到磁盘
						File newFile = new File(filePath + "/C0File" + currCallNo() + "_" + firstKey + "," + highKey);
						log.info("get task, create File:" + newFile.getName());
						try {
							newFile.createNewFile();
							long[] startEnd = serialUtil.serialize2(task, newFile);//开始写磁盘
							TreeMap<String, String> indexMap = Maps.newTreeMap();
							
							indexMap.put(firstKey, highKey + " " + startEnd[0] + " " + startEnd[1]);
							long[] se = serialUtil2.serialize2(indexMap, newFile);
							boolean rs = newFile.renameTo(new File(filePath + "/C0" + currCallNo() + "_" + firstKey + "," + highKey + "_" + startEnd[0] + "," + startEnd[1]));
							
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
				log.info("merge two file:");
				if(files.length > maxFileCount) {
					//开始两两合并
					try {
						int res = files.length % 2;
						Map<String, Entity> totalMap = Maps.newHashMap();
						for(int i = 0; i + 1 < files.length; i += 2) {
							Map<String, Entity> mt = mergeFile(files[i], files[i + 1]);
							totalMap = mergeToTotalMap(mt, totalMap);
						}
						if(res == 1) {
							Map<String, Entity> mt = mergeFile(null, files[files.length - 1]);
							totalMap = mergeToTotalMap(mt, totalMap);
						}
//						//test only
//						totalMap.entrySet().stream().forEach(item ->{
//							if(item.getKey().startsWith(deleteKey)) {
//								log.info("contains delete");
//							}
//						});
//						//test only
						//切分totalMap为多块；
						persistentTotalMap(totalMap);
						//删除所有C0文件:
						for(int i = 0; i < files.length; i++) {
							String name = files[i].getName();
							boolean delete = files[i].delete();
							log.info(delete + " delete the C0 File:" + name);
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
//					Arrays.asList(files).stream().reduce(null, (fil1, file2) ->mergeTwoFile(fil1, file2));
				}//
				
			}
		});
	}

	private void persistentTotalMap(Map<String, Entity> totalMap) {
		//此时可以删除有删除标记的那些key了；NO
//		removeDeleteKey(totalMap);
		//按行数合并--key的个数
		TreeMap<String, Entity> blockMap = Maps.newTreeMap();
		int i = 0;
		File file = new File(filePath + "/C1" + currCallNo());
		TreeMap<String, String> indexMap = Maps.newTreeMap();
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
			String firstKey = indexMap.firstKey();
			String highKey = indexMap.lastKey();
			boolean rename = file.renameTo(new File(filePath + "/C1" + currCallNo() + "_" + firstKey + "," + highKey + "_" + startEnd[0] + "," + startEnd[1]));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void removeDeleteKey(Map<String, Entity> totalMap) {
		Iterator<Entry<String, Entity>> iterator = totalMap.entrySet().iterator();
		for(;iterator.hasNext();) {
			Entry<String, Entity> next = iterator.next();
			if(next.getValue().status == 0) {
				iterator.remove();
			}
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
		mergeKey(totalMap);//去重
		return totalMap;
	}

	/**
	 * gson序列化也占用很大空间；	
	 * @param file
	 * @param file2
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
		List<String> removeKeyList = Lists.newArrayList();
		for(;iterator1.hasNext();) {
			Entry<String, Entity> entry = iterator1.next();
			String key = entry.getKey();
			Entity val = entry.getValue();
			int len1 = key.lastIndexOf("'");
			String prefix = key.substring(0, len1 + 1);
			if(prefix.equals(oldPrfix)) {
				//查看新老key是否要根据操作类型合并
				if(val.status == 0) {//删除--在C1层次的合并，所以前面的都可以删除了，因为肯定不会出现还需要删除的;;只需要留下delete标记的
					//前面的都需要删除--因为时间上必然都靠前
					removeKeyList.addAll(keyList);
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
//				map1.remove(removeKey);//删除老版本
				removeKeyList.add(removeKey);
			}
		}
		//合并完毕
		//删除老版本
		removeKeyList.forEach(key ->{
			map1.remove(key);
		});
	}
	
	public synchronized void putVal(String key, String val) {
		String oldVal = memstore.put(key, new Entity().setVal(val).setStatus((short)1)).getVal();
		if(memstore.size() > maxMemStoreSize) {
			//开始新建memstore,异步序列化到磁盘
			try {
				boolean suc = tasks.offer(memstore, 1000, TimeUnit.MILLISECONDS);
				log.info("offer tasks:" + suc + ", task size:" + tasks.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			memstore = Maps.newTreeMap();
		}
	}
	
	public synchronized void putVal(KeyValue keyVal) {
		String key = keyVal.rowkey + "'" + keyVal.colFml + "'" + keyVal.col + "'" + System.currentTimeMillis();
		Entity oldVal = memstore.put(key, new Entity().setVal(keyVal.val).setStatus((short)1));
		if(memstore.size() > maxMemStoreSize) {
			//开始新建memstore,异步序列化到磁盘
			try {
				boolean suc = tasks.offer(memstore, 1000, TimeUnit.MILLISECONDS);
				log.info("offer tasks:" + suc + ", task size:" + tasks.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			memstore = Maps.newTreeMap();
		}
	}
	
	public boolean deleVal(KeyValue keyVal) {
		String key = keyVal.rowkey + "'" + keyVal.colFml + "'" + keyVal.col + "'" + System.currentTimeMillis();
//		memstore.remove(key);
		//为了性能，直接加到memstore中即可::因为肯定不存在：时间太短
		memstore.put(key, new Entity().setStatus((short)0).setVal(keyVal.val));
		//从C0文件里删除
		//从C1文件里删除
//		File folder = new File(filePath);
//		File[] c0Files = filterFile(key, folder, "C0");
//		if(c0Files == null || c0Files.length == 0) {
//			//从C1中判断,
//			File[] c1Files = filterFile(key, folder, "C1");
//			if(c0Files == null || c0Files.length == 0) {
//				return true;
//			}
//		}else {
//			
//		}
		return true;
	}
	
	public SortedMap<String, Entity> getVal(String key) {//不含时间戳
		//先从memstore里取，然后从所有的C0中取，然后加载C1的索引，从索引块里取
		SortedMap<String, Entity> rsMap = Maps.newTreeMap();
		String fromKey = key + defaultTimeStamp;
		String toKey = key + defaultTsBigger;
		String ck = memstore.ceilingKey(fromKey);
		String fk = memstore.floorKey(toKey);
		
		if(ck != null && fk != null) {//包含key
			SortedMap<String, Entity> subMap = memstore.subMap(fk, true, ck, true);
			AtomicBoolean hasDel = new AtomicBoolean(false);
			SortedMap<String, Entity> rs = getFromSubMap(key, subMap, hasDel);
			
			if(rs.size() > 0) {
				return rs;
			}else if(hasDel.get()) {//memstore中存在，是被删除了
				return rs;
			}
		}
		File folder = new File(filePath);
		File[] c0Files = filterFile(key, folder, "C0");
		if(c0Files == null || c0Files.length == 0) {
			return findFromC1(key, rsMap, fromKey, toKey, folder);
		}else {
			//加载C0File,
			for(File c0 :  c0Files) {
				String name = c0.getName();
				String[] part = name.split("_");
				String[] startEnd = part[1].split(",");//直接看到索引--即其实，单个文件可以没有索引
				if(ifKeyInIt(key, startEnd[0], startEnd[1]) && part.length > 2) {
					//startEnd[0].substring(0, startEnd[0].length() - defaultTimeStamp.length()).equals(key)
//					|| startEnd[1].substring(0, startEnd[1].length() - defaultTimeStamp.length()).equals(key)
					String[] startEnd2 = part[2].split(",");
					TreeMap<String, Entity> dataMap = serialUtil.deserialize3(c0, TreeMap.class, Long.valueOf(startEnd2[0]), Long.valueOf(startEnd2[1]));
					SortedMap<String, Entity> subMap = dataMap.subMap(fromKey, true, toKey, true);
					SortedMap<String, Entity> rs = getFromSubMap2(key, subMap, null);
					rsMap.putAll(rs);
				}
			}
			//移除需要删除的
			AtomicBoolean hasDele= new AtomicBoolean(false);
			rsMap = getFromSubMap(key, rsMap, hasDele);
			if(rsMap.isEmpty() && hasDele.get()) {//C0上就删除了，不用去C1中寻找 了
				return rsMap;
			}
			//C0没有加载C1
			if(rsMap.size() == 0) {
				return findFromC1(key, rsMap, fromKey, toKey, folder);
			}
		}
		return rsMap;
		
	}

	private SortedMap<String, Entity> findFromC1(String key, SortedMap<String, Entity> rsMap, String fromKey, String toKey, File folder) {
		//从C1中判断,
		File[] c1Files = filterFile(key, folder, "C1");
		//加载C1File
		for(File c1 :  c1Files) {
			String name = c1.getName();
			String[] startEnd = name.split("_")[2].split(",");
			TreeMap<String, String> indexMap = serialUtil2.deserialize2(c1, TreeMap.class, Long.valueOf(startEnd[0]), Long.valueOf(startEnd[1]));
			//判断是否在索引里
			String cek = indexMap.ceilingKey(key + defaultTimeStamp);
			String toCek = indexMap.floorKey(key + defaultTsBigger);
//			String prefix = cek.substring(0, cek.length() - defaultTimeStamp.length());
			if(toCek != null && cek != null) {//存在
//						indexMap.tailMap(fromKey, inclusive)
				SortedMap<String, String> subMap = indexMap.subMap(toCek, true, cek, true);
				subMap.forEach((kk, val) ->{
					String[] arr = val.split(" ");
					long start = Long.valueOf(arr[1]);
					long end = Long.valueOf(arr[2]);
					TreeMap<String, Entity> map = serialUtil.deserialize3(c1, TreeMap.class, start, end);
					SortedMap<String, Entity> smap = map.subMap(fromKey, true, toKey, true);
					SortedMap<String, Entity> rs = getFromSubMap2(key, smap, null);
					rsMap.putAll(rs);
				});
			}
		}
		AtomicBoolean hasDele= new AtomicBoolean(false);
		SortedMap<String, Entity> rsMapx = getFromSubMap(key, rsMap, hasDele);
		if(rsMapx.isEmpty() && hasDele.get()) {//C1上就删除了，不用去C2中寻找 了
			return rsMapx;
		}
		return rsMapx;
	}

	private SortedMap<String, Entity> getFromSubMap3(String key, SortedMap<String, Entity> subMap) {
		//移除需要删除的
		
		return subMap;
	}
	
	private SortedMap<String, Entity> getFromSubMap2(String key, SortedMap<String, Entity> subMap, AtomicBoolean hasDel) {
		SortedMap<String, Entity> rs = Maps.newTreeMap();
		subMap.forEach((k, v) ->{
			if(k.startsWith(key)) {
				rs.put(k, v);
			}
		});
		return rs;
	}
	
	private SortedMap<String, Entity> getFromSubMap(String key, SortedMap<String, Entity> subMap, AtomicBoolean hasDel) {
		SortedMap<String, Entity> rs = Maps.newTreeMap();
		List<String> removeKeyList = Lists.newArrayList();
		List<String> cacheList = Lists.newArrayList();
		subMap.forEach((k, v) ->{
			if(k.startsWith(key)) {// && v.status == 1
				if(v.status == 1) {
					cacheList.add(k);
					rs.put(k, v);
				}else {
					removeKeyList.addAll(cacheList);
					cacheList.clear();//本身也不加
					if(hasDel != null) {
						hasDel.set(true);
					}
				}
				
			}
		});
		removeKeyList.forEach(k ->{
			rs.remove(k);
		});
		//
		return rs;
	}

	private File[] filterFile(String key, File folder, String prefix) {
		File[] c0Files = folder.listFiles((file, name) -> {
			if(name.startsWith(prefix)) {
				String[] range = name.split("_")[1].split(",");
				String startKey = range[0];
				String endKey = range[1];
				return ifKeyInIt(key, startKey, endKey);
			}
			return false;
		});
		return c0Files;
	}
	private boolean ifKeyInIt(String key, String startKey, String endKey) {
		String rk = startKey.substring("rowkey".length(), startKey.indexOf("'"));
		String rk2 = endKey.substring("rowkey".length(), endKey.indexOf("'"));
		String kk = key.substring("rowkey".length(), key.indexOf("'"));
		long kkLine = Long.valueOf(kk);
		if(Long.valueOf(rk) <= kkLine ||
				Long.valueOf(rk2) >= kkLine) {
			//startKey.substring(0, startKey.length() - defaultTimeStamp.length()).equals(key)
//					|| endKey.substring(0, endKey.length() - defaultTimeStamp.length()).equals(key)
			return true;
		}
		return false;
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
	
	@Data
	@Accessors(chain = true)
	static class KeyValue{
		String rowkey;
		String colFml;
		String col;
		
		String val;
	}
	
	public static void main(String[] args) {
		test2();
//		tte();
//		mergeFileTest();
//		ee();
	}
	private static void ee() {
		AtomicInteger in = new AtomicInteger(0);
		atomicTest(in);
		System.out.println(in.get());
	}

	private static void atomicTest(AtomicInteger in) {
		in.incrementAndGet();
	}

	private static void mergeFileTest() {
		//
		int maxTasks = 100;
		 int maxMemStoreSize = 100;
		 int maxFileCount = 20;
		 long maxFileSize = 10;//无用
		 int maxVersionCount = 1;
		 int maxBlockKeySize = 200;
		 ServiceLSMUtilConcurrent util = new ServiceLSMUtilConcurrent(maxTasks, "D:\\msc", 
				maxMemStoreSize, maxFileCount, maxFileSize, maxVersionCount, maxBlockKeySize);
		Map<String, Entity> mergeFile = util.mergeFile(new File("D:\\msc\\C01578368112914a12_rowkey0000000051'colfml'name'1578368112662,rowkey0000009967'colfml'name'1578368112630_0,6063"), 
				new File("D:\\msc\\C01578368114007a14_rowkey0000000185'colfml'name'1578368113949,rowkey0000009810'colfml'name'1578368113204_0,6063"));
		Gson gs = new Gson();
		System.out.println(gs.toJson(mergeFile));
		Map<String, Entity> mergeFile2 = util.mergeFile(new File("D:\\msc\\C01578368115085a16_rowkey0000000032'colfml'name'1578368114676,rowkey0000009944'colfml'name'1578368114603_0,6063"), 
				new File("D:\\msc\\C01578368116154a18_rowkey0000000144'colfml'name'1578368115518,rowkey0000009990'colfml'name'1578368115496_0,6063"));
		util.mergeToTotalMap(mergeFile, mergeFile2);
		System.out.println(gs.toJson(mergeFile2));
		
	}

	private String deleteKey = null;
	
	private static void tte() {
		System.out.println((System.currentTimeMillis()+""));
		System.out.println("0000000000000");
	}

	private static void test2() {
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
	
		int maxTasks = 100;
		 int maxMemStoreSize = 100;
		 int maxFileCount = 20;
		 long maxFileSize = 10;//无用
		 int maxVersionCount = 3;
		 int maxBlockKeySize = 200;
		//开始测试
		 ServiceLSMUtilConcurrent util = new ServiceLSMUtilConcurrent(maxTasks, "D:\\msc", 
				maxMemStoreSize, maxFileCount, maxFileSize, maxVersionCount, maxBlockKeySize);
		int count = 0;
		String lastVal = "";
		int findTimes = 0;
		int uniqueKey = 0;
		while(true) {
			if(count++ > 100000) {
				break;
			}
			if(count % 1000 == 0) {
				String key = "rowkey" + lastVal + "'colfml'name'";
				SortedMap<String, Entity> val = util.getVal(key);
				log.info("key:" + key + " val:" + new Gson().toJson(val));
				//删除
				util.deleteKey = key;
				util.deleVal(new KeyValue().setRowkey("rowkey" + lastVal).setColFml("colfml").setCol("name")
						.setVal(lastVal + ""));
				//证明删除也是有效的--内存里找
				SortedMap<String, Entity> val2 = util.getVal(key);
				log.info("delete then, " + val2);
			}
			
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			String d = uniqueKey++ + "";//(int)(Math.random() * 10000)
			for(int i = d.length(); i < 10; i++) {
				d = "0" + d;
			}
			if(count == 2) {
				lastVal = d;
			}
			final String rk = d;
			util.putVal(new KeyValue().setRowkey("rowkey" + rk).setColFml("colfml").setCol("name")
					.setVal(rk + ""));
//			util.tpool1.addTask(() -> {
//				util.putVal(new KeyValue().setRowkey("rowkey" + rk).setColFml("colfml").setCol("name")
//						.setVal(rk + ""));
//			});
			
//			String key = util.memstore.firstKey();
//			System.out.println(util.getVal(key));
//			log.info("memstore size:" + util.memstore.size());
		}
	}
}
