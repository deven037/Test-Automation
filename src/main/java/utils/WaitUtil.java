package utils;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public class WaitUtil {

    private WebDriverWait wait;
    private WebDriver driver;

    public WaitUtil(WebDriver driver, int timeoutInSeconds) {
    	this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public void waitForUrl(String url) {
    	try {
    		wait.until(ExpectedConditions.urlContains(url));
		} catch (TimeoutException e) {
			System.out.println("Environment feels slow : " + e);
		}
    }
    
    public WebElement waitForVisiblity(By locator) {
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
//    public void waitForInteractableAndClick(By locator) {
//        try {
//            waitForClickability(locator).click();
//        } catch (ElementClickInterceptedException e) {
//        	dismissOverlayIfPresent();
//            waitForClickability(locator).click();
//        }
//    }
    
    public void waitForInteractableAndClick(By locator) {

        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );

        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    element
            );

            wait.until(ExpectedConditions.elementToBeClickable(element)).click();

        } catch (ElementClickInterceptedException e) {
            dismissOverlayIfPresent();
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    element
            );
        }
    }


    
    public void dismissOverlayIfPresent() {

        By overlay = By.xpath(
            "/html/body/div[2]/div/div"
        );

        try {
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(overlay));

            
            new Actions(driver)
            .sendKeys(Keys.ESCAPE)
            .perform();

            
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));

        } catch (TimeoutException e) {
            // Overlay never appeared → nothing to do
        }
    }
    
    public boolean waitForElementInvisibility(By locator) {
    	return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    public WebElement waitForPresence(By locator) {
    	return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    
    /////click backspace/////
    public void clickBackSpace() {
    	new Actions(driver)
        .sendKeys(Keys.BACK_SPACE)
        .perform();
    }

}
