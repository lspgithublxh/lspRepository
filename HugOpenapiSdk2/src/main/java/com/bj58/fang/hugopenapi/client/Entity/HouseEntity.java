package com.bj58.fang.hugopenapi.client.Entity;

import java.util.ArrayList;
import java.util.List;

import com.bj58.fang.hugopenapi.client.annotations.Request;

/**
 * 没有@Request不验证，没有@Request/@UpdateRequest不不增加到map结构里
 * @ClassName:HouseEntity
 * @Description:
 * @Author lishaoping
 * @Version V1.0
 * @Package com.bj58.fang.hugopenapi.outentity
 */
public class HouseEntity {

	@Request(needVali = true, strLenth = "[1,20]")
	private String bianhao;// 经纪公司内部房源编号，<=20位，字母数字均可
	@Request(needVali = true, strLenth="[1,300]", failWhileNeed = "xiaoquId")
	private String xiaoqu;// 小区名称
	@Request(needVali = true, strLenth="[1,300]", failWhileNeed = "xiaoquId")
	private String quyu;// 区域
	@Request(needVali = true, failWhileNeed = "xiaoqu,quyu")
	private Integer xiaoquId;// 磐石小区id
	@Request(needVali = true, strLenth = "[10,30]", lianxuShuzi = 8)
	private String title;// 标题，>=10字，<=30字，不能连续------wait
	@Request(needVali = false, strLenth = "[2,150)")
	private String dizhi;// 详细地址，字符长度[2-150)个字符
	@Request(needVali = true, numBetween = "[1,15]")
	private Integer shi;// 室，1-15的整数
	@Request(needVali = true, numBetween = "[0,15]")
	private Integer ting;// 厅，0-15的整数
	@Request(needVali = true, numBetween = "[0,15]")
	private Integer wei;// 卫，0-15的整数
	@Request(needVali = true, numBetween = "[1,99]&[suozailouceng,99]")
	private Integer zonglouceng;// 总楼层，>=1,<=99,>=所在楼层，
	@Request(needVali = true, numBetween = "[-9,99]", notEqual = "0")
	private Integer suozailouceng;// 所在楼层，>=-9,!=0,<=99,整数
	@Request(needVali = true, numBetween = "[10,5000]", xiaoshuweishu = "[0,2]")
	private Double mianji;// 建筑面积，>=10,<=5000,最多两位小数
	@Request(needVali = true, enumVal = "1,2,3,4,5,6,7,8,9,10")
	private Integer chaoxiang;// 朝向
	@Request(needVali = true, enumVal = "1,2,4,6")
	private Integer zhuangxiuqingkuang;// 装修情况
	@Request(needVali = true, enumVal = "1,2,4,5,6,7,8,9,10")
	private Integer fangwuleixing;// 房屋类型
	@Request(needVali = false, strLenth = "[0,30]", dependOn = "loudongdanwei")
	private String loudong;// 户室号-栋/弄/座/号/号楼/胡同，<=30字，且单位必填
	@Request(needVali = false, strLenth = "[0,30]", dependOn = "danyuandanwei")
	private String danyuan;// 户室号-单元/栋/幢/号/号楼，<=30字，且单位必填
	@Request(needVali = false, strLenth = "[0,30]")
	private String menpaihao;// 户室号-室，<=30字，且单位必填----单位未定义

	@Request(needVali = false, enumVal = "1,2,3,4,5,6")
	private String loudongdanwei;// 户室号-栋单位
	@Request(needVali = false, enumVal = "1,2,3,4,5")
	private String danyuandanwei;// 户室号-单元单位
	@Request(needVali = false, enumVal = "1,0")
	private Integer dianti;// 是否有电梯
	@Request(needVali = false, enumVal = "1,2,3")
	private Integer heating;// 房屋供暖情况
	@Request(needVali = true)
	private Long jiage;// 总价，单位：元
	@Request(needVali = false, strLenth = "[20,300]", lianxuShuzi = 8, allowBadChar = false)
	private String fangyuanxiangqing;// 房源详情>=20字，<=300字，不支持图片模板与富文本,不能连续8位数字及以上，不能特殊字符
	@Request(needVali = true, jsonVali = "PicEntity", isJsonArray = true, arrNum = "{\"pic\":{\"1\":\"[3,10]\",\"2\":\"[1,3]\",\"3\":\"[0,10]\"}}")
	private String pic;// 房源图片（json格式），图片规则

	//传入
	private List<PicEntity> picList = new ArrayList<PicEntity>();
		
	public List<PicEntity> getPicList() {
		return picList;
	}

