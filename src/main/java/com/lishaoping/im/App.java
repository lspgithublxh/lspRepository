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
			return i;//��ִ����return��䣬���ҷŵ��˾ֲ������
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("run l a");
			++i;
			System.out.println("i ֵ��" + i);
		}
		return 0;
	}
}
