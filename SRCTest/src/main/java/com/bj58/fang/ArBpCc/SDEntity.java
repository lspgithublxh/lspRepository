package com.bj58.fang.ArBpCc;

/**
 * serviceDes
 * @ClassName:SDEntity
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月7日
 * @Version V1.0
 * @Package com.bj58.fang.ArBpCc
 */
public class SDEntity {

	private String name;
	private String url;//应该独立，name其实意义不大---或者name下包含很多个url--要逐个申请
	private String[] iop;
	private long regTime;//首次服务注册时间
	private long lastUpdateTime;//上次服务更新时间
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String[] getIop() {
		return iop;
	}
	public void setIop(String[] iop) {
		this.iop = iop;
	}
	
	public long getRegTime() {
		return regTime;
	}
	public void setRegTime(long regTime) {
		this.regTime = regTime;
	}
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public SDEntity(String name, String url, String[] iop) {
		super();
		this.name = name;
		this.url = url;
		this.iop = iop;
	}
	
}
