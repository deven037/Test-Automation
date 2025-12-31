package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
import java.nio.file.*;

public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getExtent() {

        if (extent == null) {

            // 1️⃣ Clean old reports
            cleanReports();

            // 2️⃣ Create Spark reporter (IMPORTANT)
            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter("reports/index.html");

            // 3️⃣ Spark UI configuration (CRITICAL FOR JENKINS UI)
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("UI Automation Results");
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setEncoding("utf-8");

            // 4️⃣ Attach reporter
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // 5️⃣ System info (optional but good)
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Java", System.getProperty("java.version"));
        }
        return extent;
    }

    // 🔥 CLEAN REPORTS SAFELY
    private static void cleanReports() {
        Path reportsPath = Paths.get("reports");

        try {
            if (Files.exists(reportsPath)) {
                Files.walk(reportsPath)
                        .sorted((a, b) -> b.compareTo(a))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException ignored) {}
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
