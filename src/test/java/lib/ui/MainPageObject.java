package lib.ui;

import com.sun.rmi.rmid.ExecPermission;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lib.Platform;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Executable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected WebDriver driver;

    public MainPageObject(RemoteWebDriver driver) {
        this.driver = driver;
    }

    @Step("Wait for element is present")
    public WebElement waitForElementPresent(String locator, String error_message, long timeInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @Step("Wait for element is present")
    public WebElement waitForElementPresent(String locator, String error_message) {
        return waitForElementPresent(locator, error_message, 10);
    }

    @Step("Wait for element and click on it")
    public WebElement waitForElementAndClick(String locator, String error_message, long timeInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeInSeconds);
        element.click();
        return element;
    }

    @Step("Wait for element and send keys")
    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeInSeconds);
        element.sendKeys(value);
        return element;
    }

    @Step("Wait for element is not present")
    public boolean waitForElementNotPresent(String locator, String error_message, long timeInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    @Step("Wait for element and clear it")
    public WebElement waitForElementAndClear(String locator, String error_message, long timeInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeInSeconds);
        element.clear();
        return element;
    }

    @Step("Make sure element has text '{expected_value}'")
    public boolean assertElementHasText(String locator, String expected_value, String error_message, long timeInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.withMessage(error_message + "\n");
        if (Platform.getInstance().isMW()) {
            return wait.until(ExpectedConditions.attributeContains(by, "placeholder", expected_value));
        }
        return wait.until(ExpectedConditions.textToBe(by, expected_value));
    }

    @Step("Make sure element contains text '{expected_value}'")
    public boolean assertElementContainsText(String locator, String expected_value, String error_message, long timeInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.withMessage(error_message + "\n");
        if (Platform.getInstance().isMW()) {
            return wait.until(ExpectedConditions.textToBe(by, expected_value));
        }
        return wait.until(ExpectedConditions.attributeContains(by, "name", expected_value));
    }

    @Step("Swipe article up (works only for iOS and Android)")
    public void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            action
                    .press(PointOption.point(x, start_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x, end_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

    @Step("Swipe article up quickly (works only for iOS and Android)")
    public void swipeUpQuick() {
        swipeUp(200);
    }

    @Step("Swipe article till element not present (works only for iOS and Android)")
    public void swipeUpToFindElement(String locator, String error_message, int max_swipes) {
        By by = this.getLocatorByString(locator);
        int already_swipe = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swipe > max_swipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swipe;
        }
    }

    @Step("Scroll article up (works only for Mobile Web)")
    public void scrollWebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Scroll article till element not present (works only for Mobile Web)")
    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_swipes) {
        int already_swipe = 0;
        WebElement element = waitForElementPresent(locator, error_message);
        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++already_swipe;
            if (already_swipe > max_swipes) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }

    @Step("Swipe article till element appear (works only for iOS and Android)")
    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes) {
        int already_swipe = 0;
        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (already_swipe > max_swipes) {
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swipe;
        }
    }

    @Step("make sure element is on the screen")
    public boolean isElementLocatedOnTheScreen(String locator) {
        int element_location_by_y = this.waitForElementPresent(locator, "Cannot find element by locator", 10).getLocation().getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    @Step("Swipe element to the left (works only for iOS and Android)")
    public void swipeElementToLeft(String locator, String error_message) {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(
                    locator,
                    error_message,
                    10);
            int left_x = element.getLocation().getX();
            int right_x = left_x + element.getSize().getWidth();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action
                    .press(PointOption.point(right_x, middle_y))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                    .moveTo(PointOption.point(left_x, middle_y))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    @Step("Get amount of elements")
    public int getAmountElements(String locator) {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator) {
        return getAmountElements(locator) > 0;
    }

    @Step("Click on element")
    public void tryClickElementsWithFewAttempts(String locator, String error_message, int amount_of_attempts) {
        int current_attempts = 0;
        boolean needMoreAttempts = true;
        while (needMoreAttempts) {
            try {
                this.waitForElementAndClick(locator, error_message, 3);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (current_attempts > amount_of_attempts) {
                    this.waitForElementAndClick(locator, error_message, 3);
                }
            }
            ++current_attempts;
        }
    }

    @Step("Chech element not present")
    public void assertElementNotPresent(String locator, String error_message) {
        int amountOfElements = getAmountElements(locator);
        if (amountOfElements > 0) {
            String default_message = "An element" + locator + " supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    @Step("Check element is present")
    public void assertElementPresent(String locator, String error_message) {
        int amountOfElements = getAmountElements(locator);
        if (amountOfElements == 0) {
            String default_message = "An element " + locator + " supposed to be present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    @Step("Wait for element and get attribute")
    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private By getLocatorByString(String locator_with_type) {
        String[] exploded_locator = locator_with_type.split(Pattern.quote(":"), 2);
        String by_type = exploded_locator[0];
        String locator = exploded_locator[1];

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else if (by_type.equals("name")) {
            return By.name(locator);
        } else if (by_type.equals("css")) {
            return By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator " + locator_with_type);
        }
    }

    @Step("Take screenshot")
    public String takeScreenshot(String name) {
        TakesScreenshot ts = (TakesScreenshot) this.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/" + name + "_screenshot.png";
        try {
            FileUtils.copyFile(source, new File(path));
            System.out.println("The screenshot was taken:" + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }

    @Attachment
    public static byte[] screenshot(String path) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return bytes;
    }
}
