package pages;

import base.PageBase;

public class ManagerAddCustomerPage extends PageBase {

    public ManagerAddCustomerPage typeFirstName(String firstName) {
        type("firstNameField_CSS", firstName);
        return this;
    }

    public ManagerAddCustomerPage typeLastName(String lastName) {
        type("lastNameField_XPATH", lastName);
        return this;
    }

    public ManagerAddCustomerPage typePostCode(String postCode) {
        type("postCodeField_CSS", postCode);
        return this;
    }

    public void clickAddCustomerButton() {
        click("addBtn_CSS");
    }
}
