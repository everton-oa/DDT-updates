package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utilities.DriverFactory;
import utilities.ExcelReader;
import utilities.ExtentManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static utilities.DriverFactory.getDriver;

public class TestBase {

    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/excel/testdata.xlsx");
    public static WebDriverWait wait;
    public static ExtentTest test;
    public ExtentReports rep = ExtentManager.getInstance();

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    @BeforeSuite
    public void initFiles() {
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/Config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            config.load(fis);
            log.info("Config loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/OR.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            OR.load(fis);
            log.info("OR loaded\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void setUp() {
//        getDriver().manage().window().maximize();
        getDriver().manage().window().setPosition(new Point(2000, 1));
        getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
        wait = new WebDriverWait(getDriver(), 2);
        log.info("URL opened");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.killDriver();
        log.debug("Test completed\n");
        System.out.print(ANSI_GREEN + "###################################################################### \n\n" + ANSI_RESET);
    }
}

// TODO implementar novos assertions
// TODO adicionar informacoes ao readme - como clonar e executar o projeto
// TODO adicionar informacoes sobre como acessar os relatorios / logs depois de executar os testes
// TODO criar documentação para os metodos e para que eles servem. adicionar comentarios sobre o que é necessario alterar para utilizar em outro projeto ex: paths ou variaveis