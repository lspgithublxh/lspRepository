package com.lishaoping.im;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        int i = hello();//
        System.out.println(i);
    }

	private static int hello() {
		int i = 0; 
		try {
			i++;
			return i;//先执行了return语句，并且放到了局部变量里。
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("run l a");
			++i;
			System.out.println("i 值：" + i);
		}
		return 0;
	}
}
