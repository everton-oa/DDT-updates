package pages;

import base.PageBase;

public class HomeLoginPage extends PageBase {

    public HomeLoginPage openHomePageUrl(String pageUrl) {
        openPage(pageUrl);
        return this;
    }

    public void clickCustomerLoginButton(String locator) {
        click(locator);
    }

    public ManagerPage clickBankManagerLoginButton() {
        click("bankManagerLoginBtn_CSS");
        return new ManagerPage();
    }
}