	/**
	 * 房源图片，图片规则 @如果设置了pic则不填
	 */
	public void setPicList(List<PicEntity> picList) {
		StringBuilder builder = new StringBuilder();
		if(picList != null && picList.size() > 0) {
			builder.append("[");
			for(PicEntity entity : picList) {
				builder.append(String.format("{\"category\":\"%s\",\"url\":\"%s\",\"iscover\":\"%s\"},", 
						entity.getCategory(), entity.getUrl(), entity.getIscover()));
			}
			builder.replace(builder.length() - 1, builder.length(), "]");//逗号替换为]
		}
		this.pic = builder.toString();
		this.picList = picList;
	}
	/**
	 * 经纪公司内部房源编号，<=20位，字母数字均可
	 */
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}

	public String getXiaoqu() {
		return xiaoqu;
	}

	/**
	 * 小区名称
	 */
	public void setXiaoqu(String xiaoqu) {
		this.xiaoqu = xiaoqu;
	}

	public String getQuyu() {
		return quyu;
	}

	/**
	 * 区域
	 */
	public void setQuyu(String quyu) {
		this.quyu = quyu;
	}

	public Integer getXiaoquId() {
		return xiaoquId;
	}

	/**
	 * 磐石小区id
	 */
	public void setXiaoquId(Integer xiaoquId) {
		this.xiaoquId = xiaoquId;
	}

	public String getTitle() {
		return title;
	}

	/**
	 * 标题，>=10字，<=30字，不能连续------wait
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDizhi() {
		return dizhi;
	}

	/**
	 * 详细地址，字符长度[2-150)个字符
	 */
	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}

	public Integer getShi() {
		return shi;
	}

	/**
	 * 室，1-15的整数
	 */
	public void setShi(Integer shi) {
		this.shi = shi;
	}

	public Integer getTing() {
		return ting;
	}

	/**
	 * 厅，0-15的整数
	 */
	public void setTing(Integer ting) {
		this.ting = ting;
	}

	public Integer getWei() {
		return wei;
	}

	/**
	 * 卫，0-15的整数
	 */
	public void setWei(Integer wei) {
		this.wei = wei;
	}

	public Integer getZonglouceng() {
		return zonglouceng;
	}

	/**
	 * 总楼层，>=1,<=99,>=所在楼层，
	 */
	public void setZonglouceng(Integer zonglouceng) {
		this.zonglouceng = zonglouceng;
	}

	public Integer getSuozailouceng() {
		return suozailouceng;
	}

	/**
	 * 所在楼层，>=-9,!=0,<=99,整数
	 */
	public void setSuozailouceng(Integer suozailouceng) {
		this.suozailouceng = suozailouceng;
	}

	public Double getMianji() {
		return mianji;
	}

	/**
	 * 建筑面积，>=10,<=5000,最多两位小数
	 */
	public void setMianji(Double mianji) {
		this.mianji = mianji;
	}

	public Integer getChaoxiang() {
		return chaoxiang;
	}

	/**
	 * 朝向
	 */
	public void setChaoxiang(Integer chaoxiang) {
		this.chaoxiang = chaoxiang;
	}

	public Integer getZhuangxiuqingkuang() {
		return zhuangxiuqingkuang;
	}

	/**
	 * 装修情况
	 */
	public void setZhuangxiuqingkuang(Integer zhuangxiuqingkuang) {
		this.zhuangxiuqingkuang = zhuangxiuqingkuang;
	}

	public Integer getFangwuleixing() {
		return fangwuleixing;
	}

	/**
	 * 房屋类型
	 */
	public void setFangwuleixing(Integer fangwuleixing) {
		this.fangwuleixing = fangwuleixing;
	}

	public String getLoudong() {
		return loudong;
	}

	/**
	 * 户室号-栋/弄/座/号/号楼/胡同，<=30字，且单位必填
	 */
	public void setLoudong(String loudong) {
		this.loudong = loudong;
	}

	public String getDanyuan() {
		return danyuan;
	}

	/**
	 * 户室号-单元/栋/幢/号/号楼，<=30字，且单位必填
	 */
	public void setDanyuan(String danyuan) {
		this.danyuan = danyuan;
	}

	public String getMenpaihao() {
		return menpaihao;
	}

	/**
	 * 户室号-室，<=30字，且单位必填----单位未定义
	 */
	public void setMenpaihao(String menpaihao) {
		this.menpaihao = menpaihao;
	}

	public String getLoudongdanwei() {
		return loudongdanwei;
	}

	/**
	 * 户室号-栋/弄/座/号/号楼/胡同，<=30字，且单位必填
	 */
	public void setLoudongdanwei(String loudongdanwei) {
		this.loudongdanwei = loudongdanwei;
	}

	public String getDanyuandanwei() {
		return danyuandanwei;
	}

	/**
	 * 户室号-单元/栋/幢/号/号楼，<=30字，且单位必填
	 */
	public void setDanyuandanwei(String danyuandanwei) {
		this.danyuandanwei = danyuandanwei;
	}

	public Integer getDianti() {
		return dianti;
	}

	/**
	 * 是否有电梯
	 */
	public void setDianti(Integer dianti) {
		this.dianti = dianti;
	}

	public Integer getHeating() {
		return heating;
	}

	/**
	 * 房屋供暖情况
	 */
	public void setHeating(Integer heating) {
		this.heating = heating;
	}

	public Long getJiage() {
		return jiage;
	}

	/**
	 * 总价，单位：元
	 */
	public void setJiage(Long jiage) {
		this.jiage = jiage;
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

	public String getPic() {
		return pic;
	}

	/**
	 * 房源图片（json格式），图片规则  @如果设置了picList则不填
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}

}
