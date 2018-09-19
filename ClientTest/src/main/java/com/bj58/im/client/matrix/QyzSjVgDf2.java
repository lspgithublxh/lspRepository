package com.bj58.im.client.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 行列式的计算：具体矩阵 用初等变换；；含有lamda用  从内到外增长的方式进行 逼近 计算
 * @ClassName:QyzSjVgDf
 * @Description:
 * @Author lishaoping
 * @Date 2018年9月18日
 * @Version V1.0
 * @Package com.bj58.im.client.matrix
 */
public class QyzSjVgDf2 {

	public static void main(String[] args) {
//		Double[][] s = {{2d,3d},{2d,4d}};
//		printMatrix(s);
//		Double[][] s2 = zhuanzhi(s);
//		printMatrix(s2);
//		Double[][] s3 = chengfa(s2, s);
//		printMatrix(s3);
//		Double hls = hanglieshi(s);
//		System.out.println(hls);
//		Double hls2 = hanglieshi(s2);
//		System.out.println(hls2);
//		Double hls3 = hanglieshi(s3);
//		System.out.println(hls3);
		//对角化 专门测试
//		duijiaohua(s);
//		printMatrix(s);
//		duijiaohuaQiang(s);
//		printMatrix(s);
		//计算特征值 和 特征向量
		try {
//			printMatrix(s);
//			bijinHanglishi(s);
//			printMatrix(s3);
//			bijinHanglishi(s3);
//			System.out.println("------------------");
//			Double[][] sx = {{1d,2d},{3d,4d}};
//			qysvdSee(sx);
//			Double[][] sx2 = {{14d,2d},{3d,4d}};
//			qysvdSee(sx2);
			Double[][] sx3 = {{10d,2d},{3d,4d}};
			qysvdSee(sx3);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void qysvdSee(Double[][] A) {
		Double[][] ATA = chengfa(zhuanzhi(A), A);
		List<Body> tzlist = bijinHanglishi(ATA);
		Double[][] V = new Double[A[0].length][A[0].length];
		int i = 0;
		Double[][] U = new Double[A.length][A.length];
		Double[][] XGM = new Double[A.length][A[0].length];
		for(Body bo : tzlist) {
			Double[] vi = doubleToDouble(bo.tzVetor);
			danweihua(vi);
			V[i++] = vi;//转置
			Double[][] xv = new Double[1][];
			xv[0] = vi;
			Double[][] ui = chengfa(A, zhuanzhiRight(xv));
			ui = zhuanzhiRight(ui);
			Double[] ui_ = ui[0];
			Double xgm = danweihua(ui_);
			Double[] xa = new Double[A[0].length];
			xa[i - 1] = xgm;
			replaceNullLing(xa);
			XGM[i - 1] = xa;
			U[i - 1] = ui_;//
		}
		//计算U
		System.out.println("U:");
		printMatrix(U);
		System.out.println("XGM:");
		printMatrix(XGM);
		System.out.println("V:");
		printMatrix(V);
	}
	
	
	private static void replaceNullLing(Double[] xa) {
		for(int i = 0 ; i < xa.length; i++) {
			if(xa[i] == null) {
				xa[i] = 0d;
			}
		}
	}

	private static Double danweihua(Double[] vi) {
		Double r = 0d;
		for(Double s : vi) {
			r += s * s;
		}
		r = Math.sqrt(r);
		for(int i = 0; i < vi.length; i++) {
			vi[i] /= r; 
		}
		return r;
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
	
//	private static Double[] zhuanzhi(Double[] matrix) {
//		Double[] rs = new Double[matrix.length];
//		for(int j = 0; j < matrix.length; j++) {
//			rs[j] = matrix[j];
//		}
//		return rs;
//	}
	
	private static Double[][] zhuanzhiRight(Double[][] matrix) {
		Double[][] rs = new Double[matrix[0].length][matrix.length];
		for(int i = 0; i < matrix.length; i++) {
			Double[] line = matrix[i];
			for(int j = 0; j < line.length; j++) {
				rs[j][i] = line[j];
			}
		}
		return rs;
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
			for(int k = 0; k < matrix2[0].length; k++) {
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
			if(Math.abs(matrix[c][j]) > 0.001) {
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
	
	/**
	 * 强交换 用来计算 特征向量用处不大
	 * @param 
	 * @author lishaoping
	 * @Date 2018年9月18日
	 * @Package com.bj58.im.client.matrix
	 * @return void
	 */
	private static void duijiaohuaQiang(Double[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			jiaohuanij(matrix, i, i);
			if(Math.abs(matrix[i][i]) <= 0.001) {//为0的一行
				//往上走：如果有非0的，那么交换;第一次为0就交换
				boolean hasLou = false;
				for(int k = i - 1; k >= 0; k--) {
					if(Math.abs(matrix[i][i]) > 0.001) {//缺条件----前面都是0 这一列不为0
						//qie
						boolean keyi = true;
						for(int cc = 0; cc < i; cc++){//列
							if(Math.abs(matrix[k][cc]) > 0.001) {
								keyi = false;
								break;
							}
						}
						if(keyi) {
							jiaohuanijPure(matrix, i, k);
							hasLou = true;
							break;
						}
					}
				}
//				if(!hasLou) continue;//其实没必要再动，因为肯定都是0了
			}
			for(int j = i + 1; j < matrix.length; j++) {
				Double k = Math.abs(matrix[i][i]) < 0.001 ? 0 : - matrix[j][i] / matrix[i][i];
				chengkaddto(matrix, i, j, k);
			}
			
			for(int j = i - 1; j >= 0; j--) {
				Double k = Math.abs(matrix[i][i]) < 0.001 ? 0 : - matrix[j][i] / matrix[i][i];
				chengkaddto(matrix, i, j, k );
			}
		}
	}
	
	private static Double daicanHanglishi(Double[][] matrix, Double lamda) {
		for(int i = 0; i < matrix.length; i++) {
			matrix[i][i] -= lamda; 
		}
		return hanglieshi(matrix);
	}
	
	private static Double[] copy2(Double[] matrix) {
		Double[] cc = new Double[matrix.length];
		for(int i = 0; i < matrix.length; i++) {
			cc[i] = matrix[i];
		}
		return cc;
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

	static QyzSjVgDf2 instance = new QyzSjVgDf2();
	
	/**
	 * 特征值 和特征向量
	 * @param 
	 * @author lishaoping
	 * @Date 2018年9月19日
	 * @Package com.bj58.im.client.matrix
	 * @return Double
	 */
	private static List<Body> bijinHanglishi(Double[][] matrix) {
		List<Entity> rs = new ArrayList<>();
		Double startLeft = -100d;
		Double startRight = 0d;
		
		Double currIndex = startLeft;
		boolean left = true;
		Double kuan = 100d;
		Double step = 0.1d;
		double yueshu = 0.01d;
		while(true) {
 			searchThers(matrix, currIndex, rs,step, yueshu);
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
				if(rs.size() < matrix.length) {
					yueshu += yueshu;
					startLeft = -100d;
					startRight = 0d;
					currIndex = startLeft;
					System.out.println("yueshu:" + yueshu);
				}else {
					break;
				}
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
		List<Entity> guolv = new ArrayList<>();
		for(Entity old : rs) {
			boolean has = false;
			for(Entity guo : guolv) {
				if(Math.abs(guo.lamda - old.lamda) < 0.1) {//相隔太近  认为是一个lamda
					has = true;
					break;
				}
			}
			if(!has) {
				guolv.add(old);
			}
		}
		rs = guolv;
//		System.out.println(rs);
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
//			printMatrix(tis);
			int count = 0;
			double f = 0.001;
//			duijiaohuaQiang(tis);
			duijiaohua(tis);
			while(true) {
				int addC = getTzxl3(zhengjiaojuzhen, lamda, tis, f);
				if(count++ == 1000 || addC > 0) {
					break;
				}else {
					f += f;
					System.out.println("f:" + f);
				}
			}
			
			//求出每个对角阵对应的n-r个特征向量
			//直接可以算出来！！
			
//			getTzxl2(zhengjiaojuzhen, lamda, tis);
//			getTzxl1(zhengjiaojuzhen, lamda, tis);
		}
		System.out.println(zhengjiaojuzhen);
		return zhengjiaojuzhen;
	}

	private static void searchThers(Double[][] matrix,double start, List<Entity> rs, double bujin, double yueshu) {
		int searchCount = 100000;
		int lessEnough = 0;
		int zengzhang = 0;
		Double oldhls = 1000d;
		for(Double index = 0d; index <= 100d; index += bujin) {
			Double hls = daicanHanglishi(copy(matrix), index + start);
//				System.out.println("hls:" + hls);//非常耗时--绝大部分时间
			if(Math.abs(hls) < yueshu) {//认为行列式备选 的特征值
				rs.add(instance.new Entity(Math.abs(hls),index + start));
			}
			if(searchCount-- == 0) {
				break;
			}
			if(Math.abs(hls) < 0.05) {
				bujin = 0.00001;//即便很小，抖动也很大;此时也可以当作是特征值了--左右进行有必要
				if(lessEnough++ > 10) {
					rs.add(instance.new Entity(Math.abs(hls),index + start));
					bujin = 0.1;
				}
				zengzhang = 0;
			}else if(Math.abs(hls) < 0.1) {
				bujin = 0.00001;zengzhang = 0;
//					System.out.println("hls1:" + hls);
			}else if(Math.abs(hls) < 1) {	
				bujin = 0.0001;zengzhang = 0;
//					System.out.println("hls2:" + hls);
			}else if(Math.abs(hls) < 5) {
				bujin = 0.0001;
				if(Math.abs(hls) > oldhls && Math.abs(hls) - oldhls < 0.5) {
					if(zengzhang++ > 10) {
						rs.add(instance.new Entity(Math.abs(hls),index + start));
						bujin = 0.1;
						zengzhang = 0;
					}
				}
//					System.out.println("hls3:" + hls);
			}else {
				zengzhang = 0;
			}
			
			oldhls = Math.abs(hls);
		}
	}

	private static int getTzxl3(List<Body> zhengjiaojuzhen, Double lamda, Double[][] tis, double yueshu) {
		//先对角化--强或者弱都可以
		//某一行：
		//从右到左 的  所有非0 下标-元素值
		//去除已经定的 下标 
		//对剩余下标  个数 2  > 2判断，  来从已有向量 完善位置的值  、 或者  在已有 基础上  生成 新的向量    
		//已定下标 map  增加  定 的下标
		//下一行
		List<Integer> yidingxiabiao = new ArrayList<>();
		List<Integer> currFeilingxiabiao = new ArrayList<>();
		List<Integer> ziyouxiabiao = new ArrayList<>();
		List<Double[]> rs = new ArrayList<>();
		for(int i = tis.length - 1 ; i >= 0; i--) {
			currFeilingxiabiao.clear();
			for(int j = i; j <= tis.length - 1; j++) {
				if(Math.abs(tis[i][j]) > yueshu) {
					currFeilingxiabiao.add(j);
					ziyouxiabiao.remove((Integer)j);//本行开始，不再自由
				}else {
					if(!ziyouxiabiao.contains(j) && !yidingxiabiao.contains(j)) {
						ziyouxiabiao.add(j);//自由
					}
				}
			}
			List<Integer> rest = new ArrayList<Integer>(currFeilingxiabiao);
			currFeilingxiabiao.removeAll(yidingxiabiao);
			rest.removeAll(currFeilingxiabiao);
			Double restVal = null;
//			if(rest.size() > 0) {
			
			
//			}
			if(rs.size() == 0 && currFeilingxiabiao.size() > 0) {//为空的清醒
				rs = geneNull(currFeilingxiabiao.size() == 1 ? 1 : currFeilingxiabiao.size() - 1, tis[0].length);
			}
			if(currFeilingxiabiao.size() == 2) {
				for(Double[] x : rs) {
					restVal = 0d;
					for(Integer xia : rest) {//用定值来算的 rest 就是yidingxiabiao
						restVal += tis[i][xia] * x[xia];//放在里面，因为 需要值-不仅系数
					}
					int xiab = currFeilingxiabiao.get(currFeilingxiabiao.size() - 1);
					x[xiab] = 1d;
					x[currFeilingxiabiao.get(0)] = -(tis[i][xiab] * x[xiab] + restVal) / tis[i][currFeilingxiabiao.get(0)];
				}
				
			}
			if(currFeilingxiabiao.size() == 1) {
				for(Double[] x : rs) {
					restVal = 0d;
					for(Integer xia : rest) {//用定值来算的 rest 就是yidingxiabiao
						restVal += tis[i][xia] * x[xia];//放在里面，因为 需要值-不仅系数
					}
					x[currFeilingxiabiao.get(0)] = -restVal / tis[i][currFeilingxiabiao.get(0)];
				}
			}
			
			if(currFeilingxiabiao.size() > 2) {
				int size = currFeilingxiabiao.size();
				List<Double[]> rs2 = new ArrayList<>();
				for(Double[] x : rs) {
					for(int ind = 1; ind < size; ind++) {
						Double[] xcopy = copy2(x);
						xcopy[currFeilingxiabiao.get(ind)] = 1d;
						for(int dex = 0; dex < currFeilingxiabiao.size(); dex++) {
							if(dex != ind) {
								xcopy[currFeilingxiabiao.get(dex)] = 0d;
							}
						}
						restVal = 0d;
						for(Integer xia : rest) {//用定值来算的 rest 就是yidingxiabiao
							restVal += tis[i][xia] * xcopy[xia];//放在里面，因为 需要值-不仅系数
						}
						xcopy[currFeilingxiabiao.get(0)] = -(tis[i][currFeilingxiabiao.get(ind)] * xcopy[currFeilingxiabiao.get(ind)] + restVal) / tis[i][currFeilingxiabiao.get(0)];
						rs2.add(xcopy);
					}
				}
				rs = rs2;
			}
			yidingxiabiao.addAll(currFeilingxiabiao);
		}
		//如果访问完了，还是一个已定下标 也 没有，说明是全0矩阵---开始加正交向量
		if(yidingxiabiao.size() == 0) {
			for(int i = 0; i < tis[0].length; i++) {
				double[] col = new double[tis.length];
				col[i] = 1d;
				rs.add(doubleToDouble(col));
			}
		}
		//还存在自由变量的 -----应该都是同列号，可能不止一列 需要再次扩张
		if(ziyouxiabiao.size() > 0) {
			List<Double[]> ls = new ArrayList<>();
			for(Double[] x : rs) {
				for(int i = 0; i < ziyouxiabiao.size(); i++) {
					Double[] cx = copy2(x);
					cx[ziyouxiabiao.get(i)] = 1d;
					for(int j = 0; j < ziyouxiabiao.size(); j++) {
						if(j != i) {
							cx[ziyouxiabiao.get(j)] = 0d;
						}
					}
					ls.add(cx);
				}
			}
			rs = ls;
		}
		int cc = 0;
		for(Double[] x : rs) {
			if(lingxiangl(x)) continue;
			cc++;
			zhengjiaojuzhen.add(instance.new Body(x, lamda));
		}
		return cc;
	}
	
	private static boolean lingxiangl(Double[] x) {
		boolean is = true;
		for(Double y : x) {
			if(Math.abs(y) > 0.0001) {//认为非0向量
				is = false;
			}
		}
		return is;
	}

	private static List<Double[]> geneNull(int line, int col) {
		List<Double[]> s = new ArrayList<>();
		for(int i = 0 ; i < line; i++) {
			Double[] c1 = new Double[col];
			s.add(c1);
		}
		return s;
	}
	
	private static Double[] doubleToDouble(double[] col) {
		Double[] s = new Double[col.length];
		int i = 0;
		for(double d : col) {
			s[i++] = d;
		}
		return s;
	}
	
	private static void getTzxl2(List<Body> zhengjiaojuzhen, Double lamda, Double[][] tis) {
		//已经有的定值
		List<Integer> lingzhi = new ArrayList<>();
		//已经有的任意值---计算当前任意值用
		List<Integer> renyizhi = new ArrayList<>();//未使用的
		List<Double[]> rs = new ArrayList<>();
		for(int i = tis.length - 1 ; i >= 0; i--) {
			if(i == tis.length - 1) {
				if(Math.abs(tis[i][i]) < 0.001) {
					renyizhi.add(i);
				}else {
					Double[] one = new Double[tis.length];
					one[i] = 0d;
					rs.add(one);
					lingzhi.add(i);
				}
			}else {
				int zongbianliang = tis[0].length - i;
				int lingzhigeshu = lingzhi.size();//0值
				int renyizhigeshu = renyizhi.size();
				if(lingzhigeshu == zongbianliang - 1) {
					lingzhi.add(i);
					continue;
				}
//				if(Math.abs(tis[i][i]) < 0.001) {
//					renyizhi.add(i);
//					continue;
//				}
				//当前行 任意变量 个数
				List<Integer> currRenyi = new ArrayList<>();
				for(int j = i; j < tis[0].length; j++) {
					if(Math.abs(tis[i][j]) < 0.001) {
						if(renyizhi.contains(j)) {
							currRenyi.add(j);
						}
					}
				}
				
				//计算ii 位置的变量值
			}
		}
	}

	private static void getTzxl1(List<Body> zhengjiaojuzhen, Double lamda, Double[][] tis) {
		for(int in  = 0; in < tis.length; in++ ) {
			if(Math.abs(tis[in][in]) < 0.001) {
				double[] tzVector = new double[tis[0].length];
				tzVector[in] = 1d;
				zhengjiaojuzhen.add(instance.new Body(tzVector, lamda));
			}
		}
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
		
		public Body(Double[] tzVector, Double tzz) {
			super();
			double[] s = new double[tzVector.length];
			for(int i = 0; i < tzVector.length; i++) {
				s[i] = tzVector[i];
			}
			this.tzVetor = s;
			this.tzz = tzz;
		}
		
		@Override
		public String toString() {
			return "[tzVetor=" + Arrays.toString(tzVetor) + ", tzz=" + tzz + "]";
		}
	}
}
