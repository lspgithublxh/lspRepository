//package com.bj58.im.client.mediaTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jzy3d.chart.Chart;
//import org.jzy3d.chart.ChartLauncher;
//import org.jzy3d.colors.Color;
//import org.jzy3d.colors.ColorMapper;
//import org.jzy3d.colors.colormaps.ColorMapRainbow;
//import org.jzy3d.maths.Coord3d;
//import org.jzy3d.maths.Range;
//import org.jzy3d.plot3d.builder.Builder;
//import org.jzy3d.plot3d.builder.Mapper;
//import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
//import org.jzy3d.plot3d.primitives.ScatterMultiColor;
//import org.jzy3d.plot3d.primitives.Shape;
//import org.jzy3d.plot3d.rendering.canvas.Quality;
//
//import javafx.application.Application;
//import javafx.stage.Stage;
//
///**
// * swing + javafx 3d 
// * jogamp   jogl
// * jzy3d 效果极好
// * webGL + 后端数据   ：这样更好展示 数据效果  ——javafx中使用：浏览器中开发    。。。一般使用对webGL的高层封装：比如Twater的mono产品   。webGL是OpenGL的简化版本(本地无法访问openGL) 
// * @ClassName:Jzy3D
// * @Description:
// * @Author lishaoping
// * @Date 2018年8月21日
// * @Version V1.0
// * @Package com.bj58.im.client.mediaTest
// */
//public class Jzy3D extends Application{
//
//	/**
//	 * 直接范例，但是缺库
//	 * @param 
//	 * @author lishaoping
//	 * @Date 2018年8月21日
//	 * @Package com.bj58.im.client.mediaTest
//	 * @return void
//	 */
//		public static void main(String[] args) {
//			test();
////			scatter();
//		}
////		launch(args);
//
//
//
//
//	private static void scatter() {
//		int size = 1000;
//		double x, y, z;
//
//		List lista = new ArrayList();
//		for (int i = 0; i < size; i++) {
//		x = (float) Math.random() - 0.5f;
//		y = (float) Math.random() - 0.5f;
//		z = (float) Math.random() - 0.5f;
//		lista.add(new Coord3d(x, y, z));
//		}
//
//		// Create surface 曲面
//		Shape surface = Builder.buildDelaunay(lista);
//		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(),
//		surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
//		surface.setFaceDisplayed(true);
//		surface.setWireframeDisplayed(false);
//		surface.setWireframeColor(Color.BLACK);
//
//		Chart chart = new Chart(Quality.Advanced);
//		chart.getScene().getGraph().add(surface);
//		ChartLauncher.openChart(chart);
//
//		Coord3d[] points = new Coord3d[size];
//		lista.toArray(points);
//
//		// Create scatter 散点
////			MultiColorScatter scatter = new MultiColorScatter(points, new ColorMapper(new ColorMapRainbow(), -0.5f, 0.5f));
//		ScatterMultiColor scatter = new ScatterMultiColor(points, new ColorMapper(new ColorMapRainbow(), -0.5f, 0.5f));
//		
//		chart = new Chart();
//		chart.getAxeLayout().setMainColor(Color.WHITE);
//		chart.getView().setBackgroundColor(Color.BLACK);
//		chart.getScene().add(scatter);
//		ChartLauncher.openChart(chart);
//	}
//	
//		
//	static int Nmax = 100; // Size of box
//	
//	private static void test() {
//		double V[][] = new double[Nmax][Nmax];
//		int i, j, iter;
//
//		for (i = 0; i < Nmax; i++) {
//		for (j = 0; j < Nmax; j++)
//		V[i][j] = 0.;
//		}
//		for (i = 0; i < Nmax; i++)
//		V[i][0] = 100.; // V( wire ) = 100 V
//
//		// Iterations
//		for (iter = 0; iter < 1000; iter++) {
//		// x , then y directions
//		for (i = 1; i < (Nmax - 1); i++) {
//		for (j = 1; j < (Nmax - 1); j++)
//		// THE ALGORITM
//		V[i][j] = (V[i + 1][j] + V[i - 1][j] + V[i][j + 1] + V[i][j - 1]) / 4.;
//		}
//		}
//
//		// Define a function to plot
//
//		Mapper mapper = new Mapper() {
//		public double f(double x, double y) {
//		return V[(int)x][(int)y];
//		}
//		};
//
//		// Define range and precision for the function to plot
//		Range range = new Range(0, Nmax-1);
//		int steps = Nmax;
//
//		// Create a surface drawing that function
//		Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
//		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(),
//		surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
//		surface.setFaceDisplayed(true);
//		surface.setWireframeDisplayed(false);
//		surface.setWireframeColor(Color.BLACK);
//
//		// Create a chart and add the surface
//		Chart chart = new Chart(Quality.Advanced);
//		chart.getScene().getGraph().add(surface);
//		ChartLauncher.openChart(chart);
//
//	}
//
//
//
//
//	@Override
//	public void start(Stage arg0) throws Exception {
//		
//	}
//
//}
