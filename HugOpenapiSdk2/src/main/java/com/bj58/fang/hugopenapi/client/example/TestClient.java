package com.bj58.fang.hugopenapi.client.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bj58.fang.hugopenapi.client.Entity.BrokerESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.BrokerZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.CompanyESFEntity;
import com.bj58.fang.hugopenapi.client.Entity.CompanyZFEntity;
import com.bj58.fang.hugopenapi.client.Entity.PicEntity;
import com.bj58.fang.hugopenapi.client.Entity.RentedDetailsEntity;
import com.bj58.fang.hugopenapi.client.Entity.result.CommonResult;
import com.bj58.fang.hugopenapi.client.enumn.AddOrUpdate;
import com.bj58.fang.hugopenapi.client.enumn.house.Chaoxiang;
import com.bj58.fang.hugopenapi.client.enumn.house.PaymentTerms;
import com.bj58.fang.hugopenapi.client.enumn.house.RentType;
import com.bj58.fang.hugopenapi.client.enumn.pub.DeptIdType;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterAge;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterType;
import com.bj58.fang.hugopenapi.client.enumn.rentdetail.RenterWork;
import com.bj58.fang.hugopenapi.client.exception.ESFException;
import com.bj58.fang.hugopenapi.client.exception.ZFException;
import com.bj58.fang.hugopenapi.client.service.ESFService;
import com.bj58.fang.hugopenapi.client.service.InitService;
import com.bj58.fang.hugopenapi.client.service.XQService;
import com.bj58.fang.hugopenapi.client.service.ZFService;
import com.bj58.fang.hugopenapi.client.service.pub.BrokerPublicService;
import com.bj58.fang.hugopenapi.client.service.pub.CompanyPublicService;
import com.bj58.fang.hugopenapi.client.service.pub.entity.EmployeeEntity;

public class TestClient {

	public static void main(String[] args) {
		//1.正式环境   初始化clientId, clientSecret
//		InitService.init("b6ab5dc63efb2ea7c7de1317bd9a9d58", "180def1ba07798ba4447790830358be3", 1000000l);
		//1.2所在项目是否部署在分布式环境，集群中; 是则要配置tokenserver所在机器的ip,port
//		Map<String, Integer> ipPortMap = new HashMap<String, Integer>();
//		ipPortMap.put("localhost", 16778);
//		InitService.configDistributeCondition(true, ipPortMap);
		//2.测试阶段
		InitService.init("8d23abaa64aaa37d423b730242ffd753", "180def1ba07798ba4447790830358be3", 1000000l);
		InitService.test(true, "5f29993c5735f99d5e97dad6e536b99c");
		//线程池参数配置：可以采用默认
//		DefaultHttpPoolingManager.config(200, 20, 2 * 1000, 2 * 1000, 2 * 1000);
		//3.具体的调用：
		//发布二手房！
//		publishCompanyESF(AddOrUpdate.Add);
//		publishCompanyESF(AddOrUpdate.Update);
//		publishBrokeESF(AddOrUpdate.Add);
//		publishBrokeESF(AddOrUpdate.Update);
		//租房
//		publishCompanyZF(AddOrUpdate.Add);
//		publishCompanyZF(AddOrUpdate.Update);
//		publishBrokerZF(AddOrUpdate.Add);
//		publishBrokerZF(AddOrUpdate.Update);

		//小区模糊匹配
//		xiaoquGet();
		//cmd模式测试
//		runbyCmd();
		//公共接口测试
		publicInterfaceTest();
	}

