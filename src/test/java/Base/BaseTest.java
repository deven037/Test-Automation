package Base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;
import utils.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = DriverFactory.initDriver();
        driver.manage().window().maximize();
    }

  
    protected void navigateTo(String urlKey) {

        String url = ConfigReader.get(urlKey);

        if (url == null || url.isEmpty()) {
            throw new RuntimeException(
                "URL not found in config.properties for key: " + urlKey
            );
        }

        driver.get(url);
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            DriverFactory.quitDriver();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
