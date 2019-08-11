package com.bj58.fang.hugopenapi.client.Entity;

import com.bj58.fang.hugopenapi.client.annotations.Request;

public class ESFEntity extends HouseEntity {

	@Request(needVali = false, strLenth = "[0,20]")
	private String beianbianhao;// 备案编号，<=20位，字母数字均可
	@Request(needVali = false, numBetween = "[10,5000]&[mianji,5000]", xiaoshuweishu = "[0,2]")
	private Double taoneimianji;// 套内使用面积，>=10,<=5000,最多两位小数；且小于建筑面积
	@Request(needVali = true, numBetween = "[1900,(%year+3)]") // 运算表达式
	private Integer jianzhuniandai;// 建筑年代，>=1900,<=当前年+3,整数
	@Request(needVali = false, enumVal = "1,2,3")
	private Integer jianzhuxingshi;// 建筑形式
	@Request(needVali = false, enumVal = "1,2")
	private Integer isyishoufang;// 是否一手房
	@Request(needVali = false, enumVal = "1,2,3,4,5,6,7")
	private String fushuxinxi;// 附属信息（赠送信息）
	@Request(needVali = false, enumVal = "1,70,2,50,3,40")
	private Integer chanquannianxian;// 产权年限
	@Request(needVali = false, enumVal = "1,2,3,4,5,6")
	private Integer chanquanleixing;// 产权类型
	@Request(needVali = false, enumVal = "1,2")
	private Integer weiyizhufang;// 唯一住房
	@Request(needVali = false, enumVal = "1,2,3")
	private Integer fangbennianxian;// 房本年限（房屋年限）
	@Request(needVali = false)
	private Long shoufu;// 首付要求，单位：元
	@Request(needVali = false, numBetween = "[0.5,3.0]")
	private Double yongjin;// 佣金，单位：百分比。数值为总价乘以百分比，只能填写[0.5-3.0],一手房可填0
	@Request(needVali = false, strLenth = "[20,300]", lianxuShuzi = 8, allowBadChar = false)
	private String fangyuanxiangqing;// 房源详情>=20字，<=300字，不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	@Request(needVali = false, strLenth = "[20,300]", lianxuShuzi = 8, allowBadChar = false)
	private String yezhuxintai;// 业主心态 ≥20字，≤300字 不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	@Request(needVali = false, strLenth = "[20,300]", lianxuShuzi = 8, allowBadChar = false)
	private String xiaoqupeitao;// 小区配套 ≥20字，≤300字 不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	@Request(needVali = false, strLenth = "[20,300]", lianxuShuzi = 8, allowBadChar = false)
	private String fuwujieshao;// 服务介绍 ≥20字，≤300字 不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	@Request(needVali = false, enumVal = "1,2")
	private String fuwuleixing;// 服务类型
	@Request(needVali = false)
	private String fangyuantag;// 房源标签，枚举值通过接口获取
	
	@Request(needVali = true, xiaoshuweishu="[0,2]")
	private Long jiage;// 总价，单位：元  //覆盖父类属性--覆盖之后也必须加get/set方法 //, numBetween="[2,100000]"
	
	public String getBeianbianhao() {
		return beianbianhao;
	}

	/**
	 * 备案编号，<=20位，字母数字均可
	 */
	public void setBeianbianhao(String beianbianhao) {
		this.beianbianhao = beianbianhao;
	}

	public Double getTaoneimianji() {
		return taoneimianji;
	}

	/**
	 * 套内使用面积，>=10,<=5000,最多两位小数；且小于建筑面积
	 */
	public void setTaoneimianji(Double taoneimianji) {
		this.taoneimianji = taoneimianji;
	}

	public Integer getJianzhuniandai() {
		return jianzhuniandai;
	}

	/**
	 * 建筑年代，>=1900,<=当前年+3,整数
	 */
	public void setJianzhuniandai(Integer jianzhuniandai) {
		this.jianzhuniandai = jianzhuniandai;
	}

