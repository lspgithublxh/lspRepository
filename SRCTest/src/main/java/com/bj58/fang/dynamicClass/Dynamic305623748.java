package com.bj58.fang.dynamicClass;import java.lang.reflect.Method; public class Dynamic305623748 extends Aimple{ private com.bj58.fang.dynamicClass.CBInterface cb;public void set8y38mc(com.bj58.fang.dynamicClass.CBInterface cb){ this.cb = cb;} @Override
 public void method1() {  ProxySub proxy = ProxySub.getInstance();
 Method m1 = null;
 Method m2 = null; try {m1 = this.getClass().getDeclaredMethod("method1");
 m2 = super.getClass().getDeclaredMethod("method1");
} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();} cb.callback(this,proxy, m1, m2 );}
@Override
 public java.lang.Object method3(java.lang.Object args0,java.lang.Integer args1) {  ProxySub proxy = ProxySub.getInstance();
 Method m1 = null;
 Method m2 = null; try {m1 = this.getClass().getDeclaredMethod("method3",java.lang.Object.class, java.lang.Integer.class);
 m2 = super.getClass().getDeclaredMethod("method3",java.lang.Object.class, java.lang.Integer.class);
} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();} return (java.lang.Object)cb.callback(this,proxy, m1, m2 ,args0,args1);}
@Override
 public int method2(java.lang.Object args0,java.lang.String args1) {  ProxySub proxy = ProxySub.getInstance();
 Method m1 = null;
 Method m2 = null; try {m1 = this.getClass().getDeclaredMethod("method2",java.lang.Object.class, java.lang.String.class);
 m2 = super.getClass().getDeclaredMethod("method2",java.lang.Object.class, java.lang.String.class);
} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();} return (int)cb.callback(this,proxy, m1, m2 ,args0,args1);}
 private Object proxy(Method method, BIaoji a, Object... args){ if("public void com.bj58.fang.dynamicClass.Aimple.method1()".equals(method.toGenericString())){super.method1();}else if("public java.lang.Object com.bj58.fang.dynamicClass.Aimple.method3(java.lang.Object,java.lang.Integer)".equals(method.toGenericString())){return super.method3((java.lang.Object)args[0],(java.lang.Integer)args[1]);}else if("public int com.bj58.fang.dynamicClass.Aimple.method2(java.lang.Object,java.lang.String)".equals(method.toGenericString())){return super.method2((java.lang.Object)args[0],(java.lang.String)args[1]);}; return null;}
}
