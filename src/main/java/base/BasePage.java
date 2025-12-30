package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
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

    // ✅ ADD THIS METHOD
    protected String getText(By locator) {
        return getElement(locator).getText();
    }
}
