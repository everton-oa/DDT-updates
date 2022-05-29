package utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import base.TestBase;

import static utilities.DriverFactory.getDriver;

public class TestUtil extends TestBase {
	
	public static String screeshotPath;
	public static String screeshotName;
	
	public static void captureScreenShoot() throws IOException {

		File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		screeshotName = simpleDateFormat.format(new Date())+".jpg";
		screeshotPath = System.getProperty("user.dir") + "/target/surefire-reports/html/screenshot/";
		// TODO alterar separators para funcionar em qualquer sistema operacional
//		screeshotPath = System.getProperty("user.dir") +File.separator+"target/surefire-reports/html/screenshot/";
		FileUtils.copyFile(scrFile, new File(screeshotPath + screeshotName));

		test.log(LogStatus.INFO, "Screenshot captured");
		test.log(LogStatus.INFO, test.addScreenCapture("screenshot/" + TestUtil.screeshotName));
		log.debug("Screenshot captured. -> target/surefire-reports/html/screenshot/" + screeshotName);
		System.out.print(ANSI_RED + "Screenshot captured. -> target/surefire-reports/html/screenshot/" + screeshotName + "\n" + ANSI_RESET);
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(Method method) {
		String sheetName = method.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		Object[][] data = new Object[rows - 1][1];
		Hashtable<String, String> table = null;
		
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<String, String >();
			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}
		}
		return data;
	}
	
	public static boolean isTestCaseRunnable(String testName, ExcelReader excel) {
		String sheetName = "testSuite";
		int rows = excel.getRowCount(sheetName);
		
		for (int rNum=2; rNum<=rows; rNum++) {
			String testCase = excel.getCellData(sheetName, "tcid", rNum);
			if (testCase.equalsIgnoreCase(testName)) {
				String runMode = excel.getCellData(sheetName, "runmode", rNum);
				if (runMode.equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}
		}
		return false;
	}
}
