package com.bj58.im.client.mediaTest;

import java.io.IOException;
import java.net.URL;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 安装指导：http://blog.sina.com.cn/s/blog_5de5b7180100mdfe.html
 * 
 * @ClassName:JMFTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月2日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class JMFTest {

	public static void main(String[] args) {
		
	 System.out.println();
	 	test();
	}

	private static void test() {
		try {
//			Player player = Manager.createPlayer(new URL("file:///D:/abd.mp3"));
			AudioFileFormat f = AudioSystem.getAudioFileFormat(new URL("file:///D:/abd.mp3"));
			System.out.println(f.properties());
			Player player = Manager.createRealizedPlayer(new URL("file:///D:/abd.mp3"));
			player.start();
			Thread.sleep(10 * 1000);
			player.stop();
			player.close();
		} catch (NoPlayerException  | IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (CannotRealizeException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
