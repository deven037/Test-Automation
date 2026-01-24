package parabank;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.ParaBankPage;

public class ValidateUserRegistration extends BaseTest{
	
	private ParaBankPage para;
	
	@BeforeMethod
	public void setupBank() {
		navigateTo("para_bank");
		para = new ParaBankPage(driver);
		
	}
	 
  @Test
  public void userRegister() {
	  para.registerUser();
	  para.enterDetails();
	  verifySuccessfulMsg();
  }
  
  protected void verifySuccessfulMsg() {
	  String verifyMsg = para.successMsg();
	  Assert.assertEquals(para.successMsg(), verifyMsg);
  }
}
