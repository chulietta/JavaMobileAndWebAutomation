package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SKIP_ELEMENT,
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULTS_LIST,
            SEARCH_RESULT_BY_ORDER_NUMBER_TPL,
            SEARCH_FROM_ARTICLE_BUTTON;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    //TEMPLATES METHODS
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByOrderNumber(String number) {
        return SEARCH_RESULT_BY_ORDER_NUMBER_TPL.replace("{NUMBER}", number);
    }

    private static String getResultSearchElementByTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    //TEMPLATES METHODS

    @Step("Waiting for article with title and description")
    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(search_result_xpath, "Cannot find result with title " + title + " and description " + description, 15);
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 10);
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Cancel is still present", 5);
    }

    @Step("Click button to cancel search result")
    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    @Step("Initializing the search field")
    public void initSearchInput() {
        this.waitForElementAndClick(SKIP_ELEMENT, "Cannot find Skip button", 10);
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find Search Wikipedia input", 15);
    }

    @Step("Click into the search line")
    public void clickSearchInput() {
        if (Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(SEARCH_FROM_ARTICLE_BUTTON, "Cannot find and click search button", 15);
        }
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find Search Wikipedia input", 5);
    }

    @Step("Typing '{search_line}' to search line")
    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find search input", 20);
    }

    @Step("Waiting for search result and select an article by substring in article title")
    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find result with substring " + substring, 15);
    }

    @Step("Waiting for search cancel button to disappear")
    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    @Step("Getting amount of founded articles")
    public int getAmountOfFoundedArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by request",
                15
        );
        return this.getAmountElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Waiting for empty results label")
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result label by the request", 15);
    }

    @Step("Making sure there are no results of search")
    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We found some results by request"
        );
    }

    @Step("Making sure there is a placeholder in search line")
    public void assertSearchInputPlaceholder(String placeholder) {
        this.assertElementHasText(
                SEARCH_INPUT,
                placeholder,
                "Search input has wrong placeholder!",
                5
        );
    }

    @Step("Making sure there is a list of search results")
    public void waitForSearchListIsPresented() {
        this.waitForElementPresent(
                SEARCH_RESULTS_LIST,
                "Search results are empty",
                15
        );
    }

    @Step("Making sure there is no list of search results")
    public void waitForSearchListIsNotPresented() {
        this.waitForElementNotPresent(
                SEARCH_RESULTS_LIST,
                "Search results not cleared",
                15
        );
    }

    @Step("Making sure that result has searched word in title")
    public void assertSearchResultContainsSearchRequest(String order_number) {
        String search_result_xpath = getResultSearchElementByOrderNumber(order_number);
        String search_request = "Java";
        this.assertElementContainsText(
                search_result_xpath,
                search_request,
                "Search result " + order_number + " not includes " + search_request,
                15
        );
    }
}
