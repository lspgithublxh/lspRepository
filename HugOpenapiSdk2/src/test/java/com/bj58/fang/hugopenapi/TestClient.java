package com.bj58.fang.hugopenapi;

import java.util.ArrayList;
import java.util.List;

import com.bj58.fang.hugopenapi.client.Entity.BrokerESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.BrokerZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.CompanyESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.CompanyZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.PicEntity;
import com.bj58.fang.hugopenapi.client.Entity.RentedDetailsEntity;
import com.bj58.fang.hugopenapi.client.enumn.AddOrUpdate;
import com.bj58.fang.hugopenapi.client.enumn.house.Chaoxiang;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterAge;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterType;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterWork;
import com.bj58.fang.hugopenapi.client.exception.ESFException;
import com.bj58.fang.hugopenapi.client.exception.ZFException;
import com.bj58.fang.hugopenapi.client.service.ESFService;
import com.bj58.fang.hugopenapi.client.service.InitService;
import com.bj58.fang.hugopenapi.client.service.XQService;
import com.bj58.fang.hugopenapi.client.service.ZFService;

public class TestClient {

	public static void main(String[] args) {
		InitService.init("b6ab5dc63efb2ea7c7de1317bd9a9d58", "180def1ba07798ba4447790830358be3", 10000000l);
//		BrokerESFEntity entity = new BrokerESFEntity();
//		System.out.println(entity.getClass().getName().equals(BrokerESFEntity.class.getName()));
		//发布二手房！
		publishCompanyESF(AddOrUpdate.Add);
//		publishCompanyESF(AddOrUpdate.Update);
		//租房
//		publishCompanyZF(AddOrUpdate.Add);
//		publishCompanyZF(AddOrUpdate.Update);
		
//		publishBrokeESF(AddOrUpdate.Add);
//		publishBrokeESF(AddOrUpdate.Update);
//		
//		publishBrokerZF(AddOrUpdate.Add);
//		publishBrokerZF(AddOrUpdate.Update);
		
		
//		xiaoquGet();
		
	}

	private static void xiaoquGet() {
		String xq = XQService.getInstance().matchXiaoqu("远洋", 10);
		System.out.println(xq);
	}

	private static void publishCompanyZF(AddOrUpdate operate) {
		CompanyZFEntity entity = new CompanyZFEntity();

		entity.setRentType(1);
		entity.setPaymentTerms(1);
		entity.setRoomType(1);
		entity.setBuildingArea(300d);
		entity.setBianhao("123");
		entity.setXiaoqu("新建小区");
		entity.setQuyu("朝阳");
//		entity.setXiaoquId(1);
		entity.setTitle("好的顶顶顶顶顶顶顶顶顶休息休息 title");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);

		entity.setMianji(30d);
		entity.setChaoxiang(1);
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(120l);
		List<PicEntity> picList = new ArrayList<PicEntity>();
		for(int i = 0; i  < 9; i++) {
			PicEntity pic = new PicEntity();
			pic.setCategory( i / 3 + 1 + "");
			pic.setIscover("0");
			pic.setUrl(String.format("example.pic/pic_%s.jpg", i));
			picList.add(pic);
		}
		entity.setPicList(picList);
		List<RentedDetailsEntity> renList = new ArrayList<RentedDetailsEntity>();
		for(int i = 0; i  < 9; i++) {
			RentedDetailsEntity ren = new RentedDetailsEntity();
			ren.setRenterAge(RenterAge.age_0_20.getValue());
			ren.setRenterNum("1");
			ren.setRenterType(RenterType.nansheng.getValue());
			ren.setRenterWork(RenterWork.caiwukuaiji.getValue());
			renList.add(ren);
		}
		entity.setRentedDetailsList(renList);
//		entity.setRentedDetails(rentedDetails);
//		entity.setPic("[{\"category\":\"1\",\"url\":\"example.pic/pic_2.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_3.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"3\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"2\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"}]");
//		entity.setRentedDetails("[{\"renterType\":\"101\",\"renterNum\":\"1\",\"renterAge\":\"201\",\"renterWork\":\"16\"},{\"renterType\":\"101\",\"renterNum\":\"2\",\"renterAge\":\"202\",\"renterWork\":\"27\"}]");
		try {
			String rs = ZFService.getInstance().addUpdateZF(entity, operate);//, ComanyOrBroker.Comany, UrlConst.COM_ZF_ADD
			System.out.println(rs);
		} catch (ZFException e) {
			e.printStackTrace();
		}
	}
	
	private static void publishBrokerZF(AddOrUpdate operate) {
		BrokerZFEntity entity = new BrokerZFEntity();

		entity.setBrokerid(1l);
		entity.setRentType(1);
		entity.setPaymentTerms(1);
		entity.setRoomType(1);
		entity.setBuildingArea(300d);
		entity.setBianhao("123");
		entity.setXiaoqu("新建小区");
		entity.setQuyu("朝阳");
//		entity.setXiaoquId(1);
		entity.setTitle("好的顶顶顶顶顶顶顶顶顶休息休息 title");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);

		entity.setMianji(30d);
		entity.setChaoxiang(1);
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(120l);
		List<PicEntity> picList = new ArrayList<PicEntity>();
		for(int i = 0; i  < 9; i++) {
			PicEntity pic = new PicEntity();
			pic.setCategory( i / 3 + 1 + "");
			pic.setIscover("0");
			pic.setUrl(String.format("example.pic/pic_%s.jpg", i));
			picList.add(pic);
		}
		entity.setPicList(picList);
		List<RentedDetailsEntity> renList = new ArrayList<RentedDetailsEntity>();
		for(int i = 0; i  < 9; i++) {
			RentedDetailsEntity ren = new RentedDetailsEntity();
			ren.setRenterAge(RenterAge.age_0_20.getValue());
			ren.setRenterNum("1");
			ren.setRenterType(RenterType.nansheng.getValue());
			ren.setRenterWork(RenterWork.caiwukuaiji.getValue());
			renList.add(ren);
		}
		entity.setRentedDetailsList(renList);
