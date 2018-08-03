package com.bj58.im.client.mediaTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class JaudiotaggerTest {

	public static void main(String[] args) throws IOException {
//		test();
		InputStream in = new FileInputStream("D:\\abd.mp3");//FIlename 是你加载的声音文件如(“game.wav”)
		AudioStream as = new AudioStream(in);//失败
		AudioPlayer.player.start(as);//用静态成员player.start播放音乐
	}

	private static void test() {
		try {
			AudioFile file = AudioFileIO.read(new File("D:\\abd.mp3"));
			//读取文件数据
			System.out.println(file.getAudioHeader().getBitRate());
			System.out.println(file.getAudioHeader());
			
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}
}
