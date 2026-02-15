package Pages;

import exceptions.Errors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import base.BasePage;
import testdata.Traveller;

public class ParaBankPage extends BasePage {

	private String username;
	private String password;



	public ParaBankPage(WebDriver driver) {
		super(driver);
		this.username = "test-" + System.currentTimeMillis();
		this.password = "test@1234";
	}

	private By register = By.xpath("//a[text()='Register']");
	
	private By detailsFields = By.xpath("//*[@id=\"customerForm\"]");
	
	private By first_name = By.xpath("//*[@id=\"customer.firstName\"]");
	private By last_name = By.xpath("//*[@id=\"customer.lastName\"]");
	private By address = By.xpath("//*[@id=\"customer.address.street\"]");
	private By city = By.xpath("//*[@id=\"customer.address.city\"]");
	private By state = By.xpath("//*[@id=\"customer.address.state\"]");
	private By zip_code = By.xpath("//*[@id=\"customer.address.zipCode\"]");
	private By phone = By.xpath("//*[@id=\"customer.phoneNumber\"]");
	private By ssn = By.xpath("//*[@id=\"customer.ssn\"]");
	private By registerUsername = By.xpath("//*[@id=\"customer.username\"]");
	private By registerPassword = By.xpath("//*[@id=\"customer.password\"]");
	private By registerConfirmPassword = By.xpath("//*[@id=\"repeatedPassword\"]");
	private By registerBtn = By.xpath("//input[@value='Register']");
	
	private By userCreatedSuccessfulMsg = By.xpath("//*[@id=\"rightPanel\"]/p");
	
	
	private By loginUsername = By.xpath("//input[@name='username']");
	private By loginPassword = By.xpath("//input[@name='password']");
	private By loginBtn = By.xpath("//input[@value='Log In']");
	
	private By accountTable = By.xpath("//*[@id=\"accountTable\"]");
	
	private By openNewAccountLink = By.xpath("//a[text()='Open New Account']");
	private By accountTypeDropDown = By.xpath("//*[@id=\"type\"]");
	private By accountIdDropDown = By.xpath("//*[@id=\"fromAccountId\"]");
	//private By clickOpenAccBtn = By.xpath("//*[@id=\"openAccountForm\"]/form/div/input");
	private By clickOpenAccBtn = By.cssSelector("input[value='Open New Account']");
	
	private By getCreatedAccountNumber = By.xpath("//*[@id=\"newAccountId\"]");
	private By getCreatedAccountNumberFromAccountDetails = By.xpath("//*[@id=\"accountId\"]");

    private By errorOnCreateAccount = By.xpath("//*[@id=\"openAccountError\"]/p");
	 
	
	
	public void registerUser() {
		click(register);
	}
	
	public void enterDetails(Traveller traveller){
		wait.waitForVisiblity(detailsFields);
		
		type(first_name, traveller.firstName);
		type(last_name, traveller.lastName);
		type(address, traveller.address);
		type(city, traveller.city);
		type(state, traveller.state);
		type(zip_code, traveller.zip);
		type(phone, traveller.phone);
		type(ssn, traveller.ssn);
		wait.waitForPresence(registerUsername);
		type(registerUsername, this.username);
		type(registerPassword, this.password);
		type(registerConfirmPassword, this.password);
	}
	
	//method overloading
	public void enterDetails() {
		enterDetails(new Traveller());
	}
	
	public void clickRegister() {
		click(registerBtn);
	}
	
	public void loginUser() {
		type(loginUsername, this.username);
		type(loginPassword, this.password);
		click(loginBtn);
	}
	
	public boolean accountDetail() {
		wait.waitForVisiblity(accountTable);
		return getElement(accountTable).isDisplayed();
	}
	
	public String successMsg() {
		String message = getText(userCreatedSuccessfulMsg);
		return message;
	}
	
	
	//account opening
	public void openCreateAccountPage() {
		click(openNewAccountLink);
		wait.waitForVisiblity(accountTypeDropDown);
	}
	
	public void openSavingsAccount(String accType) {
		click(accountTypeDropDown);
		selectFromDropDown(accountTypeDropDown, accType);
	}
	
	public void selectAccountNumberFromDropDown(int option) {
        if(getElement(accountIdDropDown).isDisplayed()){
            try {
                click(accountIdDropDown);
                selectFromDropDownByOption(accountIdDropDown, option);
            } catch (Exception e){
                throw new Errors(Errors.ErrorType.DATA_ISSUE, "Locator not present");
            }

        } else {
            String errorMsg = getText(errorOnCreateAccount);
            throw new Errors(Errors.ErrorType.DATA_ISSUE, errorMsg);
        }
	}
	
	public void clickOpenAccountBtn() {
		wait.waitForPresence(clickOpenAccBtn);	
		click(clickOpenAccBtn);
	}
	
	public String getAccountNumber() {
		String accNumber = getText(getCreatedAccountNumber);
		click(getCreatedAccountNumber);
		return accNumber;
	}
	
	public String getAccNumberFromAccountDetails() {
		String number = getText(getCreatedAccountNumberFromAccountDetails);
		return number;
	}

}
