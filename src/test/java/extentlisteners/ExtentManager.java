package extentlisteners;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;


import base.TestBase;

public class ExtentManager {

    private static ExtentReports extent;
    public static String fileName;

    public static void createInstance(String fileName) {

    }

    public static void captureScreenshot() throws IOException {

        Date d = new Date();
        fileName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";

        File screeshot = ((TakesScreenshot)  TestBase.driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screeshot, new File("./reports/"+fileName));
    }

    public static void captureElementScreenshot(WebElement element) throws IOException {

        Date d = new Date();
        String fileName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";

        File screeshot = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screeshot, new File(".//screenshot//"+"Element_"+fileName));
    }

}