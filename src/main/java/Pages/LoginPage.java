package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import base.BasePage;

public class LoginPage extends BasePage {

    private By username = By.id("username");
    private By password = By.id("password");
    private By loginBtn = By.id("submit");
    private By successMsg = By.cssSelector(
        "#loop-container div.post-content p.has-text-align-center"
    );
    private By incorrectuserMsg = By.id("error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String user) {
        type(username, user);
    }

    public void enterPassword(String pass) {
        type(password, pass);
    }

    public void clickLogin() {
    	wait.waitForVisiblity(loginBtn);
        click(loginBtn);
    }

    public boolean isLoginSuccessful() {
        return isDisplayed(successMsg);
    }

    public String getSuccessMessage() {
        return getText(successMsg);
    }
    
    public boolean isLoginUnsuccessfull() {
    	return isDisplayed(incorrectuserMsg);
    }
    
    
    public String getIncorrectUserMsg() {
    	return getText(incorrectuserMsg);
    }
}
