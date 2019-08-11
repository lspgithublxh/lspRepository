package com.bj58.fang.hugopenapi.client.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.FIELD})
@Documented
@Inherited
public @interface UpdateRequest {

	public boolean needVali() default true;//是否需要验证
	public String strLenth() default "";//字符串长度范围[0,20]
	public String numBetween() default "";//整数/浮点数的取值范围[1,10]
	public String enumVal() default "";//枚举值1,2,3,4
	public String notEqual() default "";//不等于-1
	public boolean allowBadChar() default false;//包含特殊字符?
	public int lianxuShuzi() default -1;//连续数字个数--可以匹配出来--指定为范围
	public String timeFormat() default "";//时间格式
	public String xiaoshuweishu() default "";//小数个数
	public String numOrChar() default "";//数字或者字符
	public String jsonVali() default "";//对应的json类名是哪个，以便反射加载进来
	public boolean isJsonArray() default false;//配套jsonVali,目前只对一级进行验证
}
