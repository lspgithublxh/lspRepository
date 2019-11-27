package com.li.shao.ping.KeyListBase.datastructure.test;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.experimental.Accessors;

public class QuestionMergeRangeTest {

	@Data
	@Accessors(chain = true)
	static class Node{
		private int start;
		private int end;
	}
	
	public static void main(String[] args) {
		String[] str = new String[] {"1,6","6,7", "9, 10", "10,15"};
//		merge();//[{3,4},{6,8},{1,10}] //["4,5","7,8"]
		List<Node> combine = combine(Lists.newArrayList(new Node().setStart(1).setEnd(10),
				new Node().setStart(4).setEnd(10),
				new Node().setStart(9).setEnd(19),
				new Node().setStart(20).setEnd(23),
				new Node().setStart(56).setEnd(89),
				new Node().setStart(13).setEnd(34),
				new Node().setStart(15).setEnd(56)));
		System.out.println(combine);
	}

	private static Node[] merge(Node node1, Node node2) {
		int min = node1.start > node2.start ? node2.start : node1.start;
		int max = node1.end > node2.end ? node1.end : node2.end;
		if(min == node1.start && max == node1.end
				|| min == node2.start && max == node2.end) {
			return new Node[] {new Node().setStart(min).setEnd(max)};
		}
		if(min == node1.start && (node1.end >= node2.start)
				|| min == node2.start && (node2.end >= node1.start)) {
			return new Node[] {new Node().setStart(min).setEnd(max)};
		}
		return new Node[] {node1, node2};
	}
	
	private static List<Node> combine(List<Node> source){
		int i = source.size() - 1;
		List<Node> nextTurn = Lists.newArrayList(source);
		List<Node> nextTurn2 = Lists.newArrayList();
		while(i-- > 0) {
			Node cur = nextTurn.get(0);
			for(int j = 1; j <= nextTurn.size() - 1; j++) {
				Node[] merge = merge(cur, nextTurn.get(j));
				if(merge.length == 1) {
					cur = merge[0];
				}else if(merge.length == 2) {
					nextTurn2.add(nextTurn.get(j));
				}
			}
			nextTurn2.add(cur);
			nextTurn = nextTurn2;
			nextTurn2 = Lists.newArrayList();
		}
		return nextTurn;
	}
}
