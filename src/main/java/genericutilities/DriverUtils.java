package genericutilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverUtils {

    // Set implicit wait for the entire WebDriver session
    public static void setImplicitWait(WebDriver driver, int seconds) {
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(seconds));
    }

    // Generic explicit wait for element visibility by WebElement
    public static void waitForVisibility(WebDriver driver, WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Generic explicit wait for element visibility by locator
    public static void waitForVisibility(WebDriver driver, By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait for element to be clickable
    public static void waitForClickable(WebDriver driver, WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Wait for a custom condition
    public static void waitForCondition(WebDriver driver, ExpectedCondition<?> condition, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(seconds));
        wait.until(condition);
    }

    // Wait for element for a given number of seconds
    public static void waitForElement(int seconds) {
        try {
            Thread.sleep(seconds * 1000L); // L ensures it's a long
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Optionally, log or throw custom unchecked exception
        }
    }

    // Scrolls the page to bring the given WebElement into view
//    public void scrollToElement(WebDriver driver, WebElement element) {
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
//    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", element
        );
    }



}
