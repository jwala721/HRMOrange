package hrm.login;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;
import pageObjects.EmployeeListPage;
import pageObjects.LoginPage;
import genericutilities.ConfigLoader;

public class EmployeeListTest extends BaseTest {
    @Test
    public void verifyEmployeePresent() {
        ExtentTest test = extent.createTest("Verify Employee Present");

        try {
            // Login
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(ConfigLoader.get("username"), ConfigLoader.get("password"));

            System.out.println("Login completed successfully");

            // Create employee
            EmployeeListPage employeeListPage = new EmployeeListPage(driver);
            employeeListPage.createSearchAndVerifyEmp();

            System.out.println("Employee creation completed");
            test.log(Status.PASS, "Employee created successfully.");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e; // Re-throw to mark test as failed
        }
    }
}
