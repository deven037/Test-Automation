package utils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

   
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver initDriver() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-geolocation");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-features=FedCM"); // 👈 KEY FIX
        options.addArguments("--disable-application-cache");
        
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);  
        prefs.put("autofill.credit_card_enabled", false);

        options.setExperimentalOption("prefs", prefs);

       
        String ci = System.getenv("CI");
        if (ci != null && ci.equalsIgnoreCase("true")) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--force-device-scale-factor=1");
        } else {
            options.addArguments("--start-maximized");
        }

        WebDriver driverInstance = new ChromeDriver(options);
        driver.set(driverInstance);

        if (ci != null && ci.equalsIgnoreCase("true")) {
            driverInstance.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driverInstance.manage().window().maximize();
        }

        return driverInstance;
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
