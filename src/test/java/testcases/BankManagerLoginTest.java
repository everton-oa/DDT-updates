package testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;

public class BankManagerLoginTest extends TestBase{
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		click("bankManagerLoginBtn_CSS");
		Thread.sleep(2000);
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustomerBtn_CSS"))), "Could not find addCustomerBtn");
	}

}
