package dropdown;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.DropDownPage;

public class DropDown extends BaseTest {
  @Test
  public void practiceDropDowns() {
	  navigateTo("dropdown_url");
	  DropDownPage dp = new DropDownPage(driver);
	  
	  dp.selectPrefix("Mr.");
	  dp.selectDOB("3", "July" , "2002");
	  dp.selectMobileCode("India (+91)");
	  dp.selectNation("Indian");
	  dp.selectCountry("India");
	  dp.clickBtn();
	  verifyMsg();
  }
  
  protected void verifyMsg() {
	  DropDownPage dp = new DropDownPage(driver);
	  String message = dp.getSuccessMsg();
	  
	  Assert.assertEquals(message, message);
  }
}
