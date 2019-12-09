package com.li.shao.ping.KeyListBase.datastructure.util;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 四大静态性质：1.根黑 
 *  2.红黑(红的子是黑)
 *  3.h <= 2logN 恒成立(一个长分支的方法得出最大h)(一个结点考虑，则h<2log(N+1))
 *  4.每条路径上的黑个数一样
 *  
 * 旋转目的： 因为加结点，所在分支长了，让兄弟分支加1，自己高度减一
 * 变色目的：因为加结点，出现红红相连了，
 * 
 * 加结点后基本结构：新结点父为黑，不变；
 *           新结点的父为红(可能有黑子结点)：父可能有兄弟结点(可黑可红)。。需要分类处理
 *     1.父有子 ：直接父子红父黑即可(父子必须单一)
 *     2.父无子 父兄为黑；直接变色父祖-旋转: 祖继续
 *     3.父无子 父兄为红；父兄不可能有黑子(因为黑总数否则就不相等了，所以为叶子)：则父与父兄都变为黑，祖变为红：祖继续
 *   ---------------上述逻辑第1，3条对祖结点继续进行平衡则并不适合(因为无法断定父/父兄有无子)。。
 *   -----找到规律：
 *     1.父、父兄 都变黑，祖变红；则对父子、父兄子 没有影响。//这是一种最重要的将红色上移的方法；(父-父兄 都是红色)
 *     2.父兄为黑，则可以父祖换色，再以父为中心旋转。
 * @author lishaoping
 * @date 2019年12月6日
 * @package  com.li.shao.ping.KeyListBase.datastructure.util
 */
@Slf4j
public class BRUtil {

	
	private Node root;
	
	@Data
	@Accessors(chain = true)
	public static class Node{
		private Node left;
		private Node right;
		private Node parent;
		private int val;
		private boolean red;
	}
	
	public Node findTarget(int val) {
		Node curr = root;
		Node target = null;
		while(curr != null) {
			if(curr.val > val) {
				curr = curr.left;
			}else if(curr.val < val) {
				curr = curr.right;
			}else {
				target = curr;
				break;
			}
		}
		return target;
	}
	
	public void leftRotate(Node node) {//此时假定 node是他parent的右结点
		Node p = node.parent;
		Node pp = p == null ? null : p.parent;
		if(p == null) {
			return;//不存在这回事
		}
		
		//新相互关系设置，老相互关系废弃;;; 四个结点要改
		node.parent = pp;
		if(pp != null) {
			if(pp.right == p) {
				pp.right = node;
			}else {
				pp.left = node;
			}
		} 
		
		node.left = p;
		p.parent = node;
		
		Node cleft = node.left;
		p.right = cleft;
		if(cleft != null) {
			cleft.parent = p;
		}
		
	}
	
	public void rightRotate(Node node) {//此时假定 node是他parent的左结点---才有右旋概念
		Node p = node.parent;
		Node pp = p == null ? null : p.parent;
		if(p == null) {
			return;//不存在这回事
		}
		//新相互关系设置，老相互关系废弃;;; 四个结点要改
		node.parent = pp;
		if(pp != null) {
			if(pp.right == p) {
				pp.right = node;
			}else {
				pp.left = node;
			}
		} 
		
		node.right = p;
		p.parent = node;
		
		Node cright = node.right;
		p.left = cright;
		if(cright != null) {
			cright.parent = p;
		}
	}
	
	public void addNode(int val) {
		//增加结点，根据条件判断是否要 左旋/右旋旋转
		if(root == null) {
			root = new Node().setVal(val);
			return;
		}
		//搜索到插入位置
		Node cur = root;
		Node parent = root.parent;
		boolean toLeft = true;
		while(cur != null) {
			parent = cur;
			if(cur.val == val) {
				return;
			}else if(cur.val > val) {
				cur = cur.left;
				toLeft = true;
			}else {
				cur = cur.right;
				toLeft = false;
			}
		}
		//开始插入结点
		Node node = new Node();
		if(toLeft) {
			parent.left = node.setVal(val);
			node.parent = parent;
		}else {
			parent.right = node.setVal(val);
			node.parent = parent;
		}
		//变色阶段
		changeColor(parent);
	}

