package com.lishaoping.im.leetcode100;

/**
 * û�е�����̫��ʱ����
 *
 *@author lishaoping
 *Client
 *2020��7��11��
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
						//��һ��
						p1 = p2.next;
						p2 = p1 == null ? null :p1.next;//����ר�ż��������
					}else {
						p1.next = p2.next;
						p2.next = p1;
						if(p0 != null) {
							p0.next = p2;
						}
						//��һ�Σ�
						p0 = p1;
						p1 = p1.next;
						p2 = p1.next;
						
					}
				}else {
					//>=2���ĺϲ�
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
								//ֱ���ƶ�
								p0 = p1;
								p1 = p1.next;
								//��һ������
							}else {
								//�м����
								Node p2_next = p2.next;
								p2.next = p1.next;
								p1.next = p2;
								//��һ��
								p0 = p1;
								p1 = p1.next;
								p2 = p2_next;
								insert_num++;
							}
						}else {
							break;
						}
						if(insert_num == len) {
							break;//���
						}
					}
					if(insert_num < len) {
						//β��ֱ�Ӳ���
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
			//ȷ����һ��p2�����
			//�����null,���p2.next==null������ʾ����---�������һ��û������һ�μ��ɡ�
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
				//�����p2��������
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
