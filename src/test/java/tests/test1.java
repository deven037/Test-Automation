package tests;


import Pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseTest;



public class test1 extends BaseTest {

    @Test
    public void validLoginTest() {
    	
    	navigateTo("practice_url");
    	
    	LoginPage lp = new LoginPage(driver);

    	lp.enterUsername("student");
    	lp.enterPassword("Password123");
        lp.clickLogin();

        String text = lp.getSuccessMessage();
        Assert.assertTrue(text.contains("Congratulations student. You successfully logged in!"));
    }
}
