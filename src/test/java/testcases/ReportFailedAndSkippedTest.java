package testcases;

import base.TestBase;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.HomeLoginPage;

import java.io.IOException;

public class ReportFailedAndSkippedTest extends TestBase {

	HomeLoginPage homeLoginPage = new HomeLoginPage();

	@Test
	public void ReportFailedTest() throws IOException {
		// soft assertion to capture screenshot
		homeLoginPage.verifyEquals("1", "2");
		Assert.fail();
	}
	
	@Test
	public void ReportSkippedTest() {
		throw new SkipException("");
	}
}
