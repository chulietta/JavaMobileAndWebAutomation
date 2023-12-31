package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static lib.ui.NavigationUI.HOME_LINK;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            DESCRIPTION,
            FOOTER_ELEMENT,
            SAVE_TO_MY_LIST_BUTTON,
            ADD_TO_LIST_LINK,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            MY_LIST_TITLE_TPL,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    private static String getMyListByTitle(String title) {
        return MY_LIST_TITLE_TPL.replace("{TITLE}", title);
    }

    @Step("Waiting for title on article page")
    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find article title", 20);
    }

    @Step("Waiting for description on article page")
    public WebElement waitForDescriptionElement() {
        return this.waitForElementPresent(DESCRIPTION, "Cannot find article description", 20);
    }

    @Step("Get article title")
    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        screenshot(this.takeScreenshot("article_title"));
        if (Platform.getInstance().isMW()) {
            return title_element.getText();
        } else {
            return title_element.getAttribute("name");
        }
    }

    public String getArticleDescription() {
        WebElement description_element = waitForDescriptionElement();
        return description_element.getAttribute("name");
    }

    @Step("Swipe article to the footer")
    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find end of the article",
                    60
            );
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find end of the article",
                    60
            );
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find end of the article",
                    60
            );
        }
    }

    @Step("Add article to my list")
    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                SAVE_TO_MY_LIST_BUTTON,
                "Cannot find button to save article",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_LIST_LINK,
                "Cannot find Add to List link",
                5
        );
        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into article text input",
                15
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press Ok button",
                5
        );
    }

    @Step("Add article to my list")
    public void addArticleToMySaved() {
        this.waitForElementAndClick(
                SAVE_TO_MY_LIST_BUTTON,
                "Cannot find button to save article",
                15
        );
    }

    @Step("Add article to my list")
    public void addArticleToMySavedList() {
        this.removeArticleFromMyListIfItAdded();
        this.waitForElementAndClick(
                SAVE_TO_MY_LIST_BUTTON,
                "Cannot click button to save article",
                15
        );
    }

    @Step("Remove article from my list")
    public void removeArticleFromMyListIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from my list",
                    5
            );
            this.waitForElementPresent(
                    SAVE_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to my list after removing",
                    5
            );
        }
    }

    @Step("Close article")
    public void closeArticle() {
        this.waitForElementAndClick(
                HOME_LINK,
                "Cannot find 'W' button to close article",
                5
        );
    }

    @Step("Add article to existing list")
    public void addArticleToExistingList(String name_of_folder) {
        String my_list_xpath = getMyListByTitle(name_of_folder);
        this.waitForElementAndClick(
                SAVE_TO_MY_LIST_BUTTON,
                "Cannot find button to save article",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_LIST_LINK,
                "Cannot find Add to List link",
                5
        );
        this.waitForElementAndClick(
                my_list_xpath,
                "Cannot choose list with title " + name_of_folder,
                5
        );
    }

    @Step("Assert title is present")
    public void assertTitlePresent() {
        this.assertElementPresent(
                TITLE,
                "Cannot find article description immediately after open"
        );
    }

}