	private void changeColor(Node parent) {
		//开始判断旋转
		if(parent == null || !parent.red) {
			return;
		}
		//需要旋转
		Node pp = parent.parent;//不可能为null;因为parent是红色
		if(pp.left == parent) {//parent是左结点
			Node pb = pp.right;
			if(!pb.red) {//父兄为黑
				parent.red = false;
				pp.red = true;
				rightRotate(parent);//父右旋
			}else {//父兄为红
				parent.red = false;
				pb.red = false;
				pp.red = true;
				//祖为结点继续变色
				changeColor(pp.parent);
			}
		}else{//parent是右结点
			Node pb = pp.left;
			if(!pb.red) {//父兄为黑
				parent.red = false;
				pp.red = true;
				leftRotate(parent);
			}else {//父兄为红
				parent.red = false;
				pb.red = false;
				pp.red = true;
				//祖为结点继续变色
				changeColor(pp.parent);
			}
		}
	}
	
	/**
	 * 删除结点：
	 *  如果有右分支：找出右分支最小值，递归删除，加到这个位置;;
	 *  如果只有左分支：找出左分支最大值，删除，加到这个位置;
	 *  如果无分支：直接删除
	 *  ----有分支的删除 判断： 是红色，则直接删除；是黑色：--则直接消除了这个分支：无影响。可以删除---条件是有兄弟结点：：否则要继续判断-往上找到红结点---如果没有--只能到根节点-左结点右旋本结点变红
	 *  ----无分支的删除判断： 是红色，直接删除；是黑色：--如果有兄弟结点，直接删除；如果没有：则这个分支线需要向上，找到一个结点
	 * --
	 * 直接看黑色结点的删除方法：如果有单一分支，如果有多分支---子为红：可以变黑上进(非旋转)//无子黑，单子黑，双子黑
	 * @param node
	 */
	private void deleteNode(Node node) {
		//
		
	}
	
	/**
	 * 平衡式删除黑色无子结点---层层往上：：形成了短1黑分支
	 * @param node
	 */
	private void balanceDeleteBlackLeafNode(Node node) {
		if(node.red || node.left != null || node.right != null) {
			log.error("children not null error");
			return;
		}
		Node p = node.parent;
		if(p == null) {
			log.info("delete ok ,root ");
			return;
		}
		//先删除
		Node b = null;
		if(p.right == node) {
			b = p.left;
			p.right = null;
			node.parent = null;
		}else {
			b = p.right;
			p.left = null;
			node.parent = null;
		}
		//无需再平衡
		if(b != null) {
			return;
		}
		if(p.red) {
			p.red = false;
			return;
		}
		//注意：组内均衡，和需要一直上升平衡到根结点
		//短黑分支的顶
		Node top = p.parent;
		Node cur = p;
		while(top != null) {//不是根结点
			if(top.red) {//无它子直接变黑， 有则它子直接旋转  ---无需到根结点
				if(top.right == cur) {
					Node left = top.left;
					if(left == null) {
						top.red = false;
						return;
					}else {//直接右旋
						rightRotate(left);
						return;
					}
				}else {
					Node right = top.right;
					if(right == null) {
						top.red = false;
						return;
					}else {//直接右旋
						leftRotate(right);
						return;
					}
				}
			}else {//top是黑色 ----需要到根结点
				if(top.right == cur) {
					Node left = top.left;
					if(left == null) {
						cur = top;
						top = cur.parent;//继续
						continue;
					}
					if(left.red) {//红色有用--直接上旋, 再变黑--；；就。。黑色需要均衡---子变红：
						left.red = false;
						rightRotate(left);
						return;
					}else {//黑色，变红，右选，再继续
						top.red = true;
						rightRotate(left);
						cur = left;//继续
						top = left.parent;
						continue;
					}
				}else {
					Node right = top.right;
					if(right == null) {
						cur = top;
						top = cur.parent;//继续
						continue;
					}
					if(right.red) {//红色有用--直接上旋, 再变黑--；；就。。黑色需要均衡---子变红：
						right.red = false;
						leftRotate(right);
						return;
					}else {//黑色，变红，右选，再继续
						top.red = true;
						leftRotate(right);
						cur = right;//继续
						top = right.parent;
						continue;
					}
				}
			}
		}
		//已经到根结点，退出，完成
	}
	
	/**
	 * 平衡式删除红色无子结点
	 * @param node
	 */
	private void balanceDeleteRedLeafNode(Node node) {
		
	}
	
	public static void main(String[] args) {
		System.out.println(new Node().red);
		//打印则打印每条路径；全部路径
		
	}
}
