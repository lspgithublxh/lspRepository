package com.scrapy.test;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.xml.sax.SAXException;

public class App 
{
	static {
		System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");//chromedriver服务地址
	}
	
    public static void main( String[] args ) {
//        oneTest();
//        driver.quit();
//        driver.close();
    	xpathTest();
    }

	private static void xpathTest() {
		WebDriver driver = getOneDriver();
		driver.get("https://bj.ke.com/ershoufang/101103599958.html?fb_expo_id=111874061422358528");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); 
        WebElement element = driver.findElement(By.className("sellDetailHeader"));
        System.out.println(element.getTagName());
        System.out.println(element.getText());
        WebElement el = element.findElement(By.tagName("div").className("title").tagName("h1"));
        System.out.println("-----------");
        System.out.println(el.getAttribute("title"));
//        WebElement elemeent = driver.findElement(By.xpath("//div[@class='sellDetailHeader']"));
//        System.out.println(elemeent.getText());
//		xpath(driver.getPageSource());
//      xpath2(driver.getPageSource());
	}

	private static void xpath2(String pageSource) {
		XPathFactory fa = XPathFactory.newInstance();
		XPath p = fa.newXPath();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        org.w3c.dom.Document d;
		try {
	        DocumentBuilder db = dbf.newDocumentBuilder();
//			d = db.parse("https://bj.ke.com/ershoufang/101103599958.html?fb_expo_id=111874061422358528");
			d = db.parse(new StringBufferInputStream(pageSource));
	        org.w3c.dom.Node no = (org.w3c.dom.Node) p.evaluate("//div[@class='sellDetailHeader']", d, XPathConstants.NODE);
			System.out.println(no);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
	}

	private static void oneTest() {
        WebDriver driver = getOneDriver();
        
        driver.get("http://www.baidu.com");//打开指定的网站
        driver.findElement(By.id("kw")).sendKeys(new  String[] {"hello"});//找到kw元素的id，然后输入hello
        driver.findElement(By.id("su")).click(); 
        try {
            /**
             * WebDriver自带了一个智能等待的方法。
            dr.manage().timeouts().implicitlyWait(arg0, arg1）；
            Arg0：等待的时间长度，int 类型 ；
            Arg1：等待时间的单位 TimeUnit.SECONDS 一般用秒作为单位。
             */
           driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); 
           System.out.println(driver.getPageSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private static WebDriver getOneDriver() {
		ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList(new String[] {"--no-sandbox","window-size=1920x3000", "--disable-gpu", "--hide-scrollbars", "blink-settings=imagesEnabled=false", "--headless"}));
        WebDriver driver = new ChromeDriver(options); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
		return driver;
	}
	
	private static void xpath(String page) {
		System.out.println(page);
		String xpath = "//div[@class='sellDetailHeader']";////div[@class='title']/h1//@title
		try {
			Document document = new SAXReader().read(new StringReader(page));
			List<Node> nodes = document.selectNodes(xpath);
			for(Node d : nodes) {
				System.out.println(d.getName());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
}
