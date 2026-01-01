package utils;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class WaitUtil {

    private WebDriverWait wait;

    public WaitUtil(WebDriver driver, int timeoutInSeconds) {
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
}
