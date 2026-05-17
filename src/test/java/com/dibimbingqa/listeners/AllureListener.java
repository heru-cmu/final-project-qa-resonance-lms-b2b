package com.dibimbingqa.listeners;

import io.qameta.allure.Allure;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // attach log sederhana ke report
        Allure.addAttachment("Failure Reason",
                new ByteArrayInputStream(result.getThrowable().toString().getBytes()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Allure.addAttachment("Success",
                new ByteArrayInputStream(("Test Passed: " + result.getName()).getBytes()));
    }
}
