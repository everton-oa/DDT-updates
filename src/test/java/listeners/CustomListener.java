package listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import utilities.TestUtil;

public class CustomListener extends TestBase implements ITestListener{

	public void onTestStart(ITestResult result) {
		test = rep.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " started");
		System.out.print(ANSI_GREEN + "====================> " + result.getName() + " started \n"+ ANSI_RESET);
	}

	public void onTestSuccess(ITestResult result) {
		System.out.print(ANSI_GREEN + "====================>" + result.getName() + " successfully executed\n"+ ANSI_RESET);
		test.log(LogStatus.PASS, result.getName().toUpperCase() + " PASS");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestFailure(ITestResult result) {
		test.log(LogStatus.FAIL, result.getName() + " FAILED.");
		System.out.print(ANSI_RED + "(!) " + result.getName() + " FAILED\n"+ ANSI_RESET);

		try {
			TestUtil.captureScreenShoot();
		} catch (IOException e) {
			e.printStackTrace();
		}

		test.log(LogStatus.ERROR, "stepName", result.getThrowable());
		rep.endTest(test);
		rep.flush();
	}

	public void onTestSkipped(ITestResult result) {
		test.log(LogStatus.SKIP, result.getName() + " SKIPPED. Runner mode is NO");
		log.debug(result.getName() + " SKIPPED. Runner mode is NO ");
		System.out.print(ANSI_BLUE + "(!) " + result.getName() + " SKIPPED. Runner mode is NO \n" + ANSI_RESET);
		rep.endTest(test);
		rep.flush();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ITestContext context) {

	}
}
