package com.payment_system.ServiceTests;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class MobileTest {

    @Test
    public void mobiletest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");


        driver.manage().window().fullscreen();

        driver.get("http://mobile.bg");

        Thread.sleep(1000);

        driver.close();

    }
}
