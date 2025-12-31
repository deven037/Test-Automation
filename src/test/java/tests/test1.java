package tests;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class test1 extends BaseTest {

    @Test
    public void validLoginTest() {

        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("Password123");
        driver.findElement(By.id("submit")).click();

        String text = driver.findElement(By.tagName("h1")).getText();
        Assert.assertTrue(text.contains("Logged In"));
    }
}
