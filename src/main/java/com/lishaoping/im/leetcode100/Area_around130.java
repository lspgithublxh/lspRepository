package com.lishaoping.im.leetcode100;

import java.util.ArrayList;
import java.util.List;

public class Area_around130 {

	public static void main(String[] args) {
		stest();
	}

	static class Node{
		int i = -1;
		int j = -1;
		public Node(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}
	}
	
	private static void stest() {
		char[][] arr = {{'x','x','x','x'},
				{'x','o','o','x'},
				{'x','x','o','x'},
				{'x','o','x','x'}};
		List<Node> n = new ArrayList<>();
		for(int i = 0; i < arr[0].length;i++) {
			if(arr[0][i] == 'o') {
				n.add(new Node(0,i));
				arr[0][i] = 't';
			}
			if(arr[arr.length - 1][i] == 'o') {
				n.add(new Node(arr.length - 1, i));
				arr[arr.length-1][i] = 't';
			}
		}
		for(int i = 0; i < arr.length; i++) {
			if(arr[i][0] == 'o') {
				n.add(new Node(i,0));
				arr[i][0] = 't';
			}
			if(arr[i][arr[0].length - 1] == 'o') {
				n.add(new Node(i,arr[0].length - 1));
				arr[i][arr[0].length - 1] = 't';
			}
		}
		//内部可达
		while(!n.isEmpty()) {
			Node e = n.remove(0);
			int nexti = e.i + 1;
			int prei = e.i - 1;
			int nextj = e.j + 1;
			int prej = e.j - 1;
			if(nexti < arr.length && arr[nexti][e.j] == 'o') {
				arr[nexti][e.j] = 't';
				n.add(new Node(nexti, e.j));
			}
			if(prei >= 0 && arr[prei][e.j] == 'o') {
				arr[prei][e.j] = 't';
				n.add(new Node(prei, e.j));
			}
			if(nextj < arr[0].length && arr[e.i][nextj] == 'o') {
				arr[e.i][nextj] = 't';
				n.add(new Node(e.i, nextj));
			}
			if(prej >=0 && arr[e.i][prej] == 'o') {
				arr[e.i][prej] = 't';
				n.add(new Node(e.i, prej));
			}
		}
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length;j++ ) {
				if(arr[i][j] == 'o') {
					arr[i][j] = 'x';
				}else if(arr[i][j] == 't') {
					arr[i][j] = 'o';
				}
			}
		}
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length;j++ ) {
				System.out.print("" + arr[i][j] + ",");
			}
			System.out.println();
		}
	}
}
