package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            Path screenshotDir = Paths.get("reports/screenshots");
            Files.createDirectories(screenshotDir);

            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Path dest = screenshotDir.resolve(testName + ".png");
            Files.copy(src.toPath(), dest);

            return dest.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
