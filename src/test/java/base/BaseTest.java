package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        // Initialize WebDriver
        driver = DriverFactory.initDriver();

        // Navigate to URL
        driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    @AfterMethod
    public void tearDown() {
        // Quit WebDriver
        DriverFactory.quitDriver();
    }
    
    public WebDriver getDriver() {
        return driver;
    }
}
