package com.bj58.im.client.mediaTest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 * 1.cmd命令启动quicktime 本地播放器，来播放
 * 2.自己的播放器
 * 参考播放器：https://blog.csdn.net/clayanddev/article/details/70213335?ref=myread
 * 
 * 
 * --------要求32位dll，播放不行
 * @ClassName:UKCOPlayer
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月3日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class UKCOPlayer {

	public static void main(String[] args) {
		test();
	}

	static JFrame frame = null;
	
	private static void test() {
		System.load("D:\\download\\jar\\libvlc\\libvlc.dll");
		System.setProperty("java.class.path", System.getProperty("java.class.path") + ";D:\\download\\jar\\libvlc\\");
		System.out.println(System.getProperty("java.class.path"));
		NativeLibrary.addSearchPath("libvlc", "file:///D:/download/jar/libvlc");
		EmbeddedMediaPlayerComponent epc = new EmbeddedMediaPlayerComponent();
		
		new NativeDiscovery().discover();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame = new JFrame("播放器");
				frame.setBounds(100, 100, 300, 200);
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						epc.release();
						System.exit(0);
					}
				});
				frame.setContentPane(epc);
				frame.setVisible(true);
				epc.getMediaPlayer().playMedia("file:///D:/video.mp4");
			}
		});
	}
}
