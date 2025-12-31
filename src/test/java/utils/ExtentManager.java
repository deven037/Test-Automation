package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {
        if (extent == null) {
            cleanReports();
            ExtentSparkReporter reporter =
                    new ExtentSparkReporter("reports/index.html");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }

    private static void cleanReports() {
        try {
            Path reports = Paths.get("reports");
            if (Files.exists(reports)) {
                Files.walk(reports)
                        .sorted((a, b) -> b.compareTo(a))
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (Exception ignored) {}
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
