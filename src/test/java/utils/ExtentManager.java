package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {

        if (extent == null) {

            // Ensure reports directory exists
            new File("reports").mkdirs();

            ExtentSparkReporter spark =
                    new ExtentSparkReporter("reports/index.html");

            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System info (shows in report)
            extent.setSystemInfo("Project", "Test Automation");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Executed By", System.getProperty("user.name"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }

        return extent;
    }
}
