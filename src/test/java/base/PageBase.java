package base;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import utilities.TestUtil;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;
import static utilities.DriverFactory.getDriver;

public class PageBase {

    /*
     * Keyword actions
     *
     */

    public void openPage(String pageUrl) {
        getDriver().get(pageUrl);
    }

    public void click(String locator) {
        if (locator.endsWith("_CSS")) {
            getDriver().findElement(By.cssSelector(TestBase.OR.getProperty(locator))).click();
        } else if (locator.endsWith("_XPATH")) {
            getDriver().findElement(By.xpath(TestBase.OR.getProperty(locator))).click();
        } else if (locator.endsWith("_ID")) {
            getDriver().findElement(By.id(TestBase.OR.getProperty(locator))).click();
        }
        TestBase.test.log(LogStatus.INFO, "Clicked on " + locator);
        TestBase.log.debug("Clicked on " + locator);
        System.out.print(TestBase.ANSI_GREEN + "Clicked on " + locator + "\n" + TestBase.ANSI_RESET);
    }

    public void type(String locator, String value) {
        if (locator.endsWith("_CSS")) {
            getDriver().findElement(By.cssSelector(TestBase.OR.getProperty(locator))).clear();
            getDriver().findElement(By.cssSelector(TestBase.OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_XPATH")) {
            getDriver().findElement(By.xpath(TestBase.OR.getProperty(locator))).clear();
            getDriver().findElement(By.xpath(TestBase.OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_ID")) {
            getDriver().findElement(By.id(TestBase.OR.getProperty(locator))).clear();
            getDriver().findElement(By.id(TestBase.OR.getProperty(locator))).sendKeys(value);
        }
        TestBase.test.log(LogStatus.INFO, "Typed " + value + " on " + locator);
        TestBase.log.debug("Typed " + value + " on " + locator);
        System.out.print(TestBase.ANSI_GREEN + "Typed " + value + " on " + locator + "\n" + TestBase.ANSI_RESET);
    }

    static WebElement dropdown;

    public void selectDropDown(String locator, String value) {
        if (locator.endsWith("_CSS")) {
            dropdown = getDriver().findElement(By.cssSelector(TestBase.OR.getProperty(locator)));
        } else if (locator.endsWith("_XPATH")) {
            dropdown = getDriver().findElement(By.xpath(TestBase.OR.getProperty(locator)));
        } else if (locator.endsWith("_ID")) {
            dropdown = getDriver().findElement(By.id(TestBase.OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);
        select.selectByVisibleText(value);

        TestBase.test.log(LogStatus.INFO, "Selected " + value + " from " + locator);
        TestBase.log.debug("Selected " + value + " from " + locator);
        System.out.print(TestBase.ANSI_GREEN + "Selected " + value + " from " + locator + "\n" + TestBase.ANSI_RESET);
    }

    public boolean isElementPresent(By by) {
        try {
            getDriver().findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // TODO este metodo está dentro da classe testutil
    public void isTestRunnable(String runMode) {
        if (runMode.equalsIgnoreCase("n")) {
            throw new SkipException("");
        }
    }

    /*
     * Assertions
     *
     */
    public void verifyEquals(String actual, String expected) throws IOException {
        try {
            assertEquals(actual, expected);
            TestBase.test.log(LogStatus.PASS, "Verification successfully executed - Actual result: " + actual + " => Expected result: " + expected);
            TestBase.log.debug("Verification successfully executed - Actual result: " + actual + " => Expected result: " + expected);
            System.out.print(TestBase.ANSI_GREEN + "Verification successfully executed - Actual result: " + actual + " => Expected result: " + expected + "\n" + TestBase.ANSI_RESET);
        } catch (Throwable failure) {
            TestBase.test.log(LogStatus.WARNING, "Verification failed - " + failure.getMessage());
            TestBase.log.debug("Verification failed - " + failure.getMessage());
            System.out.print(TestBase.ANSI_RED + "Verification failed - " + failure.getMessage() + "\n" + TestBase.ANSI_RESET);

            TestUtil.captureScreenShoot();
        }
    }

    public void assertStringEquals(String actual, String expected) throws IOException {
        try {
            assertEquals(actual, expected);
            TestBase.test.log(LogStatus.PASS, "Verification successfully executed - Actual result: " + actual + " => Expected result: " + expected);
            TestBase.log.debug("Verification successfully executed - Actual result: " + actual + " => Expected result: " + expected);
            System.out.print(TestBase.ANSI_GREEN + "Validation successfully executed - Actual result: " + actual + " => Expected result: " + expected + "\n" + TestBase.ANSI_RESET);
        } catch (Throwable failure) {
            TestBase.test.log(LogStatus.WARNING, "Verification failed - " + failure.getMessage());
            TestBase.log.debug("Verification failed - " + failure.getMessage());
            System.out.print(TestBase.ANSI_RED + "Validation failed - " + failure.getMessage() + "\n" + TestBase.ANSI_RESET);

            TestUtil.captureScreenShoot();
        }
    }

    /*
     * Tabelas
     *
     */
    // get to one specific table cell
    public WebElement getTableCell(String col, String value, String colValue, String tableLocator) {
        WebElement table = getDriver().findElement(By.xpath(TestBase.OR.getProperty(tableLocator)));
        int idCol = getColIndex(col, table);
        int idRow = getRowIndex(value, table, idCol);

        int idColumn = getColIndex(colValue, table);

        return table.findElement(By.xpath("./tbody/tr[" + idRow + "]/td[" + idColumn + "]"));
    }

    // return the number of one specific row
    public int getRowIndex(String value, WebElement table, int idCol) {
        List<WebElement> rows = table.findElements(By.xpath("./tbody/tr/td[" + idCol + "]"));

        int idRow = -1;
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getText().equalsIgnoreCase(value)) {
                idRow = i + 1;
                break;
            }
        }
        return idRow;
    }

    // return the number of one specific column
    public int getColIndex(String col, WebElement table) {
        List<WebElement> cols = table.findElements(By.xpath(".//thead/tr/td"));
        int idCol = -1;
        for (int i = 0; i < cols.size(); i++) {
            if (cols.get(i).getText().equalsIgnoreCase(col)) {
                idCol = i + 1;
                break;
            }
        }
        return idCol;
    }

    public String getTextCell(String col, String value, String colValue, String tableLocator) {
        WebElement cell = getTableCell(col, value, colValue, tableLocator);
        return cell.getText();
    }

    public void clickCellButton(String col, String value, String colValue, String tableLocator) {
        WebElement cell = getTableCell(col, value, colValue, tableLocator);
        cell.click();
    }
}
