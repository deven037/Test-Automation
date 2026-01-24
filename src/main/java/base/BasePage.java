package base;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
        wait.waitForClickability(locator).click();
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
    
    protected void selectFromDropDown(By locator, String text) {
    	WebElement dropDown =  wait.waitForClickability(locator);
    	Select select = new Select(dropDown);
    	select.selectByVisibleText(text);
    }
    
    protected void selectFromDropDownByOption(By locator, int optionNumber) {

        if (optionNumber <= 0) {
            throw new IllegalArgumentException(
                "Option number must be >= 1. Given: " + optionNumber
            );
        }

        WebElement dropDown = wait.waitForClickability(locator);
        wait.waitForOptions(locator, optionNumber);

        Select select = new Select(dropDown);
        select.selectByIndex(optionNumber - 1);
    }

    
    protected String getCurrentWindow() {
    	return driver.getWindowHandle();
    }
    
    protected void switchToWindow(String windowHandle) {
    	driver.switchTo().window(windowHandle);
    }
    
    protected void closeCurrentWindow() {
    	driver.close();
    }
    
    protected void switchToNewWindow(String parentWindow) {

        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }
    
}
