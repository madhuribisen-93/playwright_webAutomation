package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import static factory.BrowserFactory.takeScreenshot;

public class ExtendReportListener implements ITestListener {
    private static final String OUTPUT_FOLDER = "./build/";
    private static final String FILE_NAME = "TestExecutionReport.html";

    private static ExtentReports extent = init();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    private static ExtentReports extentReports;

    private static ExtentReports init() {
        Path path = Paths.get(OUTPUT_FOLDER);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        reporter.config().setReportName("Deals Automation Execution Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "Mac");
        extentReports.setSystemInfo("Browser", "Chrome");
        extentReports.setSystemInfo("BuildVersion", "1.32.0");
        extentReports.setSystemInfo("Platform", "Web");

        return extentReports;
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);
        System.out.println(methodName + "started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());  // this will create a new test case with the method name
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + "passed!");
        test.get().pass("Test passed");
        test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + "failed!");
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + "skipped!");
        test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Execution Started!");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Execution is ending");
        extentReports.flush();
        test.remove();
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}