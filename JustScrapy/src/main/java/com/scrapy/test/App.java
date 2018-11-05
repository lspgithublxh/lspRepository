package com.scrapy.test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App 
{
    public static void main( String[] args )
    {
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");//chromedriver服务地址
        ChromeOptions options = new ChromeOptions();
        options.addArguments(Arrays.asList(new String[] {"--no-sandbox","window-size=1920x3000", "--disable-gpu", "--hide-scrollbars", "blink-settings=imagesEnabled=false", "--headless"}));
        WebDriver driver = new ChromeDriver(options); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
        
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
//        driver.quit();
//        driver.close();
    }
}
