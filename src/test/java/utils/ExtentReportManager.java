package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Deve ser chamado uma única vez (normalmente no onStart do listener)
    public static void setExtentReports(ExtentReports extent) {
        extentReports = extent;
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        extentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
