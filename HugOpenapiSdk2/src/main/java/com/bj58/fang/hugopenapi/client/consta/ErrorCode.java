package com.bj58.fang.hugopenapi.client.consta;

public class ErrorCode {

	public static final int code400000 = 400000;//默认的错误状态码
	public static final int code400101 = 400101;//缺少token校验参数
	public static final int code400102 = 400102;//请勿重复调用申请token接口，如需获取新的token请调用刷新token接口
	public static final int code400104 = 400104;//客户端编号[clientId]错误
	public static final int code400105 = 400105;//客户端口令[token]校验失败
	public static final int code400106 = 400106;//校验失败，请稍后重试
	public static final int code400108 = 400108;//找不到token历史信息，无法刷新，请先申请token
	public static final int code400109 = 400109;//时间戳[timeSign]必须是整数值
	public static final int code400110 = 400110;//时间戳[timeSign]不在有效范围，请确认使用的是客户端的当前时间戳
	public static final int code400204 = 400204;//公司房源编号[bianhao]是必填参数
	public static final int code400205 = 400205;//小区名称[xiaoqu]是必填参数
	public static final int code400206 = 400206;//区域[quyu]是必填参数
	public static final int code400213 = 400213;//图片类型[pic.category]不能为空
	public static final int code400214 = 400214;//图片路径[pic.url]不能为空
	public static final int code400215 = 400215;//图片是否为封面图[pic.iscover]不能为空
	public static final int code400216 = 400216;//小区不存在
	public static final int code400217 = 400217;//图片数量不符合要求
	public static final int code400218 = 400218;//无效的平台编号[plats]
	public static final int code400220 = 400220;//该三网经纪人不存在
	public static final int code400221 = 400221;//枚举值必须为数字
	public static final int code400224 = 400224;//三网经纪人编号[brokerid]是必填参数
	public static final int code400225 = 400225;//无效的三网经纪人编号[brokerid]
	public static final int code400226 = 400226;//经纪人不属于当前的经纪公司
	public static final int code400228 = 400228;//图片格式有误，参数不可为空
	public static final int code400229 = 400229;//该三网经纪人缺少所属经纪公司信息
	public static final int code400231 = 400231;//该公司房源不是有效房源，不允许发布
	public static final int code400232 = 400232;//指定类别和城市不在灰度范围中，暂时不允许通过开发接口发布该房源
	public static final int code400233 = 400233;//该小区处于锁定状态，不允许发布房源
	public static final int code400300 = 400300;//必填字段校验失败
	public static final int code400301 = 400301;//规则校验失败
	public static final int code400302 = 400302;//图片数量校验失败
	public static final int code400303 = 400303;//磐石校验价格失败
	public static final int code400304 = 400304;//磐石校验面积失败
	public static final int code400305 = 400305;//猎人风控事中校验失败
	public static final int code400307 = 400307;//客户端签名[signature]错误
	public static final int code400501 = 400501;//该公司房源不存在
	public static final int code400502 = 400502;//该经纪人房源不存在
	public static final int code400503 = 400503;//经纪人校验失败：该房源不属于该经纪人
	public static final int code400505 = 400505;//三网标准房源编号[houseId]不能为空
	public static final int code400506 = 400506;//无效的三网标准房源编号[houseId]
	public static final int code500001 = 500001;//接口请求频率达到最大值，该次请求被拒绝，请稍后重试
}
