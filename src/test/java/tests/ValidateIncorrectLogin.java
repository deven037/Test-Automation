package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.LoginPage;

public class ValidateIncorrectLogin extends BaseTest {
	
  @Test
  public void validateIncorrectLogin() {
	  LoginPage lp = new LoginPage(driver);
	  
	  lp.enterUsername("test");
	  lp.enterPassword("Password123");
	  lp.clickLogin();
	  
	  String incorrectUserMsg = "";

	  if(lp.isLoginUnsuccessfull()) {
		  incorrectUserMsg = lp.getIncorrectUserMsg();
	  }
	  
	  Assert.assertTrue(incorrectUserMsg.contains("Your username is invalid!"));
	  
  }
}
