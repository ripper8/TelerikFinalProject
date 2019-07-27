package com.payment_system.ServiceTests;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MobileTest {

    @Test
    public void mobiletest() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.manage().window().fullscreen();

        driver.get("http://mobile.bg");

        Thread.sleep(1000);

        driver.close();

    }
}
