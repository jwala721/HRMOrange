package hrm.login;

import com.aventstack.extentreports.ExtentReports;
import genericutilities.ExtentReportManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import genericutilities.ConfigLoader;


public class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(ConfigLoader.get("baseUrl"));
        extent = ExtentReportManager.getInstance();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (extent != null) {
            extent.flush();
        }
    }
}
