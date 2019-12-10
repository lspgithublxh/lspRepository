package com.li.shao.ping.KeyListBase.datastructure.util;

import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

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
public class BRUtil2 {

	
	private Node root;
	
	@Data
	@Accessors(chain = true)
	public static class Node{
		private Node left;
		private Node right;
		private Node parent;
		private int val;
		private boolean red = true;// = true
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
		
		Node cleft = node.left;
		p.right = cleft;
		if(cleft != null) {
			cleft.parent = p;
		}
		
		node.left = p;
		p.parent = node;
		
		
		
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
		
		Node cright = node.right;
		p.left = cright;
		if(cright != null) {
			cright.parent = p;
		}
		
		node.right = p;
		p.parent = node;
		
		
	}
	
	public void addNode(int val) {
		//增加结点，根据条件判断是否要 左旋/右旋旋转
		if(root == null) {
			root = new Node().setVal(val).setRed(false);
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
		//重新root找到阶段
		Node newRoot = root;
		while(newRoot.parent != null) {
			newRoot = newRoot.parent;
		}
		root = newRoot;
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
			if(pb == null || !pb.red) {//父兄为黑 TODO父兄不存在
				Node cright = parent.right;
				if(cright != null && cright.red) {//TODO add 需要先左旋, 必须直接引用
					leftRotate(cright);
					changeColor(cright);//变色
				}else {
					parent.red = false;
					pp.red = true;
					rightRotate(parent);//父右旋
				}
				
			}else {//父兄为红
				parent.red = false;
				pb.red = false;
				pp.red = true;
				//祖为结点继续变色
				if(pp.parent == null) {//root
					pp.red = false;
				}else {
					changeColor(pp.parent);
				}
				
			}
		}else{//parent是右结点
			Node pb = pp.left;
			if(pb == null || !pb.red) {//父兄为黑
				Node cleft = parent.left;
				if(cleft != null && cleft.red) {
					rightRotate(cleft);
					changeColor(cleft);//变色
				}else {
					parent.red = false;
					pp.red = true;
					leftRotate(parent);
				}
				
			}else {//父兄为红
				parent.red = false;
				pb.red = false;
				pp.red = true;
				//祖为结点继续变色
				if(pp.parent == null) {//add right pp.right == null || 
					pp.red = false;
				}else {
					changeColor(pp.parent);
				}
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
	private void deleteNode(int val) {
		//找到删除点 --看能不能删--转到替换点
		//找到替换点，数据替换后，替换点为该删点----直到这个改删点 删除后可以直接被连接，则直接连接，然后进行向上均衡即可。
		Node cur = root;
		Node p = root.parent;
		while(cur != null) {
			if(cur.val == val) {
				break;
			}else if(cur.val < val) {
				p = cur;
				cur = cur.right;
			}else {
				p = cur;
				cur = cur.left;
			}
		}
		//不存在
		if(cur == null) {
			return;
		}
		//存在，需要删除
		if(cur.right != null) {
			Node replace = findRightReplaceNode(cur.right);
			//先替换值，再看删除替换结点--肯定是单结点
			cur.val = replace.val;
			deleteSingleNodeNotLeftNode(replace);
		}else if(cur.left != null){
			Node replace = findLeftReplaceNode(cur.left);
			cur.val = replace.val;
			deleteSingleNodeNotRightNode(replace);
		}else {
			if(cur.red) {
				if(p.left == cur) {
					p.left = null;
					cur.parent = null;
				}else {
					p.right = null;
					cur.parent = null;
				}
			}else {
				balanceDeleteBlackLeafNode(cur);
			}
		}
	}
	
	/**
	 *  删除单一子结点，或者无子结点
	 * @param node
	 */
	public void deleteSingleNodeNotRightNode(Node node) {
		Node p = node.parent;
		if(node.left == null) {//无子结点
			if(node.red) {//直接删除
				node.parent = null;
				if(p.left == node) {
					p.left = null;
				}else {
					p.right = null;
				}
			}else {
				balanceDeleteBlackLeafNode(node);
			}
		}else {//有右结点
			if(node.red) {//直接父子相连. 不用平衡
				zuPointToZi2(node, p);
			}else {
				if(node.left.red) {//红色变黑，替换
					node.left.red = false;
					zuPointToZi2(node, p);
				}else {//是黑色，需要平衡，
					zuPointToZi2(node, p);
					//只平衡
					pBalance(node.left);
				}
			}
		}
	}
	
	/**
	 * 删除单一子结点，或者无子结点
	 * @param node
	 */
	public void deleteSingleNodeNotLeftNode(Node node) {
		Node p = node.parent;
		if(node.right == null) {//无子结点
			if(node.red) {//直接删除
				node.parent = null;
				if(p.left == node) {
					p.left = null;
				}else {
					p.right = null;
				}
			}else {
				balanceDeleteBlackLeafNode(node);
			}
		}else {//有右结点
			if(node.red) {//直接父子相连. 不用平衡
				zuPointToZi(node, p);
			}else {
				if(node.right.red) {//红色变黑，替换
					node.right.red = false;
					zuPointToZi(node, p);
				}else {//是黑色，需要平衡，
					zuPointToZi(node, p);
					//只平衡
					pBalance(node.right);
				}
			}
		}
	}

	private void zuPointToZi2(Node node, Node p) {
		if(p.left == node) {
			p.left = node.left;
			node.left.parent = p;
		}else {
			p.right = node.left;
			node.left.parent = p;
		}
	}
	
	private void zuPointToZi(Node node, Node p) {
		if(p.left == node) {
			p.left = node.right;
			node.right.parent = p;
		}else {
			p.right = node.right;
			node.right.parent = p;
		}
	}
	
	/**
	 * 寻找最左结点
	 * @param node
	 * @return
	 */
	public Node findRightReplaceNode(Node node) {
		Node cur = node;
		while(cur.left != null) {
			cur = cur.left;
		}
		return cur;
	}
	
	/**
	 * 寻找最右结点
	 * @param node
	 * @return
	 */
	public Node findLeftReplaceNode(Node node) {
		Node cur = node;
		while(cur.right != null) {
			cur = cur.right;
		}
		return cur;
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
		pBalance(p);
	}

	/**
	 * p所在分支少1黑，再均衡；到root
	 * p是黑色，p有双子或者单个子
	 * @param p
	 */
	private void pBalance(Node p) {
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
						Node cleftR = left.right;//TODO 右旋之前
						if(cleftR != null && cleftR.red) {//分两大类操作
							Node cleftF = left.left;
							if(cleftF == null || !cleftF.red) {
								cleftR.red = false;
								left.red = true;
								leftRotate(cleftR);
								//开始 平衡
								top.red = true;
								rightRotate(cleftR);
								cur = cleftR;
								top = left.parent;
								continue;
							}else {//双红
								cleftF.red = false;
								cleftR.red = false;
								left.red = true;
								//处理
								left.red = false;
								rightRotate(left);
								return;
							}
						}else {//已经是黑色，不动
							top.red = true;
							rightRotate(left);
							cur = left;//继续
							top = left.parent;
							continue;
						}
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
						Node crightL = right.left;
						if(crightL != null && crightL.red) {//左旋之前
							Node crightR = right.right;
							if(crightR == null || !crightR.red) {
								crightL.red = false;
								right.red = true;
								rightRotate(crightL);
								//处理
								top.red = true;
								leftRotate(crightL);
								cur = crightL;
								top = crightL.parent;
								continue;
							}else {//双红
								crightR.red = false;
								crightL.red = false;
								right.red = true;
								//处理
								right.red = false;
								leftRotate(right);
								return;
							}
						}else {
							top.red = true;
							leftRotate(right);
							cur = right;//继续
							top = right.parent;
							continue;
						}
						
					}
				}
			}
		}
		//已经到根结点，退出，完成
	}
	
	public void logPath(Node cur, String path, List<String> li) {
		path += cur.val + ",";
		if(cur.left != null) {
			logPath(cur.left, path, li);
		} 
		if(cur.right != null) {
			logPath(cur.right, path, li);
		}
		if(cur.right == null && cur.left == null){
			li.add(path);
		}
	}
	
	@Slf4j
	@Accessors(chain = true)
	@Data
	static class EcharNode{
		private String name;
		private Integer value;
		private List<EcharNode> children;
	}
	
	public void logTreeForEchart(Node node, EcharNode er, String direction) {
		if(node == null) {
			return;
		}
		er.name = direction + (node.red ? "red" : "blk") + node.val;
		if(node.left != null) {
			EcharNode left = new EcharNode().setName(node.val + "");
			er.setChildren(Lists.newArrayList()).getChildren().add(left);
			logTreeForEchart(node.left, left, "lef_");
		}
		if(node.right != null) {
			EcharNode right = new EcharNode().setName(node.val + "");
			if(er.getChildren() == null) {
				er.setChildren(Lists.newArrayList()).getChildren().add(right);
			}else {
				er.getChildren().add(right);
			}
			logTreeForEchart(node.right, right, "rig_");
		}
		if(node.right == null && node.left == null) {
			er.value = node.val;
		}
	}
	
