package com.bj58.fang.hugopenapi.client.validate;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bj58.fang.hugopenapi.client.Entity.ESFEntity;
import com.bj58.fang.hugopenapi.client.annotations.Request;
import com.bj58.fang.hugopenapi.client.enumn.pic.Category;
import com.bj58.fang.hugopenapi.client.util.JSONUtils;
import com.bj58.fang.hugopenapi.client.util.StringUtils;

/**
 * 统一验证逻辑--对javabean对象/非JSON对象
 * @ClassName:ValidateByAnnoUtil
 * @Description:
 * @Author lishaoping
 * @Date 2018年12月13日
 * @Version V1.0
 * @Package com.bj58.fang.hugopenapi.client.validate
 */
public class ValidateByAnnoUtil {

	private String regEx = "([ _`~!@#$%^&*()+=|\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t)";
    private Pattern specCharP = Pattern.compile(regEx);//{}':;',
	private SimpleDateFormat timeFormat = null;

	private static ValidateByAnnoUtil util = new ValidateByAnnoUtil();
     
     public static ValidateByAnnoUtil getInstance() {
    	 return util;
     }
     
	/**
	 * 验证 实例 父类会一起验证
	 * @param 
	 * @author lishaoping
	 * @Date 2018年12月12日
	 * @Package com.bj58.fang.hugopenapi.client.validate
	 * @return String
	 */
	public String validateEntity(Object entity) {
		StringBuilder message = new StringBuilder();
		Field[] fields = entity.getClass().getDeclaredFields();
		List<Class<?>> subClassList = new ArrayList<Class<?>>();
		message.append(validateEntity(fields, entity, entity.getClass(), subClassList));
		Class<?> su = entity.getClass().getSuperclass();
		while(!"java.lang.Object".equals(su.getName())) {
			fields = su.getDeclaredFields();
			message.append(validateEntity(fields, entity, su, subClassList));
			subClassList.add(su);
			su = su.getSuperclass();
		}
		return message.toString();
	}
	/**
	 * 验证
	 * @param 
	 * @author lishaoping
	 * @param subClassList 
	 * @param su 
	 * @Date 2018年12月12日
	 * @Package com.bj58.fang.hugopenapi.client.validate
	 * @return String
	 */
    public String validateEntity(Field[] fields, Object entity, Class<?> curr, List<Class<?>> subClassList) {
    	StringBuilder message = new StringBuilder();
    	List<Field> waitVali = new ArrayList<Field>();//不能为null/验证失败的字段
		for(Field f : fields) {
			Request an = f.getAnnotation(Request.class);
			try {
				if (an == null || !an.needVali()) {
					continue;
				}
				String ftype = f.getType().getSimpleName();
				f.setAccessible(true);
				Object val = f.get(entity);
				String key = f.getName();
				if(val == null) {
					if(!StringUtils.isEmptyString(an.failWhileNeed())) {//二选1
						waitVali.add(f);
					}else if(subClassList.size() > 0){
						boolean subHas = false;
						for(Class<?> sub : subClassList) {
							Field subf = null;
							try {
								subf = sub.getDeclaredField(key);
							} catch (Exception e) {
								continue;
							}
							if(subf != null) {//子类有,不再验证
								subHas = true;
								break;
							}
						}
						if(!subHas) {//否则子类有已经验证，则continue
							message.append(String.format("\r\n/**/%s 's value is null: ", key));
						}
					}else {
						message.append(String.format("\r\n/**/%s 's value is null: ", key));
					}
					continue;
				}else {
					
				}
				if("String".equals(ftype)) {
					String v = (String) val;
					if (!an.allowBadChar()) {
						Matcher mat = specCharP.matcher(v);
						if (mat.find()) {
							String bad = mat.group(1);
							message.append(String.format("\r\n/**/%s 's value contains special char: %s", key, bad));
						}
					}
					if(an.lianxuShuzi() != -1) {
						Pattern numberMuchP = Pattern.compile(String.format("\\d{%s,}", an.lianxuShuzi()));
						Matcher mat = numberMuchP.matcher(v);
						if (mat.find()) {
							message.append(String.format("\r\n/**/%s 's value of serianumber is too long, must be < %s",
									 key, an.lianxuShuzi()));
						}
					}
					if(!StringUtils.NULLSTR.equals(an.strLenth())) {
						String len = an.strLenth();
						fanwei(message, key, v, len);
					}
					if(!StringUtils.NULLSTR.equals(an.timeFormat())) {
						try {
							timeFormat = new SimpleDateFormat(an.timeFormat());//
							timeFormat.parse(v);
						} catch (Exception e) {
							message.append(String.format("\r\n/**/%s 's value must be '%s' format", key, an.timeFormat()));
						}
					}
					if(!StringUtils.NULLSTR.equals(an.numOrChar())) {
						char[] vl = v.toCharArray();
						for(char x : vl) {
							if((x >= 'a' && x <= 'z') || (x <= 'Z' && x >= 'A') ||
									(x >= '0' && x <= '9')) {
							}else {
								message.append(String.format("\r\n/**/%s 's value contains char which is not a-zA-Z0-9", key));
								break;
							}
						}
					}
					//json验证
					if(!StringUtils.NULLSTR.equals(an.jsonVali())) {
						if(an.isJsonArray()) {
							JSONArray arr = JSONArray.parseArray(v);//an.jsonVali()
							String arrRes = an.arrNum();
							JSONObject arrDes = JSONObject.parseObject(arrRes);
							Object pic = arrDes.get("pic");
							Map<String, Integer> typeCount = null;
							JSONObject picO = null;
							if(pic != null) {
//								StringBuilder bu = new StringBuilder();
								picO = (JSONObject) pic;
								typeCount = new HashMap<String, Integer>();
								
//								fanwei2(message, key, v, picO.get(""));
							}
							StringBuilder bu = new StringBuilder();
							for(int i = 0 ;i < arr.size(); i++) {
								JSONObject json = arr.getJSONObject(i);
								validateJSON(message, json, an.jsonVali(), key);
								if(picO != null) {
									String valK = json.getString("category");
									Integer vcount = typeCount.get(valK);
									if(vcount == null) {
										vcount = 0;
										typeCount.put(valK, 1);
									}else {
										typeCount.put(valK, vcount + 1);
									}
									
								}
							}
							if(picO != null) {
								for(Entry<String, Object> entry : picO.entrySet()) {
									String valK = entry.getKey();
									Integer vcount = typeCount.get(valK);
									vcount = vcount == null ? 0 : vcount;
									String qujian = entry.getValue().toString();
									int rtx = fanwei2(bu, key, vcount + 0.0d, qujian);
									if(rtx == 1) {//有问题
										message.append(String.format("\r\n/**/%s 's  pic jsonarray, %s num %s is outside the scope :%s", key, Category.getName(Integer.valueOf(valK)),
												vcount, qujian));
									}
								}
							}
						}else {
							JSONObject json = JSONObject.parseObject(v);//an.jsonVali()
							validateJSON(message, json, an.jsonVali(), key);
						}
					}
				} else if ("Boolean".equals(ftype)) {
					
				} else {//认为是数字
					Double v = Double.valueOf(val + "");
					if(!StringUtils.NULLSTR.equals(an.numBetween())) {
						String len = an.numBetween();
						if(len.contains("&") || len.contains("|")) {
							String[] condition = len.split("[\\|&]");//不等用专门的字段
							//现在只支持单一运算& 或者只|
							boolean and = len.contains("&") ? true : false;
							int valiOkCount = 0;
							StringBuilder outMes = new StringBuilder();
							for(String con : condition) {
								StringBuilder bu = new StringBuilder();
								//如果包含%,先替换
								if(con.matches(".*%.*")) {
									con = tihuan(len);
								}
								if(con.matches(".*?[A-Za-z].*")) {//   .*?[\[\(,][A-Za-z].*
									fanwei3(bu, key, v, con, fields, entity, curr);
								}else {
									fanwei2(bu, key, v, con);
								}
								if(bu.length() == 0) {
									valiOkCount++;
								}else {
									outMes.append(bu.toString());
								}
							}
							boolean ok = and ? valiOkCount == condition.length ? true : false : 
												valiOkCount < condition.length ? true : false;
							if(!ok) {
								message.append(outMes.toString());
							}
						}else {
							if(len.matches(".*%.*")) {
								len = tihuan(len);
							}
							fanwei2(message, key, v, len);
						}
						
					}
					if("Float".equals(ftype) || "Double".equals(ftype)) {
						if(!StringUtils.NULLSTR.equals(an.xiaoshuweishu())) {
							String[] two = (val + "").split("\\.");
							String xiao = an.xiaoshuweishu();
							int rs = fanwei2(message, key, two[1].length() + 0.0d, xiao);
							if(rs == 1) {
								message.append(String.format("\r\n/**/%s xiaoshu weishu is too much, outside of %s", key, an.xiaoshuweishu()));
							}
						}
					}
				}
				//枚举不分
				if(!StringUtils.NULLSTR.equals(an.enumVal())) {
					String mei = an.enumVal();
					String[] meiArr = mei.split(",");
					String v = val + "";
					String[] va = v.contains("|") ? v.split("|") : new String[] {v};
					for(String item : va) {
						if(!Arrays.asList(meiArr).contains(item)) {
							message.append(String.format("\r\n/**/%s 's value is not in the fanwei:  %s",
									key, an.enumVal()));
						}
					}
					
				}
				if(message.toString().contains(String.format("/**/%s", key))) {//表示验证失败
					if(!StringUtils.isEmptyString(an.dependOn())) {//则依赖不能失败
						waitVali.add(f);
					}
					if(!StringUtils.isEmptyString(an.failWhileNeed())) {//则二选1不能失败
						if(!waitVali.contains(f)) {
							waitVali.add(f);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.append(String.format("\r\n/**/%s validate failed! exception:",
									f.getName(), e.getMessage()));
			}
		}
		//2.wait to yanzheng
		String rs = message.toString();
		for(Field f : waitVali) {
			try {
				Request an = f.getAnnotation(Request.class);
				String[] ziduan = new String[0];
				String name = f.getName();
				if(!StringUtils.isEmptyString(an.dependOn())) {
					String dependOn = an.dependOn();
					ziduan = dependOn.split(",");
				}else if(!StringUtils.isEmptyString(an.failWhileNeed())){
					String dependOn = an.dependOn();
					ziduan = dependOn.split(",");
				}
				//一看自己有没有通过--二看依赖有没有通过 --自己没通过那么依赖必须通过；自己通过则依赖可以不通过
				
				for(String z : ziduan) {
					if(rs.contains(String.format("\r\n/**/%s ", z))) {//依赖没通过
						message.append(String.format("\r\n/**/%s depend on %s , which validate failed!", name, z));
					}else {
						for(Field of : fields) {
							if(of.getName().equals(z)) {
								Object ofV = of.get(entity);
								if(ofV == null) {//依赖的值为null
									message.append(String.format("\r\n/**/%s depend on %s , which is null", name, z));
								}
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	private void validateJSON(StringBuilder message, JSONObject json, String jsonVali, String key) {
		Class<?> cla = JSONUtils.getInstance().findClass(jsonVali);
		if(cla != null) {
			Object rs = JSONUtils.getInstance().jsonToBean(cla, json);
			if(rs != null) {
				message.append(validateEntity(rs));
			}else {
				message.append(String.format("\r\n/**/%s json validate while json trans to class %s failed!", key, jsonVali));
			}
		}else {
			message.append(String.format("\r\n/**/%s json validate while class %s not find!", key, jsonVali));
		}
		
	}

	private String tihuan(String len) {
		String left = len.substring(0, 1);
		String right = len.substring(len.length() - 1, len.length());
		String[] nums = len.substring(1, len.length() - 1).split(",");
		String ret = left;
		if(nums[0].matches(".*%.*")) {//包含
			String it = nums[0];
			Double valx = getValByPersent(it.substring(1, it.length() - 1));
			ret += valx;
		}else {
			ret += nums[0];
		}
		ret += ",";
		if(nums[1].matches(".*%.*")) {
			String it = nums[1];
			Double valx = getValByPersent(it.substring(1, it.length() - 1));
			ret += valx;
		}else {
			ret += nums[1];
		}
		ret += right;
		return ret;
	}
	
	

	private Double getValByPersent(String nums) {
		String[] items = nums.split("[+-/\\*]");
		Double[] its = new Double[items.length];
		String[] fu = new String[items.length - 1];
		int i = 0;
		String old = "";
		for(String item : items) {
			if("%year".equals(item)) {
				Calendar ca = Calendar.getInstance();
				its[i] = ca.get(Calendar.YEAR) + 0.0d;
			}else {
				its[i] = Double.valueOf(item);
			}
			old += item + "=";
			if(i < fu.length) {
				fu[i] = nums.substring(old.length() - 1, old.length());
			}
			i++;
		}
		Double valx = fuhaoItem(its, fu);
		return valx;
	}
	
	private Double fuhaoItem(Double[] its, String[] fu) {
		Double lastV = its[0];
		for(int i = 1; i < its.length - 1; i++) {
			Double a = its[i];
			String f = fu[i];
			if("+".equals(f)) {
				lastV = lastV + a;
			}else if("-".equals(f)) {
				lastV = lastV - a;
			}else if("*".equals(f)) {
				lastV = lastV * a;
			}else if("/".equals(f)) {
				lastV = lastV / a;
			}
		}
		return lastV;
	}

	private int fanwei3(StringBuilder message, String key, Double v, String len, Field[] fields, Object entity, Class<?> curr) {
		String left = len.substring(0, 1);
		String right = len.substring(len.length() - 1, len.length());
		String[] nums = len.substring(1, len.length() - 1).split(",");
		Double lefv = 99877.0d;
		Double rightv = -99877.0d;
		if(nums[0].matches(".*?[A-Za-z].*")) {
			lefv = getFiledV(message, fields, entity, nums[0], curr);
		}else {
			lefv = Double.valueOf(nums[0].trim());
		}
		if(nums[1].matches(".*?[A-Za-z].*")) {
			rightv = getFiledV(message, fields, entity, nums[1], curr);
		}else {
			rightv = Double.valueOf(nums[1].trim());
		}
		Double valL = v;
		boolean leftOk = false;
		boolean rightOk = false;
		if("(".equals(left)) {
			leftOk = valL > lefv ? true : false;
		}else {
			leftOk = valL >= lefv ? true : false;
		}
		if(")".equals(right)) {
			rightOk = valL < rightv ? true : false;
		}else {
			rightOk = valL <= rightv ? true : false;
		}
		if(!(leftOk && rightOk)) {
			message.append(String.format("\r\n/**/%s 's value  is outside the scope,  must be %s",
					key, len));
			return 1;
		}
		return 0;
	}
	private Double getFiledV(StringBuilder message, Field[] fields, Object entity, String field, Class<?> curr) {
		Double lefv = 0.0d;
		boolean find = false;
		for(Field f : fields) {
			if(f.getName().equals(field)) {
				try {
					f.setAccessible(true);
					Object fv = f.get(entity);
					lefv = Double.valueOf(fv.toString());
				} catch (Exception e) {
					e.printStackTrace();
					message.append(String.format("\r\n/**/%s 's value  is not the right type,  which must be number",
							f.getName()));
				}
				find = true;
				break;
			}
		}
		Class<?> superCla = curr.getSuperclass();
		while(!"java.lang.Object".equals(superCla.getName()) && !find) {
			fields = superCla.getDeclaredFields();
			for(Field f : fields) {
				if(f.getName().equals(field)) {
					try {
						Object fv = f.get(entity);
						lefv = Double.valueOf(fv.toString());
					} catch (Exception e) {
						e.printStackTrace();
						message.append(String.format("\r\n/**/%s 's value  is not the right type,  which must be number",
								f.getName()));
					}
					find = true;
					break;
				}
			}
		}
		return lefv;
	}
	private void fanwei(StringBuilder message, String key, String v, String len) {
		String left = len.substring(0, 1);
		String right = len.substring(len.length() - 1, len.length());
		String[] nums = len.substring(1, len.length() - 1).split(",");
		Integer lefv = Integer.valueOf(nums[0].trim());
		Integer rightv = Integer.valueOf(nums[1].trim());
		Integer valL = v.length();
		boolean leftOk = false;
		boolean rightOk = false;
		if("(".equals(left)) {
			leftOk = valL > lefv ? true : false;
		}else {
			leftOk = valL >= lefv ? true : false;
		}
		if(")".equals(right)) {
			rightOk = valL < rightv ? true : false;
		}else {
			rightOk = valL <= rightv ? true : false;
		}
		if(!(leftOk && rightOk)) {
			message.append(String.format("\r\n/**/%s 's value length is outsite the scope, must be %s",
					key, len));
		}
	}
	
	private int fanwei2(StringBuilder message, String key, Double v, String len) {
		String left = len.substring(0, 1);
		String right = len.substring(len.length() - 1, len.length());
		String[] nums = len.substring(1, len.length() - 1).split(",");
		Double lefv = Double.valueOf(nums[0].trim());
		Double rightv = Double.valueOf(nums[1].trim());
		Double valL = v;
		boolean leftOk = false;
		boolean rightOk = false;
		if("(".equals(left)) {
			leftOk = valL > lefv ? true : false;
		}else {
			leftOk = valL >= lefv ? true : false;
		}
		if(")".equals(right)) {
			rightOk = valL < rightv ? true : false;
		}else {
			rightOk = valL <= rightv ? true : false;
		}
		if(!(leftOk && rightOk)) {
			message.append(String.format("\r\n/**/%s 's value  is outside the scope,  must be %s",
					key, len));
			return 1;
		}
		return 0;
	}
	
	public String validateBianhao(String bianhao) {
		StringBuilder message = new StringBuilder();
		if(!StringUtils.isEmptyString(bianhao)) {
			String len = "[0,20]";
			fanwei(message, "bianhao", bianhao, len);
			char[] vl = bianhao.toCharArray();
			for(char x : vl) {
				if((x >= 'a' && x <= 'z') || (x <= 'Z' && x >= 'A') ||
						(x >= '0' && x <= '9')) {
				}else {
					message.append(String.format("\r\n/**/%s 's value contains char which is not a-zA-Z0-9", "bianhao"));
					break;
				}
			}
		}else {
			message.append(String.format("\r\n/**/bianhao 's value is null"));
		}
		return message.toString();
	}
	
	public static void main(String[] args) {
		Object 	val = 3;
		Double v = Double.valueOf(val.toString());
		System.out.println(v);
		System.out.println(ESFEntity.class.getDeclaredFields().length);
		System.out.println(ESFEntity.class.getSuperclass().getDeclaredFields().length);
		System.out.println("[3.3,3]".matches(".*?[A-Za-z].*"));
		System.out.println("\r\n/**/sdf\r\n/**/sdf".split("/\\*\\*/")[1]);
		JSONObject o = new JSONObject();
		o.put("ss", "1");
		System.out.println(o.getIntValue("ss"));
		System.out.println("[1900,(%year+3)]".matches(".*%.*"));
	}
}
