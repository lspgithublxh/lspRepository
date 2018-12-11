package com.bj58.fang.dynamicClass;import java.lang.reflect.Method; public class Dynamic2018699554 implements AConnection{ private com.bj58.fang.dynamicClass.CBInterface cb;public void set8y38mc(com.bj58.fang.dynamicClass.CBInterface cb){ this.cb = cb;} @Override
 public void method1() { Method m1 = null;
 Method m2 = null; try {m1 = this.getClass().getDeclaredMethod("method1");
 m2 = super.getClass().getDeclaredMethod("method1");
} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();} }
@Override
 public java.lang.Object method3(java.lang.Object args0,java.lang.Integer args1) { Method m1 = null;
 Method m2 = null; try {m1 = this.getClass().getDeclaredMethod("method3",java.lang.Object.class, java.lang.Integer.class);
 m2 = super.getClass().getDeclaredMethod("method3",java.lang.Object.class, java.lang.Integer.class);
} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();} return (java.lang.Object)cb.callback(m1, m2 ,args0,args1);}
@Override
 public int method2(java.lang.Object args0,java.lang.String args1) { Method m1 = null;
 Method m2 = null; try {m1 = this.getClass().getDeclaredMethod("method2",java.lang.Object.class, java.lang.String.class);
 m2 = super.getClass().getDeclaredMethod("method2",java.lang.Object.class, java.lang.String.class);
} catch (NoSuchMethodException | SecurityException e) {e.printStackTrace();} 
 return (int)cb.callback(m1, m2 ,args0,args1);}
}