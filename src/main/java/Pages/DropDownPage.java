package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.BasePage;

public class DropDownPage extends BasePage {
	
	static WebElement wb;

	public DropDownPage(WebDriver driver) {
		super(driver);
	}

	private By prefix = By.xpath("//*[@id=\"prefix\"]");
	
	private By month = By.xpath("//*[@id=\"dob_month\"]");
	private By date = By.xpath("//*[@id=\"dob_date\"]");
	private By year = By.xpath("//*[@id=\"dob_year\"]");
	
	private By phCityCode = By.xpath("//*[@id=\"country_code\"]");
	private By nationality = By.xpath("//*[@id=\"nationality\"]");
	private By country = By.xpath("//*[@id=\"country\"]");
	
	private By submit = By.xpath("/html/body/div[3]/div/div[2]/div/form/div[15]/div[2]/input");
	private By successMsg = By.xpath("/html/body/div[3]/div/div/div[2]/p");
	
	public void selectPrefix(String prefixText) {
		wait.waitForClickability(prefix);
		click(prefix);
		selectFromDropDown(prefix, prefixText);
	}
	
	public void selectDOB(String dates, String months, String years) {
		selectFromDropDown(month, months);
		selectFromDropDown(date, dates);
		selectFromDropDown(year, years);
	}
	
	public void selectMobileCode(String code) {
		selectFromDropDown(phCityCode, code);
	}
	
	public void selectNation(String nation) {
		selectFromDropDown(nationality, nation);
	}
	
	public void selectCountry(String countryy) {
		selectFromDropDown(country, countryy);
	}
	
	public void clickBtn() {
		click(submit);
	}
	
	public String getSuccessMsg() {
		String msg = getText(successMsg);
		return msg;
	}

}
