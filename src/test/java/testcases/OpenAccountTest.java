package testcases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.HomeLoginPage;
import utilities.TestUtil;

import java.util.Hashtable;

public class OpenAccountTest extends TestBase {

	HomeLoginPage homeLoginPage = new HomeLoginPage();

	@Test (dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest (Hashtable<String, String> data) {
//		if (!TestUtil.isTestRunnable("OpenAccountTest", excel)) {
//			throw new SkipException("");
//		}
		homeLoginPage.openHomePageUrl(config.getProperty("testurl"))
				.clickBankManagerLoginButton()
				.clickOpenAccountButton()
				.selectCustomerDropDown(data.get("customer"))
				.selectCurrencyDropDown(data.get("currency"))
				.clickProcessButton();
		// TODO como utilizar o istestrunnable para fazer verificacao da tab testsuite e da tab do test

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
	}
}
