package com.bj58.fang.hugopenapi.client.validate;

public enum ValidateType {

	MAXLenth("maxlength", "20"), NOTNULL1("notnull", ""), 
	NOTNULL2("notnull2", ""), Between("between", "[1,2]"), Between2("length_between", "10,20"),
	Betweenlr("between", "(1,2)"), Betweenl("between", "(1,2]"), Betweenr("between", "[1,2)"),
	NOTequal("notequal", "!="), IS_INT("is_int", "1"),
	Seri_num("lianxushuzi", "1223344432"), Special_char("bad_char", "\b"),
	charORNum("charornum", "3434xsdfs"), longType("longtype", "3333333"),
	enumType("meiquzhi", "1,2,3,4"),enumMutiple("duoxuan", "1,2,3,4"),doubleType("doubleType", "2.2"),
	xiaoshugeshu("xiaoshugeshu", "xiaoshugeshu"), less("xiaoyu", "<"), greater("greater", ">"),
	timeFormat1("yyyy-MM-dd hh:mm:ss", ""), equalOrLess("xiaoyu2", "<="), equalOrGreater("strictgreater", ">=");
	
	private String name;
	private String val;
	private ValidateType(String name, String val) {
		this.name = name;
		this.val = val;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	
	
}
