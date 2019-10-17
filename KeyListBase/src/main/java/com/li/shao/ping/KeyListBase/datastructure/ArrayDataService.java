package com.li.shao.ping.KeyListBase.datastructure;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.li.shao.ping.KeyListBase.datastructure.entity.ListNode;

/**
 * 最简单的数组的方式来实现
 * TODO 
 * @author lishaoping
 * @date 2019年10月17日
 * @file ArrayDataService
 * @param <D>
 */
public class ArrayDataService<D extends Serializable> implements DataService{

	private Map<Long, List<ListNode<D>>> dataMap = Maps.newConcurrentMap();
	
	@Override
	public String queryTopNByKey(Long key, int n) {
		List<ListNode<D>> personDataList = dataMap.get(key);
		StringBuilder builder = new StringBuilder();//最简单最规范
		for(int i = 0; i < n; i++) {//数据V是 类似mysql的列 族 格式 "{dsfsdf|sdfsf|sdfsf}"
			builder.append(personDataList.get(i).getId()).append("`")
			.append(personDataList.get(i).getScore()).append("`")
			.append(personDataList.get(i).getValue()).append("`");
		}
		return builder.substring(0, builder.capacity() - 1).toString();
	}

	@Override
	public void updateNodesByKey(String nodes, Long key) {
		int index = 0;
		int count = 0;
		StringBuilder builder = new StringBuilder();
		List<ListNode<D>> nodeList = Lists.newArrayList();
		char[] chrs = nodes.toCharArray();
		while(index < chrs.length) {
			int i = index;
			for(; chrs[i] != '`'; i++) {
				builder.append(chrs[i]);
			}
			switch (count) {
			case 0:
				ListNode<D> node = new ListNode<D>();
				node.setId(Long.valueOf(builder.toString()));
				nodeList.add(node);
				break;
			case 1:
				nodeList.get(nodeList.size() - 1).setScore(Double.valueOf(builder.toString()));
				break;
			case 2:
				nodeList.get(nodeList.size() - 1).setValue((D) builder.toString());//序列化方式
				break;
			default:
				break;
			}
			builder.setLength(0);
			count = ++count % 3;
			index = i + 1;
		}
		//开始存入本地数据
		List<ListNode<D>> dataList = dataMap.get(key);
		//顺序索引找到id,二分找到score位置,,移除后，二分找到新存插入位置，再次顺序移动更新引用
		for(ListNode<D> waitNode : nodeList) {
			int pos = 0;
			for(ListNode<D> node : dataList) {
				if(node.getId() == waitNode.getId()) {
					node.setWait(true);
					break;
				}
				pos++;
			}
			int left = 0;
			int right = dataList.size() - 1;
			int tag = 0;
			while(left < right) {
				if(dataList.get(left).getScore() <= waitNode.getScore()) {
					break;
				}else if(dataList.get(right).getScore() >= waitNode.getScore()) {
					tag = 1;
					break;
				}
				int center = (left + right) / 2;
				if(dataList.get(center).getScore() <= waitNode.getScore()) {
					right = center;
				}else{
					left = center;
				}
			}
			//....待做；复杂
		}
	}

	@Override
	public void initUserNodesByKeyAndData(String nodes, Long key) {
		
	}

	@Override
	public void deleteByKey(Long key) {
		
	}

	
}
