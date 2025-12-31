package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Base.BaseTest;

public class test1 extends BaseTest {

    @Test
    public void verifyValidLogin() {
        LoginPage lp = new LoginPage(driver);

        lp.enterUsername("student");
        lp.enterPassword("Password123");
        lp.clickLogin();

        Assert.assertTrue(lp.isLoginSuccessful(), "Login failed!");
        System.out.println(lp.getSuccessMessage());
    }
}
