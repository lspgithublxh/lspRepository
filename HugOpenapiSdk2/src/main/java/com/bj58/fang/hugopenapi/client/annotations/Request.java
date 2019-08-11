package com.bj58.fang.hugopenapi.client.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bj58.fang.hugopenapi.client.data.Data;

@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.FIELD})
@Documented
@Inherited
public @interface Request {

	public boolean needVali() default true;//是否需要验证
	public String strLenth() default "";//字符串长度范围[0,20]
	public String numBetween() default "";//整数/浮点数的取值范围[1,10]
	public String enumVal() default "";//枚举值1,2,3,4
	public String notEqual() default "";//不等于-1
	public boolean allowBadChar() default true;//包含特殊字符?
	public int lianxuShuzi() default -1;//连续数字个数--可以匹配出来--指定为范围
	public String timeFormat() default "";//时间格式
	public String xiaoshuweishu() default "";//小数个数
	public String numOrChar() default "";//数字或者字符
	public String jsonVali() default "";//对应的json类名是哪个，以便反射加载进来
	public boolean isJsonArray() default false;//配套jsonVali,目前只对一级进行验证
	public String arrNum() default "";//数组中各个元素的个数
	public boolean override() default false;//是否覆盖父类的属性, 是，那么父类不会在验证这个属性，包括是否为null---因为肯定为null
//	public boolean multipleValue() default false;//多值说明，每个值之间|分割
	//可以加一个是否需要的配置---比如更新时不需要 某些字段--外层remove就可以
//	public boolean esfNeed() default true;
//	public boolean brokerEsfneed() default true;
	
	public String failWhileNeed() default "";//为空或失败 而要求不能失败的， 磐石小区为例xiaoquId  表示 该字段为null时候哪个字段哪些字段不能为null,,或者该字段不为null时哪些字段也不能为Null
	public String dependOn() default "";//自己如果有值，且要求其他也需要有的
	public Class< ? extends Data> other() default Data.class;//Data里按照bean的类型而存储不同的信息
}
