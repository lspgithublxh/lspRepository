package com.lishaoping.im.leetcode;

public class Unix_path_normal {

	public static void main(String[] args) {
		String v = path();
		System.out.println(v);
	}

	private static String path() {//方法2：正则表达式
//		String path = "/a/../../b/../c//.//";
//		String path = "/a//b////c/d//././/..";
//		String path = "/a/./b/../../c/";
//		String path = "/../";
		String path = "/home/";
		String normal = "";
		String source = path;
		while(true) {
			int ind = source.indexOf("/");
			String pre = ind < 0 ? source : source.substring(0, ind);
			if(pre.equals("")) {
				//根路径
				normal = normal.equals("") ? "/" : normal;
			}else if(pre.equals(".")) {
			}else if(pre.equals("..")) {
				normal = normal.substring(0, normal.lastIndexOf("/"));
				normal = normal.equals("") ? "/" : normal;
			}else {
				normal += normal.endsWith("/") ? pre : "/" + pre;
			}
			if(ind < 0) {
				break;
			}
			source = source.substring(ind + 1);
//			System.out.println(source);
			if(source.equals("")) {
				break;
			}
		}
		return normal;
	}
}
