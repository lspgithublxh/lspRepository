package com.bj58.fang.hugopenapi.client.enumn.rentdetail;

public enum RenterWork {
	jiaoyupeixun(20), renlixingzheng(21), shengchanzhizao(9), nengyuanhuagong(8), xueshukeyan(19), ruanjian(
			1), zixunguwen(18), falvfanyi(17), caiwukuaiji(3), jianzhufangdichan(16), tongxindianzi(2), yingshiyishu(
					15), yinxingbaoxian(5), xiezuochuban(14), zhengquanjinrong(4), meitiguanggao(13), shichanggongguan(
							12), maoyi(7), shengwuyiliao(11), caigouwuliu(6), chuangyezhe(30), qita(31), kuaixiao(
									10), hulianwang(0), yundongyuan(27), huanbao(26), ziyouzhiye(29), xuesheng(
											28), xiaoshou(23), fuwuye(22), nonglinmuyu(25), gongwuyuan(24);
	private int value;

	private RenterWork(int value) {
		this.value = value;
	}
	public String getValue() {
		return value + "";
	}
}