package com.cn.jmw.uitls;

/**
 * @author jmw
 * @Description TODO
 * @date 2023年06月12日 11:34
 * @Version 1.0
 */
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Date;
import java.util.List;

public class SelenuimUtils {

    public static void main(String[] args) throws InterruptedException {
        //options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        //设置驱动
        System.setProperty("webdriver.chrome.driver","D:\\coding\\deletedemo\\demo-selenium\\src\\main\\resources\\chromedriver.exe");

        // 1.创建webdriver驱动
        WebDriver driver = new ChromeDriver();
        // 2.打开百度首页
        driver.get("https://ones.eoitek.com/wiki/#/team/UK9JeLWj/space/Dq5FN92S/page/FA6Vsptw");

        List<WebElement> elements = driver.findElements(By.className("ones-tree-list-holder-inner"));


        //TODO 外部一级菜单
        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
//        // 3.获取输入框，输入selenium
//        driver.findElement(By.id("kw")).sendKeys("selenium");
        // 4.获取“百度一下”按钮，进行搜索
//        driver.findElement(By.id("su")).click();
        // 5.退出浏览器
        Thread.sleep(10000);
        driver.quit();
    }
}