	private static void publicInterfaceTest() {
//		String brokerid = "11111l";
//		String bianhao = "1122334432";
		BrokerPublicService serv = new BrokerPublicService();
		CompanyPublicService service = new CompanyPublicService();
		//TODO success:
		//获取当前城市的房源标签
//		CommonResult rs = service.currCityFangTag(100);
		//公司房源是否存在
//		CommonResult rs = service.existsCompanyFang("1122334432", Cateid.ershoufang);
		//公司库房源关联经纪人房源总数查询接口
//		CommonResult rs = service.getBrokerHouseCountByBianhao("1122334432", Cateid.ershoufang, Type.youxiao, null, null);
		//查询公司库房源状态
//		CommonResult rs = service.getCompanyFangState("1122334432", Cateid.ershoufang);
		//公司库关联经纪人房源列表
//		CommonResult rs = service.getSanWangFANGByCompanyFang("1122334432", Cateid.ershoufang);
		//内部房源编号更新公司库房源状态
//		CommonResult rs = service.updateCompanyFangState("1122334432", Cateid.ershoufang, State.chengjiao);
		//删除房源
//		CommonResult rs = service.deleteHouse("1122334432", Cateid.ershoufang);
		//公司库房源认领 公司房源库到经纪人房源库, brokerids如果多值，值之间|分割，如122|334|567
//		CommonResult rs = service.postCompanyFangToBrokerFang("1122334432", "11111", Plats.wuba, Cateid.ershoufang);

		//TODO 400226 经纪人不属于当前的经纪公司,
		//删除经纪人的全部房源
//		CommonResult rs = service.deleteBrokerFang("1111", "0");
		
		//TODO 404 未上线 1221
		//根据身份证号码查询三网经纪人id
//		CommonResult rs = serv.getSanwangBrokerIdByIdentity("");
		//获取三网经纪人id
//		CommonResult rs = serv.getSanwangBrokerByPhoneOrUserName(QueryType.by_mobilePhone, "1122222", "lsp", true);
		//根据用户名获取三网经纪人ID
//		CommonResult rs = serv.getSanwangBrokerIdByUserName("lsp");
		//查询经纪人通话详情
//		CommonResult rs = serv.queryBrokerTelephone("1111", "1122222", null, null, null, null);
		//绑定三网账号
//		CommonResult rs = service.bindSanWangAcount("", UserIdType.jingjigongsiUser, 1111l);
//		DealRecordEntity dre = new DealRecordEntity();
//		dre.setBianhao("1122334432");
//		dre.setCommission(1000l);
//		dre.setContractNo("xxxs");
//		dre.setDealPrice(1000000l);
//		dre.setDealTime(System.currentTimeMillis());
//		dre.setDeptId("ss");
//		dre.setDeptIdType(DeptIdType.jingjigongsibumenid.getValue());
//		dre.setDeptLevel(1);
//		dre.setUserId("");
//		dre.setUserIdType(UserIdType.jingjigongsiUser.getValue());
		//新建成交记录接口
//		CommonResult rs = service.dealRecordAddUpdate(dre);
		//解除三网绑定
//		CommonResult rs = service.deleteSiteBinding("1111", UserIdType.jingjigongsiUser);
		//部门查询
//		CommonResult rs = service.departmentQuery("1", DeptIdType.jingjigongsibumenid, 1);
		//员工离职
//		CommonResult rs = service.employeeDimission("1111", UserIdType.jingjigongsiUser);
		//员工批量冻结
//		CommonResult rs = service.employeeFreezeBatch("1111", UserIdType.jingjigongsiUser, IsFreeze.dongjie);
		//员工查询
//		CommonResult rs = service.employeeQuery("1111", UserIdType.jingjigongsiUser);
//		DepartmentEntity dte = new DepartmentEntity();
//		dte.setBrokerageDeptId("1");
//		dte.setDeptAddress("海淀区");
//		dte.setDeptLevel(1);
//		dte.setDeptName("房产");
//		dte.setParentDeptId("0");
//		dte.setParentDeptIdType(DeptIdType.jingjigongsibumenid.getValue());
//		dte.setParentDeptLevel(1);
//		dte.setUserId("1111");
//		dte.setUserIdType(UserIdType.jingjigongsiUser.getValue());
		//部门新增
//		CommonResult rs = service.departmentAdd(dte);
//		HouseWorkEntity hwe = new HouseWorkEntity();
//		hwe.setBianhao("");
//		hwe.setBizType(BizType.ershoufang.getValue());
//		hwe.setBrokerageWorkId("1");
//		hwe.setCustomerId("2");
//		hwe.setFollowTime(System.currentTimeMillis());
//		hwe.setUserId("122");
//		hwe.setUserIdType(UserIdType.jingjigongsiUser.getValue());
//		hwe.setWorkContent("");
		//新建更新带看记录
//		CommonResult rs = service.houseworkAddUpdate(hwe);
		EmployeeEntity ee = new EmployeeEntity();
		ee.setAccountId("1");
		ee.setAccountName("1");
		ee.setBrokerageDeptLevel(1);
		ee.setBrokerId(1111l);
		ee.setDeptId("1");
		ee.setDeptIdType(DeptIdType.jingjigongsibumenid.getValue());
		ee.setEntryTime("" + System.currentTimeMillis());
		ee.setPassword("112");
		ee.setPhone("123");
		ee.setPositionId(1);
		ee.setTrueName("lsp");
		//员工新增
		CommonResult rs = service.employeeAddUpdate(ee);
//		RoleEntity re = new RoleEntity();
//		re.setBianhao("1122334432");
//		re.setGuardian("1111");
//		re.setInputPerson("1111");
//		re.setKey("1111");
//		re.setPromotion("1111");
//		re.setSurvey("1111");
//		re.setUserIdType(UserIdType.jingjigongsiUser.getValue());
		//角色新增和更新
//		CommonResult rs = service.roleAddUpdate(re);
		//配置可视盘
//		CommonResult rs = service.settingsViewRange("1", DeptIdType.jingjigongsibumenid, "320");
		//获取当前城市的房源标签的接口
//		CommonResult rs = service.getHousetag(100);
		
		System.out.println(rs);
	}

