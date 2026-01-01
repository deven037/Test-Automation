package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.LoginPage;
import utils.WaitUtil;

public class ValidateIncorrectLogin extends BaseTest {
	
  @Test
  public void validateIncorrectLogin() {
	  LoginPage lp = new LoginPage(driver);
	
	  waitForUrl("https://practicetestautomation.com/practice-test-login/");
	  lp.enterUsername("test");
	  lp.enterPassword("Password123");
	  lp.clickLogin();
	  

	  if(lp.isLoginUnsuccessfull()) {
		 String incorrectUserMsg = lp.getIncorrectUserMsg();
		  Assert.assertTrue(incorrectUserMsg.contains("Your username is invalid!"));
	  }
	  
  }
  
  protected void waitForUrl(String url) {
	  WaitUtil wait = new WaitUtil(driver, 10);
	  wait.waitForUrl(url);
  }
}
