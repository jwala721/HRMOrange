package pageObjects;

import genericutilities.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class EmployeeListPage {
    private WebDriver driver;

    // Class-level constant: %s will be replaced by the labelText
    private static final String GENERIC_INPUT_XPATH = "//label[normalize-space(text())=\"%s\"]/ancestor::div[contains(@class,'oxd-input-group')]//input";

    // Class-level constant for date input field by label
    public static final String GENERIC_DATE_INPUT_XPATH = "//label[normalize-space(text())='%s']/ancestor::div[contains(@class,'oxd-input-group')]//input[contains(@placeholder,'yyyy-dd-mm')]";
    public static final String GENERIC_DROPDOWN_XPATH = "//label[normalize-space(text())='%s']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]";
    public static final String GENERIC_OPTION_XPATH = "//div[@role='option']//span[normalize-space(text())='%s']";
    public static final String GENERIC_TOAST_XPATH = "//p[contains(@class,'oxd-text--toast-message') and text()='%s']";



    @FindBy(xpath = "//span[text()='PIM']")
    public WebElement pimMenu;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    private List<WebElement> searchFields;

    @FindBy(xpath = "//button[text()='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//table//tr")
    private List<WebElement> employeeRows;

    @FindBy(xpath = "//button[contains(@class,'oxd-button--secondary') and .//i[contains(@class,'bi-plus')]]")
    private WebElement addButton;

    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[text()='Employee Id']/following::input[1]")
    private WebElement employeeIdInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement saveButton;

//    @FindBy(xpath = "//p[contains(@class,'oxd-text--toast-message') and text()='Successfully Saved']")
//    private WebElement successBanner;

    @FindBy(xpath = "//button[contains(@class, 'oxd-button') and @type='submit']")
    private List<WebElement> saveButtons;

    @FindBy(xpath = "//a[@class='oxd-topbar-body-nav-tab-item' and normalize-space(text())='Employee List']")
    private WebElement employeeListTab;

    // stores data used for search/verification after creation
    private String newEmployeeId;
    public String firstName;
    public String lastName;


    public EmployeeListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setInputField(String labelText, String value) {
        String xpath = String.format(GENERIC_INPUT_XPATH, labelText);
        WebElement input = driver.findElement(By.xpath(xpath));
        DriverUtils.waitForClickable(driver, input, 10); // Your wait utility
        input.clear();
        input.sendKeys(value);
    }

    public void setDateField(String labelText, String value) {
        String xpath = String.format(GENERIC_DATE_INPUT_XPATH, labelText);
        WebElement input = driver.findElement(By.xpath(xpath));
        DriverUtils.waitForClickable(driver, input, 10);
        input.clear();
        input.sendKeys(value);
    }

    public void selectDropdownOption(String labelText, String optionText) {
        // 1. Locate dropdown by label and open it
        String dropdownXpath = String.format(GENERIC_DROPDOWN_XPATH, labelText);
        WebElement dropdown = driver.findElement(By.xpath(dropdownXpath));
        DriverUtils.waitForClickable(driver, dropdown, 10);
        dropdown.click();

        // 2. Locate option by optionText and select it
        String optionXpath = String.format(GENERIC_OPTION_XPATH, optionText);
        WebElement option = driver.findElement(By.xpath(optionXpath));
        DriverUtils.waitForClickable(driver, option, 10);
        option.click();
    }

    public void navigateToPIM() {
        // Wait for PIM menu to be clickable before clicking
        DriverUtils.waitForClickable(driver, pimMenu, 15);
        pimMenu.click();
    }

    // fills all employee fields with random values and stores employeeId for later search
    public void fillRandomEmployeeDetails() {
        Random rand = new Random();

        // One letter + 4 digits (total 5 chars)
        char letter = (char) ('A' + rand.nextInt(26));
        int num = rand.nextInt(10000); // 0 to 9999
        newEmployeeId = letter + String.format("%04d", num);

        firstName = "TestFirst" + rand.nextInt(1000);
        lastName = "TestLast" + rand.nextInt(1000);

        DriverUtils.waitForClickable(driver, employeeIdInput, 10);
        employeeIdInput.clear();
        employeeIdInput.sendKeys(newEmployeeId);

        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);

        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void assertToastMessage(WebDriver driver, String message, int seconds) {
        String xpath = String.format(GENERIC_TOAST_XPATH, message);
        By toastLocator = By.xpath(xpath);

        // Wait for toast to appear using generic method
        DriverUtils.waitForVisibility(driver, toastLocator, seconds);

        // Locate and assert toast banner
        WebElement toastBanner = driver.findElement(toastLocator);
        Assert.assertTrue(toastBanner.isDisplayed(), "Toast banner not displayed!");
        Assert.assertTrue(toastBanner.getText().contains(message), "Toast banner text mismatch!");
    }

    public void assertEmployeeInTable() {
        // Wait for table to be visible (after search)
        DriverUtils.waitForVisibility(driver, By.xpath("//div[contains(@class,'oxd-table-body')]"), 10);

        // XPath for the employee row matching all key data
        String rowXpath = String.format(
                "//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-row--clickable') and " +
                        "div[1][normalize-space(text())='%s'] and " +
                        "div[2][contains(normalize-space(text()),'%s')] and " +
                        "div[3][contains(normalize-space(text()),'%s')]]",
                newEmployeeId, firstName, lastName
        );

        // Wait for the row to appear
        DriverUtils.waitForVisibility(driver, By.xpath(rowXpath), 10);

        // Locate and assert
        WebElement employeeRow = driver.findElement(By.xpath(rowXpath));
        Assert.assertTrue(employeeRow.isDisplayed(), "Employee row not found in table!");

        System.out.println("Verified employee present: " +
                newEmployeeId + " | " + firstName + " | " + lastName);
    }



    // end-to-end: create employee, search by random ID, and verify
    public void createSearchAndVerifyEmp() {
        // Navigate to PIM
        navigateToPIM();
        // Wait for Add button and click
        DriverUtils.waitForClickable(driver, addButton, 10);
        addButton.click();
        // Fill employee details
        fillRandomEmployeeDetails();
        // Click Save
        DriverUtils.waitForClickable(driver, saveButton, 10);
        saveButton.click();
        assertToastMessage(driver, "Successfully Saved", 10);

        System.out.println("Employee created with ID: " + newEmployeeId);
        DriverUtils.waitForElement(4);
        // Example: Setting 'Other Id'
        setInputField("Other Id", "BRwiee");
        DriverUtils.waitForElement(4);
        // Example: Setting "Driver's License Number"
        setInputField("Driver's License Number", "DL12345");
        //datePicker
        setDateField("Date of Birth", "1990-01-15");
        setDateField("License Expiry Date", "2025-12-23");

        selectDropdownOption("Nationality", "Indian");
        selectDropdownOption("Marital Status", "Single");
        saveButtons.get(0).click();
        assertToastMessage(driver, "Successfully Updated", 10);
        DriverUtils.waitForElement(4);
//        selectDropdownOption("Blood Type", "O+");
        DriverUtils.scrollToElement(driver, saveButton);
        saveButtons.get(1).click();
        assertToastMessage(driver, "Successfully Saved", 10);

        employeeListTab.click(); // Opens the Employee List tab
        DriverUtils.waitForElement(4);
        setInputField("Employee Name", firstName+" "+lastName);

        searchFields.get(0).sendKeys(Keys.ENTER);
        DriverUtils.waitForElement(4);
        System.out.println("Full Employee Name: " + firstName + " " + lastName);
        DriverUtils.waitForElement(4);
//        setInputField("Employee Name", newEmployeeId);
        System.out.println("Employee ID: " + newEmployeeId);
        DriverUtils.waitForClickable(driver, saveButton, 10);
        saveButton.click();
        // Verification step: Assert the employee is in the list
        assertEmployeeInTable();

    }
}
