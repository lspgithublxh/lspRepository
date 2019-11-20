package com.li.shao.ping.KeyListBase.datastructure.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.li.shao.ping.KeyListBase.datastructure.entity.SkipNode;

/**
 * 
 * TODO 
 * @author lishaoping
 * @date 2019年11月20日
 * @file SkipListUtil
 */
public class SkipListUtil {

	SkipNode root;
	
	/**
	 * 
	 *
	 * @author lishaoping
	 * @date 2019年11月20日
	 * TODO
	 */
	public SkipNode query(Integer val) {
		SkipNode cur = root;
		while(cur.getVal() < val) {
			if(cur.getNext().getVal() > val) {
				cur = cur.getBottom();
			}else{
				cur = cur.getNext();
			}
			if(cur == null) {
				return null;
			}
			if(cur.getVal() == val) {//bottom寻找
				while(cur.getBottom() != null) {
					cur = cur.getBottom();
				}
				return cur;
			}
		}
		if(cur.getVal() == val) {
			while(cur.getBottom() != null) {
				cur = cur.getBottom();
			}
			return cur;
		}
		return null;
	}
	
	public boolean add(Integer val) {
		if(root == null) {
			root = new SkipNode().setVal(val);
			return true;
		}
		SkipNode cur = root;
		List<SkipNode> trace = Lists.newArrayList();
		SkipNode leftNode = null;
		boolean isCenter = false;
		while(cur.getVal() < val) {
			if(cur.getNext() == null) {
				insertPointStartInsert(val, trace, cur);
			}
			if(cur.getNext().getVal() > val) {
				trace.add(cur);
				cur = cur.getBottom();
				isCenter = true;
			}else{
				leftNode = cur;
				cur = cur.getNext();
				isCenter = false;
			}
			if(cur == null) {
				if(isCenter) {
					SkipNode last = trace.get(trace.size() - 1);
					insertPointStartInsert(val, trace, last);
				}else {//尾部新增
					while(leftNode.getBottom() != null) {
						trace.add(leftNode);
						leftNode = leftNode.getBottom();
					}
					insertPointStartInsert(val, trace, leftNode);
				}
				return true;
			}
			if(cur.getVal() == val) {
				return true;
			}
		}
		if(cur.getVal() == val) {
			return true;
		}else {
			int nextInsert = cur.getVal();
			while(cur != null) {
				cur.setVal(val);
				cur = cur.getBottom();
			}
			add(nextInsert);
			return true;
		}
	}

	public void insertPointStartInsert(Integer val, List<SkipNode> trace, SkipNode last) {
		SkipNode insert = new SkipNode().setVal(val).setNext(last.getNext());
		last.setNext(insert);
		//随机决定是否向上插入一个节点
		SkipNode curBottom = last;
		for(int i = trace.size() - 2; i >= 0; i--) {
			SkipNode up = trace.get(i);
			//
			if(Math.random() > 0.5) {
				//插入
				SkipNode inser2 = new SkipNode().setVal(val).setNext(up.getNext());
				up.setNext(inser2);
				up.setBottom(curBottom);
				curBottom = up;
			}else {
				break;
			}
		}
	}
	
	public boolean deleteNode(Integer val) {
		SkipNode cur = root;
		SkipNode left = null;
		while(cur.getVal() < val) {
			if(cur.getNext().getVal() > val) {
				cur = cur.getBottom();
			}else{
				left = cur;
				cur = cur.getNext();
			}
			if(cur == null) {
				return true;
			}
			if(cur.getVal() == val) {//
				left.setNext(cur.getNext());
				cur.setNext(null);
				SkipNode nextPoint = left;
				while(nextPoint.getBottom() != null) {
					nextPoint = locationDelete(nextPoint.getBottom(), val);
				}
			}
		}
		if(cur.getVal() == val) {
			left.setNext(cur.getNext());
			cur.setNext(null);
			SkipNode nextPoint = left;
			while(nextPoint.getBottom() != null) {
				nextPoint = locationDelete(nextPoint.getBottom(), val);
			}
		}
		return true;
	}

	private SkipNode locationDelete(SkipNode startPoint, Integer val) {
		SkipNode cur = startPoint;
		while(cur.getNext() != null) {
			if(cur.getNext().getVal() == val) {
				cur.setNext(cur.getNext().getNext());
				cur.getNext().setNext(null);
				return cur;
			}
			cur = cur.getNext();
		}
		return null;
	}
	
	public static void main(String[] args) {
		SkipListUtil skipListUtil = new SkipListUtil();
		for(int i = 0; i < 15; i++) {
			int d = (int)(Math.random() * 100);
			System.out.print(d + ",");
			boolean rs = skipListUtil.add(d);
//			System.out.print(rs + ",");
		}
		System.out.println();
		SkipNode c = skipListUtil.root;
		SkipNode cc = c;
		while(c.getBottom() != null) {
			cc = c;
		}
		while(cc != null) {
			System.out.print(cc.getVal() + ",");
			cc = cc.getNext();
		}
		
	}
}
