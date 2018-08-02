package com.bj58.im.client.mediaTest;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class JaudiotaggerTest {

	public static void main(String[] args) {
		test();
	}

	private static void test() {
		try {
			AudioFile file = AudioFileIO.read(new File("D:\\abd.mp3"));
			System.out.println(file.getAudioHeader().getBitRate());
			System.out.println(file.getAudioHeader());
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}
}
