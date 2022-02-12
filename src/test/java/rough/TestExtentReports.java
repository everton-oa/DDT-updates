package rough;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestExtentReports {
    public ExtentSparkReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    @BeforeTest
    public void setReport(){
        htmlReporter = new ExtentSparkReporter("./reports/extent.html");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Automation test results");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Automation Tester", "Everton Araujo");
        extent.setSystemInfo("Organization", "Automation.Co");
    }

    @AfterTest
    public void endReport(){
        extent.flush();
    }

    @AfterMethod
    public void updateResults(ITestResult result){
        if(result.getStatus()==ITestResult.FAILURE){
            String methodName = result.getMethod().getMethodName();
            Markup markup = MarkupHelper.createLabel(methodName.toUpperCase()+" - failed", ExtentColor.RED);
            test.fail("Test failed");
            test.fail(markup);
        }else
        if(result.getStatus()==ITestResult.SUCCESS){
            Markup markup = MarkupHelper.createLabel("Test failed", ExtentColor.GREEN);
            test.pass("Test successfully completed");
            test.pass(markup);
        }else if(result.getStatus()==ITestResult.SKIP){
            Markup markup = MarkupHelper.createLabel("Test failed", ExtentColor.AMBER);
            test.skip("Test skipped");
            test.skip(markup);
        }
    }

    @Test
    public void loginTest(){
        test = extent.createTest("Login Test");
        test.log(Status.INFO, "Entering username");
        test.log(Status.INFO, "Entering password");
    }

    @Test
    public void exampleFailTest(){
        test = extent.createTest("Failed test example");
        test.log(Status.INFO, "Filling form fields");
        Assert.fail();
    }

    @Test
    public void exampleSkipTest(){
        test = extent.createTest("Skipped test example");
        test.log(Status.INFO, "Test Skipped");
        throw new SkipException("Skip the test");
    }
}