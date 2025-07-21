package org.training.selenium.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.apache.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.training.selenium.util.ExtentReportNG;

import java.io.IOException;

public class Listeners extends BaseScript implements ITestListener {

    public static Logger log = Logger.getLogger(Listeners.class);
    ExtentTest test;
    ExtentReports extent = ExtentReportNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    @Override
    public void onTestStart(ITestResult iTestResult) {
        test = extent.createTest(iTestResult.getMethod().getMethodName());
        extentTest.set(test);
        log.info("Test STARTED: " + iTestResult.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        extentTest.get().log(Status.PASS,"Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        extentTest.get().fail(iTestResult.getThrowable());
        log.error("Test FAILED: " + iTestResult.getMethod().getMethodName(), iTestResult.getThrowable());

        try {
            driver = (WebDriver) iTestResult.getTestClass().getRealClass().getField("driver").get(iTestResult.getInstance());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Failed to access WebDriver from test class", e);
        }

        try {
            String filePath = takeScreenShot(iTestResult.getMethod().getMethodName(), driver);
            extentTest.get().addScreenCaptureFromPath(filePath, iTestResult.getMethod().getMethodName());
            log.error("Screenshot saved at: " + filePath);
        } catch (IOException e) {
            log.error("Failed to capture screenshot", e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        extentTest.get().log(Status.SKIP, "Test Skipped: " + iTestResult.getThrowable());
        log.warn("Test SKIPPED: " + iTestResult.getMethod().getMethodName(), iTestResult.getThrowable());

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        //log.info("Test Suite STARTED: " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("Test Suite FINISHED: " + iTestContext.getName());
        extent.flush();
    }
}