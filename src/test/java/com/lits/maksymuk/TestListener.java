package com.lits.maksymuk;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("*** TEST STARTED : " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("*** TEST SUCCESS : " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("*** TEST FAILURE : " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
