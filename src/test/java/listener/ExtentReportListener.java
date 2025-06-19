package listener;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;
import utils.ExtentReportManager;

public class ExtentReportListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report/extent-report.html");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Dog API Test Report");
        spark.config().setReportName("Automated API Tests Dog API");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Projeto", "Dog API Automation");
        extent.setSystemInfo("Executado por", System.getProperty("user.name"));

        ExtentReportManager.setExtentReports(extent);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = ExtentReportManager.createTest(result.getMethod().getMethodName());
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().pass(" Teste passou com sucesso");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip(" Teste ignorado: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flush();
    }
}