//	public List<String> logPath() {
//		List<String> li = Lists.newArrayList();
//		Node cur = root;
//		String path = "";
//		while(cur != null) {
//			path += cur.val + ",";
//			if(cur.left != null) {
//				cur = cur.left;
//			}else if(cur.right != null) {
//				cur = cur.right;
//			}else {
//				li.add(path);
//				path = "";
//			}
//		}
//		return li;
//	}
	
	public static void main(String[] args) {
//		addNodeTest();//
		deleteNodeTest();
	}

	private static void deleteNodeTest() {
		BRUtil2 util = new BRUtil2();
		IntStream.of(93,10,19,63,48,37,50,40,28,77,20,85,37,3).boxed().forEach(item ->{//,37,50,40,28,77,20,85,37,3
//			int m = (int)(Math.random() * 100);
			if(item == 63) {
				System.out.println();
			}
			util.addNode(item);
//			System.out.println(item);
			EcharNode ro = new EcharNode();
			util.logTreeForEchart(util.root, ro, "roo_");
			System.out.println(new Gson().toJson(ro));
		});
		System.out.println("delete after");
		util.deleteNode(63);
		
		EcharNode ro = new EcharNode();
		util.logTreeForEchart(util.root, ro, "roo_");
		System.out.println(new Gson().toJson(ro));
	}

	private static void addNodeTest() {
		System.out.println(new Node().red);
		BRUtil2 util = new BRUtil2();
//		for(int i = 0; i < 10; i++) {
//			int m = (int)(Math.random() * 10);
//			System.out.println(m);
//			util.addNode(m);
//		}
		IntStream.of(93,10,19,63,48).boxed().forEach(item ->{//,37,50,40,28,77,20,85,37,3
//			int m = (int)(Math.random() * 100);
			if(item == 63) {
				System.out.println();
			}
			util.addNode(item);
//			System.out.println(item);
			EcharNode ro = new EcharNode();
			util.logTreeForEchart(util.root, ro, "roo_");
			System.out.println(new Gson().toJson(ro));
		});
		List<String> nodes = Lists.newArrayList();
		util.logPath(util.root, "", nodes);
		for(String path : nodes) {
			System.out.println(path);
		}
		EcharNode ro = new EcharNode();
		util.logTreeForEchart(util.root, ro, "roo_");
		System.out.println(new Gson().toJson(ro));

		//打印则打印每条路径；全部路径
//		System.out.println(new Gson().toJson(util.root));
	}
}
