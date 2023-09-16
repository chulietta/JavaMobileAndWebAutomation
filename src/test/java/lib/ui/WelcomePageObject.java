package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    private final static String
            STEP_LEARN_MORE_LINK = "name:Learn more about Wikipedia",
            STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
            STEP_ADD_OR_EDIT_PREFERRED_LANGUAGES_LINK = "name:Add or edit preferred languages",
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "name:Learn more about data collected",
            NEXT_LINK = "name:Next",
            GET_STARTED_BUTTON = "name:Get started",
            SKIP = "xpath://XCUIElementTypeButton[@name='Skip']";


    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Check \"More\" link is present")
    public void waitForLearnMoreLink() {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    @Step("Check \"New Ways To Explore\" text is present")
    public void waitForNewWaysToExploreText() {
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' text", 10);
    }

    @Step("Check \"Add or Edit Preferred Language\" text is present")
    public void waitForAddOrEditPreferredLanguagesText() {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANGUAGES_LINK, "Cannot find 'Add or edit preferred languages' link", 10);
    }

    @Step("Check \"Learn More About Data\" text is present")
    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Cannot find 'Learn more about data collected' link", 10);
    }

    @Step("Click \"Next\" button")
    public void clickNextButton() {
        this.waitForElementAndClick(NEXT_LINK, "Cannot find and click 'Next' link", 10);
    }

    @Step("Click \"Get Started\" button")
    public void clickGetStartedButton() {
        this.waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find and click 'Get started' button", 10);
    }

    @Step("Click \"Skip\" button")
    public void clickSkip() {
        this.waitForElementAndClick(SKIP, "Cannot find and click skip button", 10);
    }
}
