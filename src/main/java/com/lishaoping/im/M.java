package com.lishaoping.im;

public class M {

	public static void main(String[] args) {
		//
		test();
	}

	private static void test() {
		//
		System.out.println(solve("012345BZ16"));;
	}
	
	 public static int solve (String s) {
	     int l = s.length();
	     int i = 0;
	     String rs = "";
	     int split = -1;
	     int start = 0;
	     int max = -1;
	     do {
	    	 char x = s.charAt(i++);
	    	 if((x - '0' >= 0 && x - '0' <= 9) || 
	    			 (x - 'A' >= 0 && x <= 'F')) {
	    	 }else {
	    		 split = i - 1;
	    		 max = findMax(s.substring(start, split), max);
	    		 start = i;
	    	 }//整数中最大的
	     }while(i < l);
		return max; 
	 }

	private static int findMax(String ss, int max) {
		int vv = max;
		for(int i = 0; i < ss.length(); i++) {
			for(int j = i+1; j <= ss.length(); j++) {
				int v = Integer.valueOf(ss.substring(i, j), 16);
				if(v > vv) {
					vv = v;
				}
			}
			
		}
		return vv;
	}
	 
}
