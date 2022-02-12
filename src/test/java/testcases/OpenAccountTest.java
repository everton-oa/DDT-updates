package testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(Hashtable<String, String> data) {

		if (data.get("runmode").equalsIgnoreCase("N"))
			throw new SkipException("openAccountTest".toUpperCase() + " - Test Case Skipped: Run mode is No");

		click("openAccountBtn_CSS");
		select("customerNameDd_ID", data.get("customer"));
		select("currencyDd_ID", data.get("currency"));
		click("processBtn_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//		Assert.assertTrue(alert.getText().contains(alertText));
		alert.accept();
	}
}
