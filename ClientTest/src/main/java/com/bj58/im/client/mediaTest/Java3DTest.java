package com.bj58.im.client.mediaTest;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * 3d开发基本文档
 * https://www.cnblogs.com/dennisit/archive/2013/05/06/3063042.html
 * @ClassName:Java3DTest
 * @Description:
 * @Author lishaoping
 * @Date 2018年8月17日
 * @Version V1.0
 * @Package com.bj58.im.client.mediaTest
 */
public class Java3DTest {

	public static void main(String[] args) {
		SimpleUniverse uni = new SimpleUniverse();
		BranchGroup group = new BranchGroup();
		Cone cone = new Cone(.5f, .5f, 1, null);
		group.addChild(cone);
		Color3f col = new Color3f(1.8f, .1f, .1f);
		BoundingSphere sh = new BoundingSphere(new Point3d(0, 0, 0), 100);
		Vector3f dir = new Vector3f(4, -7, 12);
		DirectionalLight dl = new DirectionalLight(col, dir);
		dl.setInfluencingBounds(sh);
		group.addChild(dl);
		
		uni.getViewingPlatform().setNominalViewingTransform();
		uni.addBranchGraph(group);
		
	}
}
