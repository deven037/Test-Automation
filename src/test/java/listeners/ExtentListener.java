package listeners;

import Base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.*;

import utils.ExtentManager;
import utils.ScreenshotUtil;

public class ExtentListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());

        Object instance = result.getInstance();

        if (instance instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) instance;

            String path = ScreenshotUtil.captureScreenshot(
                    baseTest.getDriver(),
                    result.getMethod().getMethodName()
            );

            if (path != null) {
                test.get().addScreenCaptureFromPath(path);
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
