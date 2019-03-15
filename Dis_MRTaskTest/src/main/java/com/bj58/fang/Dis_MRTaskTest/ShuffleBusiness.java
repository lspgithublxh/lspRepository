package com.bj58.fang.Dis_MRTaskTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.bj58.fang.Dis_MRTaskTest.entity.OneTaskEntity;
import com.bj58.fang.Dis_MRTaskTest.serialize.FanxuGuifan;
import com.bj58.fang.Dis_MRTaskTest.serialize.SeriUtil;

/**
 * 产品设计和算法设计应该一样，都是想得特别的清楚之\定义清楚后才能行动
 * 
 * 1.一次shuffle请求要建立一个实例
 * 2.shuffle节点id:必须按照Ip分，并且可以获取；；
 * @ClassName:ShuffleBusiness
 * @Description:
 * @Author lishaoping
 * @Date 2019年3月15日
 * @Version V1.0
 * @Package com.bj58.fang.Dis_MRTaskTest
 */
public class ShuffleBusiness extends Thread{

	ReadThread rt;
	WriteThread wt;
	
	static ConcurrentHashMap<String, OneTaskEntity> taskResultMap = new ConcurrentHashMap<>();
	static List<String> shuffleIPList;//读取配置文件而设置
	static List<String> workerIPList;//读取配置文件而设置
	
	public ReadThread getRt() {
		return rt;
	}
	public void setRt(ReadThread rt) {
		this.rt = rt;
	}
	public WriteThread getWt() {
		return wt;
	}
	public void setWt(WriteThread wt) {
		this.wt = wt;
	}
	
	public List<String> getShuffleIPList() {
		return shuffleIPList;
	}
	public void setShuffleIPList(List<String> shuffleIPList) {
		this.shuffleIPList = shuffleIPList;
	}
	public List<String> getWorkerIPList() {
		return workerIPList;
	}
	public void setWorkerIPList(List<String> workerIPList) {
		this.workerIPList = workerIPList;
	}
	
	@Override
	public void run() {
		byte[] data = rt.getData();
		String mes = rt.mes;
		String[] detail = mes.split("\\|");
		//根据mes不同分类处理
		if(detail != null && detail.length > 0) {//shuffle|taskId|type|status|ip|class
			if("shuffle".equals(detail[0])) {
				OneTaskEntity task = taskResultMap.get(detail[1]);
				List<Object> vals;
				synchronized (ShuffleBusiness.class) {//锁住并行操作
					String status = detail[3];
					if(status == "-") {
						//没有值操作
						if(task == null) {
							task = new OneTaskEntity();
							Set<String> hasFinishedIPs = new HashSet<>();
							hasFinishedIPs.add(detail[4]);
							task.setHasFinishedIPMap(hasFinishedIPs);
						}else {
							Set<String> hasFinishedIPs = task.getHasFinishedIPMap();
							if(hasFinishedIPs == null) {
								hasFinishedIPs = new HashSet<>();
							}
							hasFinishedIPs.add(detail[4]);
						}
					}else {
						FanxuGuifan guifan = SeriUtil.getData(data);//最好是反序列化  为指定的类型
						Map<String, Object> daM = guifan.getDataMap();
						
						//key如果是- 则表明没有值，完成
						if(task == null) {
							task = new OneTaskEntity();
							ConcurrentHashMap<String, List<Object>> kvMap = new ConcurrentHashMap<>();
							for(Entry<String, Object> en : daM.entrySet()) {
								vals = new ArrayList<>();
								kvMap.put(en.getKey(), vals);
								vals.add(en.getValue());
							}
							task.setKvMap(kvMap);
							Set<String> hasFinishedIPs = new HashSet<>();
							hasFinishedIPs.add(detail[4]);
							task.setHasFinishedIPMap(hasFinishedIPs);
						}else {
							ConcurrentHashMap<String, List<Object>> kvMap = task.getKvMap();
							for(Entry<String, Object> en : daM.entrySet()) {
								vals = kvMap.get(en.getKey());
								if(vals == null) {
									vals = new ArrayList<>();
								}
								vals.add(en.getValue());
							}
							Set<String> hasFinishedIPs = task.getHasFinishedIPMap();
							if(hasFinishedIPs == null) {
								hasFinishedIPs = new HashSet<>();
							}
							hasFinishedIPs.add(detail[4]);
						}
					}
					//是否已经满了
					if(task.getHasFinishedIPMap().size() == workerIPList.size()) {
						//直接运行reduce任务---两两
						Map<String, Object> reduce = reduceTask(task);
						//说明计算已经结束，写入文件
						File file = new File(mes);
						if(!file.exists()) {
							try {
								file.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {
							FileWriter writer = new FileWriter(file);
							for(Entry<String, Object> en : reduce.entrySet()) {
								writer.write(en.getKey() + "\r\n");
								writer.write(en.getValue().toString() + "\r\n");
								writer.write("\r\n");
								writer.flush();
							}
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}else if("shuffle_noneed".equals(detail[0])) {//对于没有文件的worker节点，不会发送请求给shuffle进程
				//直接保存ip
				
			}
		}
	}
	private Map<String, Object> reduceTask(OneTaskEntity task) {
		ConcurrentHashMap<String, List<Object>> kvMap = task.getKvMap();
		int size = kvMap.size();
		if(size == 0) {
			return null;
		}
		Map<String, Object> reduc = new HashMap<>();
		for(Entry<String,  List<Object>> en : kvMap.entrySet()) {
			List<Object> objs = en.getValue();
			if(objs.size() == 1) {
				reduc.put(en.getKey(), objs.get(0));
			}else {
				Object left = objs.get(0);
				for(int i = 1; i < objs.size(); i++) {
					Object end = mapper(left, objs.get(i));
					left = end;
				}
				reduc.put(en.getKey(), left);
			}
		}
		return reduc;
	}
	private Object mapper(Object left, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
