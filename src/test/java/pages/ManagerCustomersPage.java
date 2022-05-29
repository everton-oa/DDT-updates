package pages;

import base.PageBase;

import java.io.IOException;

public class ManagerCustomersPage extends PageBase {

    public String getCustomerByFirstName(String firstName) {
        return getTextCell("First Name", firstName, "First Name", "customersTable_XPATH");
    }

    public void deleteCustomerByFirstName(String firstName) {
        clickCellButton("First Name", firstName, "Delete Customer", "customersTable_XPATH");
    }

    public void assertCustumerIsAdded(String expectedFirstname, String actualFirstname) throws IOException {
        assertStringEquals(expectedFirstname, actualFirstname);

    }
}
