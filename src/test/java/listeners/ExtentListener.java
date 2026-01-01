package listeners;

import Base.BaseTest;
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

    // Maintains execution order + final status
    private static final Map<String, String> testResults = new LinkedHashMap<>();

    /* =========================================================
       SUITE START → PRINT TESTS TO BE EXECUTED (ONCE)
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
                testResults.put(testName, "PENDING");
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
        testResults.put(testName, "PASS");

        test.get().pass("Test passed");
        System.out.println("FINISHED TEST : " + testName);
    }

    /* =========================================================
       TEST FAILURE
       ========================================================= */
    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        testResults.put(testName, "FAIL");

        test.get().fail(result.getThrowable());
        System.out.println("FINISHED TEST : " + testName);

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

    /* =========================================================
       TEST SKIPPED
       ========================================================= */
    @Override
    public void onTestSkipped(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        testResults.put(testName, "SKIPPED");

        test.get().skip("Test skipped");
        System.out.println("FINISHED TEST (SKIPPED) : " + testName);
    }

    /* =========================================================
       SUITE FINISH → PRINT FINAL RESULTS (ONCE)
       ========================================================= */
    @Override
    public void onFinish(ISuite suite) {

        System.out.println("\n================= TEST RESULTS =================");
        System.out.println("+----+--------------------------+--------+");
        System.out.printf("| %-2s | %-24s | %-6s |\n", "No", "Test Name", "Status");
        System.out.println("+----+--------------------------+--------+");

        AtomicInteger index = new AtomicInteger(1);
        testResults.forEach((testName, status) -> {
            System.out.printf("| %-2d | %-24s | %-6s |\n",
                    index.getAndIncrement(), testName, status);
        });

        System.out.println("+----+--------------------------+--------+");

        extent.flush();
    }
}