	public Integer getJianzhuxingshi() {
		return jianzhuxingshi;
	}

	/**
	 * 建筑形式
	 */
	public void setJianzhuxingshi(Integer jianzhuxingshi) {
		this.jianzhuxingshi = jianzhuxingshi;
	}

	public Integer getIsyishoufang() {
		return isyishoufang;
	}

	/**
	 * 是否一手房
	 */
	public void setIsyishoufang(Integer isyishoufang) {
		this.isyishoufang = isyishoufang;
	}

	public String getFushuxinxi() {
		return fushuxinxi;
	}

	/**
	 * 附属信息（赠送信息）
	 */
	public void setFushuxinxi(String fushuxinxi) {
		this.fushuxinxi = fushuxinxi;
	}

	public Integer getChanquannianxian() {
		return chanquannianxian;
	}

	/**
	 * 产权年限
	 */
	public void setChanquannianxian(Integer chanquannianxian) {
		this.chanquannianxian = chanquannianxian;
	}

	public Integer getChanquanleixing() {
		return chanquanleixing;
	}

	/**
	 * 产权类型
	 */
	public void setChanquanleixing(Integer chanquanleixing) {
		this.chanquanleixing = chanquanleixing;
	}

	public Integer getWeiyizhufang() {
		return weiyizhufang;
	}

	/**
	 * 唯一住房
	 */
	public void setWeiyizhufang(Integer weiyizhufang) {
		this.weiyizhufang = weiyizhufang;
	}

	public Integer getFangbennianxian() {
		return fangbennianxian;
	}

	/**
	 * 房本年限（房屋年限）
	 */
	public void setFangbennianxian(Integer fangbennianxian) {
		this.fangbennianxian = fangbennianxian;
	}

	public Long getShoufu() {
		return shoufu;
	}

	/**
	 * 首付要求，单位：元
	 */
	public void setShoufu(Long shoufu) {
		this.shoufu = shoufu;
	}

	public Double getYongjin() {
		return yongjin;
	}

	/**
	 * 佣金，单位：百分比。数值为总价乘以百分比，只能填写[0.5-3.0],一手房可填0
	 */
	public void setYongjin(Double yongjin) {
		this.yongjin = yongjin;
	}

	public String getFangyuanxiangqing() {
		return fangyuanxiangqing;
	}

	/**
	 * 房源详情>=20字，<=300字，不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	 */
	public void setFangyuanxiangqing(String fangyuanxiangqing) {
		this.fangyuanxiangqing = fangyuanxiangqing;
	}

	public String getYezhuxintai() {
		return yezhuxintai;
	}

	/**
	 * 业主心态 ≥20字，≤300字 不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	 */
	public void setYezhuxintai(String yezhuxintai) {
		this.yezhuxintai = yezhuxintai;
	}

	public String getXiaoqupeitao() {
		return xiaoqupeitao;
	}

	/**
	 * 小区配套 ≥20字，≤300字 不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	 */
	public void setXiaoqupeitao(String xiaoqupeitao) {
		this.xiaoqupeitao = xiaoqupeitao;
	}

	public String getFuwujieshao() {
		return fuwujieshao;
	}

	/**
	 * 服务介绍 ≥20字，≤300字 不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	 */
	public void setFuwujieshao(String fuwujieshao) {
		this.fuwujieshao = fuwujieshao;
	}

	public String getFuwuleixing() {
		return fuwuleixing;
	}

	/**
	 * 服务类型
	 */
	public void setFuwuleixing(String fuwuleixing) {
		this.fuwuleixing = fuwuleixing;
	}

	public String getFangyuantag() {
		return fangyuantag;
	}

	/**
	 * 房源标签，枚举值通过接口获取
	 */
	public void setFangyuantag(String fangyuantag) {
		this.fangyuantag = fangyuantag;
	}

	/**
	 *  总价，单位：元
	 */
	public Long getJiage() {
		return jiage;
	}

	public void setJiage(Long jiage) {
		this.jiage = jiage;
	}

}
