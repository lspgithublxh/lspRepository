package com.bj58.im.client.mediaTest;

import java.nio.ByteBuffer;

import org.jim2mov.core.DefaultMovieInfoProvider;
import org.jim2mov.core.FrameSavedListener;
import org.jim2mov.core.ImageProvider;
import org.jim2mov.core.Jim2Mov;
import org.jim2mov.core.MovieInfoProvider;
import org.jim2mov.core.MovieSaveException;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.util.ImageUtils;

public class JimToMovTest {

	public static void main(String[] args) {
	      test();
	}

	private static void  test() {
		Webcam came = Webcam.getDefault();
		came.open();
		
		
		DefaultMovieInfoProvider p = new DefaultMovieInfoProvider("ss.mov");
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
					return ImageUtils.toByteArray(came.getImage(), "jpg");
				}}, p, 
			new FrameSavedListener() {
				@Override
				public void frameSaved(int arg0) {
				System.out.println("保存了第" + arg0 + "帧");
			}});
			jm.saveMovie(MovieInfoProvider.TYPE_QUICKTIME_JPEG);
		} catch (MovieSaveException e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - t1);
	}
}
