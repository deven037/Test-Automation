package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtil;

public class BasePage {

    protected WebDriver driver;
    protected WaitUtil wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtil(driver, 30);
    }

    protected WebElement getElement(By locator) {
        return driver.findElement(locator);
    }

    protected void click(By locator) {
        getElement(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isDisplayed(By locator) {
        return getElement(locator).isDisplayed();
    }

    protected String getText(By locator) {
        return getElement(locator).getText();
    }
    
    protected void chooseFlightType(By locator) {
    	getElement(locator).click();
    }
    
    protected void customTimeout(int seconds) throws Exception {
    	Thread.sleep(seconds * 1000L);
        Thread.currentThread().interrupt();
    }
    
}
