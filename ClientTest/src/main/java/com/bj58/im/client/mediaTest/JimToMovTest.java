package com.bj58.im.client.mediaTest;

import java.io.IOException;
import java.net.URL;

import javax.media.IncompatibleSourceException;
import javax.media.protocol.URLDataSource;

import org.jim2mov.core.DefaultMovieInfoProvider;
import org.jim2mov.core.FrameSavedListener;
import org.jim2mov.core.ImageProvider;
import org.jim2mov.core.Jim2Mov;
import org.jim2mov.core.MovieInfoProvider;
import org.jim2mov.core.MovieSaveException;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.util.ImageUtils;
import com.sun.media.jfxmedia.MediaPlayer;
import com.sun.media.parser.video.QuicktimeParser;
/**
 * 录制 + 显示 + 实时传输
 * 显示方法：自定义一种文件格式，保存，读取数据时，一帧一帧数据的读。。。。或者有专门的读数据类---一帧一帧的读
 * 参考player读取数据的过程---读取方法,而取得数据
 * @ClassName:JimToMovTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月2日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class JimToMovTest {

	public static void main(String[] args) {
//	      test();
//	      test3();
		  test4();
	}

	private static void test4() {
		
	}
	
	private static void test3() {
		QuicktimeParser p = new QuicktimeParser();
		try {
//			p.setSource();
			URLDataSource us = new URLDataSource(new URL("file:///D:/cache1/luzhi1533260555284.mp4"));
			us.connect();
			p.setSource(us);
			System.out.println(p.getName());
		} catch (IncompatibleSourceException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void  test() {
		Webcam came = Webcam.getDefault();
		came.open();
		
		saveImageToFile(came);
	}

	private static void saveImageToFile(Webcam came) {
		//mp4 mov都可以
		DefaultMovieInfoProvider p = new DefaultMovieInfoProvider("file://D:/cache1/luzhi" + System.currentTimeMillis() + ".mp4");
		p.setFPS((float) (100 / 3.6));//帧频率  每秒钟10帧
		p.setMWidth(320);
		p.setMHeight(240);
		p.setNumberOfFrames(100);//帧数，无限
		long t1 = System.currentTimeMillis();
		try {
			Jim2Mov jm = new Jim2Mov(new ImageProvider() {
				@Override
				public byte[] getImage(int arg0) {
					System.out.println("生产第" + arg0 + "帧");
					byte[] d = ImageUtils.toByteArray(came.getImage(), "jpg");
					System.out.println(d.length);
					return d;
				}}, p, 
			new FrameSavedListener() {
				@Override
				public void frameSaved(int arg0) {
				System.out.println("保存了第" + arg0 + "帧");
			}});
			jm.saveMovie(MovieInfoProvider.TYPE_QUICKTIME_JPEG);//TYPE_QUICKTIME_JPEG
		} catch (MovieSaveException e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - t1);
	}
}
