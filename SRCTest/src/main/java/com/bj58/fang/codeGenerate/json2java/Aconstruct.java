package com.bj58.fang.codeGenerate.json2java;
import java.io.IOException;
import java.io.File;
import org.apache.commons.io.FileUtils;
import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import com.bj58.fang.codeGenerate.json2json.JSONCom;
public class Aconstruct{ 

	 public static void main(String[] args) throws IOException {
		new Aconstruct().test();
	}

	public void test() throws IOException {
		A a = new A();
		A_detail a_detail = new A_detail();
		A_detail_dispatchResults a_detail_dispatchResults = new A_detail_dispatchResults();
		A_detail_dispatchResults a_detail_dispatchResults1 = new A_detail_dispatchResults();
		A_detail_dispatchResults a_detail_dispatchResults2 = new A_detail_dispatchResults();
		a.setCode(200l);
		a.setDetail(a_detail);
		a.setMessage("操作成功");
		a.setStatus("OK");
		a_detail.setMsg("操作成功");
		a_detail.setCode(200l);
		a_detail.setHouseState(3l);
		a_detail.setUnitedHouseId(219704644012032l);
		a_detail_dispatchResults.setMsg("ok");
		a_detail_dispatchResults.setInfoid(0l);
		a_detail_dispatchResults.setCode(200l);
		a_detail_dispatchResults.setInfoState(5l);
		a_detail_dispatchResults.setUnitedHouseId(219704644012032l);
		a_detail_dispatchResults.setCompany(1l);
		a_detail_dispatchResults.setUrl("");
		a_detail_dispatchResults1.setMsg("操作成功");
		a_detail_dispatchResults1.setInfoid(1062164941l);
		a_detail_dispatchResults1.setCode(200l);
		a_detail_dispatchResults1.setInfoState(12l);
		a_detail_dispatchResults1.setUnitedHouseId(219704644012032l);
		a_detail_dispatchResults1.setCompany(2l);
		a_detail_dispatchResults1.setUrl("");
		a_detail_dispatchResults2.setMsg("操作成功");
		a_detail_dispatchResults2.setInfoid(0l);
		a_detail_dispatchResults2.setCode(200l);
		a_detail_dispatchResults2.setInfoState(4l);
		a_detail_dispatchResults2.setUnitedHouseId(219704644012032l);
		a_detail_dispatchResults2.setCompany(3l);
		a_detail_dispatchResults2.setUrl("");
		A_detail_dispatchResults[] a_detail_dispatchResultsArr = {a_detail_dispatchResults, a_detail_dispatchResults1, a_detail_dispatchResults2};
		a_detail.setDispatchResults(a_detail_dispatchResultsArr);
		System.out.println(JSONCom.jsonCom(JSONObject.toJSONString(a), FileUtils.readFileToString(new File(Aconstruct.class.getResource("").getPath().replace("/target/classes", "/src/main/java") + "/json.txt"), "utf-8")));
	}
}