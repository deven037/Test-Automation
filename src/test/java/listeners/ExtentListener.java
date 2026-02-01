package listeners;

import Base.BaseTest;
import exceptions.Errors;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import org.testng.*;

import utils.ExtentManager;
import utils.ScreenshotUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ExtentListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    /* =========================================================
       STORE FINAL RESULT + ERROR TYPE
       ========================================================= */
    private static final Map<String, ResultInfo> testResults = new LinkedHashMap<>();

    private static class ResultInfo {
        String status;
        String errorType;

        ResultInfo(String status, String errorType) {
            this.status = status;
            this.errorType = errorType;
        }
    }

    /* =========================================================
       SUITE START → PRINT TESTS TO BE EXECUTED
       ========================================================= */
    @Override
    public void onStart(ISuite suite) {

        System.out.println("\n================= TESTS TO BE EXECUTED =================");
        System.out.println("+----+--------------------------+");
        System.out.printf("| %-2s | %-24s |\n", "No", "Test Name");
        System.out.println("+----+--------------------------+");

        AtomicInteger index = new AtomicInteger(1);
        suite.getAllMethods().forEach(method -> {
            String testName = method.getMethodName();
            if (!testResults.containsKey(testName)) {
                testResults.put(testName, new ResultInfo("PENDING", "NONE"));
                System.out.printf("| %-2d | %-24s |\n",
                        index.getAndIncrement(), testName);
            }
        });

        System.out.println("+----+--------------------------+");
    }

    /* =========================================================
       TEST START
       ========================================================= */
    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    /* =========================================================
       TEST SUCCESS
       ========================================================= */
    @Override
    public void onTestSuccess(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        testResults.put(testName, new ResultInfo("PASS", "NONE"));

        test.get().pass("Test passed");
        System.out.println("FINISHED TEST : " + testName);
    }

    /* =========================================================
       TEST FAILURE → ERROR TYPE AWARE
       ========================================================= */
    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();

        String errorType = "UNKNOWN";

        if (throwable instanceof Errors) {
            Errors err = (Errors) throwable;
            errorType = err.getErrorType().name();
        }

        testResults.put(testName, new ResultInfo("FAIL", errorType));

        test.get().fail(throwable);

        if (throwable instanceof Errors) {

            Errors err = (Errors) throwable;

            switch (err.getErrorType()) {

                case AUTOMATION:
                    test.get().assignCategory("AUTOMATION ERROR");
                    test.get().fail("Failure Type: Automation Issue");
                    attachScreenshot(result, testName);
                    break;

                case DATA_ISSUE:
                    test.get().assignCategory("DATA ISSUE");
                    test.get().fail("Failure Type: Test Data Issue");
                    test.get().info("Screenshot skipped for data issues");
                    break;
            }

        } else {
            test.get().assignCategory("UNKNOWN ERROR");
            attachScreenshot(result, testName);
        }

        System.out.println("FINISHED TEST : " + testName);
    }

    /* =========================================================
       TEST SKIPPED
       ========================================================= */
    @Override
    public void onTestSkipped(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        testResults.put(testName, new ResultInfo("SKIPPED", "NONE"));

        test.get().skip("Test skipped");
        System.out.println("FINISHED TEST (SKIPPED) : " + testName);
    }

    /* =========================================================
       SUITE FINISH → PRINT FINAL RESULT TABLE
       ========================================================= */
    @Override
    public void onFinish(ISuite suite) {

        System.out.println("\n================= TEST RESULTS =================");
        System.out.println("+----+--------------------------+--------+---------------+");
        System.out.printf(
                "| %-2s | %-24s | %-6s | %-13s |\n",
                "No", "Test Name", "Status", "Error Type"
        );
        System.out.println("+----+--------------------------+--------+---------------+");

        AtomicInteger index = new AtomicInteger(1);
        testResults.forEach((testName, info) -> {
            System.out.printf(
                    "| %-2d | %-24s | %-6s | %-13s |\n",
                    index.getAndIncrement(),
                    testName,
                    info.status,
                    info.errorType
            );
        });

        System.out.println("+----+--------------------------+--------+---------------+");

        extent.flush();
    }

    /* =========================================================
       HELPER → SCREENSHOT ONLY FOR AUTOMATION ERRORS
       ========================================================= */
    private void attachScreenshot(ITestResult result, String testName) {

        Object instance = result.getInstance();

        if (instance instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) instance;

            String path = ScreenshotUtil.captureScreenshot(
                    baseTest.getDriver(), testName
            );

            if (path != null) {
                test.get().addScreenCaptureFromPath(path);
            }
        }
    }
}
