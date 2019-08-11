package com.bj58.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CopyAndPackage {

	public static void main(String[] args) {
		//直接用文件流
		String file1 = "D:\\software\\huglist.txt";
		String file2 = "D:\\software\\ttt.txt";
//		fileCopy(file1, file2);
		//先复制，后压缩
		//复制classes
		long t1 = System.currentTimeMillis();
		folder1ToFolder2("D:\\project\\workspace\\scf\\job\\hugwidebizjob\\hugwidebizjob\\target\\hugwidebizjob\\WEB-INF", 
				"D:\\download\\wfdemo\\hbg_scf_hugwidebizjob\\webapps\\WEB-INF");
		//复制配置
		folder1ToFolder2("D:\\project\\workspace\\scf\\job\\hugwidebizjob\\hugwidebizjob\\wfconfig\\sandbox\\hbg_web_hugwidebizjob", 
				"D:\\download\\wfdemo\\hbg_scf_hugwidebizjob\\wf\\conf\\hbg_web_hugwidebizjob");
		//压缩
//		compress("D:\\download\\wfdemo\\hbg_scf_hugwidebizjob");
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);//大约耗时15s, 但是仅仅只能在windows上看，linux上不能运行
	}

	private static void compress(String f) {
		File fi = new File(f);
		File f2 = new File(f + System.currentTimeMillis() + ".zip");
		try {
			f2.createNewFile();
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f2));
			exeCompress(fi, out, fi.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void exeCompress(File fi, ZipOutputStream out, String name) throws IOException {
		if(fi.isFile()) {
			out.putNextEntry(new ZipEntry(name));
			FileInputStream in = new FileInputStream(fi);
			int l = 0;
			byte[] o = new byte[1024];
			while((l = in.read(o)) > 0) {
				out.write(o, 0, l);
			}
			out.closeEntry();
			in.close();
		}else {
			File[] files = fi.listFiles();
			if(files == null || files.length == 0) {
				out.putNextEntry(new ZipEntry(name + "/"));
				out.closeEntry();
			}else {
				for(File fx : files) {
					exeCompress(fx, out, name + "/" + fx.getName());
				}
			}
			
		}
	}

	private static void folder1ToFolder2(String dir1, String dir2) {
		File d1 = new File(dir1);
		File d2 = new File(dir2);
		File[] lists = d1.listFiles();
		for(File fo : lists) {
			if(fo.isDirectory()) {
				String subDir = dir2 + "\\" + fo.getName();
				mkdir(subDir);
				folder1ToFolder2(fo.getAbsolutePath(), subDir);
			}else {
				fileCopy(fo.getAbsolutePath(), dir2 + "\\" + fo.getName());
				System.out.println("filecopy: " + fo.getAbsolutePath());
			}
		}
	}
	
	private static void mkdir(String name) {
		File dir = new File(name);
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	private static void fileCopy(String file1, String file2) {
		try {
			File f2 = new File(file2);
			if(f2.exists()) {
				f2.delete();
			}else {
				f2.createNewFile();
			}
			FileInputStream in = new FileInputStream(file1);
			FileOutputStream out = new FileOutputStream(file2);
			in.getChannel().transferTo(0, in.getChannel().size(), out.getChannel());
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
