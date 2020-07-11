package com.lishaoping.im.leetcode100;

/**
 * 没有调过，太耗时！！
 *
 *@author lishaoping
 *Client
 *2020年7月11日
 */
public class Merge_sort_list148 {

	public static void main(String[] args) {
		tst();
	}

	static class Node{
		int v;
		Node next;
		public Node(int v) {
			super();
			this.v = v;
		}
		
	}
	
	private static void tst() {
		int v = 0;
		Node n = new Node(v++);
		Node n1 = new Node(v+5);
		Node n2 = new Node(v+4);
		Node n3 = new Node(v+6);
		Node n4 = new Node(v+7);
		Node n5 = new Node(v+3);
		n.next = n1;
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		int len = 1;
		Node p1 = n;
		Node p2 = n.next;
		Node p0 = null;
		while(true) {
			int insert_num = 0;
			while(p2 != null) {
				if(p1.next == p2) {
					if(p1.v <= p2.v) {
						//下一步
						p1 = p2.next;
						p2 = p1 == null ? null :p1.next;//或者专门加入而排序
					}else {
						p1.next = p2.next;
						p2.next = p1;
						if(p0 != null) {
							p0.next = p2;
						}
						//下一次：
						p0 = p1;
						p1 = p1.next;
						p2 = p1.next;
						
					}
				}else {
					//>=2个的合并
					Node p2_start = p2;
					while(p1 != p2_start) {
						if(p1.next != p2_start) {
							if(p1.v >=p2.v) {
								Node p2_next = p2.next;
								if(p0 != null) {
									p0.next = p2;
								}
								p2.next = p1;
								p1 = p2;
								p2 = p2_next;
								insert_num++;
							}else if(p2.v >= p1.next.v) {
								//直接移动
								p0 = p1;
								p1 = p1.next;
								//下一步插入
							}else {
								//中间插入
								Node p2_next = p2.next;
								p2.next = p1.next;
								p1.next = p2;
								//下一步
								p0 = p1;
								p1 = p1.next;
								p2 = p2_next;
								insert_num++;
							}
						}else {
							break;
						}
						if(insert_num == len) {
							break;//完成
						}
					}
					if(insert_num < len) {
						//尾部直接插入
						p0.next = p2;
					}
					while(insert_num < len) {
						p1 = p2;
						insert_num++;
						p2 = p2.next;
					}
					p0 = p1;
					p1 = p2;
					int i = 0;
					while(i++ < len) {
						p2 = p2.next;
					}
					
				}
			}
			len *=2;
			//确定下一次p2的起点
			//如果是null,如果p2.next==null，都表示结束---或者最后一个没排序，排一次即可。
			p1 = n;
			p2 = n;
			int i = 0;
			while(i++ < len) {
				p2 = p2.next;
			}
			if(p2 == null) {
				break;
			}
			if(p2.next == null) {
				//对这个p2进行排序
				while(p1 != null) {
					if(p2.v >= p1.v) {
						if(p2.v <= p1.next.v) {
							p2.next = p1.next;
							p1.next = p2;
						}else {
							p1 = p1.next;
						}
					}else {
						p2.next = p1;
						n = p2;
					}
				}
			}
		}
	}
}
