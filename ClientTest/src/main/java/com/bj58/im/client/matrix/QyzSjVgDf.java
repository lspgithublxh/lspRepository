package com.bj58.im.client.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
		//对角化 专门测试
//		duijiaohua(s);
//		printMatrix(s);
//		duijiaohuaQiang(s);
		printMatrix(s);
		
		//计算特征值 和 特征向量
		bijinHanglishi(s);
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
	
	private static void jiaohuanijPure(Double[][] matrix, int i, int j) {
		Double[] temp = matrix[i];
		matrix[i] = matrix[j];
		matrix[j] = temp;
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
//		duijiaohua(matrix);
		duijiaohuaQiang(matrix);
		//计算对角线元素的乘积
		Double hls = 1d;
		for(int i = 0; i < matrix.length; i++) {
			hls *= matrix[i][i];
		}
		return hls;
	}

	private static void duijiaohua(Double[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			jiaohuanij(matrix, i, i);//会改变行列式符号
			if(matrix[i][i] == 0) {//为0的一行
				continue;
			}
			for(int j = i + 1; j < matrix.length; j++) {
				chengkaddto(matrix, i, j, - matrix[j][i] / matrix[i][i] );
			}
		}
	}
	
	private static void duijiaohuaQiang(Double[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			jiaohuanij(matrix, i, i);
			if(matrix[i][i] == 0) {//为0的一行
				//往上走：如果有非0的，那么交换;第一次为0就交换
				boolean hasLou = false;
				for(int k = i - 1; k >= 0; k--) {
					if(matrix[k][i] != 0) {
						jiaohuanijPure(matrix, i, k);
						hasLou = true;
						break;
					}
				}
//				if(!hasLou) continue;//其实没必要再动，因为肯定都是0了
			}
			for(int j = i + 1; j < matrix.length; j++) {
				chengkaddto(matrix, i, j, - matrix[j][i] / matrix[i][i] );
			}
			
			for(int j = i - 1; j >= 0; j--) {
				chengkaddto(matrix, i, j, - matrix[j][i] / matrix[i][i] );
			}
		}
	}
	
	private static Double daicanHanglishi(Double[][] matrix, Double lamda) {
		for(int i = 0; i < matrix.length; i++) {
			matrix[i][i] -= lamda; 
		}
		return hanglieshi(matrix);
	}
	
	private static Double[][] copy(Double[][] matrix) {
		Double[][] cc = new Double[matrix.length][matrix[0].length];
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				cc[i][j] = matrix[i][j];
			}
		}
		return cc;
	}

	static QyzSjVgDf instance = new QyzSjVgDf();
	
	private static Double bijinHanglishi(Double[][] matrix) {
		List<Entity> rs = new ArrayList<>();
		Double startLeft = -100d;
		Double startRight = 0d;
		Double kuan = 100d;
		Double currIndex = startLeft;
		boolean left = true;
		while(true) {
			for(Double index = 0d; index <= kuan; index += 0.1) {
				currIndex += index;
				Double hls = daicanHanglishi(copy(matrix), index);
				if(Math.abs(hls) < 0.001) {//认为行列式备选 的特征值
					rs.add(instance.new Entity(Math.abs(hls),index));
				}
			}
			if(rs.size() >= matrix.length) {
				break;
			}
			if(left) {
				currIndex = startRight;
				startRight += kuan;
			}else {
				startLeft -= kuan;
				currIndex = startLeft;
			}
			left = !left;
			if(startRight > 3000 || startLeft < -3000) {
				break;
			}
		}
		Collections.sort(rs, new Comparator<Entity>() {
			@Override
			public int compare(Entity o1, Entity o2) {
				if(o1.hls > o2.hls) {
					return 1;
				}else if(o1.hls < o2.hls) {
					return -1;
				}
				return 0;
			}
		});
		System.out.println(rs);
		//根据行列式 求出 特征值：组合的方式--正交的方式 
		//假定  rs中的都是 特征值
		List<Body> zhengjiaojuzhen = new ArrayList<>();
		for(Entity tz : rs) {
			Double lamda = tz.lamda;
			//
			Double[][] tis = copy(matrix);
			for(int i = 0; i < tis.length; i++) {
				tis[i][i] -= lamda;
			}
			//对角化
			duijiaohuaQiang(tis);
			//求出每个对角阵对应的n-r个特征向量
			//直接可以算出来！！
			
			for(int in  = 0; in < tis.length; in++ ) {
				if(Math.abs(tis[in][in]) < 0.001) {
					double[] tzVector = new double[tis[0].length];
					tzVector[in] = 1d;
					zhengjiaojuzhen.add(instance.new Body(tzVector, lamda));
				}
			}
		}
		System.out.println(zhengjiaojuzhen);
		return null;
	}
	
	class Entity{
		private Double hls;
		private Double lamda;
		public Entity(Double hls, Double lamda) {
			super();
			this.hls = hls;
			this.lamda = lamda;
		}
		@Override
		public String toString() {
			return "[hls=" + hls + ", lamda=" + lamda + "]";
		}
		
	}
	
	class Body{
		private double[] tzVetor;
		private Double tzz;
		public Body(double[] tzVector, Double tzz) {
			super();
			this.tzVetor = tzVector;
			this.tzz = tzz;
		}
		@Override
		public String toString() {
			return "[tzVetor=" + Arrays.toString(tzVetor) + ", tzz=" + tzz + "]";
		}
	}
}
