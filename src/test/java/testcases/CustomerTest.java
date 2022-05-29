package testcases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomeLoginPage;
import pages.ManagerCustomersPage;
import pages.ManagerPage;
import utilities.TestUtil;

import java.io.IOException;
import java.util.Hashtable;

public class CustomerTest extends TestBase {

    HomeLoginPage homeLoginPage = new HomeLoginPage();
    ManagerPage managerPage = new ManagerPage();
    ManagerCustomersPage managerCustomersPage = new ManagerCustomersPage();

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void addCustomerTest(Hashtable<String, String> data) throws IOException {
        homeLoginPage.openHomePageUrl(config.getProperty("testurl"))
                .clickBankManagerLoginButton()
                .clickAddCustomerButton()
                .typeFirstName(data.get("firstname"))
                .typeLastName(data.get("lastname"))
                .typePostCode(data.get("postcode"))
                .clickAddCustomerButton();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
        alert.accept();

        String customerFirstName = managerPage.clickCustomersButton()
                        .getCustomerByFirstName(data.get("firstname"));
        managerCustomersPage.assertCustumerIsAdded(data.get("firstname"), customerFirstName);
    }

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void removeCustomerTest(Hashtable<String, String> data) {
        homeLoginPage = new HomeLoginPage();
        homeLoginPage.openHomePageUrl(config.getProperty("testurl"))
                .clickBankManagerLoginButton()
                .clickAddCustomerButton()
                .typeFirstName(data.get("firstname"))
                .typeLastName(data.get("lastname"))
                .typePostCode(data.get("postcode"))
                .clickAddCustomerButton();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        managerPage.clickCustomersButton();
        managerCustomersPage.deleteCustomerByFirstName(data.get("firstname"));
        // TODO Validar que customer foi removido
    }
}
