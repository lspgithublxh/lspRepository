package com.bj58.im.client.matrix;

/**
 * 行列式的计算：具体矩阵 用初等变换；；含有lamda用  从内到外增长的方式进行 逼近 计算
 * @ClassName:QyzSjVgDf
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月18日
 * @Version V1.0
 * @Package com.bj58.im.client.matrix
 */
public class QyzSjVgDf {

	public static void main(String[] args) {
		Double[][] s = {{2d,3d},{2d,4d}};
		printMatrix(s);
		Double[][] s2 = zhuanzhi(s);
		printMatrix(s2);
		Double[][] s3 = chengfa(s2, s);
		printMatrix(s3);
		Double hls = hanglieshi(s);
		System.out.println(hls);
		Double hls2 = hanglieshi(s2);
		System.out.println(hls2);
		Double hls3 = hanglieshi(s3);
		System.out.println(hls3);
	}

	private static void printMatrix(Double[][] s) {
		String out = "{";
		for(int i = 0; i < s.length; i++) {
			Double[] line = s[i];
			String str = "{";
			for(int j = 0; j < line.length; j++) {
				str += line[j] + ",";
			}
			str = str.substring(0, str.length() - 1) + "},";
			out += str;
		}
		out = out.substring(0, out.length() - 1) + "}";
		System.out.println(out);
	}
	
	private static Double[][] zhuanzhi(Double[][] matrix) {
		Double[][] rs = new Double[matrix.length][matrix[0].length];
		for(int i = 0; i < rs.length; i++) {
			Double[] line = matrix[i];
			for(int j = 0; j < line.length; j++) {
				rs[j][i] = line[j];
			}
		}
		return rs;
	}
	
	private static Double[][] chengfa(Double[][] matrix, Double[][] matrix2) {
		Double[][] rs = new Double[matrix.length][matrix2[0].length];
		for(int i = 0; i < matrix.length; i++) {
			Double[] line = matrix[i];
			for(int k = 0; k < matrix2.length; k++) {
				Double count = 0d;
				for(int j = 0; j < line.length; j++) {
					count += line[j] * matrix2[j][k];
				}
				rs[i][k] = count;
			}
		}
		return rs;
	}

	private static void chengkaddto(Double[][] matrix, int i, int j , Double k) {
		for(int c = 0; c < matrix[0].length; c++) {
			matrix[j][c] += matrix[i][c] * k;//如果是到自己 ，那么行列式会变为原来的k+1倍数
		}
	}
	
	private static boolean jiaohuanij(Double[][] matrix, int i, int j) {
		boolean changed = false;
		for(int c = i; c < matrix.length; c++) {
			if(matrix[c][j] != 0) {
				Double[] temp = matrix[i];
				matrix[i] = matrix[c];
				matrix[c] = temp;
				changed = true;
				break;
			}
		}
		return changed;
	}
	
	private static Double hanglieshi(Double[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			jiaohuanij(matrix, i, i);
			for(int j = i + 1; j < matrix.length; j++) {
				chengkaddto(matrix, i, j, - matrix[j][i] / matrix[i][i] );
			}
		}
		//计算对角线元素的乘积
		Double hls = 1d;
		for(int i = 0; i < matrix.length; i++) {
			hls *= matrix[i][i];
		}
		return hls;
	}
}
