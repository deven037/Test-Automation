package tests;

import Base.BaseTest;
import Pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class test1 extends BaseTest {

    @Test
    public void validLoginTest() {
    	
    	LoginPage lp = new LoginPage(driver);

    	lp.enterUsername("student");
    	lp.enterPassword("Password123");
        lp.clickLogin();

        String text = lp.getSuccessMessage();
        Assert.assertTrue(text.contains("Congratulations student. You successfully logged in!"));
    }
}
