package pages;

import base.PageBase;

public class ManagerOpenAccountPage extends PageBase {

    public ManagerOpenAccountPage selectCustomerDropDown(String customer) {
        selectDropDown("customerNameDd_ID", customer);
        return this;
    }

    public ManagerOpenAccountPage selectCurrencyDropDown(String currency) {
        selectDropDown("currencyDd_ID", currency);
        return this;
    }

    public void clickProcessButton() {
        click("processBtn_CSS");
    }
}
