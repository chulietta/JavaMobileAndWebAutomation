package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MY_LIST_OPEN_LINK,
            HOME_LINK,
            OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickMyList() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementsWithFewAttempts(
                    MY_LIST_OPEN_LINK,
                    "Cannot find navigation to My list",
                    15
            );
        } else {
            this.waitForElementAndClick(
                    MY_LIST_OPEN_LINK,
                    "Cannot find navigation to My list",
                    5
            );
        }
    }

    public void openNavigation() {
        if (Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 15);
        } else {
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
