package com.bj58.im.client.recog;

public class Compare {

	public static void compare(byte[][] jietu, byte[][] jietu2) {
		int row = jietu.length > jietu2.length ? jietu2.length : jietu.length;
		int col = jietu[0].length > jietu2[0].length ? jietu2[0].length : jietu[0].length;
		int right = 0;
		int wrong = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if(jietu[i][j] == jietu2[i][j]) {
					right++;
				}else {
					wrong++;
				}
			}
		}
		System.out.println(String.format("相似率：%.1f%%", right / (double)( wrong  + right) * 100 ));
	}
}
