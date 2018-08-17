package com.bj58.im.client.mediaTest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

/**
 * 除此之外，物体碰撞检测、3d图像都有---从openimaj里
 * @ClassName:FaceDectect
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月17日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class FaceDectect extends JFrame implements Runnable, com.github.sarxos.webcam.WebcamPanel.Painter{

	private BufferedImage buf;
	private Webcam webcam = null;
	private List<DetectedFace> faces = null;
	private static final HaarCascadeDetector detector = new HaarCascadeDetector();
	private WebcamPanel.Painter painter = null;
	
	public FaceDectect() throws IOException {
		buf = ImageIO.read(new FileInputStream("D:\\compu.jpg"));
		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.open(true);
	}
	
	public static void main(String[] args) {
		
	}

	@Override
	public void run() {
		while (true) {
			if (!webcam.isOpen()) {
				return;
			}
			faces = detector.detectFaces(ImageUtilities.createFImage(webcam.getImage()));
		}
	}

	@Override
	public void paintImage(WebcamPanel arg0, BufferedImage arg1, Graphics2D arg2) {
		
	}

	@Override
	public void paintPanel(WebcamPanel arg0, Graphics2D arg1) {
		
	}
}