	private static void runbyCmd() {
		String cmd = "";
		Scanner scanner = new Scanner(System.in);
		Class<?> type = TestClient.class;
		TestClient test = new TestClient();
		while(true) {
			cmd = scanner.nextLine();
			String[] methodP = cmd.split("\\s+");
			try {
				Method m = type.getDeclaredMethod(methodP[0], AddOrUpdate.class);
				if("add".equals(methodP[1])) {
					System.out.println();
					m.invoke(null, AddOrUpdate.Add);//methodP[1]
				}else if("update".equals(methodP[1])) {
					m.invoke(null, AddOrUpdate.Update);//methodP[1]
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private static void xiaoquGet() {
		String xq = XQService.getInstance().matchXiaoqu("远洋", 10);
		System.out.println(xq);
	}

	private static void publishCompanyZF(AddOrUpdate operate) {
		CompanyZFEntity entity = new CompanyZFEntity();

		entity.setRentType(RentType.hezu.getValue());
		entity.setPaymentTerms(PaymentTerms.bannianfu.getValue());
		entity.setRoomType(1);
		entity.setBuildingArea(300d);
		entity.setBianhao("123");
//		entity.setXiaoqu("1+1城中城");
//		entity.setQuyu("上海周边");
		entity.setXiaoquId(100375975);
		entity.setTitle("好的顶顶顶顶顶顶顶顶顶休息休息 title");
		entity.setShi(1);
		entity.setTing(1);
		entity.setWei(1);
		entity.setZonglouceng(1);
		entity.setSuozailouceng(1);

		entity.setMianji(30d);
		entity.setChaoxiang(1);//Chaoxiang.bei;
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

		entity.setHouseid(1111l);
		entity.setBrokerid(1501178l);
		entity.setRentType(1);
		entity.setPaymentTerms(1);
		entity.setRoomType(1);
		entity.setBuildingArea(300d);
		entity.setBianhao("123");
//		entity.setXiaoqu("新建小区");
//		entity.setQuyu("朝阳");
		entity.setXiaoquId(100375975);
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
		entity.setBrokerid(1501178l);
		entity.setJianzhuniandai(1992);
		entity.setBianhao("123");
//		entity.setXiaoqu("");
//		entity.setQuyu("");
		entity.setXiaoquId(320);
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
		//补充信息
		
		try {
			String rs = ESFService.getInstance().addUpdateESF(entity, oprate);//, AddOrUpdate.Add, ComanyOrBroker.Comany, UrlConst.COM_ESF_ADD
			System.out.println(rs);
		} catch (ESFException e) {
			e.printStackTrace();
		}
	}
}