//		entity.setRentedDetails(rentedDetails);
//		entity.setPic("[{\"category\":\"1\",\"url\":\"example.pic/pic_2.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_3.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"3\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"2\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"}]");
//		entity.setRentedDetails("[{\"renterType\":\"101\",\"renterNum\":\"1\",\"renterAge\":\"201\",\"renterWork\":\"16\"},{\"renterType\":\"101\",\"renterNum\":\"2\",\"renterAge\":\"202\",\"renterWork\":\"27\"}]");
		try {
			String rs = ZFService.getInstance().addUpdateZF(entity, operate);//, ComanyOrBroker.Comany, UrlConst.COM_ZF_ADD
			System.out.println(rs);
		} catch (ZFException e) {
			e.printStackTrace();
		}
	}
	
	private static void publishBrokerZF() {
		BrokerZFEntity entity = new BrokerZFEntity();

		entity.setBrokerid(1l);
		entity.setRentType(1);
		entity.setPaymentTerms(1);
		entity.setRoomType(1);
		entity.setBuildingArea(300d);
		entity.setBianhao("123");
		entity.setXiaoqu("新建小区");
		entity.setQuyu("朝阳");
//		entity.setXiaoquId(1);
		entity.setTitle("好的顶顶顶顶顶顶顶顶顶休息休息");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);

		entity.setMianji(30d);
		entity.setChaoxiang(1);
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(120l);
		entity.setPic("[{\"category\":\"1\",\"url\":\"example.pic/pic_2.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_3.jpg\",\"iscover\":\"0\"},{\"category\":\"1\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"3\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"},{\"category\":\"2\",\"url\":\"example.pic/pic_0.jpg\",\"iscover\":\"0\"}]");
		try {
			String rs = ZFService.getInstance().addUpdateZF(entity, AddOrUpdate.Add);//, ComanyOrBroker.Broker, UrlConst.COM_ZF_ADD
			System.out.println(rs);
		} catch (ZFException e) {
			e.printStackTrace();
		}
	}

	private static void publishBrokeESF(AddOrUpdate operate) {
		BrokerESFEntity entity = new BrokerESFEntity();
		entity.setBrokerid(11111l);
		entity.setJianzhuniandai(1992);
		entity.setBianhao("123");
//		entity.setXiaoqu("");
//		entity.setQuyu("");
		entity.setXiaoquId(1);
		entity.setTitle("eeeeeeeeeeeeeeeeeeeeeeeee");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);

		entity.setMianji(20d);
		entity.setChaoxiang(1);
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(1l);
		List<PicEntity> picList = new ArrayList<PicEntity>();
		for(int i = 0; i  < 9; i++) {
			PicEntity pic = new PicEntity();
			pic.setCategory( i / 3 + 1 + "");
			pic.setIscover("0");
			pic.setUrl(String.format("example.pic/pic_%s.jpg", i));
			picList.add(pic);
		}
		entity.setPicList(picList);
//		entity.setPic("[  {      \"category\":\"1\",       \"url\":\"example.pic/pic_0.jpg\",       \"iscover\":\"0\"  }]");
		try {
			String rs = ESFService.getInstance().addUpdateESF(entity, operate);//, ComanyOrBroker.Comany, UrlConst.COM_ESF_ADD
			System.out.println(rs);
		} catch (ESFException e) {
			e.printStackTrace();
		}
	}

	private static void publishCompanyESF(AddOrUpdate oprate) {
		CompanyESFEntity entity = new CompanyESFEntity();
		entity.setBeianbianhao("1122334432");
		entity.setBianhao("1122334432");
		entity.setXiaoqu("远洋山水北区");
		entity.setQuyu("石景山");
		entity.setTitle("测试一套二手房,嗯好的不错,eeeeeee呃呃呃呃呃呃");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(10);
		entity.setSuozailouceng(1);
		entity.setMianji(80.19d);
		entity.setJianzhuniandai(1992);
		entity.setChaoxiang(Chaoxiang.dong.getValue());
		entity.setZhuangxiuqingkuang(1);
		entity.setFangwuleixing(1);
		entity.setJiage(7000000l);//20000
		List<PicEntity> picList = new ArrayList<PicEntity>();
		for(int i = 0; i  < 9; i++) {
			PicEntity pic = new PicEntity();
			pic.setCategory( i / 3 + 1 + "");
			pic.setIscover("0");
			pic.setUrl(String.format("example.pic/pic_%s.jpg", i));
			picList.add(pic);
		}
		entity.setPicList(picList);
		try {
			String rs = ESFService.getInstance().addUpdateESF(entity, oprate);//, AddOrUpdate.Add, ComanyOrBroker.Comany, UrlConst.COM_ESF_ADD
			System.out.println(rs);
		} catch (ESFException e) {
			e.printStackTrace();
		}
	}
}
