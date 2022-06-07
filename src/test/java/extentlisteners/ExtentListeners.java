package extentlisteners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.TestUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static base.TestBase.*;

public class ExtentListeners implements ITestListener {

    static Date date = new Date();
    static String fileName = "Extent_" + date.toString().replace(":", "_").replace(" ", "_") + ".html";
    private static ExtentReports extent = ExtentManager.createInstance(System.getProperty("user.dir") + "/target/" + fileName);
    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " | @TestCase: " + result.getMethod().getMethodName());
        testReport.set(test);
        testReport.get().info(result.getName() + " started");
        System.out.print(ANSI_GREEN + "====================> " + result.getName() + " started \n"+ ANSI_RESET);
    }

    public void onTestSuccess(ITestResult result) {
        String logText = "<b>TEST CASE: " + result.getMethod().getMethodName() + " PASSED</b>";
        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        testReport.get().pass(markup);
        System.out.print(ANSI_GREEN + "====================> " + result.getName() + " successfully executed\n"+ ANSI_RESET);
    }

    public void onTestFailure(ITestResult result) {
        String failMessage = "<b>TEST CASE: " + result.getMethod().getMethodName() + " FAILED</b>";
        Markup markup = MarkupHelper.createLabel(failMessage, ExtentColor.RED);
        testReport.get().log(Status.FAIL, markup);
        log.debug("(!) " + result.getName() + " FAILED");
        System.out.print(ANSI_RED + "(!) " + result.getName() + " FAILED\n"+ ANSI_RESET);

        try {
            TestUtil.captureScreenShoot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            testReport.get().info("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screeshotPath+TestUtil.screeshotName)
                            .build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        testReport.get().info("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured: Click here to see"
                + "</font>" + "</b >" + "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>" + " \n");
	/*	try {

			ExtentManager.captureScreenshot();
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName)
							.build());
		} catch (IOException e) {

		}*/

    }

    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "<b>Test Case: " + methodName + " SKIPPED. Runner mode is NO</b>";
        Markup markup = MarkupHelper.createLabel(logText, ExtentColor.AMBER);
        testReport.get().skip(markup);
        log.debug(result.getName() + " SKIPPED. Runner mode is NO ");
        System.out.print(ANSI_BLUE + "(!) " + result.getName() + " SKIPPED. Runner mode is NO \n" + ANSI_RESET);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}
