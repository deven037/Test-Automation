package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {

        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String screenshotDir = "reports/screenshots/";
            new File(screenshotDir).mkdirs();

            String path = screenshotDir + testName + ".png";
            FileUtils.copyFile(src, new File(path));

            return path;
        } catch (Exception e) {
            return null;
        }
    }
}
