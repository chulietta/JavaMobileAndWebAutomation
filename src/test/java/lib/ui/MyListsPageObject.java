package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            ARTICLE_BY_TITLE_TPL,
            CLOSE_SYNC_POPUP,
            SWIPE_ACTION_DELETE_BUTTON,
            REMOVE_FROM_SAVED_BUTTON,
            DELETING_FROM_LIST_MESSAGE;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveButtonByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    @Step("Make sure article with title '{article_title}' is present")
    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article with title " + article_title,
                15
        );
    }

    @Step("Make sure article with title '{article_title}' is not present")
    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                15
        );
    }

    @Step("Swipe article for deleting from saved list")
    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        if ((Platform.getInstance().isAndroid() || Platform.getInstance().isIOS())) {
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article with title " + article_title
            );
            if (Platform.getInstance().isIOS()) {
                this.waitForElementAndClick(
                        SWIPE_ACTION_DELETE_BUTTON,
                        "Cannot find and click delete button",
                        10
                );
            }
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementPresent(
                    remove_locator,
                    "Cannot find button to remove article from saves",
                    15
            );
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saves",
                    15
            );
            this.waitForElementNotPresent(
                    DELETING_FROM_LIST_MESSAGE,
                    "Deleting message still visible",
                    15
            );
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(article_title);
    }

    @Step("Click on article title in saved list")
    public void clickArticleTitleInMyList(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                article_xpath,
                "Cannot navigate to article",
                5
        );
    }

    @Step("Close popup")
    public void closeSyncPopup() {
        this.waitForElementAndClick(
                CLOSE_SYNC_POPUP,
                "Cannot close sync popup",
                5
        );
    }
}
