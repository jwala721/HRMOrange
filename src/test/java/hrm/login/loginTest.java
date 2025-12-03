package hrm.login;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import genericutilities.ConfigLoader;

public class loginTest extends BaseTest {

    @Test
    public void testValidLogin() {
        ExtentTest test = extent.createTest("Valid Login - " + ConfigLoader.get("username"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigLoader.get("username"), ConfigLoader.get("password")); // use login method

        if (loginPage.isErrorMessageDisplayed()) {
            test.log(Status.FAIL, "Login failed: " + loginPage.getErrorMessage());
        } else {
            test.log(Status.PASS, "Login successful for user: " + ConfigLoader.get("username"));
        }
    }

    @Test
    public void testInvalidLogin() {
        ExtentTest test = extent.createTest("Invalid Login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUser", "wrongPass"); // use login method

        if (loginPage.isErrorMessageDisplayed()) {
            test.log(Status.PASS, "Login failed as expected: " + loginPage.getErrorMessage());
        } else {
            test.log(Status.FAIL, "Unexpected login success with invalid credentials");
        }
    }
}
